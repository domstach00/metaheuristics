package org.example;

import lombok.RequiredArgsConstructor;
import org.example.config.ConfigSA;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;
import org.example.loader.LoaderTTP;
import org.example.logs.CsvRecordSA;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.Specimen;
import org.example.neighborhood.Neighborhood;
import org.example.operators.mutation.IMutation;
import org.example.support.Utils;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class SimulatedAnnealing {

    private final ConfigSA configSA;
    private final DataTTP dataTTP;

    private final IEvaluator evaluator;
    private final IInitialization initialization;
    private final IItemSelector itemSelector;

    private final IMutation mutation;

    private Neighborhood neighborhood;

    private Specimen bestSpecimen;
    private Specimen currentSpecimen;

    private int currentIteration = 0;


    public void start() {
        File file = new File(Utils.getInputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        currentSpecimen = init(dataTTP);
        bestSpecimen = currentSpecimen;

        neighborhood = new Neighborhood(mutation, evaluator);

        run();
    }

    public void startTest() {

    }

    private Specimen init(DataTTP dataTTP) {
        Specimen specimen = new Specimen(dataTTP);

        specimen.init(initialization, itemSelector);
        specimen.eval(evaluator);

        return specimen;
    }

    private void run() {
        double currentTemp = configSA.getStartTemp();

        while (currentIteration < configSA.getIteration()) {
            runIteration(currentTemp);
            currentTemp *= configSA.getAnnealingRate();

            log();
            currentIteration++;
            System.out.println("Iter: " + currentIteration + "\tTemp: " + currentTemp);
        }
        finish();
    }

    private void runIteration(double currentTemp) {
        Specimen bestNeighbor = neighborhood.bestNeighbor(currentSpecimen, configSA.getNSize());


        if (bestNeighbor.getFitness() > bestSpecimen.getFitness())
            bestSpecimen = bestNeighbor;

        if (currentSpecimen.getFitness() < bestNeighbor.getFitness())
            currentSpecimen = bestNeighbor;
        else if (ThreadLocalRandom.current().nextDouble() <
                Math.exp( -Math.abs(currentSpecimen.getFitness() - bestNeighbor.getFitness()) / currentTemp ))
            currentSpecimen = bestNeighbor;
    }

    private void log() {
        CsvRecordSA csvRecordSA = new CsvRecordSA(currentIteration, bestSpecimen.getFitness(), currentSpecimen.getFitness());
        Logger.log(csvRecordSA);

    }

    private void finish() {

    }
}
