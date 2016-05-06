package io.kastor.generator.handler;

import io.kastor.annotation.KastorIdentity;
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

         GeneratedClass generatedClass = new IdentityGenerator().generateFor(type);
         new GeneratedSourceFile(generatedClass.getQualifiedName(), annotatedElement).write(generatedClass.getGeneratedSource());
      }
   }

}
