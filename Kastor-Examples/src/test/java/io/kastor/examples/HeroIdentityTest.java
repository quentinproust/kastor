package io.kastor.examples;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class HeroIdentityTest extends AbstractIdentityTest<Hero> {

   private Hero getHero(String name, int power, Weapon weapon) {
      Hero hero = getHeroWithoutWeapon(name, power);
      hero.setWeapon(weapon);
      return hero;
   }

   private Hero getHeroWithoutWeapon(String name, int power) {
      Hero hero = new Hero();
      hero.setName(name);
      hero.setPower(power);
      return hero;
   }

   private Weapon getWeapon(String name, int power, String type) {
      Weapon weapon = new Weapon();
      weapon.setName(name);
      weapon.setPower(power);
      weapon.setType(type);
      return weapon;
   }

   private Supplier<Hero> getHeroSupplierWithWeapon(String name, int power, String weaponName, int weaponPower, String type) {
      return () -> getHero(name, power, getWeapon(weaponName, weaponPower, type));
   }

   private Supplier<Hero> getHeroSupplierWithoutWeapon(String name, int power) {
      return () -> getHeroWithoutWeapon(name, power);
   }

   protected Stream<Supplier<Hero>> getEqualityTestValues() {
      return Stream.of(
            getHeroSupplierWithWeapon("King Arthur", 350, "Excalibur", 9999, "Sword"),
            getHeroSupplierWithWeapon("Ringwraith", 350, "Morgul-blade", 800, "Dagger"),
            getHeroSupplierWithWeapon("Honjo \"Echizen no kami\" Shigenaga", 350, "Honjo Masamune", Integer.MAX_VALUE, "Sword"),
            getHeroSupplierWithWeapon("Robin Wood", 350, "Longbow", 150, "Bow"),
            getHeroSupplierWithWeapon("Sangoku", 350, "Nyoibo", 300, "Staff"),
            getHeroSupplierWithoutWeapon("Sangoku", 9999)
      );
   }

   protected Stream<HashcodeExpectation> getHashcodeTestValues() {
      return Stream.of(
            expectHashcode(getHero("King Arthur", 350, getWeapon("Excalibur", 9999, "Sword")), -188264630),
            expectHashcode(getHero("Ringwraith", 350, getWeapon("Morgul-blade", 800, "Dagger")), 1202809290),
            expectHashcode(getHero("Honjo \"Echizen no kami\" Shigenaga", 350, getWeapon("Honjo Masamune", Integer.MAX_VALUE, "Sword")), -586760979),
            expectHashcode(getHero("Robin Wood", 350, getWeapon("Longbow", 150, "Bow")), -1278402555),
            expectHashcode(getHero("Sangoku", 350, getWeapon("Nyoibo", 300, "Staff")), 1326947672),
            expectHashcode(getHeroWithoutWeapon("Sangoku", 9999), -544844094)
      );
   }

   protected boolean equals(Hero a, Hero b) {
      return HeroIdentity.equals(a, b);
   }

   @Override
   protected int hashcode(Hero value) {
      return HeroIdentity.hashCode(value);
   }

}