package org.example.operators.mutation;

import org.example.model.Specimen;

import java.util.Random;

public class MutationSwap implements IMutation {
    Random random = new Random();

    @Override
    public void mutation(Specimen specimen, double probability) {
        for (int i = 0; i < specimen.getNodeGenome().length; i++) {
            if (random.nextDouble() <= probability)
                swap(specimen.getNodeGenome(), i);
        }
    }

    private void swap(Integer[] genome, int index) {
        if (genome.length < 1)
            return;
        int swapWith = random.nextInt(genome.length);
        while (swapWith == index)
            swapWith = random.nextInt(genome.length);

        Integer tmp = genome[index];
        genome[index] = genome[swapWith];
        genome[swapWith] = genome[tmp];
    }
}
