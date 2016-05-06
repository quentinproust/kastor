package io.kastor.generator.source;

public class GeneratedClass {

   private String qualifiedName;
   private String generatedSource;

   public GeneratedClass(String qualifiedName, String generatedSource) {
      this.qualifiedName = qualifiedName;
      this.generatedSource = generatedSource;
   }

   public String getQualifiedName() {
      return qualifiedName;
   }

   public String getGeneratedSource() {
      return generatedSource;
   }
}
