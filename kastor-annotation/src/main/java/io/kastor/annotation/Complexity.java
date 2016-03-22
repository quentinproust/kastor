package io.kastor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO : comment
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Complexity {
   Level value();

   public enum Level {
      VERY_SIMPLE, SIMPLE, MEDIUM, COMPLEX, VERY_COMPLEX
   }
}
