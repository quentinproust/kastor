package io.kastor.generator;

import io.kastor.generator.handler.HandlerConfiguration;
import io.kastor.generator.source.GeneratedSourceFile;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class KastorAnnotationProcessor extends AbstractProcessor {

   private final HandlerConfiguration config = new HandlerConfiguration();

   public Set<Class> getSupportedAnnotations() {
      return config.getSupportedAnnotations();
   }

   @Override
   public Set<String> getSupportedAnnotationTypes() {
      return getSupportedAnnotations().stream().map(Class::getName).collect(Collectors.toSet());
   }

   @Override
   public synchronized void init(ProcessingEnvironment processingEnv) {
      super.init(processingEnv);

      Logger.withContext(processingEnv);
      GeneratedSourceFile.withContext(processingEnv);
      ProcessingContext.withContext(processingEnv);
   }

   @Override
   public boolean process(Set<? extends TypeElement> annotations,
                          RoundEnvironment roundEnv) {

      final boolean needToProcess =
            !(roundEnv.processingOver() || annotations.isEmpty());
      if (!needToProcess) {
         return false;
      }


      for (Class annotation : getSupportedAnnotations()) {
         try {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            config.getHandler(annotation).handle(elements);
         } catch (Exception ex) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.toString());
            StackTraceElement[] stack = ex.getStackTrace();
            for (StackTraceElement line : stack) {
               processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "\t" + line);
            }
         }
      }

      return false;
   }
}