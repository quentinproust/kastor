package io.kastor.generator.source.hashcode;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.List;

public class FieldHashcodeFactory {

   private static List<FieldHashcodeStrategy> strategies = Arrays.asList(
         new KastorAnnotatedFieldHashcode(),
         new SimpleFieldHashcode("double", "Double.hashCode(o.{0})"),
         new SimpleFieldHashcode("float", "Float.hashCode(o.{0})"),
         new SimpleFieldHashcode("long", "(int) (o.{0} ^ (o.{0} >>> 32))"),
         new SimpleFieldHashcode(Arrays.asList("char", "byte", "short"), "(int) o.{0}"),
         new SimpleFieldHashcode("int", "o.{0}"),
         new SimpleFieldHashcode("boolean", "(o.{0} ? 1 : 0)"),
         new BaseFieldHashcode()
   );

   public static FieldHashcodeStrategy get(Element e) {
      for (FieldHashcodeStrategy s : strategies) {
         if (s.isApplicable(e)) return s;
      }
      throw new IllegalArgumentException("No strategy found for " + e);
   }

}
