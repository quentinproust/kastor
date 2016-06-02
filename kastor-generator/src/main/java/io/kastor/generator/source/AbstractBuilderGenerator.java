package io.kastor.generator.source;

import io.kastor.annotation.KastorBuilder;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBuilderGenerator {

   public static final String CLASS_SUFFIX = "Builder";
   private final Element element;
   private final KastorBuilder annotation;
   private JavaClassSource javaClass;
   private Set<Element> fields;

   public AbstractBuilderGenerator(Element element) {
      this.element = element;
      annotation = element.getAnnotation(KastorBuilder.class);
      javaClass = createClass(element);
      getFieldsToInclude(element);
   }

   protected JavaClassSource generateBuilder() {
      createConstructor();
      generateFieldBuilderMethod();

      return javaClass;
   }

   protected abstract void createConstructor();

   protected Set<Element> getFields() {
      return fields;
   }

   protected JavaClassSource getJavaClass() {
      return javaClass;
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

   public abstract String getFieldSetterMethodBody(Element field);

   private String capitalizeFirstLetter(Element field) {
      String fieldName = field.toString();
      return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
   }

   private JavaClassSource createClass(Element element) {
      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage(element.getEnclosingElement().toString())
            .setName(getClassName(element));
      return javaClass;
   }

   public abstract String getClassName(Element element);

}
