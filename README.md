# kastor

[![Build Status](https://travis-ci.org/quentinproust/kastor.svg?branch=master)](https://travis-ci.org/quentinproust/kastor)

Kastor is Java [Annotation Processor](http://docs.oracle.com/javase/7/docs/api/javax/annotation/processing/Processor.html) generating boring but useful code. 

The advantages of using Kastor : 
* It doesn't modify your code. 
* There is no dependency at runtime, only at compile. 
* If at one point, you decide to stop using Kastor, you only need to remove the annotations and copy the generated java classes in your source code.
* It doesn't use reflection to access property values. It just use getter.

For now, you can use 3 annotations : 
* @KastorIdentity
* @KastorComparable
* @KastorBuilder

## Setup
### Maven
TODO

### Graddle
TODO

## Identity

@KastorIdentity will generate a class with null-safe equals and hashCode implementation.

Example class `Hero` and `Weapon`:
```java
@KastorIdentity
public class Hero {
  private String name;
  private Weapon weapon;
  // getter setter ...
  
  // Equals and hashCode will use the generated class
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Hero)) return false;
    
    return HeroIdentity.equals(this, (Hero) o);
  }
  
  @Override
  public int hashCode() {
    return HeroIdentity.hashCode(this);
  }
}

@KastorIdentity
public class Weapon {
  private int power;
  private String type;
  // getter setter ...
  
  // Equals and hashCode will use the generated class
  // The code is similar to the Hero class
  ...
}
```
Generated class `HeroIdentity` and `WeaponIdentity` :
```java
public class HeroIdentity {
  public static boolean equals(Hero a, Hero b) { ... }
	public static int hashCode(Hero o) { ... }
}

public class WeaponIdentity {
  public void equals(Weapon a, Weapon b) { ... }
  public int hashCode() { ... }
}
```

The @KastorIdentity allows to include or exclude properties. For exemple, we might want the identity of a hero to be defined only by its name. All the examples below are equivalent :
```java
@KastorIdentity(includeFields = {"name"})
@KastorIdentity(excludeFields = {"weapon"})
// It's weird but you can do it if you want
@KastorIdentity(includeFields = {"name", "weapon"}, excludeFields = {"weapon"})
```

If you try to include or exclude a field which doesn't exist, you'll get a compilation error. It's useful if you mispelled the name by mistake or if you refactored your code and the @KastorIdentity config was missed in the process.

## Comparable

@KastorComparable will generate a class with null-safe comparator implementation.

Example class `Hero` and `Weapon` same as before but with @KastorComparable config :
```java
@KastorComparable
public class Hero { ... }

@KastorComparable {
public class Weapon { ... }
```
By default, it will compare all properties. 

```java
public class HeroComparator {
	public static Comparator<Hero> getComparator() {
		return new HeroInternalComparator();
	}

	public static final int compare(Hero a, Hero b) {
		return getComparator().compare(a, b);
	}

	private static class HeroInternalComparator implements Comparator<Hero> {
		public int compare(Hero a, Hero b) { ... }
	}
}

// Usage :
Hero a = ..., b = ...;
HeroComparator.compare(a, b);
HeroComparator.getComparator().compare(a, b);
Collections.sort(heros, HeroComparator.getComparator());
Collections.sort(heros, HeroComparator::compare);
```

You can obviously specify the properties you want to compare and the order - like includeFields in KastorIdentity, you'll get a compilation error if a field doesn't exist :

```java
@KastorComparable(order = {"weapon", "name"})
public class Hero { ... }
```

You can also define multiple comparators :

```java
@KastorComparable(order = {"weapon", "name"})
@KastorComparable(name = "ByWeapon", order = {"weapon"})
public class Hero { ... }
```

```java

public class HeroComparator {
	public static Comparator<Hero> getComparator() {
		return new HeroInternalComparator();
	}

	public static final int compare(Hero a, Hero b) {
		return getComparator().compare(a, b);
	}

	private static class HeroInternalComparator implements Comparator<Hero> {
		public int compare(Hero a, Hero b) { ... }
	}
	
	public static Comparator<Hero> getByWeaponComparator() {
    return new HeroByWeaponInternalComparator();
  }

  public static final int compareByWeapon(Hero a, Hero b) {
    return getByWeaponComparator().compare(a, b);
  }

  private static class HeroByWeaponInternalComparator implements Comparator<Hero> {
    public int compare(Hero a, Hero b) { ... }
  }
}

Usage : 
HeroComparator.compareByWeapon(a, b);
or
HeroComparator.getByWeaponComparator().compare(a, b);
```

## Builder

@KastorBuilder will generate a class which can be used to create objects using the Builder pattern.
The annotation can be used in the same way as @KastorIdentity.

It will generate 3 builders for each class :
* a default builder, for example HeroBuilder. When you set a property, it change the current builder.
* an immutable builder. When you set a property, it create a new builder, leaving the current one unchanged. It can create a lot of builder instance as each call to a method will give a new builder.
* a template builder. When you set a property, you'll get a new default builder, leaving the template unchanged. It create less instances thant immutable builder. Note the creating a template from an immutable builder will just give you the current builder.

Examples with the `Weapon` class : 
```java
@KastorBuilder
public class Weapon {
  private int power;
  private String type;
  // getter setter ...
}

// Default builder :
Weapon sword = new WeaponBuilder().withType("sword").withPower(42).build();

// Immutable builder :
WeaponBuilder swordBuilder = new WeaponBuilder().getImmutable().withType("sword"); // 3 builders created on this line
Weapon a = swordBuilder.withPower(42).build(); // The swordBuilder is not modified // +1 builder
Weapon b = swordBuilder.withPower(59).build(); // The swordBuilder is not modified // +1 builder

// Template builder :
WeaponBuilder swordBuilder = new WeaponBuilder().withType("sword").getTemplate();
Weapon a = swordBuilder.withPower(42).build(); // The swordBuilder is not modified
Weapon b = swordBuilder.withPower(59).build(); // The swordBuilder is not modified

```
