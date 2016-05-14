package io.kastor.generator.source.equality;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;

public class FieldEqualityFactory {

   private static List<FieldEqualityStrategy> strategies = Arrays.asList(
         new KastorAnnotatedFieldEquality(),
         new NullableFieldEquality("java.math.BigDecimal", "a.{0}.compareTo(b.{0}) == 0"),
         new NullableFieldEquality("java.lang.Double", "Double.doubleToLongBits(a.{0}) == Double.doubleToLongBits(b.{0})"),
         new SimpleFieldEquality("double", "Double.doubleToLongBits(a.{0}) == Double.doubleToLongBits(b.{0})"),
         new NullableFieldEquality("java.lang.Float", "Float.floatToIntBits(a.{0}) == Float.floatToIntBits(b.{0})"),
         new SimpleFieldEquality("float", "Float.floatToIntBits(a.{0}) == Float.floatToIntBits(b.{0})"),
         new SimpleFieldEquality(Arrays.asList("boolean", "char", "byte", "short", "int", "long"), "a.{0} == b.{0}"),
         new BaseFieldEquality()
   );

   public static FieldEqualityStrategy get(Element e) {
      for (FieldEqualityStrategy s : strategies) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
