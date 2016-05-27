package io.kastor.examples;


import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable(order = {"power", "type", "name"})
public class Weapon {

   private String name;

   private int power;

   private String type;

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

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return "Weapon{" +
            "name='" + name + '\'' +
            ", power=" + power +
            ", type='" + type +
            "'}";
   }

}
