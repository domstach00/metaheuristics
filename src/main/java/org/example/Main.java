package org.example;

import org.example.evaluator.AnotherEvaluator;
import org.example.initialization.InitializationRandom;
import org.example.itemselector.ItemSelectorPriceAndWeight;
import org.example.model.DataTTP;
import org.example.operators.crossover.CrossoverPartiallyMatched;
import org.example.operators.mutation.MutationSwap;
import org.example.operators.mutation.MutationSwapTS;
import org.example.operators.selection.SelectionRoulette;
import org.example.support.Utils;


public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        DataTTP dataTTP = new DataTTP();
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(
                Utils.getSuggestedConfigSA(),
                dataTTP,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                new MutationSwapTS()
        );
        simulatedAnnealing.start();
        long endTime = System.nanoTime();
        long time = (endTime - startTime) / 1_000_000_000;
        System.out.println("Time [s]: " + time);
    }

    public static void runTS() {
        DataTTP dataTTP = new DataTTP();
        TabuSearch tabuSearch = new TabuSearch(
                Utils.getSuggestedConfigTS(),
                dataTTP,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                new MutationSwapTS()
        );
        tabuSearch.start();
    }

    public static void runEA() {
        DataTTP dataTTP = new DataTTP();
        WorkFlow workFlow = new WorkFlow(
                dataTTP,
                Utils.getSuggestedConfigEA(),
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                new CrossoverPartiallyMatched(),
                new MutationSwap(),
                new SelectionRoulette());
        workFlow.start();
    }
}