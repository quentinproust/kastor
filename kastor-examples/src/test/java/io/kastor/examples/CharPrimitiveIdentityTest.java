package io.kastor.examples;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CharPrimitiveIdentityTest extends AbstractIdentityTest<CharPrimitiveType> {

   protected Stream<Supplier<CharPrimitiveType>> getEqualityTestValues() {
      return Stream.of('a', 'b', Character.MIN_VALUE, Character.MAX_VALUE)
            .map(this::createSupplier);
   }

   private Supplier<CharPrimitiveType> createSupplier(char value) {
      return () -> new CharPrimitiveType(value);
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(new CharPrimitiveType((char) 0), 31),
            expectHashcode(new CharPrimitiveType('a'), 128),
            expectHashcode(new CharPrimitiveType('b'), 129)
      );
   }

   protected boolean equals(CharPrimitiveType a, CharPrimitiveType b) {
      return CharPrimitiveTypeIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(CharPrimitiveType value) {
      return CharPrimitiveTypeIdentity.hashCode(value);
   }

}