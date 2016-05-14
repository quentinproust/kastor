package io.kastor.generator.source;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FieldOperation implements FieldStrategy {

   private final String operation;
   private final Predicate<Element> applicable;

   private FieldOperation(String operation, Predicate<Element> applicable) {
      this.operation = operation;
      this.applicable = applicable;
   }

   public static FieldOperation acceptAll(String operation) {
      return new FieldOperation(operation, (e) -> true);
   }

   public static FieldOperation acceptType(String operation, String type) {
      return new FieldOperation(operation, (e) -> type.equals(e.asType().toString()));
   }

   public static FieldOperation acceptTypes(String operation, String... types) {
      List<String> typeList = Arrays.asList(types);
      return new FieldOperation(operation, (e) -> typeList.contains(e.asType().toString()));
   }

   public boolean isApplicable(Element element) {
      return applicable.test(element);
   }

   public Optional<String> getFieldOperation(Element field) {
      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(operation, getter));
   }

}
