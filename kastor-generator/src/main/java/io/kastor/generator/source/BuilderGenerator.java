package io.kastor.generator.source;

import io.kastor.annotation.KastorBuilder;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BuilderGenerator {

   public static final String CLASS_SUFFIX = "Builder";
   private final Element element;
   private final KastorBuilder annotation;
   private JavaClassSource javaClass;
   private Set<Element> fields;

   public BuilderGenerator(Element element) {
      this.element = element;
      annotation = element.getAnnotation(KastorBuilder.class);
      javaClass = createClass(element);
      getFieldsToInclude(element);
   }

   public GeneratedClass generate() {
      createConstructor();
      copyFields();
      generateFieldBuilderMethod();
      generateBuildMethod();
      generateTemplateBuilder();
      generateImmutableBuilder();

      return new GeneratedClass(javaClass);
   }

   private void generateTemplateBuilder() {
      JavaClassSource templateBuilderClass = new TemplateBuilderGenerator(element).generate();
      templateBuilderClass.extendSuperType(javaClass);
      javaClass.addNestedType(templateBuilderClass);

      javaClass.addMethod()
            .setPublic()
            .setName("getTemplate")
            .setReturnType(element + CLASS_SUFFIX)
            .setBody("return new " + templateBuilderClass.getName() + "(this);");
   }

   private void generateImmutableBuilder() {
      JavaClassSource immutableBuilderClass = new ImmutableBuilderGenerator(element).generate();
      immutableBuilderClass.extendSuperType(javaClass);
      javaClass.addNestedType(immutableBuilderClass);

      javaClass.addMethod()
            .setPublic()
            .setName("getImmutable")
            .setReturnType(element + CLASS_SUFFIX)
            .setBody("return new " + immutableBuilderClass.getName() + "(this);");
   }

   private void getFieldsToInclude(Element element) {
      Set<String> fieldNames = getFieldNames(element);

      fields = FieldUtils.getFields(element)
            .filter(x -> fieldNames.contains(x.toString()))
            .collect(Collectors.toSet());
   }

   private Set<String> getFieldNames(Element element) {
      Set<String> fieldNames = new HashSet<>();
      fieldNames.addAll(Arrays.asList(annotation.includeFields()));
      if (fieldNames.isEmpty()) {
         fieldNames.addAll(FieldUtils.getFieldNames(element));
      }
      fieldNames.removeAll(Arrays.asList(annotation.excludeFields()));
      return fieldNames;
   }

   private void createConstructor() {
      javaClass.addMethod()
            .setConstructor(true)
            .setPublic()
            .setBody("");

      javaClass.addMethod()
            .setConstructor(true)
            .setPrivate()
            .setBody(fields.stream().map(f -> "this." + f + " = value." + f + ";").collect(Collectors.joining()))
            .addParameter(element + CLASS_SUFFIX, "value");
   }

   private void generateBuildMethod() {
      javaClass.addMethod()
            .setPublic()
            .setName("build")
            .setReturnType(element.toString())
            .setBody(
                  element + " x = new " + element + "();"
                        + fields.stream().map(y -> "x." + FieldUtils.getFieldSetter(y).get() + "(this." + y + ");").collect(Collectors.joining())
                        + ";return x;"
            );
   }

   private void generateFieldBuilderMethod() {
      FieldUtils.getFields(element).forEach(
            x -> javaClass.addMethod()
                  .setPublic()
                  .setName("with" + capitalizeFirstLetter(x))
                  .setReturnType(element + CLASS_SUFFIX)
                  .setBody(getFieldSetterMethodBody(x))
                  .addParameter(x.asType().toString(), x.toString())
      );
   }

   private String getFieldSetterMethodBody(Element x) {
      return "this." + x + "=" + x + "; return this;";
   }

   private String capitalizeFirstLetter(Element field) {
      String fieldName = field.toString();
      return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
   }

   private void copyFields() {
      fields.stream().forEach(
            x -> javaClass.addField()
                  .setProtected()
                  .setName(x.toString())
                  .setType(x.asType().toString())
      );
   }

   private JavaClassSource createClass(Element element) {
      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage(element.getEnclosingElement().toString())
            .setName(element.getSimpleName() + CLASS_SUFFIX);
      return javaClass;
   }

}