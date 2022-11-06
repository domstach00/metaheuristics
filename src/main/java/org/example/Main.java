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
import org.example.workflow.IWorkFLow;
import org.example.workflow.SimulatedAnnealing;
import org.example.workflow.TabuSearch;
import org.example.workflow.WorkFlow;


public class Main {

    public static IWorkFLow workFlow;

    public static String currentFile;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        runSA();

        long endTime = System.nanoTime();
        long time = (endTime - startTime) / 1_000_000_000;
        System.out.println("Time [s]: " + time);
    }

    public static void runSA() {
        DataTTP dataTTP = new DataTTP();
        workFlow = new SimulatedAnnealing(
                Utils.getSuggestedConfigSA(),
                dataTTP,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                new MutationSwapTS()
        );
        workFlow.start();
    }

    public static void runTS() {
        DataTTP dataTTP = new DataTTP();
        workFlow = new TabuSearch(
                Utils.getSuggestedConfigTS(),
                dataTTP,
                new AnotherEvaluator(dataTTP),
                new InitializationRandom(),
                new ItemSelectorPriceAndWeight(),
                new MutationSwapTS()
        );
        workFlow.start();
    }

    public static void runEA() {
        DataTTP dataTTP = new DataTTP();
        workFlow = new WorkFlow(
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