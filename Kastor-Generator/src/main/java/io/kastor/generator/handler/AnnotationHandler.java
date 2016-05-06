package io.kastor.generator.handler;

import javax.lang.model.element.Element;
import java.util.Set;

public interface AnnotationHandler<T> {
   void handle(Set<Element> elements);
}
