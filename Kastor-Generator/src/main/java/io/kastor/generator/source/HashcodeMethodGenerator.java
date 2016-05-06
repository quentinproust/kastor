package io.kastor.generator.source;


import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.text.MessageFormat;
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
      equalityMethod.setBody(equalityMethod.getBody() + " return " + String.join(" + ", fieldComparisons) + ";");
   }

   private List<String> createFieldComparisons(TypeElement element) {
      String hash = "Objects.hashCode(o.{0})";

      return element.getEnclosedElements().stream()
            .filter(e -> e.getKind() == ElementKind.FIELD)
            .map(e -> getFieldGetter(element, e))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(getter -> MessageFormat.format(hash, getter))
            .collect(Collectors.toList());
   }

   private Optional<String> getFieldGetter(TypeElement element, Element field) {
      return element.getEnclosedElements().stream()
            .filter(x -> x.getKind() == ElementKind.METHOD)
            .filter(x -> isFieldGetter(field, x))
            .map(x -> x.getSimpleName() + "()")
            .findFirst();
   }

   private boolean isFieldGetter(Element field, Element method) {
      String methodName = method.getSimpleName().toString();
      String getMethodName = "get" + field.getSimpleName().toString();
      String isMethodName = "is" + field.getSimpleName().toString();

      return methodName.equalsIgnoreCase(getMethodName) || methodName.equalsIgnoreCase(isMethodName);
   }
}
