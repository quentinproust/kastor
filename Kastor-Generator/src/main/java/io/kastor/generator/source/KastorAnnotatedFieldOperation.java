package io.kastor.generator.source;

import io.kastor.generator.ProcessingContext;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.Optional;

public class KastorAnnotatedFieldOperation implements FieldStrategy {

   private final String operation;
   private final Class<? extends Annotation> annotationClass;

   public KastorAnnotatedFieldOperation(String operation, Class<? extends Annotation> annotationClass) {
      this.operation = operation;
      this.annotationClass = annotationClass;
   }

   @Override
   public boolean isApplicable(Element e) {
      Annotation annotation = null;

      Element typeElement = ProcessingContext.getEnv().getTypeUtils().asElement(e.asType());
      if (typeElement != null) {
         annotation = typeElement.getAnnotation(annotationClass);
      }
      return annotation != null;
   }

   @Override
   public Optional<String> getFieldOperation(Element field) {
      String comparison = field.asType().toString() + operation;

      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(comparison, getter));
   }

}
