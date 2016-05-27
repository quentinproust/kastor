package io.kastor.generator.source;

import org.jboss.forge.roaster.model.source.JavaClassSource;

public class GeneratedClass {

   private final String qualifiedName;
   private final String generatedSource;

   public GeneratedClass(JavaClassSource javaClass) {
      this.qualifiedName = javaClass.getQualifiedName();
      this.generatedSource = javaClass.toString();
   }

   public String getQualifiedName() {
      return qualifiedName;
   }

   public String getGeneratedSource() {
      return generatedSource;
   }
}
