package io.kastor.generator;


import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

public class Logger {

   private static final InheritableThreadLocal<Messager> MESSAGER =
         new InheritableThreadLocal<>();

   public static void withContext(final ProcessingEnvironment environment) {
      MESSAGER.set(environment.getMessager());
   }

   public static void logError(String errorMessage) {
      MESSAGER.get().printMessage(Diagnostic.Kind.ERROR, errorMessage);
   }

}
