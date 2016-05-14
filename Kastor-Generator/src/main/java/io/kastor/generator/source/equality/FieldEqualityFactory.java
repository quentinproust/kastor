package io.kastor.generator.source.equality;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.source.FieldStrategy;
import io.kastor.generator.source.KastorAnnotatedFieldOperation;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;

import static io.kastor.generator.source.FieldOperation.*;

public class FieldEqualityFactory {

   private static List<FieldStrategy> strategies = Arrays.asList(
         acceptAnnotatedType("Identity.equals(a.{0}, b.{0})"),
         acceptNullableType("a.{0}.compareTo(b.{0}) == 0", "java.math.BigDecimal"),
         acceptNullableType("Double.doubleToLongBits(a.{0}) == Double.doubleToLongBits(b.{0})", "java.lang.Double"),
         acceptNullableType("Float.floatToIntBits(a.{0}) == Float.floatToIntBits(b.{0})", "java.lang.Float"),
         acceptType("Double.doubleToLongBits(a.{0}) == Double.doubleToLongBits(b.{0})", "double"),
         acceptType("Float.floatToIntBits(a.{0}) == Float.floatToIntBits(b.{0})", "float"),
         acceptTypes("a.{0} == b.{0}", "boolean", "char", "byte", "short", "int", "long"),
         acceptAll("Objects.equals(a.{0}, b.{0})")
   );

   private static FieldStrategy acceptAnnotatedType(String operation) {
      return new KastorAnnotatedFieldOperation(operation, KastorIdentity.class);
   }

   private static FieldStrategy acceptNullableType(String operation, String type) {
      return acceptType("!(a.{0} == b.{0} || (a.{0} != null && " + operation + "))", type);
   }

   public static FieldStrategy get(Element e) {
      for (FieldStrategy s : strategies) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
