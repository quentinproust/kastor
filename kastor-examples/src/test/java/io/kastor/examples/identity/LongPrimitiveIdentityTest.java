package io.kastor.examples.identity;

import io.kastor.examples.LongPrimitiveType;
import io.kastor.examples.LongPrimitiveTypeIdentity;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class LongPrimitiveIdentityTest extends AbstractIdentityTest<LongPrimitiveType> {

   protected Stream<Supplier<LongPrimitiveType>> getEqualityTestValues() {
      return Stream.of(0L, 1L, -1L, Long.MIN_VALUE, Long.MAX_VALUE)
            .map(this::createSupplier);
   }

   private Supplier<LongPrimitiveType> createSupplier(long value) {
      return () -> new LongPrimitiveType(value);
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new LongPrimitiveType(0), 31),
            expectHashcode(new LongPrimitiveType(1), 32),
            expectHashcode(new LongPrimitiveType(Long.MIN_VALUE), -2147483617),
            expectHashcode(new LongPrimitiveType(Long.MAX_VALUE), -2147483617)
      );
   }

   protected boolean equals(LongPrimitiveType a, LongPrimitiveType b) {
      return LongPrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(LongPrimitiveType value) {
      return LongPrimitiveTypeIdentity.hashCode(value);
   }

}