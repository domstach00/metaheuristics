package org.example;

import org.example.config.ConfigEA;
import org.example.loader.LoaderTTP;
import org.example.logs.Analysis;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.Specimen;
import org.example.support.Evaluator;
import org.example.support.Utils;

import java.io.File;
import java.util.ArrayList;

public class WorkFlow {

    private static final String inputPath = Utils.getInputPath;

    private long currentIteration = 0L;

    public void start() {
        DataTTP dataTTP = new DataTTP();
        File file = new File(inputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        ArrayList<Specimen> population = initPopulation(Utils.getSuggestedConfigEA(), dataTTP);

        log(population);
//        Specimen specimen = new Specimen(dataTTP);
//        Evaluator evaluator = new Evaluator(dataTTP);
//
//        specimen.init();
//        specimen.eval(evaluator);

    }

    private ArrayList<Specimen> initPopulation(ConfigEA configEA, DataTTP dataTTP) {
        ArrayList<Specimen> population = new ArrayList<>(configEA.getPopSize());
        for (int i = 0; i < configEA.getPopSize(); i++) {
            Specimen specimen = new Specimen(dataTTP);
            Evaluator evaluator = new Evaluator(dataTTP);

            specimen.init();
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
        Logger.log(analysis);
    }

}
