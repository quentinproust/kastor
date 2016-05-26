package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class DoublePrimitiveType {

   private double value;

   public DoublePrimitiveType(double value) {
      this.value = value;
   }

   public double getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof DoublePrimitiveType)) return false;

      DoublePrimitiveType that = (DoublePrimitiveType) o;
      return DoublePrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return DoublePrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "DoublePrimitiveType{" +
            "value=" + value +
            '}';
   }
}
