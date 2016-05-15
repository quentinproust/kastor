package io.kastor.examples.comparator;

import io.kastor.annotation.KastorComparable;

@KastorComparable(order = {"power", "name"})
public class Hero {

   private String name;

   private int power;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getPower() {
      return power;
   }

   public void setPower(int power) {
      this.power = power;
   }

}
