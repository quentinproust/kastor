package io.kastor.examples.builder;

import io.kastor.examples.Weapon;
import io.kastor.examples.WeaponBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeaponBuilderTest {

   @Test
   public void newBuilderShouldProduceNullProperties() {
      Weapon weapon = new WeaponBuilder().build();
      assertWeaponWithNullProperties(weapon);
   }

   @Test
   public void newImmutableBuilderShouldProduceNullProperties() {
      Weapon weapon = new WeaponBuilder().getImmutable().build();
      assertWeaponWithNullProperties(weapon);
   }

   @Test
   public void newTemplateBuilderShouldProduceNullProperties() {
      Weapon weapon = new WeaponBuilder().getTemplate().build();
      assertWeaponWithNullProperties(weapon);
   }

   private void assertWeaponWithNullProperties(Weapon weapon) {
      assertThat(weapon.getName()).isNull();
      assertThat(weapon.getType()).isNull();
      assertThat(weapon.getPower()).isZero();
   }


   @Test
   public void completeBuilderShouldProduceCompletelyInitializedObject() {
      Weapon weapon = initCompletely(new WeaponBuilder());
      assertWeaponIsCompletelyInitialized(weapon);
   }

   @Test
   public void completeImmutableBuilderShouldProduceCompletelyInitializedObject() {
      Weapon weapon = initCompletely(new WeaponBuilder().getImmutable());
      assertWeaponIsCompletelyInitialized(weapon);
   }

   @Test
   public void completeTemplateShouldProduceCompletelyInitializedObject() {
      Weapon weapon = initCompletely(new WeaponBuilder().getTemplate());
      assertWeaponIsCompletelyInitialized(weapon);
   }

   private Weapon initCompletely(WeaponBuilder weaponBuilder) {
      return weaponBuilder.withName("excalibur")
            .withType("sword")
            .withPower(650)
            .build();
   }

   private void assertWeaponIsCompletelyInitialized(Weapon weapon) {
      assertThat(weapon.getName()).isEqualTo("excalibur");
      assertThat(weapon.getType()).isEqualTo("sword");
      assertThat(weapon.getPower()).isEqualTo(650);
   }

   @Test
   public void mofidyingPropertyChangeTheCurrentBuilder() {
      WeaponBuilder builder = new WeaponBuilder();

      WeaponBuilder builder1 = builder.withName("excalibur");
      WeaponBuilder builder2 = builder.withName("masamune");

      assertThat(builder1).isEqualTo(builder);
      assertThat(builder2).isEqualTo(builder);
      assertThat(builder.build().getName()).isEqualTo("masamune");
   }

   @Test
   public void mofidyingPropertyOfImmutableBuilderCreateANewBuilder() {
      WeaponBuilder builder = new WeaponBuilder().getImmutable();

      WeaponBuilder builder1 = builder.withName("excalibur");
      WeaponBuilder builder2 = builder.withName("masamune");

      assertThat(builder1).isNotEqualTo(builder);
      assertThat(builder2).isNotEqualTo(builder);

      assertThat(builder.build().getName()).isNull();
      assertThat(builder1.build().getName()).isEqualTo("excalibur");
      assertThat(builder2.build().getName()).isEqualTo("masamune");
   }

   @Test
   public void mofidyingATemplateBuilderCreateANewBuilder() {
      WeaponBuilder sword = new WeaponBuilder().withType("sword").getTemplate();

      WeaponBuilder builder1 = sword.withName("excalibur");
      builder1.withPower(950);
      WeaponBuilder builder2 = sword.withName("masamune");
      builder2.withPower(650);

      assertThat(builder1).isNotEqualTo(sword);
      assertThat(builder2).isNotEqualTo(sword);

      assertThat(sword.build().getName()).isNull();
      assertThat(builder1.build().getName()).isEqualTo("excalibur");
      assertThat(builder2.build().getName()).isEqualTo("masamune");

      assertThat(sword.build().getPower()).isZero();
      assertThat(builder1.build().getPower()).isEqualTo(950);
      assertThat(builder2.build().getPower()).isEqualTo(650);

      assertThat(sword.build().getType()).isEqualTo("sword");
      assertThat(builder1.build().getType()).isEqualTo("sword");
      assertThat(builder2.build().getType()).isEqualTo("sword");
   }

   @Test
   public void createATemplateFromImmutableBuilderGiveTheSameBuilder() {
      WeaponBuilder builder = new WeaponBuilder().getImmutable();
      WeaponBuilder template = builder.getTemplate();

      assertThat(template).isEqualTo(builder);
   }
}
