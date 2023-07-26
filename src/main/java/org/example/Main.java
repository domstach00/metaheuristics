package org.example;

import org.example.config.*;
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
import org.example.workflow.HybridEA;
import org.example.workflow.SimulatedAnnealing;
import org.example.workflow.TabuSearch;
import org.example.workflow.EvolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        System.out.println(Arrays.toString(args));
        switch (args[0]) {
            case "EA":
                for (int i = 0; i < Integer.parseInt(args[1]); i++)
                    new Thread(() ->runEA(Utils.getSuggestedConfigEA(),
                            new CrossoverOrdered(),
                            new MutationSwap(),
                            new SelectionRoulette(),
                            new ConfigLog(args[2]))
                    ).start();
                break;

            case "TS":
                for (int i = 0; i < Integer.parseInt(args[1]); i++)
                    new Thread(() ->  runTS(Utils.getSuggestedConfigTS(),
                            new MutationSwapTSSA(),
                            new ConfigLog(args[2]))
                    ).start();
                break;

            case "SA":
                for (int i = 0; i < Integer.parseInt(args[1]); i++)
                    new Thread(() -> runSA(Utils.getSuggestedConfigSA(),
                            new MutationSwapTSSA(),
                            new ConfigLog(args[2]))
                    ).start();
                break;

            default:
                threadPoolExec();
        }


        long endTime = System.nanoTime();
        long time = (endTime - startTime) / 1_000_000_000;
        System.out.println("Time [s]: " + time);
    }

    private static void run() {
//        ArrayList<ConfigSA> configSaList = new ArrayList<>();
//        configSaList.add(ConfigSA.builder().iteration(200_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(100_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(13).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(7).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(500_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_500_000).endTemp(1).annealingRate(0.9999).build());
//
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.9999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.999).build());
//        configSaList.add(ConfigSA.builder().iteration(150_000_000).nSize(10).startTemp(1_000_000).endTemp(1).annealingRate(0.99).build());
//
//
//        for (ConfigSA configSA : configSaList)
//            new Thread(() -> runSA(
//                    configSA,
//                    new MutationSwapTSSA(),
//                    new ConfigLog("medium_1.ttp"))
//            ).start();

//        ArrayList<ConfigTS> configTSList = new ArrayList<>();
//        configTSList.add(ConfigTS.builder().iteration(12_000).nSize(10).tabuSize(100).build());
//        configTSList.add(ConfigTS.builder().iteration(10_000).nSize(10).tabuSize(100).build());
//        configTSList.add(ConfigTS.builder().iteration(7_000).nSize(10).tabuSize(100).build());
//
//        configTSList.add(ConfigTS.builder().iteration(10_000).nSize(7).tabuSize(100).build());
//        configTSList.add(ConfigTS.builder().iteration(10_000).nSize(10).tabuSize(100).build());
//        configTSList.add(ConfigTS.builder().iteration(10_000).nSize(12).tabuSize(100).build());
//
//        for (ConfigTS configTS : configTSList)
//            new Thread(() -> runTS(
//                    configTS,
//                    new MutationSwapTSSA(),
//                    new ConfigLog("hard_1.ttp")
//            )).start();


//        ArrayList<ConfigEA> configEAList = new ArrayList<>();
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.3).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.4).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.5).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.2).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.1).pM(0.01).tour(5).build());
//
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.3).pM(0.10).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.3).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.3).pM(0.001).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(100).pX(0.3).pM(0.15).tour(5).build());
//
//        configEAList.add(ConfigEA.builder().popSize(120).gen(100).pX(0.3).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(90).gen(100).pX(0.3).pM(0.01).tour(5).build());
//
//        configEAList.add(ConfigEA.builder().popSize(100).gen(110).pX(0.3).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(90).pX(0.3).pM(0.01).tour(5).build());

//        configEAList.add(ConfigEA.builder().popSize(100).gen(5000).pX(0.6).pM(0.01).tour(50).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(5000).pX(0.6).pM(0.01).tour(10).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(5000).pX(0.6).pM(0.01).tour(5).build());
//        configEAList.add(ConfigEA.builder().popSize(100).gen(5000).pX(0.6).pM(0.01).tour(5).build());

//        System.out.println("X");
//        new Thread(() -> runEA(
//                configEAList.get(0),
//                new CrossoverOrdered(),
//                new MutationSwap(),
//                new SelectionRoulette(),
//                new ConfigLog("hard_0.ttp")
//        )).start();
//
//        new Thread(() -> runEA(
//                configEAList.get(1),
//                new CrossoverOrdered(),
//                new MutationSwap(),
//                new SelectionRoulette(),
//                new ConfigLog("hard_0.ttp")
//        )).start();

//        for (ConfigEA configEA : configEAList)
//            new Thread(() -> runEA(
//                    configEA,
//                    new CrossoverOrdered(),
//                    new MutationSwap(),
//                    new SelectionRoulette(),
//                    new ConfigLog("hard_0.ttp")
//            )).start();


        ArrayList<ConfigHybEA> configHybEAS = new ArrayList<>();
        configHybEAS.add(ConfigHybEA.builder().popSize(100).gen(5000).pX(0.3).pM(0.01).tour(10).maxAge(5).build());
        configHybEAS.add(ConfigHybEA.builder().popSize(100).gen(5000).pX(0.3).pM(0.01).tour(10).maxAge(10).build());
        configHybEAS.add(ConfigHybEA.builder().popSize(100).gen(5000).pX(0.3).pM(0.01).tour(10).maxAge(15).build());
        configHybEAS.add(ConfigHybEA.builder().popSize(100).gen(5000).pX(0.3).pM(0.01).tour(10).maxAge(20).build());


        for (ConfigHybEA configHybEA : configHybEAS)
            new Thread(() -> runHybEA(
                    configHybEA,
                    new CrossoverOrdered(),
                    new MutationSwap(),
                    new SelectionRoulette(),
                    new ConfigLog("hard_0.ttp")
            )).start();
    }

    public static void threadPoolExec() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1; i++) {
            executor.submit(() -> runEA(
                    Utils.getSuggestedConfigEA(),
                    new CrossoverOrdered(),
                    new MutationSwap(),
                    new SelectionRoulette(),
                    new ConfigLog("medium_0.ttp")));
//            executor.submit(() -> runHybEA(
//                    Utils.getSuggestedConfigHybridEA(),
//                    new CrossoverOrdered(),
//                    new MutationSwap(),
//                    new SelectionRoulette(),
//                    new ConfigLog("medium_0.ttp")));
        }
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

    public static void runHybEA(ConfigHybEA configHybEA, ICrossover crossover, IMutation mutation, ISelection selection, ConfigLog configLog) {
        DataTTP dataTTP = new DataTTP();
        HybridEA evolutionaryAlgorithm = new HybridEA(
                dataTTP,
                configHybEA,
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