package io.kastor.examples;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class DoublePrimitiveIdentityTest extends AbstractIdentityTest<DoublePrimitiveType> {

   protected Stream<Supplier<DoublePrimitiveType>> getEqualityTestValues() {
      return Stream.of(0.0d, Double.NaN, -0.0d, 4545121.424454d, -44222.4545199772d, Double.MIN_VALUE, Double.MAX_VALUE)
            .map(this::createSupplier);
   }

   private Supplier<DoublePrimitiveType> createSupplier(double value) {
      return () -> new DoublePrimitiveType(value);
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new DoublePrimitiveType(0.0d), 31),
            expectHashcode(new DoublePrimitiveType(Double.NaN), 2146959391),
            expectHashcode(new DoublePrimitiveType(-0.0d), -2147483617),
            expectHashcode(new DoublePrimitiveType(4545121.424454d), 444274595),
            expectHashcode(new DoublePrimitiveType(-44222.4545199772d), 1267264895)
      );
   }

   protected boolean equals(DoublePrimitiveType a, DoublePrimitiveType b) {
      return DoublePrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(DoublePrimitiveType value) {
      return DoublePrimitiveTypeIdentity.hashCode(value);
   }

}