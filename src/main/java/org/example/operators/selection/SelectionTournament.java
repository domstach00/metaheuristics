package org.example.operators.selection;


import org.example.model.Specimen;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SelectionTournament implements ISelection {
    @Override
    public <T extends Specimen> T selection(ArrayList<T> population, int n) {
        ArrayList<T> tournament = new ArrayList<>(n);

        T bestSpecimen = null;
        int randomIndex;
        while (tournament.size() < n) {
            randomIndex = ThreadLocalRandom.current().nextInt(population.size());
            if (!tournament.contains(population.get(randomIndex))){
                tournament.add(population.get(randomIndex));
                if (bestSpecimen == null)
                    bestSpecimen = population.get(randomIndex);
                if (bestSpecimen.getFitness() < population.get(randomIndex).getFitness())
                    bestSpecimen = population.get(randomIndex);

            }
        }
//        bestSpecimen = tournament.get(0);
//        for (Specimen specimen : tournament) {
//            if (bestSpecimen.getFitness() < specimen.getFitness())
//                bestSpecimen = specimen;
//        }
        assert bestSpecimen != null;
        return (T) bestSpecimen.clone();
    }

//    @Override
//    public Specimen selection(ArrayList<Specimen> population, int n) {
//        ArrayList<Specimen> tournament = new ArrayList<>(n);
//
//        Specimen bestSpecimen = null;
//        int randomIndex;
//        while (tournament.size() < n) {
//            randomIndex = ThreadLocalRandom.current().nextInt(population.size());
//            if (!tournament.contains(population.get(randomIndex))){
//                tournament.add(population.get(randomIndex));
//                if (bestSpecimen == null)
//                    bestSpecimen = population.get(randomIndex);
//                if (bestSpecimen.getFitness() < population.get(randomIndex).getFitness())
//                    bestSpecimen = population.get(randomIndex);
//
//            }
//        }
////        bestSpecimen = tournament.get(0);
////        for (Specimen specimen : tournament) {
////            if (bestSpecimen.getFitness() < specimen.getFitness())
////                bestSpecimen = specimen;
////        }
//        assert bestSpecimen != null;
//        return new Specimen(bestSpecimen);
//    }
}
