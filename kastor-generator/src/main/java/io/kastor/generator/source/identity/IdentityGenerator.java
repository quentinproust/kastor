package io.kastor.generator.source.identity;


import io.kastor.generator.source.GeneratedClass;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import javax.lang.model.element.Element;
import java.util.Objects;

public class IdentityGenerator {

   public static final String CLASS_SUFFIX = "Identity";

   public GeneratedClass generateFor(Element element) {
      final JavaClassSource javaClass = createClass(element);
      new EqualityMethodGenerator(element, javaClass).generate();
      new HashcodeMethodGenerator(element, javaClass).generate();

      return new GeneratedClass(javaClass);
   }

   private JavaClassSource createClass(Element element) {
      final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
      javaClass.setPackage(element.getEnclosingElement().toString())
            .setName(element.getSimpleName() + CLASS_SUFFIX)
            .addImport(Objects.class);
      return javaClass;
   }

}
