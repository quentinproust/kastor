package io.kastor.generator.source.hashcode;

import io.kastor.generator.source.FieldUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleFieldHashcode implements FieldHashcodeStrategy {

   private final String hashcode;
   private List<String> types = new ArrayList<>();

   public SimpleFieldHashcode(String type, String hashcode) {
      this.hashcode = hashcode;
      types.add(type);
   }

   public SimpleFieldHashcode(List<String> types, String hashcode) {
      this.hashcode = hashcode;
      this.types.addAll(types);
   }

   @Override
   public boolean isApplicable(Element e) {
      return types.contains(e.asType().toString());
   }

   @Override
   public Optional<String> getFieldHashcode(Element field) {
      return FieldUtils.getFieldGetter(field).map(getter -> MessageFormat.format(hashcode, getter));
   }

}
