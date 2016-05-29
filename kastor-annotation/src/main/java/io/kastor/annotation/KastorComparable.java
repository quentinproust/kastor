package io.kastor.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(KastorComparables.class)
public @interface KastorComparable {
   String name() default "";

   String[] order() default {};
}

