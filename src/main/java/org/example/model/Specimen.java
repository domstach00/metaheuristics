package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;

import java.util.*;

@Getter
public class Specimen implements ISpecimen {
    private final DataTTP dataTTP;
    @Setter
    private Integer[] nodeGenome;
    private HashSet<ItemTTP> knapsack;
    private int currentKnapsackWeight;

    private double fitness;

    public Specimen(DataTTP config) {
        this.dataTTP = config;
        nodeGenome = new Integer[dataTTP.getNodes().size()];
        knapsack = new HashSet<>();
        currentKnapsackWeight = 0;
    }

    public boolean addToKnapsack(ItemTTP itemTTP) {
        if (itemTTP == null || !checkIfEnoughCapacity(itemTTP) && !knapsack.contains(itemTTP))
            return false;
        knapsack.add(itemTTP);
        currentKnapsackWeight += itemTTP.getWeight();
        return true;
    }

    private boolean checkIfEnoughCapacity(ItemTTP itemTTP) {
        return itemTTP.getWeight() + currentKnapsackWeight <= dataTTP.getKnapsackCapacity();
    }

    private int calcCurrentKnapsackWeight() {
        int result = 0;
        for (ItemTTP item : knapsack) {
            result += item.getWeight();
        }
        return result;
    }

    @Override
    public void init(IInitialization initialization, IItemSelector itemSelector) {
        initialization.startInitializationNode(this);
        initialization.startInitializationItems(itemSelector, this);
    }

    @Override
    public void eval(IEvaluator evaluator) {
        fitness = evaluator.evaluateSpecimen(this);
    }

    @Override
    public void fix() {

    }
}
