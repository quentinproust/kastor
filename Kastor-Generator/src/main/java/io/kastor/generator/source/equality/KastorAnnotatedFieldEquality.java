package io.kastor.generator.source.equality;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.ProcessingContext;
import io.kastor.generator.source.FieldUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.Optional;

public class KastorAnnotatedFieldEquality implements FieldEqualityStrategy {

   @Override
   public boolean isApplicable(Element e) {
      KastorIdentity annotation = null;

      Element typeElement = ProcessingContext.getEnv().getTypeUtils().asElement(e.asType());
      if (typeElement != null) {
         annotation = typeElement.getAnnotation(KastorIdentity.class);
      }
      return annotation != null;
   }

   @Override
   public Optional<String> getFieldComparisons(Element field) {
      String comparison = field.asType().toString() + "Identity.equals(a.{0}, b.{0})";

      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(comparison, getter));
   }

}
