package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class IntPrimitiveType {

   private int value;

   public IntPrimitiveType(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof IntPrimitiveType)) return false;

      IntPrimitiveType that = (IntPrimitiveType) o;
      return IntPrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return IntPrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "IntPrimitiveType{" +
            "value=" + value +
            '}';
   }
}
