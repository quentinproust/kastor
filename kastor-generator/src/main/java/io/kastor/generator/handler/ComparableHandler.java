package io.kastor.generator.handler;

import io.kastor.annotation.KastorComparable;
import io.kastor.generator.Logger;
import io.kastor.generator.source.ComparableGenerator;
import io.kastor.generator.source.FieldUtils;
import io.kastor.generator.source.GeneratedClass;
import io.kastor.generator.source.GeneratedSourceFile;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class ComparableHandler implements AnnotationHandler<KastorComparable> {

   @Override
   public void handle(Set<Element> elements) {
      for (Element annotatedElement : elements) {
         if (annotatedElement.getKind() != ElementKind.CLASS) {
            Logger.logError("KastorComparable should only be applied on class. " +
                  "The element " + annotatedElement + " is not supposed to have this annotation.");
         }

         TypeElement type = (TypeElement) annotatedElement;

         validate(type);

         GeneratedClass generatedClass = new ComparableGenerator().generateFor(type);
         new GeneratedSourceFile(generatedClass.getQualifiedName(), annotatedElement).write(generatedClass.getGeneratedSource());
      }
   }


   private void validate(TypeElement type) {
      KastorComparable[] annotations = type.getAnnotationsByType(KastorComparable.class);

      for (KastorComparable annotation : annotations) {
         for (String field : annotation.order()) {
            if (FieldUtils.isFieldAbsent(type, field)) {
               Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be included in the comparator.");
            }
         }
      }
   }
}
