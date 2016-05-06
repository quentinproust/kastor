package io.kastor.examples.comparator;

import io.kastor.annotation.KastorComparable;

@KastorComparable
public class PolutionInformation {
   private float co2;
   private float power;
   private String fuelType;

   public float getCo2() {
      return co2;
   }

   public void setCo2(float co2) {
      this.co2 = co2;
   }

   public float getPower() {
      return power;
   }

   public void setPower(float power) {
      this.power = power;
   }

   public String getFuelType() {
      return fuelType;
   }

   public void setFuelType(String fuelType) {
      this.fuelType = fuelType;
   }
}
