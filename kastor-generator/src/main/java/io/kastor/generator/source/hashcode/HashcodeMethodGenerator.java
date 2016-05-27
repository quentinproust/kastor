package io.kastor.generator.source.hashcode;


import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.source.AbstractMethodGenerator;
import io.kastor.generator.source.FieldUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class HashcodeMethodGenerator extends AbstractMethodGenerator {
   private Set<String> fields;

   public HashcodeMethodGenerator(TypeElement element, JavaClassSource javaClass) {
      super(element, javaClass);

      getFieldsToInclude(element);
   }

   private void getFieldsToInclude(TypeElement element) {
      KastorIdentity annotation = element.getAnnotation(KastorIdentity.class);

      fields = new HashSet<>();
      fields.addAll(Arrays.asList(annotation.includeFields()));
      if (fields.isEmpty()) {
         fields.addAll(FieldUtils.getFieldNames(element));
      }
      fields.removeAll(Arrays.asList(annotation.excludeFields()));
   }

   @Override
   protected MethodSource<JavaClassSource> getMethodSignature(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> method = javaClass.addMethod()
            .setPublic()
            .setStatic(true)
            .setReturnType("int")
            .setName("hashCode");

      method.addParameter(element.getSimpleName().toString(), "o");
      return method;
   }

   @Override
   protected void addMethodStart() {
      addLine("if (o == null) { return 0; }");
      addLine("int result = 1;");
   }

   @Override
   protected void addMethodEnd() {
      addLine("return result;");
   }

   @Override
   protected Stream<? extends Element> getFieldStream(Element element) {
      return super.getFieldStream(element).filter(x -> fields.contains(x.toString()));
   }

   @Override
   protected Optional<String> getFieldOperation(Element field) {
      return FieldHashcodeFactory.get(field).getFieldOperation(field)
            .map(c -> "result = 31 * result + " + c + ";");
   }

}
