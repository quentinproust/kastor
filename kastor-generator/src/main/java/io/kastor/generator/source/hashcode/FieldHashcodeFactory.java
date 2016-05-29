package io.kastor.generator.source.hashcode;

import io.kastor.annotation.KastorIdentity;
import io.kastor.generator.source.AbstractFieldStrategyFactory;
import io.kastor.generator.source.FieldStrategy;
import io.kastor.generator.source.KastorAnnotatedFieldOperation;

import java.util.Arrays;
import java.util.List;

import static io.kastor.generator.source.FieldOperation.*;

public class FieldHashcodeFactory extends AbstractFieldStrategyFactory {

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

   @Override
   public List<FieldStrategy> getStrategies() {
      return STRATEGIES;
   }

}
