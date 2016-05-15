package io.kastor.generator.source;


import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractMethodGenerator {

   private final TypeElement element;
   private final JavaClassSource javaClass;
   private MethodSource<JavaClassSource> method;

   public AbstractMethodGenerator(TypeElement element, JavaClassSource javaClass) {
      this.element = element;
      this.javaClass = javaClass;
   }

   public void generate() {
      method = getMethodSignature(element, javaClass);
      method.setBody("");

      addMethodStart();

      addFieldOperations(element);

      addMethodEnd();
   }

   protected abstract void addMethodStart();

   protected abstract MethodSource<JavaClassSource> getMethodSignature(TypeElement element, JavaClassSource javaClass);

   protected abstract void addMethodEnd();

   private void addFieldOperations(TypeElement element) {
      getFieldStream(element)
            .map(this::getFieldOperation)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(this::addLine);
   }

   protected Stream<? extends Element> getFieldStream(Element element) {
      return FieldUtils.getFields(element);
   }

   protected abstract Optional<String> getFieldOperation(Element field);

   protected void addLine(String line) {
      method.setBody(method.getBody() + line);
   }

}
