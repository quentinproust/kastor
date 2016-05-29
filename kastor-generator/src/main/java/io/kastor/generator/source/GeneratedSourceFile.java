package io.kastor.generator.source;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

public class GeneratedSourceFile {

   private static final InheritableThreadLocal<Filer> FILER =
         new InheritableThreadLocal<>();

   private final GeneratedClass generatedClass;
   private final Element originalElement;

   public GeneratedSourceFile(GeneratedClass generatedClass, Element originalElement) {
      this.generatedClass = generatedClass;
      this.originalElement = originalElement;
   }

   public static void withContext(final ProcessingEnvironment environment) {
      FILER.set(environment.getFiler());
   }

   public void write() {
      try {
         JavaFileObject sourceFile = FILER.get().createSourceFile(generatedClass.getQualifiedName(), originalElement);

         try (
               final Writer writer = sourceFile.openWriter()
         ) {
            writer.write(generatedClass.getGeneratedSource());
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
