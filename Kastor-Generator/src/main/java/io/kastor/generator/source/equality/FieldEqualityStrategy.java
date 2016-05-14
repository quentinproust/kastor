package io.kastor.generator.source.equality;

import javax.lang.model.element.Element;
import java.util.Optional;

public interface FieldEqualityStrategy {
   boolean isApplicable(Element e);

   Optional<String> getFieldComparisons(Element field);
}
