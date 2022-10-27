package org.example;

import lombok.RequiredArgsConstructor;
import org.example.config.ConfigTS;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;
import org.example.loader.LoaderTTP;
import org.example.logs.CsvRecordTS;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.Specimen;
import org.example.neighborhood.Neighborhood;
import org.example.operators.mutation.IMutation;
import org.example.support.Utils;
import org.example.tabulistmanager.Tabu;

import java.io.File;
import java.util.ArrayList;

@RequiredArgsConstructor
public class TabuSearch {

    private final ConfigTS configTS;
    private final DataTTP dataTTP;

    private final IEvaluator evaluator;
    private final IInitialization initialization;
    private final IItemSelector itemSelector;

    private final IMutation mutation;

    private Neighborhood neighborhood;

    private Tabu tabu;

    private Specimen bestSpecimen;

    private Specimen currentSpecimen;

    private int currentIteration = 0;


    public void start() {
        tabu = new Tabu(configTS.getTabuSize());

        File file = new File(Utils.getInputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        currentSpecimen = init(dataTTP);
        bestSpecimen = currentSpecimen;

        neighborhood = new Neighborhood(mutation, evaluator);

        run();

    }

    public void startTest() {
        tabu = new Tabu(configTS.getTabuSize());

        File file = new File(Utils.getInputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        currentSpecimen = init(dataTTP);
        bestSpecimen = currentSpecimen;

        log();
    }

    private Specimen init(DataTTP dataTTP) {
        Specimen specimen = new Specimen(dataTTP);

        specimen.init(initialization, itemSelector);
        specimen.eval(evaluator);

        return specimen;
    }

    private void run() {
        while (currentIteration < configTS.getIteration()) {
            runIteration();
//            log();
            System.out.println(currentIteration++);
        }
        finish();
    }

    private void runIteration() {
        // Get neighbors
        ArrayList<Specimen> neighbors = neighborhood.neighbors(currentSpecimen, tabu, configTS.getNSize());

        // Find best neighbor
        Specimen bestNeighbor = neighbors.get(0);
        for (int i = 1; i < neighbors.size(); i++)
            if (neighbors.get(i).getFitness() > bestNeighbor.getFitness())
                bestNeighbor = neighbors.get(i);

        // Check if there is new best specimen
        if (bestSpecimen.getFitness() < bestNeighbor.getFitness())
            bestSpecimen = bestNeighbor;

        // Used specimen add to tabu
        tabu.add(currentSpecimen);

        // Move
        currentSpecimen = bestNeighbor;

        Specimen worstNeighbor = neighbors.get(0);
        for (int i = 1; i < neighbors.size(); i++)
            if (neighbors.get(i).getFitness() < worstNeighbor.getFitness())
                worstNeighbor = neighbors.get(i);

        double avgNeighbor = neighbors.stream()
                .mapToDouble(Specimen::getFitness)
                .average()
                .orElseThrow();

        CsvRecordTS csvRecordTS = new CsvRecordTS(currentIteration, bestSpecimen, bestNeighbor, worstNeighbor, avgNeighbor);
        Logger.log(csvRecordTS);
    }

    private void finish() {
        logFinalAnalysis();
    }

    private void log() {
        CsvRecordTS csvRecordTS = new CsvRecordTS(currentIteration, bestSpecimen, currentSpecimen, tabu);
        Logger.log(csvRecordTS);
    }

    private void log2() {
        CsvRecordTS csvRecordTS = new CsvRecordTS(currentIteration, bestSpecimen, currentSpecimen, tabu);
        Logger.log(csvRecordTS);
    }

    private void logFinalAnalysis() {

    }

}
