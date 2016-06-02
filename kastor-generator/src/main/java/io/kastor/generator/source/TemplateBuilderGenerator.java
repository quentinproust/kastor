package io.kastor.generator.source;

import javax.lang.model.element.Element;

public class TemplateBuilderGenerator extends AbstractBuilderGenerator {

   private final Element element;

   public TemplateBuilderGenerator(Element element) {
      super(element);
      this.element = element;
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
      return element.getSimpleName() + CLASS_SUFFIX + " o = new " + element.getSimpleName() + CLASS_SUFFIX + "(this);" +
            "o." + field + "=" + field + ";" +
            "return o;";
   }

   @Override
   public String getClassName(Element element) {
      return "Template" + element.getSimpleName() + CLASS_SUFFIX;
   }
}
