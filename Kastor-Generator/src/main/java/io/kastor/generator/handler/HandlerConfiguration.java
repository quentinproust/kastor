package io.kastor.generator.handler;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

import java.util.HashMap;
import java.util.Map;

public class HandlerConfiguration {

   private static Map<Class, AnnotationHandler> handlers = new HashMap<>();

   public HandlerConfiguration() {
      handlers.put(KastorComparable.class, new ComparableHandler());
      handlers.put(KastorIdentity.class, new IdentityHandler());
   }


   public AnnotationHandler getHandler(Class theClass) {
      AnnotationHandler handler = handlers.get(theClass);
      if (handler == null) {
         throw new IllegalArgumentException("No handler found for annotation " + theClass.getName());
      }
      return handler;
   }
}
