package io.kastor.generator.source.equality;

import io.kastor.generator.source.FieldUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleFieldEquality implements FieldEqualityStrategy {

   private final String comparison;
   private List<String> types = new ArrayList<>();

   public SimpleFieldEquality(String type, String comparison) {
      types.add(type);
      this.comparison = comparison;
   }

   public SimpleFieldEquality(List<String> types, String comparison) {
      this.types.addAll(types);
      this.comparison = comparison;
   }

   @Override
   public boolean isApplicable(Element e) {
      return types.contains(e.asType().toString());
   }

   @Override
   public Optional<String> getFieldComparisons(Element field) {
      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(comparison, getter));
   }

}
