package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class ShortPrimitiveType {

   private short value;

   public ShortPrimitiveType(short value) {
      this.value = value;
   }

   public short getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ShortPrimitiveType)) return false;

      ShortPrimitiveType that = (ShortPrimitiveType) o;
      return ShortPrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return ShortPrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "ShortPrimitiveType{" +
            "value=" + value +
            '}';
   }
}
