package org.example;

import org.example.config.ConfigEA;
import org.example.config.ConfigLog;
import org.example.config.ConfigSA;
import org.example.config.ConfigTS;
import org.example.evaluator.AnotherEvaluator;
import org.example.initialization.InitializationRandom;
import org.example.itemselector.ItemSelectorPriceAndWeight;
import org.example.model.DataTTP;
import org.example.operators.crossover.CrossoverOrdered;
import org.example.operators.crossover.ICrossover;
import org.example.operators.mutation.IMutation;
import org.example.operators.mutation.MutationSwap;
import org.example.operators.mutation.MutationSwapTSSA;
import org.example.operators.selection.ISelection;
import org.example.operators.selection.SelectionRoulette;
import org.example.support.Utils;
import org.example.workflow.SimulatedAnnealing;
import org.example.workflow.TabuSearch;
import org.example.workflow.EvolutionaryAlgorithm;


public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        new Thread(() ->
            runSA(
                    Utils.getSuggestedConfigSA(),
                    new MutationSwapTSSA(),
                    new ConfigLog("medium_1.ttp")
            )
        ).start();

        new Thread(() ->
                runTS(Utils.getSuggestedConfigTS(),
                        new MutationSwapTSSA(),
                        new ConfigLog("medium_1.ttp"))
        ).start();

        new Thread(() ->
                runEA(
                        Utils.getSuggestedConfigEA(),
                        new CrossoverOrdered(),
                        new MutationSwap(),
                        new SelectionRoulette(),
                        new ConfigLog("medium_1.ttp"))
        ).start();

        new Thread(() ->
                runSA(
                        Utils.getSuggestedConfigSA(),
                        new MutationSwapTSSA(),
                        new ConfigLog("medium_1.ttp")
                )
        ).start();

        new Thread(() ->
                runTS(Utils.getSuggestedConfigTS(),
                        new MutationSwapTSSA(),
                        new ConfigLog("medium_1.ttp"))
        ).start();

        new Thread(() ->
                runEA(
                        Utils.getSuggestedConfigEA(),
                        new CrossoverOrdered(),
                        new MutationSwap(),
                        new SelectionRoulette(),
                        new ConfigLog("medium_1.ttp"))
        ).start();

        new Thread(() ->
                runSA(
                        Utils.getSuggestedConfigSA(),
                        new MutationSwapTSSA(),
                        new ConfigLog("medium_1.ttp")
                )
        ).start();

        new Thread(() ->
                runTS(Utils.getSuggestedConfigTS(),
                        new MutationSwapTSSA(),
                        new ConfigLog("medium_1.ttp"))
        ).start();

        new Thread(() ->
                runEA(
                        Utils.getSuggestedConfigEA(),
                        new CrossoverOrdered(),
                        new MutationSwap(),
                        new SelectionRoulette(),
                        new ConfigLog("medium_1.ttp"))
        ).start();


        long endTime = System.nanoTime();
        long time = (endTime - startTime) / 1_000_000_000;
        System.out.println("Time [s]: " + time);
    }

    public static void runSA(ConfigSA configSA, IMutation mutation, ConfigLog configLog) {
        DataTTP dataTTP = new DataTTP();
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(
                configSA,
                dataTTP,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                mutation,
                configLog
        );
        simulatedAnnealing.start();
    }

    public static void runTS(ConfigTS configTS, IMutation mutation, ConfigLog configLog) {
        DataTTP dataTTP = new DataTTP();
        TabuSearch tabuSearch = new TabuSearch(
                configTS,
                dataTTP,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                mutation,
                configLog
        );
        tabuSearch.start();
    }

    public static void runEA(ConfigEA configEA, ICrossover crossover, IMutation mutation, ISelection selection, ConfigLog configLog) {
        DataTTP dataTTP = new DataTTP();
        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(
                dataTTP,
                configEA,
                crossover,
                mutation,
                selection,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                configLog
        );
        evolutionaryAlgorithm.start();
    }
}