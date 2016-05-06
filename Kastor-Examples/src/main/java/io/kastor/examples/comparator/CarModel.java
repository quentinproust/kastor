package io.kastor.examples.comparator;

import io.kastor.annotation.KastorComparable;

@KastorComparable
public class CarModel {
   private String carManufacturer;
   private EngineType engineType;
   private int numberOfWheels;

   public String getCarManufacturer() {
      return carManufacturer;
   }

   public void setCarManufacturer(String carManufacturer) {
      this.carManufacturer = carManufacturer;
   }

   public EngineType getEngineType() {
      return engineType;
   }

   public void setEngineType(EngineType engineType) {
      this.engineType = engineType;
   }

   public int getNumberOfWheels() {
      return numberOfWheels;
   }

   public void setNumberOfWheels(int numberOfWheels) {
      this.numberOfWheels = numberOfWheels;
   }

   public enum EngineType {AUTO, MANUAL}
}
