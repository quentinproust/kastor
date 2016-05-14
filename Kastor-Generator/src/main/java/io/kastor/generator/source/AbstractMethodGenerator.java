package io.kastor.generator.source;


import io.kastor.annotation.KastorIdentity;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Optional;

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

      addMethodStart();

      addFieldOperations(element);

      addMethodEnd();
   }

   protected abstract void addMethodStart();

   protected abstract MethodSource<JavaClassSource> getMethodSignature(TypeElement element, JavaClassSource javaClass);

   protected abstract void addMethodEnd();

   private void addFieldOperations(TypeElement element) {
      KastorIdentity annotation = element.getAnnotation(KastorIdentity.class);

      element.getEnclosedElements().stream()
            .filter(e -> e.getKind() == ElementKind.FIELD)
            .filter(e -> annotation.includeFields().length == 0 || Arrays.asList(annotation.includeFields()).contains(e.toString()))
            .filter(e -> !Arrays.asList(annotation.excludeFields()).contains(e.toString()))
            .map(field -> getFieldOperation(field))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(x -> addLine(x));
   }

   protected abstract Optional<String> getFieldOperation(Element field);

   protected void addLine(String line) {
      method.setBody(method.getBody() + line);
   }

}
