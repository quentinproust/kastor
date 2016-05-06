package io.kastor.generator.handler;

import io.kastor.annotation.KastorComparable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

public class ComparatorHandler implements AnnotationHandler<KastorComparable> {
   private ProcessingEnvironment processingEnvironment;

   public ComparatorHandler(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
   }

   @Override
   public void handle(Set<Element> elements) {
      processingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating Comparator");
      for (Element elem : elements) {
         List<? extends AnnotationMirror> annotationsInElement = elem.getAnnotationMirrors();
         String message = "annotation found in " + elem.getSimpleName()
               + " : " + annotationsInElement;
         processingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
      }
   }
}
