package io.kastor.examples;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class BooleanPrimitiveIdentityTest extends AbstractIdentityTest<BooleanPrimitiveType> {

   protected Stream<Supplier<BooleanPrimitiveType>> getEqualityTestValues() {
      return Stream.of(
            () -> new BooleanPrimitiveType(true),
            () -> new BooleanPrimitiveType(false)
      );
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new BooleanPrimitiveType(true), 32),
            expectHashcode(new BooleanPrimitiveType(false), 31)
      );
   }

   protected boolean equals(BooleanPrimitiveType a, BooleanPrimitiveType b) {
      return BooleanPrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(BooleanPrimitiveType value) {
      return BooleanPrimitiveTypeIdentity.hashCode(value);
   }

}