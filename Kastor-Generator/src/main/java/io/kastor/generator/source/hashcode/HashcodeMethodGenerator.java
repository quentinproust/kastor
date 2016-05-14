package io.kastor.generator.source.hashcode;


import io.kastor.generator.source.AbstractMethodGenerator;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Optional;

public class HashcodeMethodGenerator extends AbstractMethodGenerator {

   public HashcodeMethodGenerator(TypeElement element, JavaClassSource javaClass) {
      super(element, javaClass);
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
   protected Optional<String> getFieldOperation(Element field) {
      return FieldHashcodeFactory.get(field).getFieldHashcode(field)
            .map(c -> "result = 31 * result + " + c + ";");
   }

}
