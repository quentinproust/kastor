package io.kastor.examples;

import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractIdentityTest<T> {

   private SoftAssertions softAssert;

   protected abstract Stream<Supplier<T>> getEqualityTestValues();

   protected abstract Stream<HashcodeExpectation> getHashcodeTestValues();

   protected abstract boolean equals(T a, T b);

   protected abstract int hashcode(T value);

   @Before
   public void setUp() {
      softAssert = new SoftAssertions();
   }

   @After
   public void checkExpectation() {
      softAssert.assertAll();
   }

   @Test
   public void sameValueShouldBeEqual() {
      getEqualityTestValues().forEach(x -> softAssert.assertThat(equals(x.get(), x.get())).as("%s == %s", x, x).isTrue());
   }

   @Test
   public void differentValuesAreNotEqual() {
      getCombinaisons(getEqualityTestValues()).forEach(x ->
            softAssert.assertThat(equals(x.getA().get(), x.getB().get())).as("%s != %s", x.getA().get(), x.getB().get()).isFalse());
   }

   private Stream<Pair<Supplier<T>>> getCombinaisons(Stream<Supplier<T>> values) {
      List<Supplier<T>> vs = values.collect(Collectors.toList());
      return vs.stream().flatMap(x -> vs.stream().filter(y -> x != y).map(y -> new Pair<>(x, y)));
   }

   @Test
   public void aValueAndNullAreNotEqual() {
      getEqualityTestValues().forEach(x -> softAssert.assertThat(equals(x.get(), null)).as("%s != null", x).isFalse());
   }

   @Test
   public void nullAndAValueAreNotEqual() {
      getEqualityTestValues().forEach(x -> softAssert.assertThat(equals(null, x.get())).as("null == %s", x).isFalse());
   }

   @Test
   public void equalsWithBothArgsNullIsFalse() {
      assertThat(equals(null, null)).isTrue();
   }

   @Test
   public void hashCodeWithValueGeneratePositiveInteger() {
      getHashcodeTestValues().forEach(x -> softAssert.assertThat(x.getValueHashcode())
            .as("%s hashcode is %s", x.getValue(), x.getExpectedHashcode())
            .isEqualTo(x.getExpectedHashcode()));
   }

   @Test
   public void hashCodeWithNullIsEqualToZero() {
      assertThat(hashcode(null)).isZero();
   }

   protected HashcodeExpectation<T> expectHashcode(T value, int expected) {
      return new HashcodeExpectation(value, hashcode(value), expected);
   }

   protected static class HashcodeExpectation<T> {
      private final T value;
      private final int valueHashcode;
      private final int expectedHashcode;

      private HashcodeExpectation(T value, int valueHashcode, int expectedHashcode) {
         this.value = value;
         this.valueHashcode = valueHashcode;
         this.expectedHashcode = expectedHashcode;
      }

      private T getValue() {
         return value;
      }

      private int getValueHashcode() {
         return valueHashcode;
      }

      private int getExpectedHashcode() {
         return expectedHashcode;
      }
   }

   private static class Pair<T> {
      private T a, b;

      public Pair(T a, T b) {
         this.a = a;
         this.b = b;
      }

      public T getA() {
         return a;
      }

      public T getB() {
         return b;
      }
   }
}