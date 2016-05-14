package io.kastor.generator.source.equality;

import io.kastor.generator.source.FieldUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.Optional;

public class BaseFieldEquality implements FieldEqualityStrategy {

   @Override
   public boolean isApplicable(Element e) {
      return true;
   }

   @Override
   public Optional<String> getFieldComparisons(Element field) {
      String comparison = "Objects.equals(a.{0}, b.{0})";

      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(comparison, getter));
   }

}
