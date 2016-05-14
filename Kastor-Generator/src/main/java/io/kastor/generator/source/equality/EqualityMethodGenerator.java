package io.kastor.generator.source.equality;


import io.kastor.annotation.KastorIdentity;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
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
      String body = equalityMethod.getBody() + String.join(" ", fieldComparisons) + " return true;";
      equalityMethod.setBody(body);
   }

   private List<String> createFieldComparisons(TypeElement element) {
      KastorIdentity annotation = element.getAnnotation(KastorIdentity.class);

      return element.getEnclosedElements().stream()
            .filter(e -> e.getKind() == ElementKind.FIELD)
            .filter(e -> Arrays.asList(annotation.includeFields()).contains(e.toString()))
            .filter(e -> !Arrays.asList(annotation.excludeFields()).contains(e.toString()))
            .map(field -> FieldEqualityFactory.get(field).getFieldComparisons(field))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(c -> "if (!(" + c + ")) { return false; }")
            .collect(Collectors.toList());
   }
}
