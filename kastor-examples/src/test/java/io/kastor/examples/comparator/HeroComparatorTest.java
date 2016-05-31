package io.kastor.examples.comparator;

import io.kastor.examples.Hero;
import io.kastor.examples.HeroComparator;
import io.kastor.examples.Weapon;
import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

public class HeroComparatorTest {

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
      ).sorted(HeroComparator.getComparator()))
            .contains(getHeroWithWeapon(robin, bow), atIndex(0))
            .contains(getHeroWithWeapon(arthur, excalibur), atIndex(1))
            .contains(getHeroWithWeapon(honjo, masamune), atIndex(2))
            .contains(getHeroWithWeapon(ringWraith, morgul), atIndex(3))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(4));
   }

   @Test
   public void sortUsingCompareMethod() {
      assertThat(Stream.of(
            getHeroWithWeapon(sangoku, nyoibo),
            getHeroWithWeapon(arthur, excalibur),
            getHeroWithWeapon(honjo, masamune),
            getHeroWithWeapon(robin, bow),
            getHeroWithWeapon(ringWraith, morgul)
      ).sorted(HeroComparator::compare))
            .contains(getHeroWithWeapon(robin, bow), atIndex(0))
            .contains(getHeroWithWeapon(arthur, excalibur), atIndex(1))
            .contains(getHeroWithWeapon(honjo, masamune), atIndex(2))
            .contains(getHeroWithWeapon(ringWraith, morgul), atIndex(3))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(4));
   }

   @Test
   public void compareTwoHeroWithoutWeapon() {
      assertThat(Stream.of(
            sangoku.get(),
            arthur.get()
      ).sorted(HeroComparator::compare))
            .contains(arthur.get(), atIndex(0))
            .contains(sangoku.get(), atIndex(1));
   }

   @Test
   public void compareOneHeroWithAndWithoutWeapon() {
      assertThat(Stream.of(
            sangoku.get(),
            getHeroWithWeapon(sangoku, nyoibo)
      ).sorted(HeroComparator::compare))
            .contains(sangoku.get(), atIndex(0))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(1));
   }

   @Test
   public void compareWithNull() {
      assertThat(Stream.of(
            getHeroWithWeapon(sangoku, nyoibo),
            null,
            getHeroWithWeapon(arthur, excalibur)
      ).sorted(HeroComparator.getComparator()))
            .contains(null, atIndex(0))
            .contains(getHeroWithWeapon(arthur, excalibur), atIndex(1))
            .contains(getHeroWithWeapon(sangoku, nyoibo), atIndex(2))
      ;
   }

   @Test
   public void compareWithTwoNull() {
      assertThat(HeroComparator.compare(null, null)).isZero();
   }
}
