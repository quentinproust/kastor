package io.kastor.generator.source.hashcode;

import io.kastor.generator.source.FieldUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.Optional;

public class BaseFieldHashcode implements FieldHashcodeStrategy {

   @Override
   public boolean isApplicable(Element e) {
      return true;
   }

   @Override
   public Optional<String> getFieldHashcode(Element field) {
      String comparison = "Objects.hashCode(o.{0})";

      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(comparison, getter));
   }

}
