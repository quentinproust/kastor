package io.kastor.generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ProcessingContext {
   private static final InheritableThreadLocal<ProcessingEnvironment> ENVIRONMENT =
         new InheritableThreadLocal<>();

   public static void withContext(final ProcessingEnvironment environment) {
      ENVIRONMENT.set(environment);
   }

   /**
    * Returns an implementation of some utility methods for
    * operating on elements
    *
    * @return element utilities
    */
   public static ProcessingEnvironment getEnv() {
      return ENVIRONMENT.get();
   }

   /**
    * Returns an implementation of some utility methods for
    * operating on elements
    *
    * @return element utilities
    */
   public static Elements getElementUtils() {
      return ENVIRONMENT.get().getElementUtils();
   }

   /**
    * Returns an implementation of some utility methods for
    * operating on types.
    *
    * @return type utilities
    */
   public static Types getTypeUtils() {
      return ENVIRONMENT.get().getTypeUtils();
   }
}
