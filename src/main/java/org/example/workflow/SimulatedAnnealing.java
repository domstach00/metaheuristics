package org.example.workflow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.config.Config;
import org.example.config.ConfigLog;
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

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class SimulatedAnnealing implements IWorkFLow {

    private final ConfigSA configSA;
    private final DataTTP dataTTP;

    private final IEvaluator evaluator;
    private final IInitialization initialization;
    private final IItemSelector itemSelector;

    @Getter
    private final IMutation mutation;

    private Neighborhood neighborhood;

    private final ConfigLog configLog;

    private Specimen bestSpecimen;
    private Specimen currentSpecimen;

    private int currentIteration = 0;



    @Override
    public Config getConfig() {
        return configSA;
    }

    @Override
    public String getAlgName() {
        return "SA";
    }

    @Override
    public String getOperationsToLogDir() {
        return this.getMutation().getMutationName();
    }

    @Override
    public void start() {
        configLog.formatLogPath(getAlgName(), getOperationsToLogDir());
        File file = new File(configLog.getInputPath());
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

        while (currentIteration < configSA.getIteration() && currentTemp > configSA.getEndTemp()) {
            runIteration(currentTemp);
            currentTemp *= configSA.getAnnealingRate();

            log();
            currentIteration++;
            System.out.println(getAlgName() + ": Iter: " + currentIteration + "\tTemp: " + currentTemp);
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
        CsvRecordSA csvRecordSA = new CsvRecordSA(currentIteration, bestSpecimen.getFitness(), currentSpecimen.getFitness(), configSA, configLog);
        Logger.log(csvRecordSA, configLog);

    }

    private void finish() {

    }
}
