package io.kastor.generator.source.comparable;

import javax.lang.model.element.Element;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FieldPositionComparator implements Comparator<Element> {

   private Map<String, Integer> positions = new HashMap<>();

   public FieldPositionComparator(String[] orderedFields) {
      for (int i = 0; i < orderedFields.length; i++) {
         String o = orderedFields[i];
         positions.put(o, i);
      }
   }

   @Override
   public int compare(Element a, Element b) {
      int positionA = positions.getOrDefault(a.toString(), Integer.MAX_VALUE);
      int positionB = positions.getOrDefault(b.toString(), Integer.MAX_VALUE);
      return Integer.compare(positionA, positionB);
   }
}
