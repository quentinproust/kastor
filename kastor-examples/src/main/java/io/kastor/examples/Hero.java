package io.kastor.examples;

import io.kastor.annotation.KastorBuilder;
import io.kastor.annotation.KastorComparable;
import io.kastor.annotation.KastorIdentity;

@KastorIdentity
@KastorComparable(order = {"power", "weapon", "name"})
@KastorComparable(name = "ByWeapon", order = {"weapon"})
@KastorBuilder()
public class Hero {

   private String name;

   private int power;

   private Weapon weapon;

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

   public Weapon getWeapon() {
      return weapon;
   }

   public void setWeapon(Weapon weapon) {
      this.weapon = weapon;
   }

   @Override
   public String toString() {
      return "Hero{" +
            "name='" + name + '\'' +
            ", power=" + power +
            ", weapon=" + weapon +
            '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Hero)) return false;

      return HeroIdentity.equals(this, (Hero) o);
   }

   @Override
   public int hashCode() {
      return HeroIdentity.hashCode(this);
   }
}
