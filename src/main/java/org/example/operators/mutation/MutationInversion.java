package org.example.operators.mutation;

import org.example.model.Specimen;
import org.example.support.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class MutationInversion implements IMutation {

    @Override
    public Specimen mutation(Specimen specimen, double probability) {
        if (ThreadLocalRandom.current().nextDouble() > probability)
            return specimen;

        Specimen newSpecimen = new Specimen(specimen);
        int[] startAndEnd = Utils.getStartAndFinishValues(newSpecimen.getNodeGenome().length);
        int indexToStart = startAndEnd[0];
        int indexToFinish = startAndEnd[1];


        inverse(newSpecimen.getNodeGenome(), indexToStart, indexToFinish);


        return newSpecimen;
    }

    // Inverse include startIndex, but exclude endIndex
    private void inverse(Integer[] genome, int startIndex, int endIndex) {
        Integer[] genomeInverse = new Integer[genome.length];

        int genomeIndex = startIndex;
        int inverseIndex = endIndex - 1;
        while (genomeIndex < endIndex) {
            genomeInverse[inverseIndex] = genome[genomeIndex];
            genomeIndex++;
            inverseIndex--;
        }

        if (endIndex - startIndex >= 0)
            System.arraycopy(genomeInverse, startIndex, genome, startIndex, endIndex - startIndex);
    }
}
