package io.kastor.generator.source.comparable;

import io.kastor.annotation.KastorComparable;
import io.kastor.generator.source.FieldStrategy;
import io.kastor.generator.source.KastorAnnotatedFieldOperation;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;

import static io.kastor.generator.source.FieldOperation.*;

public class FieldComparisonFactory {

   private static List<FieldStrategy> strategies = Arrays.asList(
         acceptAnnotatedType("Comparator.compare(a.{0}, b.{0})"),
         acceptType("Long.compare(a.{0}, b.{0})", "long"),
         acceptTypes("Float.compare(a.{0}, b.{0})", "float", "double"),
         acceptTypes("Integer.compare(a.{0}, b.{0})", "byte", "short", "int"),
         acceptType("Boolean.compare(a.{0}, b.{0})", "boolean"),
         acceptAll("nullSafeCompare(a.{0}, b.{0})")
   );

   private static FieldStrategy acceptAnnotatedType(String operation) {
      return new KastorAnnotatedFieldOperation(operation, KastorComparable.class);
   }

   public static FieldStrategy get(Element e) {
      for (FieldStrategy s : strategies) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
