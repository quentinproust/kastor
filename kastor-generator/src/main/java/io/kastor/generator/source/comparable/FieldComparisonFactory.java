package io.kastor.generator.source.comparable;

import io.kastor.annotation.KastorComparable;
import io.kastor.generator.source.FieldStrategy;
import io.kastor.generator.source.KastorAnnotatedFieldOperation;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;

import static io.kastor.generator.source.FieldOperation.*;

public class FieldComparisonFactory {

   private static final List<FieldStrategy> STRATEGIES = Arrays.asList(
         acceptAnnotatedType(),
         acceptType("Long.compare(a.{0}, b.{0})", "long"),
         acceptType("Float.compare(a.{0}, b.{0})", "float"),
         acceptType("Double.compare(a.{0}, b.{0})", "double"),
         acceptTypes("Byte.compare(a.{0}, b.{0})", "byte"),
         acceptTypes("Character.compare(a.{0}, b.{0})", "char"),
         acceptTypes("Short.compare(a.{0}, b.{0})", "short"),
         acceptTypes("Integer.compare(a.{0}, b.{0})", "int"),
         acceptType("Boolean.compare(a.{0}, b.{0})", "boolean"),
         acceptAll("nullSafeCompare(a.{0}, b.{0})")
   );

   private static FieldStrategy acceptAnnotatedType() {
      return new KastorAnnotatedFieldOperation("Comparator.compare(a.{0}, b.{0})", KastorComparable.class);
   }

   public static FieldStrategy get(Element e) {
      for (FieldStrategy s : STRATEGIES) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
