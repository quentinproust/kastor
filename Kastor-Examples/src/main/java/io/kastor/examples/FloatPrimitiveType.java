package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class FloatPrimitiveType {

   private float value;

   public FloatPrimitiveType(float value) {
      this.value = value;
   }

   public float getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof FloatPrimitiveType)) return false;

      FloatPrimitiveType that = (FloatPrimitiveType) o;
      return FloatPrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return FloatPrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "FloatPrimitiveType{" +
            "value=" + value +
            '}';
   }
}
