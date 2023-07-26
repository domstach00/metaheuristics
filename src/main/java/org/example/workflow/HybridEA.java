package org.example.workflow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.config.Config;
import org.example.config.ConfigHybEA;
import org.example.config.ConfigLog;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;
import org.example.loader.LoaderTTP;
import org.example.logs.Analysis;
import org.example.logs.CsvRecordHybEA;
import org.example.logs.Logger;
import org.example.model.DataTTP;
import org.example.model.SexEnum;
import org.example.model.Specimen;
import org.example.model.SpecimenWithGenderAndAge;
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
    private final ConfigHybEA configHybEA;

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

    public ArrayList<SpecimenWithGenderAndAge> currentPopulation;

    private int sexEnumIterator = 0;

    private SpecimenWithGenderAndAge bestSpecimen;

    @Override
    public Config getConfig() {
        return configHybEA;
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

        ArrayList<SpecimenWithGenderAndAge> population = initPopulation(dataTTP);

        run(population);

    }

    public void startTest() {
        File file = new File(configLog.getInputPath());
        LoaderTTP loaderTTP = new LoaderTTP(dataTTP);
        loaderTTP.readAllProperties(file);

        ArrayList<Specimen> population = new ArrayList<>(configHybEA.getPopSize());
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

    private ArrayList<SpecimenWithGenderAndAge> initPopulation(DataTTP dataTTP) {
        ArrayList<SpecimenWithGenderAndAge> population = new ArrayList<>(configHybEA.getPopSize());

        for (int i = 0; i < configHybEA.getPopSize(); i++) {
            SpecimenWithGenderAndAge specimen = new SpecimenWithGenderAndAge(dataTTP, ThreadLocalRandom.current().nextInt(configHybEA.getMaxAge()));

            specimen.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
            specimen.init(initialization, itemSelector);
            specimen.eval(evaluator);

            population.add(specimen);

            if (bestSpecimen == null || bestSpecimen.getFitness() < specimen.getFitness())
                setBestSpecimen(specimen);
        }
        currentPopulation = population;
        return population;
    }

    private void run(ArrayList<SpecimenWithGenderAndAge> population) {
        ArrayList<SpecimenWithGenderAndAge> newPopulation = population;
        while (currentIteration <= configHybEA.getGen()) {
            newPopulation = runIteration(newPopulation);
            currentPopulation = newPopulation;
            System.out.println(getAlgName() + ": " + currentIteration + " PopSize: " + currentPopulation.size());
            log(newPopulation);
            currentIteration++;
        }
        finish();
    }

    private ArrayList<SpecimenWithGenderAndAge> runIteration(ArrayList<SpecimenWithGenderAndAge> population) {
        ArrayList<SpecimenWithGenderAndAge> newPopulation = new ArrayList<>();
        newPopulation.add(bestSpecimen);

        while (newPopulation.size() < configHybEA.getPopSize()) {
            SpecimenWithGenderAndAge selected1 = selection.selection(population, configHybEA.getTour());
            SpecimenWithGenderAndAge selected2 = selection.selection(population, configHybEA.getTour());
            while (selected1 == selected2 || selected1.getSex() == selected2.getSex())
                selected2 = selection.selection(population, configHybEA.getTour());

            if (ThreadLocalRandom.current().nextDouble() <= configHybEA.getPX()) {
                selected1 = crossover.crossover(selected1, selected2);
                selected1.eval(evaluator);
                selected1.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
                selected1.setAge(0);
                newPopulation.add(selected1);
            }
            selected1 = mutation.mutation(selected1, configHybEA.getPM());
            selected1.initNewItems(initialization, itemSelector);
            selected1.eval(evaluator);
            selected1.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
            newPopulation.add(selected1);
            if (newPopulation.size() < configHybEA.getPopSize()) {
                selected2 = mutation.mutation(selected2, configHybEA.getPM());
                selected2.initNewItems(initialization, itemSelector);
                selected2.eval(evaluator);
                selected2.setSex(SexEnum.values()[sexEnumIterator++ % SexEnum.values().length]);
                newPopulation.add(selected2);
            }
        }

        for (SpecimenWithGenderAndAge specimen : newPopulation)
            if (specimen.getFitness() > bestSpecimen.getFitness())
                setBestSpecimen(specimen);

        newPopulation.removeIf(specimen -> specimen.getAge() >= configHybEA.getMaxAge());
        for (SpecimenWithGenderAndAge specimen : newPopulation)
            specimen.growUp();

        return newPopulation;
    }

    private void finish() {
    }

    private void log(ArrayList<SpecimenWithGenderAndAge> pop) {
        CsvRecordHybEA csvRecordEA = new CsvRecordHybEA(this.currentIteration, pop, bestSpecimen, configHybEA, configLog);
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

    private void setBestSpecimen(SpecimenWithGenderAndAge specimen) {
        bestSpecimen = new SpecimenWithGenderAndAge(specimen);
        bestSpecimen.setAge(0);
    }
}
