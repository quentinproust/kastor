package io.kastor.generator.source.hashcode;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.ProcessingContext;
import io.kastor.generator.source.FieldUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.Optional;

public class KastorAnnotatedFieldHashcode implements FieldHashcodeStrategy {

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
   public Optional<String> getFieldHashcode(Element field) {
      String comparison = field.asType().toString() + "Identity.hashCode(o.{0})";

      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(comparison, getter));
   }

}
