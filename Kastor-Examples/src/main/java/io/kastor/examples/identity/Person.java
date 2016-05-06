package io.kastor.examples.identity;

import io.kastor.annotation.KastorIdentity;

@KastorIdentity
public class Person {
   private String socialSecurityNumber;
   private boolean vip;
   private String firstName;
   private String lastName;
   private Gender gender;
   private int age;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Person)) return false;

      return PersonIdentity.equals(this, (Person) o);
   }

   @Override
   public int hashCode() {
      return PersonIdentity.hashCode(this);
   }

   public String getSocialSecurityNumber() {
      return socialSecurityNumber;
   }

   public void setSocialSecurityNumber(String socialSecurityNumber) {
      this.socialSecurityNumber = socialSecurityNumber;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }

   public boolean isVip() {
      return vip;
   }

   public void setVip(boolean vip) {
      this.vip = vip;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public enum Gender {MALE, FEMALE, OTHER}


}
