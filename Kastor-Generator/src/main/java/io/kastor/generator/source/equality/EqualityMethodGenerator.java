package io.kastor.generator.source.equality;


import io.kastor.generator.source.AbstractMethodGenerator;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Optional;

public class EqualityMethodGenerator extends AbstractMethodGenerator {

   public EqualityMethodGenerator(TypeElement element, JavaClassSource javaClass) {
      super(element, javaClass);
   }

   @Override
   protected MethodSource<JavaClassSource> getMethodSignature(TypeElement element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> equalityMethod = javaClass.addMethod()
            .setPublic()
            .setStatic(true)
            .setReturnType("boolean")
            .setName("equals");

      equalityMethod.addParameter(element.getSimpleName().toString(), "a");
      equalityMethod.addParameter(element.getSimpleName().toString(), "b");

      return equalityMethod;
   }

   @Override
   protected void addMethodStart() {
      addLine("if (a == b) { return true; }");
      addLine("if (a == null || b == null) { return false; }");
   }

   @Override
   protected void addMethodEnd() {
      addLine("return true;");
   }

   @Override
   protected Optional<String> getFieldOperation(Element field) {
      return FieldEqualityFactory.get(field).getFieldComparisons(field)
            .map(c -> "if (!(" + c + ")) { return false; }");
   }
}
