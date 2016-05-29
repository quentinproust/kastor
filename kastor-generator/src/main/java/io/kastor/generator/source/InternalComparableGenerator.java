package io.kastor.generator.source;


import io.kastor.annotation.KastorComparable;
import io.kastor.generator.source.comparable.ComparatorMethodGenerator;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.lang.model.element.Element;

public class InternalComparableGenerator {

   public static final String INTERNAL_CLASS_SUFFIX = "InternalComparator";

   private Element element;
   private KastorComparable comparable;

   public InternalComparableGenerator(Element element, KastorComparable comparable) {
      this.element = element;
      this.comparable = comparable;
   }

   public JavaClassSource generate() {
      JavaClassSource javaClass = createClass(element, comparable);

      new ComparatorMethodGenerator(element, comparable, javaClass).generate();

      return javaClass;
   }

   private JavaClassSource createClass(Element element, KastorComparable comparable) {
      final JavaClassSource comparatorClass = Roaster.create(JavaClassSource.class);
      comparatorClass
            .setPackage(element.getEnclosingElement().toString())
            .setName(element.getSimpleName() + comparable.name() + INTERNAL_CLASS_SUFFIX)
            .setPrivate()
            .setStatic(true)
            .addInterface("java.util.Comparator<" + element + ">");
      return comparatorClass;
   }

}
