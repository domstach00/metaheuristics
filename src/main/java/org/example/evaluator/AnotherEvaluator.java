package org.example.evaluator;

import org.example.model.DataTTP;
import org.example.model.ItemTTP;
import org.example.model.NodeTTP;
import org.example.model.Specimen;

import java.util.stream.Collectors;

public class AnotherEvaluator implements IEvaluator {

    private final DataTTP dataTTP;

    public AnotherEvaluator(DataTTP dataTTP) {
        this.dataTTP = dataTTP;
    }

    @Override
    public Double evaluateSpecimen(Specimen specimen) {
        double profit = 0;
        if (specimen.getKnapsack() != null && specimen.getKnapsack().size() > 0)
            for (ItemTTP item : specimen.getKnapsack())
                profit += item.getProfit();

        double time = 0d;
        double currentWeight = updateWeight(0, specimen.getNodeGenome()[0], specimen);
        for (int i = 1; i < specimen.getNodeGenome().length; i++) {
            int finalI = i;
            NodeTTP nodeFrom = dataTTP.getNodes().stream()
                    .filter(nodeTTP -> nodeTTP.getId() == specimen.getNodeGenome()[finalI - 1])
                    .findFirst()
                    .orElseThrow();
            NodeTTP nodeTo = dataTTP.getNodes().stream()
                    .filter(nodeTTP -> nodeTTP.getId() == specimen.getNodeGenome()[finalI])
                    .findFirst()
                    .orElseThrow();
            double distance = nodeFrom.getDistance(nodeTo);

            double currentSpeed = dataTTP.getMaxSpeed() - currentWeight * ((dataTTP.getMaxSpeed() - dataTTP.getMinSpeed()) / dataTTP.getKnapsackCapacity());
            time += distance / currentSpeed;
            currentWeight += updateWeight(currentSpeed, specimen.getNodeGenome()[i], specimen);
        }

        return profit - time;
    }

    private double updateWeight(double weight, int nodeValue, Specimen specimen) {
        var itemsInNode = dataTTP.getItems().stream()
                .filter(itemTTP -> itemTTP.getId() == nodeValue)
                .collect(Collectors.toList());

        for (ItemTTP item : itemsInNode)
            if (specimen.getKnapsack().contains(item))
                weight += item.getWeight();

        return weight;
    }

}
