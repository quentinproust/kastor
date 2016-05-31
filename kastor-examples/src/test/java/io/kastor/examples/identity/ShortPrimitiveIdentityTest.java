package io.kastor.examples.identity;

import io.kastor.examples.ShortPrimitiveType;
import io.kastor.examples.ShortPrimitiveTypeIdentity;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ShortPrimitiveIdentityTest extends AbstractIdentityTest<ShortPrimitiveType> {

   protected Stream<Supplier<ShortPrimitiveType>> getEqualityTestValues() {
      return Stream.of(-1, 0, 1, (int) Short.MIN_VALUE, (int) Short.MAX_VALUE)
            .map(this::createSupplier);
   }

   private Supplier<ShortPrimitiveType> createSupplier(int value) {
      return () -> new ShortPrimitiveType((short) value);
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new ShortPrimitiveType((short) 0), 31),
            expectHashcode(new ShortPrimitiveType((short) 1), 32),
            expectHashcode(new ShortPrimitiveType(Short.MIN_VALUE), -32737),
            expectHashcode(new ShortPrimitiveType(Short.MAX_VALUE), 32798)
      );
   }

   protected boolean equals(ShortPrimitiveType a, ShortPrimitiveType b) {
      return ShortPrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(ShortPrimitiveType value) {
      return ShortPrimitiveTypeIdentity.hashCode(value);
   }

}