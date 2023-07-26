package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@ToString
public class Specimen implements ISpecimen {
    @ToString.Exclude
    protected final DataTTP dataTTP;
    @Setter
    protected Integer[] nodeGenome;
    protected double fitness;
    protected int currentKnapsackWeight;
    protected ArrayList<ItemTTP> knapsack;

//    @Setter
//    private SexEnum sex;
//    @Setter
//    private Integer age;


    public Specimen(DataTTP config) {
        this.dataTTP = config;
        this.nodeGenome = new Integer[dataTTP.getNodes().size()];
        this.knapsack = new ArrayList<>();
        this.currentKnapsackWeight = 0;
//        this.age = 0;
//        this.sex = SexEnum.values()[ThreadLocalRandom.current().nextInt(SexEnum.values().length)];
    }

    public Specimen(DataTTP config, int startAge) {
        this.dataTTP = config;
        this.nodeGenome = new Integer[dataTTP.getNodes().size()];
        this.knapsack = new ArrayList<>();
        this.currentKnapsackWeight = 0;
//        this.age = startAge;
//        this.sex = SexEnum.values()[ThreadLocalRandom.current().nextInt(SexEnum.values().length)];
    }

    public Specimen(Specimen other) {
        this.dataTTP = other.dataTTP;
        this.fitness = other.fitness;
        this.nodeGenome = other.nodeGenome.clone();
        this.currentKnapsackWeight = other.currentKnapsackWeight;
        this.knapsack = (ArrayList<ItemTTP>) other.knapsack.clone();
//        this.age = other.age;
//        this.sex = other.sex;
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

    public void initNewItems(IItemSelector itemSelector) {
        this.knapsack = new ArrayList<>();
        this.currentKnapsackWeight = 0;
        itemSelector.select(this);
    }

    @Override
    public void eval(IEvaluator evaluator) {
        fitness = evaluator.evaluateSpecimen(this);
    }

    public Specimen clone() {
        return new Specimen(this);
    }


//    public void growUp() {
//        age++;
//    }

}

