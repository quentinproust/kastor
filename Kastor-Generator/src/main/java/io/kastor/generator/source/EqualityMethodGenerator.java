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

public class EqualityMethodGenerator {

   public void generateFor(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> equalityMethod = createEqualsMethodSignature(element, javaClass);

      addObjectReferenceEqualityVerification(equalityMethod);

      addFieldComparisons(element, equalityMethod);
   }

   private MethodSource<JavaClassSource> createEqualsMethodSignature(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> equalityMethod = javaClass.addMethod()
            .setPublic()
            .setStatic(true)
            .setReturnType("boolean")
            .setName("equals");

      equalityMethod.addParameter(element.getSimpleName().toString(), "a");
      equalityMethod.addParameter(element.getSimpleName().toString(), "b");
      return equalityMethod;
   }

   private void addObjectReferenceEqualityVerification(MethodSource<JavaClassSource> equalityMethod) {
      equalityMethod.setBody("if (a == b) { return true; }" +
            "if (a == null || b == null) { return false; }");
   }

   private void addFieldComparisons(TypeElement element, MethodSource<JavaClassSource> equalityMethod) {
      List<String> fieldComparisons = createFieldComparisons(element);
      equalityMethod.setBody(equalityMethod.getBody() + " return " + String.join(" && ", fieldComparisons) + ";");
   }

   private List<String> createFieldComparisons(TypeElement element) {
      String comparison = "Objects.equals(a.{0}, b.{0})";

      return element.getEnclosedElements().stream()
            .filter(e -> e.getKind() == ElementKind.FIELD)
            .map(e -> getFieldGetter(element, e))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(getter -> MessageFormat.format(comparison, getter))
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
