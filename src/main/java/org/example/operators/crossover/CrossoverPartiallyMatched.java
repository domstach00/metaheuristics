package org.example.operators.crossover;

import org.example.model.Specimen;
import org.example.support.Utils;

import java.util.HashMap;

public class CrossoverPartiallyMatched implements ICrossover {

    @Override
    public Specimen crossover(Specimen specimen1, Specimen specimen2) {
        Specimen parent1 = new Specimen(specimen1);
        Specimen parent2 = new Specimen(specimen2);

        HashMap<Integer, Integer> parent1Map = new HashMap<>();
        HashMap<Integer, Integer> parent2Map = new HashMap<>();
        int[] startAndFinish = Utils.getStartAndFinishValues(parent1.getNodeGenome().length);
        int startIndex = startAndFinish[0];
        int finishIndex = startAndFinish[1];

        int valueFromParent1;
        int valueFromParent2;
        for (int i = startIndex; i < finishIndex; i++) {
            valueFromParent1 = parent1.getNodeGenome()[i];
            valueFromParent2 = parent2.getNodeGenome()[i];

            parent1Map.put(valueFromParent1, valueFromParent2);
            parent2Map.put(valueFromParent2, valueFromParent1);
        }

//        for (int i = 0; i < startIndex; i++)
//            if (parent2Map.get(parent1.getNodeGenome()[i]) != null)
//                parent1.getNodeGenome()[i] = parent2Map.get(parent1.getNodeGenome()[i]);
//        for (int i = finishIndex; i < parent1.getNodeGenome().length; i++)
//            if (parent2Map.get(parent1.getNodeGenome()[i]) != null)
//                parent1.getNodeGenome()[i] = parent2Map.get(parent1.getNodeGenome()[i]);

        for (int i = startIndex; i < finishIndex; i++)
            parent1.getNodeGenome()[i] = parent1Map.get(parent1.getNodeGenome()[i]);

        for (int i = 0; i < startIndex; i++)
            if (parent2Map.get(parent1.getNodeGenome()[i]) != null)
                parent1.getNodeGenome()[i] = parent2Map.get(parent1.getNodeGenome()[i]);
        for (int i = finishIndex; i < parent1.getNodeGenome().length; i++)
            if (parent2Map.get(parent1.getNodeGenome()[i]) != null)
                parent1.getNodeGenome()[i] = parent2Map.get(parent1.getNodeGenome()[i]);
        return parent1;
    }
}
