package io.kastor.examples.identity;

import io.kastor.examples.BytePrimitiveType;
import io.kastor.examples.BytePrimitiveTypeIdentity;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class BytePrimitiveIdentityTest extends AbstractIdentityTest<BytePrimitiveType> {

   protected Stream<Supplier<BytePrimitiveType>> getEqualityTestValues() {
      return Stream.of(0, 1, -1, (int) Byte.MIN_VALUE, (int) Byte.MAX_VALUE)
            .map(this::createSupplier);
   }

   private Supplier<BytePrimitiveType> createSupplier(int value) {
      return () -> new BytePrimitiveType((byte) value);
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new BytePrimitiveType((byte) 0), 31),
            expectHashcode(new BytePrimitiveType((byte) 1), 32),
            expectHashcode(new BytePrimitiveType(Byte.MAX_VALUE), 158),
            expectHashcode(new BytePrimitiveType(Byte.MIN_VALUE), -97)
      );
   }

   protected boolean equals(BytePrimitiveType a, BytePrimitiveType b) {
      return BytePrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(BytePrimitiveType value) {
      return BytePrimitiveTypeIdentity.hashCode(value);
   }

}