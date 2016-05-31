package io.kastor.generator.handler;

import io.kastor.annotation.KastorBuilder;
import io.kastor.generator.Logger;
import io.kastor.generator.source.BuilderGenerator;
import io.kastor.generator.source.FieldUtils;
import io.kastor.generator.source.GeneratedClass;
import io.kastor.generator.source.GeneratedSourceFile;

import javax.lang.model.element.Element;
import java.util.Set;

public class BuilderHandler implements AnnotationHandler<KastorBuilder> {

   @Override
   public void handle(Set<Element> elements) {
      for (Element annotatedElement : elements) {
         validate(annotatedElement);

         GeneratedClass generatedClass = new BuilderGenerator(annotatedElement).generate();
         new GeneratedSourceFile(generatedClass, annotatedElement).write();
      }
   }


   private void validate(Element type) {
      KastorBuilder annotation = type.getAnnotation(KastorBuilder.class);

      for (String field : annotation.excludeFields()) {
         if (FieldUtils.isFieldAbsent(type, field)) {
            Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be excluded from its builder.");
         }
      }
      for (String field : annotation.includeFields()) {
         if (FieldUtils.isFieldAbsent(type, field)) {
            Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be included from its builder.");
         }
      }
   }
}
