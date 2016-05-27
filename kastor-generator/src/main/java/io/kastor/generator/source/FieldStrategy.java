package io.kastor.generator.source;

import javax.lang.model.element.Element;
import java.util.Optional;

public interface FieldStrategy {
   boolean isApplicable(Element e);

   Optional<String> getFieldOperation(Element field);
}
