package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class BooleanPrimitiveType {

   private boolean value;

   public BooleanPrimitiveType(boolean value) {
      this.value = value;
   }

   public boolean getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof BooleanPrimitiveType)) return false;

      BooleanPrimitiveType that = (BooleanPrimitiveType) o;
      return BooleanPrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return BooleanPrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "BooleanPrimitiveType{" +
            "value=" + value +
            '}';
   }
}
