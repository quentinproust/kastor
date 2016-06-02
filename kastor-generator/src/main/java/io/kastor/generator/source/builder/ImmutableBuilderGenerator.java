package io.kastor.generator.source.builder;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.lang.model.element.Element;

public class ImmutableBuilderGenerator extends AbstractBuilderGenerator {

   private final Element element;

   public ImmutableBuilderGenerator(Element element) {
      super(element);

      this.element = element;
   }

   @Override
   protected JavaClassSource generateBuilder() {
      JavaClassSource javaClass = super.generateBuilder();

      generateGetTemplateMethod(javaClass);

      return javaClass;
   }

   private void generateGetTemplateMethod(JavaClassSource javaClass) {
      javaClass.addMethod()
            .setPublic()
            .setName("getTemplate")
            .setReturnType(element + CLASS_SUFFIX)
            .setBody("return this;")
            .addAnnotation(Override.class);
   }

   @Override
   protected void createConstructor() {
      getJavaClass().addMethod()
            .setConstructor(true)
            .setPrivate()
            .setBody("super(value);")
            .addParameter(element.getSimpleName() + CLASS_SUFFIX, "value");
   }

   @Override
   public String getFieldSetterMethodBody(Element field) {
      return getClassName(element) + " o = new " + getClassName(element) + "(this);" +
            "o." + field + "=" + field + "; " +
            "return o;";
   }

   @Override
   public String getClassName(Element element) {
      return "Immutable" + element.getSimpleName() + CLASS_SUFFIX;
   }
}
