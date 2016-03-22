package io.kastor.generator;


import io.kastor.annotation.Complexity;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes(value = "*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestClass extends AbstractProcessor {

   @Override
   public boolean process(Set<? extends TypeElement> annotations,
                          RoundEnvironment roundEnv) {

      for (Element elem : roundEnv.getElementsAnnotatedWith(Complexity.class)) {
         Complexity complexity = elem.getAnnotation(Complexity.class);
         String message = "annotation found in " + elem.getSimpleName()
               + " with complexity " + complexity.value();
         processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
         try {
            generate(elem, complexity);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      }

      return false;
   }

   public final void generate(Element element, Complexity complexity) throws Exception {
      final TypeElement typeElement = (TypeElement) element;
      final JavaFileObject file = createSourceFile(typeElement);
      try (
            final Writer writer = file.openWriter();
            final PrintWriter printWriter = new PrintWriter(writer)
      ) {
         final String sourceCode = "" +
               "public class " + typeElement.getSimpleName() + "Builder {" +
               "public void build() {}" +
               "}";
         printWriter.write(sourceCode);
         printWriter.flush();
         printWriter.close();
      }
   }

   public JavaFileObject createSourceFile(
         final TypeElement baseElement) throws Exception {
      final Filer filer = processingEnv.getFiler();

      return filer.createSourceFile(baseElement.getSimpleName() + "Builder", baseElement);
   }
}