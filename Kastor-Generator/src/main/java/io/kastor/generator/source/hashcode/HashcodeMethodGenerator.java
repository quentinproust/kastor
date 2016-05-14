package io.kastor.generator.source.hashcode;


import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HashcodeMethodGenerator {

   public void generateFor(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> method = createMethodSignature(element, javaClass);

      addNullObjectHashCode(method);

      addFieldHashCodes(element, method);
   }

   private MethodSource<JavaClassSource> createMethodSignature(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> method = javaClass.addMethod()
            .setPublic()
            .setStatic(true)
            .setReturnType("int")
            .setName("hashCode");

      method.addParameter(element.getSimpleName().toString(), "o");
      return method;
   }

   private void addNullObjectHashCode(MethodSource<JavaClassSource> equalityMethod) {
      equalityMethod.setBody("if (o == null) { return 0; }");
   }

   private void addFieldHashCodes(TypeElement element, MethodSource<JavaClassSource> equalityMethod) {
      List<String> fieldComparisons = createFieldComparisons(element);
      String body = equalityMethod.getBody() + "int result = 1;" + String.join(" ", fieldComparisons) + " return result;";
      equalityMethod.setBody(body);
   }

   private List<String> createFieldComparisons(TypeElement element) {
      return element.getEnclosedElements().stream()
            .filter(e -> e.getKind() == ElementKind.FIELD)
            .map(field -> FieldHashcodeFactory.get(field).getFieldHashcode(field))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(c -> "result = 31 * result + " + c + ";")
            .collect(Collectors.toList());
   }
}
