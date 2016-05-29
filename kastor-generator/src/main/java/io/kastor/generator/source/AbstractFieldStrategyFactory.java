package io.kastor.generator.source;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Optional;

public abstract class AbstractFieldStrategyFactory {

   public abstract List<FieldStrategy> getStrategies();

   public Optional<String> getFieldOperation(Element field) {
      return get(field).getFieldOperation(field);
   }

   private FieldStrategy get(Element e) {
      for (FieldStrategy s : getStrategies()) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
