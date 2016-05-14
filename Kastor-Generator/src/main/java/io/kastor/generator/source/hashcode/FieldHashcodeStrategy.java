package io.kastor.generator.source.hashcode;

import javax.lang.model.element.Element;
import java.util.Optional;

public interface FieldHashcodeStrategy {
   boolean isApplicable(Element e);

   Optional<String> getFieldHashcode(Element field);
}
