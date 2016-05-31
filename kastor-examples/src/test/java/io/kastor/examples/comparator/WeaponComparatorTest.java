package io.kastor.examples.comparator;

import io.kastor.examples.Weapon;
import io.kastor.examples.WeaponComparator;
import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

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

   private Stream<Weapon> getUnsortedHeros() {
      return Stream.of(bow, excalibur, masamune, nyoibo, morgul);
   }

   @Test
   public void sortUsingComparator() {
      assertThat(getUnsortedHeros().sorted(WeaponComparator.getComparator()))
            .contains(bow, atIndex(0))
            .contains(morgul, atIndex(1))
            .contains(nyoibo, atIndex(2))
            .contains(excalibur, atIndex(3))
            .contains(masamune, atIndex(4));
   }

   @Test
   public void sortUsingCompareMethod() {
      assertThat(getUnsortedHeros().sorted(WeaponComparator::compare))
            .contains(bow, atIndex(0))
            .contains(morgul, atIndex(1))
            .contains(nyoibo, atIndex(2))
            .contains(excalibur, atIndex(3))
            .contains(masamune, atIndex(4));
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
