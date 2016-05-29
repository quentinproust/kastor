package io.kastor.generator.handler;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.Logger;
import io.kastor.generator.source.FieldUtils;
import io.kastor.generator.source.GeneratedClass;
import io.kastor.generator.source.GeneratedSourceFile;
import io.kastor.generator.source.IdentityGenerator;

import javax.lang.model.element.Element;
import java.util.Set;

public class IdentityHandler implements AnnotationHandler<KastorIdentity> {

   @Override
   public void handle(Set<Element> elements) {
      for (Element annotatedElement : elements) {
         validate(annotatedElement);

         GeneratedClass generatedClass = new IdentityGenerator().generateFor(annotatedElement);
         new GeneratedSourceFile(generatedClass.getQualifiedName(), annotatedElement).write(generatedClass.getGeneratedSource());
      }
   }

   private void validate(Element type) {
      KastorIdentity annotation = type.getAnnotation(KastorIdentity.class);

      for (String field : annotation.excludeFields()) {
         if (FieldUtils.isFieldAbsent(type, field)) {
            Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be excluded from its identity.");
         }
      }
      for (String field : annotation.includeFields()) {
         if (FieldUtils.isFieldAbsent(type, field)) {
            Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be included from its identity.");
         }
      }
   }

}
