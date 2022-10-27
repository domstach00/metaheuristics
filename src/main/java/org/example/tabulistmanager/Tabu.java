package org.example.tabulistmanager;

import lombok.Getter;
import org.example.model.Specimen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Tabu implements Iterable<Specimen>{
    @Getter
    private final ArrayList<Specimen> tabu;
    private final HashSet<Integer> tabuGenome;
    private final int tabuSize;

    public Tabu(int tabuSize) {
        this.tabuSize = tabuSize;
        this.tabu = new ArrayList<>(tabuSize);
        this.tabuGenome = new HashSet<>(tabuSize);
    }

    public void add(Specimen specimen) {
        if (isGenomeUsed(specimen.getNodeGenome()))
            return;
        if (tabu.size() + 1 > tabuSize) {
            tabuGenome.remove(Arrays.hashCode(tabu.get(0).getNodeGenome()));
            tabu.remove(0);
        }
        tabu.add(specimen);
        tabuGenome.add(Arrays.hashCode(specimen.getNodeGenome()));
    }

    public Specimen findBest() {
        if (tabu.size() == 0)
            return null;

        Specimen bestSpecimen = tabu.get(0);
        if (tabu.size() == 1)
            return bestSpecimen;

        for (int i = 1; i < tabu.size(); i++)
            if (tabu.get(i).getFitness() > bestSpecimen.getFitness())
                bestSpecimen = tabu.get(i);

        return bestSpecimen;
    }

    public Specimen findWorst() {
        if (tabu.size() == 0)
            return null;

        Specimen worstSpecimen = tabu.get(0);
        if (tabu.size() == 1)
            return worstSpecimen;

        for (int i = 1; i < tabu.size(); i++)
            if (tabu.get(i).getFitness() < worstSpecimen.getFitness())
                worstSpecimen = tabu.get(i);

        return worstSpecimen;
    }

    public double getAverageFitness() {
        return tabu.stream()
                .mapToDouble(Specimen::getFitness)
                .average()
                .orElseThrow();
    }

    public boolean isGenomeUsed(Integer[] genome) {
        return tabuGenome.contains(Arrays.hashCode(genome));
    }

    @Override
    public Iterator<Specimen> iterator() {
        return tabu.iterator();
    }

}
