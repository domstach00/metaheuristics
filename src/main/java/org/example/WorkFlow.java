package org.example;

import lombok.RequiredArgsConstructor;
import org.example.config.ConfigEA;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;
import org.example.loader.LoaderTTP;
import org.example.logs.Analysis;
import org.example.logs.CsvRecord;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.Specimen;
import org.example.support.Utils;

import java.io.File;
import java.util.ArrayList;

@RequiredArgsConstructor
public class WorkFlow {

    private final DataTTP dataTTP;

    private final IInitialization initialization;
    private final IEvaluator evaluator;
    private final IItemSelector itemSelector;


    private static final String inputPath = Utils.getInputPath;

    private int currentIteration = 0;

    public void start() {
        File file = new File(inputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

//        this.initialization = new InitializationRandom();
//        this.evaluator = new Evaluator(dataTTP);

        ArrayList<Specimen> population = initPopulation(Utils.getSuggestedConfigEA(), dataTTP);

        log(population);


//        Specimen specimen = new Specimen(dataTTP);
//        Evaluator evaluator = new Evaluator(dataTTP);
//        InitializationGreedy initialization = new InitializationGreedy();
//
//        specimen.init(initialization);
//        specimen.eval(evaluator);


    }

    private ArrayList<Specimen> initPopulation(ConfigEA configEA, DataTTP dataTTP) {
        ArrayList<Specimen> population = new ArrayList<>(configEA.getPopSize());

        for (int i = 0; i < configEA.getPopSize(); i++) {
            Specimen specimen = new Specimen(dataTTP);

            specimen.init(initialization, itemSelector);
            specimen.eval(evaluator);

            population.add(specimen);
        }
        return population;
    }

    private void run() {


    }

    private void runIteration() {

    }

    private void log(ArrayList<Specimen> pop) {
        Analysis analysis = new Analysis();
        analysis.analysisPopulation(pop);
        CsvRecord csvRecord = new CsvRecord(this.currentIteration, analysis.getBestScore(), analysis.getAvgScore(), analysis.getWorstScore());
        Logger.log(analysis);
        Logger.log(csvRecord);
    }

}
