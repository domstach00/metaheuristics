package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;

import java.util.*;

@Getter
@ToString
public class Specimen implements ISpecimen {
    @ToString.Exclude
    private final DataTTP dataTTP;
    @Setter
    private Integer[] nodeGenome;
    private double fitness;
    private int currentKnapsackWeight;
    private ArrayList<ItemTTP> knapsack;


    public Specimen(DataTTP config) {
        this.dataTTP = config;
        nodeGenome = new Integer[dataTTP.getNodes().size()];
        knapsack = new ArrayList<>();
        currentKnapsackWeight = 0;
    }

    public Specimen(Specimen other) {
        this.dataTTP = other.dataTTP;
        this.fitness = other.fitness;
        this.nodeGenome = other.nodeGenome.clone();
        this.currentKnapsackWeight = other.currentKnapsackWeight;
        this.knapsack = (ArrayList<ItemTTP>) other.knapsack.clone();
    }

    public boolean addToKnapsack(ItemTTP itemTTP) {
        if (itemTTP == null || !checkIfEnoughCapacity(itemTTP))
            return false;
        if (!knapsack.contains(itemTTP)) {
            knapsack.add(itemTTP);
            currentKnapsackWeight += itemTTP.getWeight();
            return true;
        }
        return false;
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

    public void initNewItems(IInitialization initialization, IItemSelector itemSelector) {
        this.knapsack = new ArrayList<>();
        this.currentKnapsackWeight = 0;
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
