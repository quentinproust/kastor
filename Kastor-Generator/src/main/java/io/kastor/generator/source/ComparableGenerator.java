package io.kastor.generator.source;


import io.kastor.generator.source.comparable.ComparatorMethodGenerator;
import io.kastor.generator.source.comparable.NullSafeCompareMethodGenerator;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.TypeElement;
import java.util.Objects;

public class ComparableGenerator {

   public static final String CLASS_SUFFIX = "Comparator";
   public static final String INTERNAL_CLASS_SUFFIX = "Internal" + CLASS_SUFFIX;

   public GeneratedClass generateFor(TypeElement element) {
      final JavaClassSource javaClass = createClass(element);

      generateGetComparatorMethod(element, javaClass);
      generateCompareMethod(element, javaClass);

      generateComparator(element, javaClass);

      return new GeneratedClass(javaClass);
   }

   private void generateComparator(TypeElement element, JavaClassSource javaClass) {
      final JavaClassSource comparatorClass = createInternalComparatorClass(element);

      new ComparatorMethodGenerator(element, comparatorClass).generate();
      new NullSafeCompareMethodGenerator().generateFor(element, comparatorClass);

      javaClass.addNestedType(comparatorClass);
   }

   private void generateCompareMethod(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> javaClassSourceMethodSource = javaClass.addMethod()
            .setPublic()
            .setStatic(true)
            .setFinal(true)
            .setName("compare")
            .setReturnType("int")
            .setBody("return getComparator().compare(a, b);");

      javaClassSourceMethodSource.addParameter(element.toString(), "a");
      javaClassSourceMethodSource.addParameter(element.toString(), "b");
   }

   private void generateGetComparatorMethod(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> method = javaClass.addMethod()
            .setName("getComparator")
            .setPublic()
            .setStatic(true)
            .setReturnType("java.util.Comparator<" + element + ">")
            .setBody(
                  "return new " + element.getSimpleName() + "InternalComparator();"
            );
   }

   private JavaClassSource createInternalComparatorClass(TypeElement element) {
      final JavaClassSource comparatorClass = Roaster.create(JavaClassSource.class);
      comparatorClass
            .setPackage(element.getEnclosingElement().toString())
            .setName(element.getSimpleName() + INTERNAL_CLASS_SUFFIX)
            .setPrivate()
            .setStatic(true)
            .addInterface("java.util.Comparator<" + element + ">");
      return comparatorClass;
   }

   private JavaClassSource createClass(TypeElement element) {
      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage(element.getEnclosingElement().toString())
            .setName(element.getSimpleName() + CLASS_SUFFIX)
            .addImport(Objects.class);
      return javaClass;
   }

}
