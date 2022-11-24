package org.example.workflow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.config.Config;
import org.example.config.ConfigEA;
import org.example.config.ConfigLog;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;
import org.example.loader.LoaderTTP;
import org.example.logs.Analysis;
import org.example.logs.CsvRecordEA;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.SexEnum;
import org.example.model.Specimen;
import org.example.operators.crossover.ICrossover;
import org.example.operators.mutation.IMutation;
import org.example.operators.selection.ISelection;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class HybridEA implements IWorkFLow {
    private final DataTTP dataTTP;
    private final ConfigEA configEA;

    @Getter
    private final ICrossover crossover;
    @Getter
    private final IMutation mutation;
    @Getter
    private final ISelection selection;
    private final IEvaluator evaluator;
    private final IInitialization initialization;
    private final IItemSelector itemSelector;

    private final ConfigLog configLog;

    private int currentIteration = 0;

    public ArrayList<Specimen> currentPopulation;

    public final int maxAge;
    private static int sexEnumIterator = 0;

    @Override
    public Config getConfig() {
        return configEA;
    }

    @Override
    public String getAlgName() {
        return "HybEA";
    }

    @Override
    public String getOperationsToLogDir() {
        return this.mutation.getMutationName() + "/" + this.getCrossover().getCrossoverName() + "/" + this.selection.getSelectionName();
    }

    @Override
    public void start() {
        configLog.formatLogPath(getAlgName(), getOperationsToLogDir());
        File file = new File(configLog.getInputPath());
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        ArrayList<Specimen> population = initPopulation(dataTTP);

        run(population);

    }

    public void startTest() {
        File file = new File(configLog.getInputPath());
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        ArrayList<Specimen> population = new ArrayList<>(configEA.getPopSize());
        for (int i = 0; i < 2; i++) {
            Specimen specimen = new Specimen(dataTTP);

            specimen.init(initialization, itemSelector);
            specimen.eval(evaluator);

            population.add(specimen);
        }
        Integer[] spec1 = {7, 5, 0, 6, 1, 9, 3, 2, 8, 4};
        Integer[] spec2 = {0, 5, 8, 9, 2, 3, 4, 6, 7, 1};
        population.get(0).setNodeGenome(spec1);
        population.get(1).setNodeGenome(spec2);


        System.out.println(Arrays.toString(population.get(0).getNodeGenome()));
        System.out.println(Arrays.toString(population.get(1).getNodeGenome()));
        Specimen osobnik =  crossover.crossover(population.get(0), population.get(1));
        Specimen osobnik2 = crossover.crossover(population.get(1), population.get(0));
        System.out.println("After cross");
        System.out.println(Arrays.toString(osobnik.getNodeGenome()));
        System.out.println(Arrays.toString(osobnik2.getNodeGenome()));
    }

    private ArrayList<Specimen> initPopulation(DataTTP dataTTP) {
        ArrayList<Specimen> population = new ArrayList<>(configEA.getPopSize());

        for (int i = 0; i < configEA.getPopSize(); i++) {
            Specimen specimen = new Specimen(dataTTP, ThreadLocalRandom.current().nextInt(maxAge));

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
            System.out.println(getAlgName() + ": " + currentIteration);
            currentIteration++;
        }
        finish();
    }

//    private ArrayList<Specimen> runIteration(ArrayList<Specimen> population) {
//        Specimen selected1 = selection.selection(population, configEA.getTour());
//        Specimen selected2 = selection.selection(population, configEA.getTour());
//        while (selected1 == selected2)
//            selected2 = selection.selection(population, configEA.getTour());
//
//        if (ThreadLocalRandom.current().nextDouble() <= configEA.getPX()) {
//            selected1 = crossover.crossover(selected1, selected2);
//            selected1.eval(evaluator);
//            population.remove(ThreadLocalRandom.current().nextInt(population.size()));
//            population.add(selected1);
//        }
//        selected1 = mutation.mutation(selected1, configEA.getPM());
//        selected1.initNewItems(initialization, itemSelector);
//        selected1.eval(evaluator);
//
//        population.add()
//        return null;
//    }

    private ArrayList<Specimen> runIteration(ArrayList<Specimen> population) {
        population.removeIf(specimen -> specimen.getAge() >= maxAge);

        ArrayList<Specimen> newPopulation = new ArrayList<>();

        while (newPopulation.size() < configEA.getPopSize()) {
            double random = ThreadLocalRandom.current().nextDouble();
            Specimen selected1 = selection.selection(population, configEA.getTour());
            Specimen selected2 = selection.selection(population, configEA.getTour());
            while (selected1 == selected2 || selected1.getSex() == selected2.getSex())
                selected2 = selection.selection(population, configEA.getTour());

            if (random <= configEA.getPX()) {
                selected1 = crossover.crossover(selected1, selected2);
                selected1.eval(evaluator);
                selected1.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
                newPopulation.add(selected1);
            }
            selected1 = mutation.mutation(selected1, configEA.getPM());
            selected1.initNewItems(initialization, itemSelector);
            selected1.eval(evaluator);
            selected1.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
            newPopulation.add(selected1);
            if (newPopulation.size() < configEA.getPopSize()) {
                selected2 = mutation.mutation(selected2, configEA.getPM());
                selected2.initNewItems(initialization, itemSelector);
                selected2.eval(evaluator);
                selected2.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
                newPopulation.add(selected2);
            }
        }
        for (Specimen specimen : newPopulation)
            specimen.growUp();

        return newPopulation;
    }

    private void finish() {
    }

    private void log(ArrayList<Specimen> pop) {
        CsvRecordEA csvRecordEA = new CsvRecordEA(this.currentIteration, pop, configEA, configLog);
        Logger.log(csvRecordEA, configLog);
    }

    private void logCurrentPopulation() {
        log(currentPopulation);
    }

    private void logFinalAnalysis() {
        Analysis analysis = new Analysis();
        analysis.fetchDataFromGlobalCsvRecord();
        Logger.log(analysis, configLog);

    }
}
