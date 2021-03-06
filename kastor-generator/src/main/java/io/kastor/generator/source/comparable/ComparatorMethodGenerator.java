package io.kastor.generator.source.comparable;

import io.kastor.annotation.KastorComparable;
import io.kastor.generator.source.AbstractMethodGenerator;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class ComparatorMethodGenerator extends AbstractMethodGenerator {

   private final FieldComparisonFactory fieldOperationFactory = new FieldComparisonFactory();
   private final String[] orderedFields;

   public ComparatorMethodGenerator(Element element, KastorComparable comparable, JavaClassSource javaClass) {
      super(element, javaClass);

      orderedFields = comparable.order();
   }

   @Override
   protected MethodSource<JavaClassSource> getMethodSignature(Element element, JavaClassSource javaClass) {
      MethodSource<JavaClassSource> equalityMethod = javaClass.addMethod()
            .setPublic()
            .setReturnType("int")
            .setName("compare");

      equalityMethod.addParameter(element.toString(), "a");
      equalityMethod.addParameter(element.toString(), "b");

      return equalityMethod;
   }

   @Override
   protected void addMethodStart() {
      addLine("if (a == b) return 0;");
      addLine("if (a == null) return -1;");
      addLine("if (b == null) return 1;");
      addLine("int result = 0;");
   }

   @Override
   protected void addMethodEnd() {
      addLine("return result;");
   }

   @Override
   protected Stream<? extends Element> getFieldStream(Element element) {
      return super.getFieldStream(element).filter(this::filterFields)
            .sorted(new FieldPositionComparator(orderedFields));
   }

   private boolean filterFields(Element field) {
      return orderedFields.length == 0
            || Arrays.asList(orderedFields).contains(field.toString());
   }

   @Override
   protected Optional<String> getFieldOperation(Element field) {
      return fieldOperationFactory.getFieldOperation(field)
            .map(x -> "if (result == 0) { result = " + x + "; }");
   }
}
