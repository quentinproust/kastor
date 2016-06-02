package io.kastor.generator.source.comparable;


import io.kastor.annotation.KastorComparable;
import io.kastor.generator.source.GeneratedClass;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import java.util.Objects;

public class ComparableGenerator {

   public static final String CLASS_SUFFIX = "Comparator";

   private Element element;
   private JavaClassSource javaClass;

   public GeneratedClass generateFor(Element element) {
      this.element = element;
      javaClass = createClass(element);

      for (KastorComparable comparable : element.getAnnotationsByType(KastorComparable.class)) {
         generateGetComparatorMethod(comparable);
         generateCompareMethod(comparable);
         generateComparator(comparable);
      }

      new NullSafeCompareMethodGenerator().generateFor(element, javaClass);

      return new GeneratedClass(javaClass);
   }

   private void generateComparator(KastorComparable comparable) {
      JavaClassSource comparatorClass = new InternalComparableGenerator(element, comparable).generate();
      javaClass.addNestedType(comparatorClass);
   }

   private void generateCompareMethod(KastorComparable comparable) {
      MethodSource<JavaClassSource> javaClassSourceMethodSource = javaClass.addMethod()
            .setPublic()
            .setStatic(true)
            .setFinal(true)
            .setName("compare" + comparable.name())
            .setReturnType("int")
            .setBody("return get" + comparable.name() + "Comparator().compare(a, b);");

      javaClassSourceMethodSource.addParameter(element.toString(), "a");
      javaClassSourceMethodSource.addParameter(element.toString(), "b");
   }

   private void generateGetComparatorMethod(KastorComparable comparable) {
      javaClass.addMethod()
            .setName("get" + comparable.name() + "Comparator")
            .setPublic()
            .setStatic(true)
            .setReturnType("java.util.Comparator<" + element + ">")
            .setBody(
                  "return new " + element.getSimpleName() + comparable.name() + "InternalComparator();"
            );
   }

   private JavaClassSource createClass(Element element) {
      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage(element.getEnclosingElement().toString())
            .setName(element.getSimpleName() + CLASS_SUFFIX)
            .addImport(Objects.class);
      return javaClass;
   }

}
