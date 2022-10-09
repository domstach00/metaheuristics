package org.example.model;

import lombok.Getter;
import org.example.support.Evaluator;

import java.util.*;

@Getter
public class Specimen implements ISpecimen {
    private DataTTP dataTTP;
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

    private boolean addToKnapsack(ItemTTP itemTTP) {
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
    public void init() {
        Random random = new Random();
        ArrayList<NodeTTP> possibleNodes = new ArrayList<>(List.copyOf(dataTTP.getNodes()));

        int index = 0;
        while (possibleNodes.size() != 0) {
            NodeTTP chosenNode = possibleNodes.get(random.nextInt(possibleNodes.size()));
            nodeGenome[index] = chosenNode.getId();
            possibleNodes.remove(chosenNode);
            index++;
        }

        for (ItemTTP item : dataTTP.getItems()) {
            if (random.nextDouble() <= 0.2) {
                addToKnapsack(item);
            }
        }
    }

    @Override
    public void eval(Evaluator evaluator) {
        fitness = evaluator.evaluateSpecimen(this);
    }

    @Override
    public void fix() {

    }
}
