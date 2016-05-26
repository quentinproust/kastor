package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class CharPrimitiveType {

   private char value;

   public CharPrimitiveType(char value) {
      this.value = value;
   }

   public char getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof CharPrimitiveType)) return false;

      CharPrimitiveType that = (CharPrimitiveType) o;
      return CharPrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return CharPrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "CharPrimitiveType{" +
            "value=" + value +
            '}';
   }
}
