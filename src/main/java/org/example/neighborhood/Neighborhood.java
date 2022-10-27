package org.example.neighborhood;

import lombok.RequiredArgsConstructor;
import org.example.evaluator.IEvaluator;
import org.example.model.Specimen;
import org.example.operators.mutation.IMutation;
import org.example.tabulistmanager.Tabu;

import java.util.ArrayList;

@RequiredArgsConstructor
public class Neighborhood {
    private final IMutation mutation;
    private final IEvaluator evaluator;

    public ArrayList<Specimen> neighbors(Specimen currentSpecimen, Tabu tabu, int nSize) {
        Specimen current = new Specimen(currentSpecimen);
        ArrayList<Specimen> foundNeighbours = new ArrayList<>(nSize);

        while (foundNeighbours.size() < nSize) {
            Specimen neighbour = mutation.mutation(current, 1);
            if (!tabu.isGenomeUsed(neighbour.getNodeGenome())) {
                neighbour.eval(evaluator);
                foundNeighbours.add(neighbour);
            }
        }

        return foundNeighbours;
    }
}
