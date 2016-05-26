package io.kastor.examples;

import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable
public class LongPrimitiveType {

   private long value;

   public LongPrimitiveType(long value) {
      this.value = value;
   }

   public long getValue() {
      return value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof LongPrimitiveType)) return false;

      LongPrimitiveType that = (LongPrimitiveType) o;
      return LongPrimitiveTypeIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      return LongPrimitiveTypeIdentity.hashCode(this);
   }

   @Override
   public String toString() {
      return "LongPrimitiveType{" +
            "value=" + value +
            '}';
   }
}
