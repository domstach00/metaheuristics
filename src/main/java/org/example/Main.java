package org.example;

import org.example.evaluator.AnotherEvaluator;
import org.example.evaluator.Evaluator;
import org.example.initialization.InitializationGreedy;
import org.example.initialization.InitializationRandom;
import org.example.itemselector.ItemSelectorPrice;
import org.example.itemselector.ItemSelectorPriceAndWeight;
import org.example.model.DataTTP;
import org.example.operators.crossover.CrossoverCycle;
import org.example.operators.crossover.CrossoverOrdered;
import org.example.operators.crossover.CrossoverPartiallyMatched;
import org.example.operators.mutation.MutationInversion;
import org.example.operators.mutation.MutationSwap;
import org.example.operators.selection.SelectionRoulette;
import org.example.operators.selection.SelectionTournament;
import org.example.support.Utils;

public class Main {
    public static void main(String[] args) {
        DataTTP dataTTP = new DataTTP();
        WorkFlow workFlow = new WorkFlow(
                dataTTP,
                Utils.getSuggestedConfigEA(),
                new AnotherEvaluator(dataTTP),
                new InitializationGreedy(),
                new ItemSelectorPriceAndWeight(),
                new CrossoverOrdered(),
                new MutationSwap(),
                new SelectionRoulette());
        workFlow.start();
    }
}