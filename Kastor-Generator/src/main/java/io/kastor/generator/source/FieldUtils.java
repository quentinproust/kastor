package io.kastor.generator.source;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Optional;

public class FieldUtils {

   public static boolean exists(Element type, String field) {
      return type.getEnclosedElements().stream()
            .filter(x -> x.getKind() == ElementKind.FIELD)
            .anyMatch(x -> x.toString().equals(field));
   }

   public static Optional<String> getFieldGetter(Element field) {
      return field.getEnclosingElement().getEnclosedElements().stream()
            .filter(x -> x.getKind() == ElementKind.METHOD)
            .filter(x -> isFieldGetter(field, x))
            .map(x -> x.getSimpleName() + "()")
            .findFirst();
   }

   public static boolean isFieldGetter(Element field, Element method) {
      String methodName = method.getSimpleName().toString();
      String getMethodName = "get" + field.getSimpleName().toString();
      String isMethodName = "is" + field.getSimpleName().toString();

      return methodName.equalsIgnoreCase(getMethodName) || methodName.equalsIgnoreCase(isMethodName);
   }

}
