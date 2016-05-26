package io.kastor.examples;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class FloatPrimitiveIdentityTest extends AbstractIdentityTest<FloatPrimitiveType> {

   protected Stream<Supplier<FloatPrimitiveType>> getEqualityTestValues() {
      return Stream.of(
            Float.NaN,
            -0.0f,
            +0.0f,
            155.4455f,
            -155.4455f,
            Float.MIN_VALUE,
            Float.MAX_VALUE
      ).map(this::createSupplier);
   }

   private Supplier<FloatPrimitiveType> createSupplier(float value) {
      return () -> new FloatPrimitiveType(value);
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new FloatPrimitiveType(0), 31),
            expectHashcode(new FloatPrimitiveType(Float.NaN), 2143289375),
            expectHashcode(new FloatPrimitiveType(-0.0f), -2147483617),
            expectHashcode(new FloatPrimitiveType(155.4455f), 1125872171),
            expectHashcode(new FloatPrimitiveType(-155.4455f), -1021611477)
      );
   }

   protected boolean equals(FloatPrimitiveType a, FloatPrimitiveType b) {
      return FloatPrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(FloatPrimitiveType value) {
      return FloatPrimitiveTypeIdentity.hashCode(value);
   }

}