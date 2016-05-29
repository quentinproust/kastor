package io.kastor.generator.handler;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorComparables;
import io.kastor.generator.Logger;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public class ComparablesHandler implements AnnotationHandler<KastorComparables> {

   @Override
   public void handle(Set<Element> elements) {
      for (Element annotatedElement : elements) {
         if (validate(annotatedElement)) {
            new ComparableHandler().handle(Collections.singleton(annotatedElement));
         }
      }
   }


   private boolean validate(Element type) {
      KastorComparable[] comparables = type.getAnnotationsByType(KastorComparable.class);

      long nbDefaultComparator = Stream.of(comparables).map(KastorComparable::name)
            .filter(c -> c.equals(""))
            .count();
      if (nbDefaultComparator > 1) {
         Logger.logError("Multiple default comparator defined on %s . You can only have one KastorComparable without name.",
               type);
         return false;
      }
      return true;
   }


}
