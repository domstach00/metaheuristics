package org.example;

import lombok.RequiredArgsConstructor;
import org.example.config.ConfigEA;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;
import org.example.loader.LoaderTTP;
import org.example.logs.Analysis;
import org.example.logs.CsvRecordEA;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.Specimen;
import org.example.operators.crossover.ICrossover;
import org.example.operators.mutation.IMutation;
import org.example.operators.selection.ISelection;
import org.example.support.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class WorkFlow {

    private final DataTTP dataTTP;
    private final ConfigEA configEA;

    private final IEvaluator evaluator;
    private final IInitialization initialization;
    private final IItemSelector itemSelector;
    private final ICrossover crossover;
    private final IMutation mutation;
    private final ISelection selection;

    private static final String inputPath = Utils.getInputPath;

    private int currentIteration = 0;

    public static ArrayList<Specimen> currentPopulation;
    public static Specimen bestSpecimen = null;

    public void start() {
        File file = new File(inputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        ArrayList<Specimen> population = initPopulation(dataTTP);

        run(population);

    }

    public void startTest() {
        File file = new File(inputPath);
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        ArrayList<Specimen> population = new ArrayList<>(configEA.getPopSize());
        for (int i = 0; i < 2; i++) {
            Specimen specimen = new Specimen(dataTTP);

            specimen.init(initialization, itemSelector);
            specimen.eval(evaluator);

            population.add(specimen);
        }
        currentPopulation = population;

        runIteration(population);


    }

    private ArrayList<Specimen> initPopulation(DataTTP dataTTP) {
        ArrayList<Specimen> population = new ArrayList<>(configEA.getPopSize());

        for (int i = 0; i < configEA.getPopSize(); i++) {
            Specimen specimen = new Specimen(dataTTP);

            specimen.init(initialization, itemSelector);
            specimen.eval(evaluator);

            population.add(specimen);
        }
        currentPopulation = population;
        return population;
    }

    private void run(ArrayList<Specimen> population) {
        ArrayList<Specimen> newPopulation = population;
        while (currentIteration <= configEA.getGen()) {
            newPopulation = runIteration(newPopulation);
            currentPopulation = newPopulation;
            log(newPopulation);
            System.out.println(currentIteration);
            currentIteration++;
        }
        finish();
    }

    private ArrayList<Specimen> runIteration(ArrayList<Specimen> population) {
        ArrayList<Specimen> newPopulation = new ArrayList<>();

        while (newPopulation.size() < configEA.getPopSize()) {
            double random = ThreadLocalRandom.current().nextDouble();
            Specimen selected1 = selection.selection(population, configEA.getTour());
            Specimen selected2 = selection.selection(population, configEA.getTour());
            while (selected1 == selected2)
                selected2 = selection.selection(population, configEA.getTour());

//            if (random <= configEA.getPX()) {
//                Specimen newSpecimen = crossover.crossover(selected1, selected2);
//                newSpecimen.eval(evaluator);
//                newPopulation.add(newSpecimen);
//            }
//            else {
//                Specimen newSpecimen1 = mutation.mutation(selected1, configEA.getPM());
//                newSpecimen1.initNewItems(initialization, itemSelector);
//                newSpecimen1.eval(evaluator);
//                newPopulation.add(newSpecimen1);
//                if (newPopulation.size() < configEA.getPopSize()) {
//                    Specimen newSpecimen2 = mutation.mutation(selected2, configEA.getPM());
//                    newSpecimen2.initNewItems(initialization, itemSelector);
//                    newSpecimen2.eval(evaluator);
//                    newPopulation.add(newSpecimen2);
//                }
//            }
            if (random <= configEA.getPX()) {
                selected1 = crossover.crossover(selected1, selected2);
                selected1.eval(evaluator);
                newPopulation.add(selected1);
            }
            selected1 = mutation.mutation(selected1, configEA.getPM());
            selected1.initNewItems(initialization, itemSelector);
            selected1.eval(evaluator);
            newPopulation.add(selected1);
            if (newPopulation.size() < configEA.getPopSize()) {
                selected2 = mutation.mutation(selected2, configEA.getPM());
                selected2.initNewItems(initialization, itemSelector);
                selected2.eval(evaluator);
                newPopulation.add(selected2);
            }
        }
        return newPopulation;
    }

    private void finish() {
        logFinalAnalysis();
    }

    private void log(ArrayList<Specimen> pop) {
        CsvRecordEA csvRecordEA = new CsvRecordEA(this.currentIteration, pop);
        Logger.log(csvRecordEA);
    }

    private void logCurrentPopulation() {
        log(currentPopulation);
    }

    private void logFinalAnalysis() {
        Analysis analysis = new Analysis();
        analysis.fetchDataFromGlobalCsvRecord();
        Logger.log(analysis);

    }

    public static double getTotalFitenss() {
        double value = 0;
        for (Specimen specimen : currentPopulation) {
            value += specimen.getFitness();
        }
        return value;
    }

}
