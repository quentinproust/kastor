package io.kastor.generator.source.equality;

import java.util.List;

public class NullableFieldEquality extends SimpleFieldEquality {

   public NullableFieldEquality(String type, String comparison) {
      super(type, "!(a.{0} == b.{0} || (a.{0} != null && " + comparison + "))");
   }

   public NullableFieldEquality(List<String> types, String comparison) {
      super(types, "!(a.{0} == b.{0} || (a.{0} != null && " + comparison + "))");
   }

}
