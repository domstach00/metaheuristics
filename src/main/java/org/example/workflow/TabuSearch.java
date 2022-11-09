package org.example.workflow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.config.Config;
import org.example.config.ConfigLog;
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
import org.example.tabulistmanager.Tabu;

import java.io.File;
import java.util.ArrayList;

@RequiredArgsConstructor
public class TabuSearch implements IWorkFLow {

    private final ConfigTS configTS;
    private final DataTTP dataTTP;

    private final IEvaluator evaluator;
    private final IInitialization initialization;
    private final IItemSelector itemSelector;

    @Getter
    private final IMutation mutation;

    private final ConfigLog configLog;

    private Neighborhood neighborhood;

    private Tabu tabu;

    private Specimen bestSpecimen;

    private Specimen currentSpecimen;

    private int currentIteration = 0;


    @Override
    public Config getConfig() {
        return configTS;
    }

    @Override
    public String getAlgName() {
        return "TS";
    }

    @Override
    public String getOperationsToLogDir() {
        return this.getMutation().getMutationName();
    }

    @Override
    public void start() {
        configLog.formatLogPath(getAlgName(), getOperationsToLogDir());
        tabu = new Tabu(configTS.getTabuSize());

        File file = new File(configLog.getInputPath());
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        currentSpecimen = init(dataTTP);
        bestSpecimen = currentSpecimen;

        neighborhood = new Neighborhood(mutation, evaluator);

        run();

    }

    public void startTest() {
        tabu = new Tabu(configTS.getTabuSize());

        File file = new File(configLog.getInputPath());
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        currentSpecimen = init(dataTTP);
        bestSpecimen = currentSpecimen;

//        log();
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
            System.out.println(getAlgName() + ": " + currentIteration++);
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

        log(bestNeighbor, worstNeighbor, avgNeighbor);
    }

    private void finish() {
        logFinalAnalysis();
    }

    private void log() {
        CsvRecordTS csvRecordTS = new CsvRecordTS(currentIteration, bestSpecimen, currentSpecimen, tabu, configTS, configLog);
        Logger.log(csvRecordTS, configLog);
    }

    private void log(Specimen bestNeighbor, Specimen worstNeighbor, double avgNeighbor) {
        CsvRecordTS csvRecordTS = new CsvRecordTS(currentIteration, bestSpecimen, bestNeighbor, worstNeighbor, avgNeighbor, configTS, configLog);
        Logger.log(csvRecordTS, configLog);
    }

    private void logFinalAnalysis() {

    }

}
