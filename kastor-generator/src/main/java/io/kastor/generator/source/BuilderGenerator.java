package io.kastor.generator.source;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.lang.model.element.Element;
import java.util.stream.Collectors;

public class BuilderGenerator extends AbstractBuilderGenerator {

   private final Element element;
   private final JavaClassSource javaClass;

   public BuilderGenerator(Element element) {
      super(element);

      this.element = element;
      this.javaClass = getJavaClass();
   }

   public GeneratedClass generate() {
      JavaClassSource generatedClass = generateBuilder();

      copyFields();
      generateBuildMethod();
      generateImmutableBuilder();
      generateTemplateBuilder();

      return new GeneratedClass(generatedClass);
   }

   private void copyFields() {
      getFields().stream().forEach(
            x -> javaClass.addField()
                  .setProtected()
                  .setName(x.toString())
                  .setType(x.asType().toString())
      );
   }

   private void generateBuildMethod() {
      javaClass.addMethod()
            .setPublic()
            .setName("build")
            .setReturnType(element.toString())
            .setBody(
                  element + " x = new " + element + "();"
                        + getFields().stream().map(y -> "x." + FieldUtils.getFieldSetter(y).get() + "(this." + y + ");").collect(Collectors.joining())
                        + ";return x;"
            );
   }

   @Override
   protected void createConstructor() {
      javaClass.addMethod()
            .setConstructor(true)
            .setPublic()
            .setBody("");

      javaClass.addMethod()
            .setConstructor(true)
            .setPrivate()
            .setBody(getFields().stream().map(f -> "this." + f + " = value." + f + ";").collect(Collectors.joining()))
            .addParameter(element + CLASS_SUFFIX, "value");
   }

   private void generateTemplateBuilder() {
      JavaClassSource templateBuilderClass = new TemplateBuilderGenerator(element).generateBuilder();
      templateBuilderClass.extendSuperType(javaClass);
      javaClass.addNestedType(templateBuilderClass);

      javaClass.addMethod()
            .setPublic()
            .setName("getTemplate")
            .setReturnType(element + CLASS_SUFFIX)
            .setBody("return new " + templateBuilderClass.getName() + "(this);");
   }

   private void generateImmutableBuilder() {
      JavaClassSource immutableBuilderClass = new ImmutableBuilderGenerator(element).generateBuilder();
      immutableBuilderClass.extendSuperType(javaClass);
      javaClass.addNestedType(immutableBuilderClass);

      javaClass.addMethod()
            .setPublic()
            .setName("getImmutable")
            .setReturnType(element + CLASS_SUFFIX)
            .setBody("return new " + immutableBuilderClass.getName() + "(this);");
   }

   @Override
   public String getFieldSetterMethodBody(Element field) {
      return "this." + field + "=" + field + "; return this;";
   }

   @Override
   public String getClassName(Element element) {
      return element.getSimpleName() + CLASS_SUFFIX;
   }

}
