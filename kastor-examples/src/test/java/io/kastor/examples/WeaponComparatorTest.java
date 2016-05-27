package io.kastor.examples;

import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WeaponComparatorTest {

   private Weapon excalibur = createWeapon("Excalibur", 9999, "Sword");
   private Weapon morgul = createWeapon("Morgul-blade", 300, "Dagger");
   private Weapon masamune = createWeapon("Honjo Masamune", Integer.MAX_VALUE, "Sword");
   private Weapon bow = createWeapon("Longbow", 150, "Bow");
   private Weapon nyoibo = createWeapon("Nyoibo", 300, "Staff");

   private Weapon createWeapon(String name, int power, String type) {
      Weapon weapon = new Weapon();
      weapon.setName(name);
      weapon.setPower(power);
      weapon.setType(type);
      return weapon;
   }

   @Test
   public void sortUsingComparator() {
      assertThat(Stream.of(bow, excalibur, masamune, nyoibo, morgul))
            .usingElementComparator(WeaponComparator.getComparator())
            .contains(bow, morgul, nyoibo, excalibur, masamune);
   }

   @Test
   public void sortUsingCompareMethod() {
      assertThat(Stream.of(bow, excalibur, masamune, nyoibo, morgul))
            .usingElementComparator(WeaponComparator::compare)
            .contains(bow, morgul, nyoibo, excalibur, masamune);
   }

   @Test
   public void compareWeaponWithNull() {
      assertThat(WeaponComparator.compare(bow, null))
            .isEqualTo(1);
      assertThat(WeaponComparator.compare(null, bow))
            .isEqualTo(-1);
   }

   @Test
   public void compareWithTwoNull() {
      assertThat(WeaponComparator.compare(null, null)).isZero();
   }
}
