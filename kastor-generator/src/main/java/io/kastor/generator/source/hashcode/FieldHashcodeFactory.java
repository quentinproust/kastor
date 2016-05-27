package io.kastor.generator.source.hashcode;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.source.FieldStrategy;
import io.kastor.generator.source.KastorAnnotatedFieldOperation;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;

import static io.kastor.generator.source.FieldOperation.*;

public class FieldHashcodeFactory {

   private static final List<FieldStrategy> STRATEGIES = Arrays.asList(
         acceptAnnotatedType(),
         acceptType("Double.hashCode(o.{0})", "double"),
         acceptType("Float.hashCode(o.{0})", "float"),
         acceptType("(int) (o.{0} ^ (o.{0} >>> 32))", "long"),
         acceptTypes("(int) o.{0}", "char", "byte", "short"),
         acceptType("o.{0}", "int"),
         acceptType("(o.{0} ? 1 : 0)", "boolean"),
         acceptAll("Objects.hashCode(o.{0})")
   );

   private static FieldStrategy acceptAnnotatedType() {
      return new KastorAnnotatedFieldOperation("Identity.hashCode(o.{0})", KastorIdentity.class);
   }

   public static FieldStrategy get(Element e) {
      for (FieldStrategy s : STRATEGIES) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
