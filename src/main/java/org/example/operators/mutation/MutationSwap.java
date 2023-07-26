package org.example.operators.mutation;

import org.example.model.Specimen;

import java.util.concurrent.ThreadLocalRandom;

public class MutationSwap implements IMutation {

    @Override
    public <T extends Specimen> T mutation(T specimen, double probability) {

        T newSpecimen = (T) specimen.clone();
        for (int i = 0; i < newSpecimen.getNodeGenome().length; i++) {
            if (ThreadLocalRandom.current().nextDouble() <= probability)
                swap(newSpecimen.getNodeGenome(), i);
        }
        return newSpecimen;
    }

    private void swap(Integer[] genome, int index) {
        if (genome.length < 1)
            return;
        int swapWith;
        do
            swapWith = ThreadLocalRandom.current().nextInt(genome.length);
        while (swapWith == index);

        Integer valueFromIndex = genome[index];
        genome[index] = genome[swapWith];
        genome[swapWith] = valueFromIndex;
    }

//    @Override
//    public Specimen mutation(Specimen specimen, double probability) {
//        Specimen newSpecimen = new Specimen(specimen);
//        for (int i = 0; i < newSpecimen.getNodeGenome().length; i++) {
//            if (ThreadLocalRandom.current().nextDouble() <= probability)
//                swap(newSpecimen.getNodeGenome(), i);
//        }
//        return newSpecimen;
//    }
}
