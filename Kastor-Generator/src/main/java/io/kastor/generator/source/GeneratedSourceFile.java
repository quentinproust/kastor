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
   private String qualifiedName;
   private Element originalElement;

   public GeneratedSourceFile(String qualifiedName, Element originalElement) {
      this.qualifiedName = qualifiedName;
      this.originalElement = originalElement;
   }

   public static void withContext(final ProcessingEnvironment environment) {
      FILER.set(environment.getFiler());
   }

   public void write(String generatedSourceCode) {
      try {
         JavaFileObject sourceFile = FILER.get().createSourceFile(qualifiedName, originalElement);

         try (
               final Writer writer = sourceFile.openWriter()
         ) {
            writer.write(generatedSourceCode);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
