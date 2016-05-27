package io.kastor.examples;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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
      )).usingElementComparator(HeroComparator.getComparator())
            .contains(
                  getHeroWithWeapon(robin, bow),
                  getHeroWithWeapon(honjo, masamune),
                  getHeroWithWeapon(arthur, excalibur),
                  getHeroWithWeapon(ringWraith, morgul),
                  getHeroWithWeapon(sangoku, nyoibo)
            );
   }

   @Test
   public void sortUsingCompareMethod() {
      assertThat(Stream.of(
            getHeroWithWeapon(sangoku, nyoibo),
            getHeroWithWeapon(arthur, excalibur),
            getHeroWithWeapon(honjo, masamune),
            getHeroWithWeapon(robin, bow),
            getHeroWithWeapon(ringWraith, morgul)
      )).usingElementComparator(HeroComparator::compare)
            .contains(
                  getHeroWithWeapon(robin, bow),
                  getHeroWithWeapon(honjo, masamune),
                  getHeroWithWeapon(arthur, excalibur),
                  getHeroWithWeapon(ringWraith, morgul),
                  getHeroWithWeapon(sangoku, nyoibo)
            );
   }

   @Test
   public void compareTwoHeroWithoutWeapon() {
      assertThat(Stream.of(
            sangoku.get(),
            arthur.get()
      )).usingElementComparator(HeroComparator::compare)
            .contains(
                  arthur.get(),
                  sangoku.get()
            );
   }

   @Test
   public void compareOneHeroWithAndWithoutWeapon() {
      assertThat(Stream.of(
            sangoku.get(),
            getHeroWithWeapon(sangoku, nyoibo)
      )).usingElementComparator(HeroComparator::compare)
            .contains(
                  sangoku.get(),
                  getHeroWithWeapon(sangoku, nyoibo)
            );
   }

   @Test
   public void compareWithNull() {
      assertThat(Stream.of(getHeroWithWeapon(sangoku, nyoibo), null, getHeroWithWeapon(arthur, excalibur)))
            .usingElementComparator(HeroComparator.getComparator())
            .contains(getHeroWithWeapon(arthur, excalibur), getHeroWithWeapon(sangoku, nyoibo), null);
   }

   @Test
   public void compareWithTwoNull() {
      assertThat(HeroComparator.compare(null, null)).isZero();
   }
}
