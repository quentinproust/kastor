package io.kastor.generator.handler;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import java.util.HashMap;
import java.util.Map;

public class HandlerConfiguration {

   private static Map<Class, AnnotationHandler> handlers = new HashMap<>();

   public HandlerConfiguration(ProcessingEnvironment processingEnvironment, RoundEnvironment roundEnvironment) {
      handlers.put(KastorComparable.class, new ComparatorHandler(processingEnvironment));
      handlers.put(KastorIdentity.class, new IdentityHandler(processingEnvironment));
   }


   public AnnotationHandler getHandler(Class theClass) {
      AnnotationHandler handler = handlers.get(theClass);
      if (handler == null) {
         throw new IllegalArgumentException("No handler found for annotation " + theClass.getName());
      }
      return handler;
   }
}
