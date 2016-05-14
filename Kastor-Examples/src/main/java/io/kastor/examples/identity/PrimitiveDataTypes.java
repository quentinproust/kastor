package io.kastor.examples.identity;

import io.kastor.annotation.KastorIdentity;

import java.math.BigDecimal;

@KastorIdentity
public class PrimitiveDataTypes {
   private byte aByte;
   private short aShort;
   private int anInt;
   private long aLong;
   private float aFloat;
   private double aDouble;
   private char aChar;
   private boolean aBoolean;
   private BigDecimal bigDecimal;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof PrimitiveDataTypes)) return false;

      PrimitiveDataTypes that = (PrimitiveDataTypes) o;
      return PrimitiveDataTypesIdentity.equals(this, that);
   }

   @Override
   public int hashCode() {
      int result;
      long temp;
      result = (int) aByte;
      result = 31 * result + (int) aShort;
      result = 31 * result + anInt;
      result = 31 * result + (int) (aLong ^ (aLong >>> 32));
      result = 31 * result + (aFloat != +0.0f ? Float.floatToIntBits(aFloat) : 0);
      temp = Double.doubleToLongBits(aDouble);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      result = 31 * result + (int) aChar;
      result = 31 * result + (aBoolean ? 1 : 0);
      result = 31 * result + (bigDecimal != null ? bigDecimal.hashCode() : 0);
      return result;
   }

   public BigDecimal getBigDecimal() {
      return bigDecimal;
   }

   public void setBigDecimal(BigDecimal bigDecimal) {
      this.bigDecimal = bigDecimal;
   }

   public byte getaByte() {
      return aByte;
   }

   public void setaByte(byte aByte) {
      this.aByte = aByte;
   }

   public short getaShort() {
      return aShort;
   }

   public void setaShort(short aShort) {
      this.aShort = aShort;
   }

   public int getAnInt() {
      return anInt;
   }

   public void setAnInt(int anInt) {
      this.anInt = anInt;
   }

   public long getaLong() {
      return aLong;
   }

   public void setaLong(long aLong) {
      this.aLong = aLong;
   }

   public float getaFloat() {
      return aFloat;
   }

   public void setaFloat(float aFloat) {
      this.aFloat = aFloat;
   }

   public double getaDouble() {
      return aDouble;
   }

   public void setaDouble(double aDouble) {
      this.aDouble = aDouble;
   }

   public char getaChar() {
      return aChar;
   }

   public void setaChar(char aChar) {
      this.aChar = aChar;
   }

   public boolean isaBoolean() {
      return aBoolean;
   }

   public void setaBoolean(boolean aBoolean) {
      this.aBoolean = aBoolean;
   }
}