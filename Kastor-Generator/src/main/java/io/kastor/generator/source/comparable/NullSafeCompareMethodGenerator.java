package io.kastor.generator.source.comparable;

import io.kastor.annotation.KastorComparable;
import io.kastor.generator.ProcessingContext;
import io.kastor.generator.source.FieldUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.function.Predicate;

public class NullSafeCompareMethodGenerator {

   public void generateFor(Element element, JavaClassSource javaClass) {
      if (!isRequired(element)) return;

      MethodSource<JavaClassSource> method = getMethodSignature(javaClass);
      addBody(method);
   }

   private MethodSource<JavaClassSource> getMethodSignature(JavaClassSource javaClass) {
      MethodSource<JavaClassSource> method = javaClass.addMethod()
            .setName("nullSafeCompare")
            .setPrivate()
            .setStatic(true)
            .setReturnType("int");

      method.addTypeVariable("T")
            .setBounds("Comparable<? super T>");

      method.addParameter("T", "a");
      method.addParameter("T", "b");
      return method;
   }

   private void addBody(MethodSource<JavaClassSource> method) {
      method.setBody(
            "if (a == b) {\n" +
                  "  return 0;\n" +
                  "} else if (a == null) {\n" +
                  "  return 1;\n" +
                  "} else if (b == null) {\n" +
                  "  return -1;\n" +
                  "}\n" +
                  "  return a.compareTo(b);\n" +
                  "}"
      );
   }

   private boolean isRequired(Element element) {
      Types typeUtils = ProcessingContext.getTypeUtils();

      Predicate<Element> isPrimitiveType = (field) -> Arrays.stream(TypeKind.values())
            .filter(TypeKind::isPrimitive)
            .map(typeUtils::getPrimitiveType)
            .anyMatch(x -> typeUtils.isSameType(x, field.asType()));

      Predicate<Element> isKastorComparable = (field) -> typeUtils
            .asElement(field.asType())
            .getAnnotation(KastorComparable.class) != null;

      return FieldUtils.getFields(element)
            .anyMatch(x -> !isPrimitiveType.test(x) && !isKastorComparable.test(x));
   }


}
