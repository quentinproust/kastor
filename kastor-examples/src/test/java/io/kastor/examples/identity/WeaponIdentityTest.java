package io.kastor.examples.identity;

import io.kastor.examples.Weapon;
import io.kastor.examples.WeaponIdentity;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class WeaponIdentityTest extends AbstractIdentityTest<Weapon> {

   private Weapon getWeapon(String name, int power, String type) {
      Weapon weapon = new Weapon();
      weapon.setName(name);
      weapon.setPower(power);
      weapon.setType(type);
      return weapon;
   }

   private Supplier<Weapon> getWeaponSupplier(String name, int power, String type) {
      return () -> getWeapon(name, power, type);
   }

   protected Stream<Supplier<Weapon>> getEqualityTestValues() {
      return Stream.of(
            getWeaponSupplier("Excalibur", 9999, "Sword"),
            getWeaponSupplier("Morgul-blade", 800, "Dagger"),
            getWeaponSupplier("Honjo Masamune", Integer.MAX_VALUE, "Sword"),
            getWeaponSupplier("Longbow", 150, "Bow"),
            getWeaponSupplier("Nyoibo", 300, "Staff")
      );
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(getWeapon("Excalibur", 9999, "Sword"), 1704094430),
            expectHashcode(getWeapon("Longbow", 150, "Bow"), 2134820449),
            expectHashcode(getWeapon("Honjo Masamune", Integer.MAX_VALUE, "Sword"), -869606862),
            expectHashcode(getWeapon("Morgul-blade", 800, "Dagger"), -1674640254),
            expectHashcode(getWeapon("Nyoibo", 300, "Staff"), 1872090885)
      );
   }

   protected boolean equals(Weapon a, Weapon b) {
      return WeaponIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(Weapon value) {
      return WeaponIdentity.hashCode(value);
   }

}