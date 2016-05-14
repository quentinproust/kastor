package io.kastor.generator.handler;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.Logger;
import io.kastor.generator.source.FieldUtils;
import io.kastor.generator.source.GeneratedClass;
import io.kastor.generator.source.GeneratedSourceFile;
import io.kastor.generator.source.IdentityGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

public class IdentityHandler implements AnnotationHandler<KastorIdentity> {
   private ProcessingEnvironment processingEnvironment;

   public IdentityHandler(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
   }

   @Override
   public void handle(Set<Element> elements) {
      for (Element annotatedElement : elements) {
         if (annotatedElement.getKind() != ElementKind.CLASS) {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "KastorIndentity should only be applied on class. " +
                  "The element " + annotatedElement + " is not supposed to have this annotation.");
         }

         TypeElement type = (TypeElement) annotatedElement;

         validate(type);

         GeneratedClass generatedClass = new IdentityGenerator().generateFor(type);
         new GeneratedSourceFile(generatedClass.getQualifiedName(), annotatedElement).write(generatedClass.getGeneratedSource());
      }
   }

   private void validate(TypeElement type) {
      KastorIdentity annotation = type.getAnnotation(KastorIdentity.class);

      for (String field : annotation.excludeFields()) {
         if (!FieldUtils.exists(type, field)) {
            Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be excluded from its identity.");
         }
      }
      for (String field : annotation.includeFields()) {
         if (!FieldUtils.exists(type, field)) {
            Logger.logError("Field " + field + " does not exist in class " + type + ". It couldn't be included from its identity.");
         }
      }
   }

}
