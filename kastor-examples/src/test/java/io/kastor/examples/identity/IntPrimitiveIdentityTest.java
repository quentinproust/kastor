package io.kastor.examples.identity;

import io.kastor.examples.IntPrimitiveType;
import io.kastor.examples.IntPrimitiveTypeIdentity;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class IntPrimitiveIdentityTest extends AbstractIdentityTest<IntPrimitiveType> {

   @Override
   protected Stream<Supplier<IntPrimitiveType>> getEqualityTestValues() {
      return Stream.of(0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE).map(this::createSupplier);
   }

   private Supplier<IntPrimitiveType> createSupplier(int value) {
      return () -> new IntPrimitiveType(value);
   }

   @Override
   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new IntPrimitiveType(0), 31),
            expectHashcode(new IntPrimitiveType(-15), 16),
            expectHashcode(new IntPrimitiveType(12), 43),
            expectHashcode(new IntPrimitiveType(43), 74)
      );
   }

   @Override
   protected boolean equals(IntPrimitiveType a, IntPrimitiveType b) {
      return IntPrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(IntPrimitiveType value) {
      return IntPrimitiveTypeIdentity.hashCode(value);
   }
}