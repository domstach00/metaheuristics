package org.example.operators.selection;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.example.model.Specimen;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SelectionRoulette implements ISelection {
    @Override
    public Specimen selection(ArrayList<Specimen> population, int n) {
        double minFitness = Double.MAX_VALUE;

        for (Specimen specimen : population)
            if (minFitness > specimen.getFitness())
                minFitness = specimen.getFitness();

        ArrayList<Specimen> copy = new ArrayList<>(population);

        double finalMinFitness = minFitness - 1;
        var specimenWeights = copy.stream()
                .map(specimen ->
                        new Pair<>(specimen, specimen.getFitness() - finalMinFitness))
                .collect(Collectors.toList());


        ArrayList<Specimen> chosenSpecimens = new ArrayList<>(n);
        while (chosenSpecimens.size() < n) {
            Specimen chosen = new EnumeratedDistribution<>(specimenWeights).sample();
            if (!chosenSpecimens.contains(chosen))
                chosenSpecimens.add(chosen);
        }

        Specimen bestSpecimen = chosenSpecimens.get(0);
        for (Specimen specimen : chosenSpecimens)
            if (bestSpecimen.getFitness() < specimen.getFitness())
                bestSpecimen = specimen;
        return bestSpecimen;
    }
}
