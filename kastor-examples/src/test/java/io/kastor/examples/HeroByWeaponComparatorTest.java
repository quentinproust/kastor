package io.kastor.examples;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;

public class HeroByWeaponComparatorTest {

   private Supplier<Hero> arthur = () -> createHero("King Arthur", 350);
   private Supplier<Hero> ringWraith = () -> createHero("Ringwraith", 650);
   private Supplier<Hero> honjo = () -> createHero("Honjo \"Echizen no kami\" Shigenaga", 350);
   private Supplier<Hero> robin = () -> createHero("Robin Wood", 150);
   private Supplier<Hero> sangoku = () -> createHero("Sangoku", 9999);

   private Supplier<Weapon> excalibur = () -> createWeapon("Excalibur", 9999, "Sword");
   private Supplier<Weapon> morgul = () -> createWeapon("Morgul-blade", 300, "Dagger");
   private Supplier<Weapon> masamune = () -> createWeapon("Honjo Masamune", Integer.MAX_VALUE, "Sword");
   private Supplier<Weapon> bow = () -> createWeapon("Longbow", 150, "Bow");
   private Supplier<Weapon> nyoibo = () -> createWeapon("Nyoibo", 300, "Staff");

   private Hero createHero(String name, int power) {
      Hero hero = new Hero();
      hero.setName(name);
      hero.setPower(power);
      return hero;
   }

   private Weapon createWeapon(String name, int power, String type) {
      Weapon weapon = new Weapon();
      weapon.setName(name);
      weapon.setPower(power);
      weapon.setType(type);
      return weapon;
   }

   private Hero getHeroWithWeapon(Supplier<Hero> heroSupplier, Supplier<Weapon> weaponSupplier) {
      Hero h = heroSupplier.get();
      Weapon w = weaponSupplier.get();
      h.setWeapon(w);
      return h;
   }

   @Test
   public void sortUsingComparator() {
      assertThat(Stream.of(
            getHeroWithWeapon(sangoku, nyoibo),
            getHeroWithWeapon(arthur, excalibur),
            getHeroWithWeapon(honjo, masamune),
            getHeroWithWeapon(robin, bow),
            getHeroWithWeapon(ringWraith, morgul)
      ).sorted(HeroComparator.getByWeaponComparator()))
            .contains(getHeroWithWeapon(robin, bow), atIndex(0))
            .contains(getHeroWithWeapon(ringWraith, morgul), atIndex(1))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(2))
            .contains(getHeroWithWeapon(arthur, excalibur), atIndex(3))
            .contains(getHeroWithWeapon(honjo, masamune), atIndex(4));
   }

   @Test
   public void sortUsingCompareMethod() {
      assertThat(Stream.of(
            getHeroWithWeapon(sangoku, nyoibo),
            getHeroWithWeapon(arthur, excalibur),
            getHeroWithWeapon(honjo, masamune),
            getHeroWithWeapon(robin, bow),
            getHeroWithWeapon(ringWraith, morgul)
      ).sorted(HeroComparator::compareByWeapon))
            .contains(getHeroWithWeapon(robin, bow), atIndex(0))
            .contains(getHeroWithWeapon(ringWraith, morgul), atIndex(1))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(2))
            .contains(getHeroWithWeapon(arthur, excalibur), atIndex(3))
            .contains(getHeroWithWeapon(honjo, masamune), atIndex(4));
   }

   @Test
   public void compareTwoHeroWithoutWeapon() {
      assertThat(Stream.of(
            sangoku.get(),
            arthur.get()
      ).sorted(HeroComparator::compareByWeapon))
            .contains(sangoku.get(), atIndex(0))
            .contains(arthur.get(), atIndex(1));
   }

   @Test
   public void compareOneHeroWithAndWithoutWeapon() {
      assertThat(Stream.of(
            sangoku.get(),
            getHeroWithWeapon(sangoku, nyoibo)
      ).sorted(HeroComparator::compareByWeapon))
            .contains(sangoku.get(), atIndex(0))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(1));
   }

   @Test
   public void compareWithNull() {
      assertThat(Stream.of(getHeroWithWeapon(sangoku, nyoibo), null, getHeroWithWeapon(arthur, excalibur))
            .sorted(HeroComparator.getByWeaponComparator()))
            .contains(null, atIndex(0))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(1))
            .contains(getHeroWithWeapon(arthur, excalibur), atIndex(2));
   }

   @Test
   public void compareWithTwoNull() {
      assertThat(HeroComparator.compareByWeapon(null, null)).isZero();
   }
}