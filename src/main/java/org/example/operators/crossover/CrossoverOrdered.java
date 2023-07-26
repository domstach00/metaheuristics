package org.example.operators.crossover;

import org.example.model.Specimen;
import org.example.support.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class CrossoverOrdered implements ICrossover {

    @Override
    public <T extends Specimen> T crossover(T specimen1, T specimen2) {

        int[] startEndValues = Utils.getStartAndFinishValues(specimen1.getNodeGenome().length);
        int startIndex = startEndValues[0];
        int endIndex = startEndValues[1];

        T child = (T) specimen1.clone();

        ArrayList<Integer> blueValues = new ArrayList<>(Arrays.asList(child.getNodeGenome()).subList(startIndex, endIndex));
        Integer[] valuesFromParent = specimen2.getNodeGenome().clone();

        int parent2Index = 0;
        for (int i = 0; i < startIndex; i++) {
            if (!blueValues.contains(child.getNodeGenome()[i])) {
                while (blueValues.contains(valuesFromParent[parent2Index]))
                    parent2Index++;
                child.getNodeGenome()[i] = valuesFromParent[parent2Index];
                parent2Index++;
            }
        }

        for (int i = endIndex; i < child.getNodeGenome().length; i++) {
            if (!blueValues.contains(child.getNodeGenome()[i])) {
                while (blueValues.contains(valuesFromParent[parent2Index]))
                    parent2Index++;
                child.getNodeGenome()[i] = valuesFromParent[parent2Index];
                parent2Index++;
            }
        }

        return child;
    }

//    @Override
//    public Specimen crossover(Specimen specimen1, Specimen specimen2) {
//        int[] startEndValues = Utils.getStartAndFinishValues(specimen1.getNodeGenome().length);
//        int startIndex = startEndValues[0];
//        int endIndex = startEndValues[1];
//
//        Specimen child = new Specimen(specimen1);
//
//        ArrayList<Integer> blueValues = new ArrayList<>(Arrays.asList(child.getNodeGenome()).subList(startIndex, endIndex));
//        Integer[] valuesFromParent = specimen2.getNodeGenome().clone();
//
//        int parent2Index = 0;
//        for (int i = 0; i < startIndex; i++) {
//            if (!blueValues.contains(child.getNodeGenome()[i])) {
//                while (blueValues.contains(valuesFromParent[parent2Index]))
//                    parent2Index++;
//                child.getNodeGenome()[i] = valuesFromParent[parent2Index];
//                parent2Index++;
//            }
//        }
//
//        for (int i = endIndex; i < child.getNodeGenome().length; i++) {
//            if (!blueValues.contains(child.getNodeGenome()[i])) {
//                while (blueValues.contains(valuesFromParent[parent2Index]))
//                    parent2Index++;
//                child.getNodeGenome()[i] = valuesFromParent[parent2Index];
//                parent2Index++;
//            }
//        }
//
//        return child;
//    }
}
