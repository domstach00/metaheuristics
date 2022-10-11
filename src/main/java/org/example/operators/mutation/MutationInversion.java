package org.example.operators.mutation;

import org.example.model.Specimen;

import java.util.Random;

public class MutationInversion implements IMutation {

    @Override
    public void mutation(Specimen specimen, double probability) {
        Random random = new Random();

        int indexToStart = -1;
        int indexToFinish = -1;
        int idIndex = 0;

        while (indexToStart == -1 || indexToFinish == -1 || indexToStart == indexToFinish || Math.abs(indexToStart - indexToFinish) == 1) {
            if (random.nextDouble() <= probability)
                indexToStart = idIndex % specimen.getNodeGenome().length;
            else if (random.nextDouble() <= probability)
                indexToFinish = idIndex % specimen.getNodeGenome().length;
            idIndex++;
        }
        if (indexToStart > indexToFinish)
            inverse(specimen.getNodeGenome(), indexToFinish, indexToStart);
        else
            inverse(specimen.getNodeGenome(), indexToStart, indexToFinish);
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
