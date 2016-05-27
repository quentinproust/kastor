package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class BytePrimitiveType {

   private byte value;

   public BytePrimitiveType(byte value) {
      this.value = value;
   }

   public byte getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof BytePrimitiveType)) return false;

      BytePrimitiveType that = (BytePrimitiveType) o;
      return BytePrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return BytePrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "BytePrimitiveType{" +
            "value=" + value +
            '}';
   }
}
