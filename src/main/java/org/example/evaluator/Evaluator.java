package org.example.evaluator;

import org.example.model.DataTTP;
import org.example.model.Specimen;

import java.util.stream.Collectors;

public class Evaluator implements IEvaluator {
    DataTTP dataTTP;

    public Evaluator(DataTTP dataTTP) {
        this.dataTTP = dataTTP;
    }

    public Double evaluateSpecimen(Specimen specimen) {
        double knapsackValue=0;
        double tspCost=0;
        double currentWeight=0;

        for (int i = 0; i < specimen.getNodeGenome().length; i++) {
            Double[] nodeWeightAndProfit;
            nodeWeightAndProfit = getItemsWeightAndProfitForNode(specimen, i);
            currentWeight += nodeWeightAndProfit[0];
            knapsackValue += nodeWeightAndProfit[1];

            int finalI = i;
            var node1 = dataTTP.getNodes().stream()
                    .filter(nodeTTP -> nodeTTP.getId() == specimen.getNodeGenome()[finalI])
                    .findFirst()
                    .orElseThrow();
            var node2 = dataTTP.getNodes().stream()
                    .filter(nodeTTP -> nodeTTP.getId() == specimen.getNodeGenome()[(finalI + 1) % specimen.getNodeGenome().length])
                    .findFirst()
                    .orElseThrow();

            double distance = dataTTP.getNodes()
                    .get(dataTTP.getNodes()
                            .indexOf(node1))
                    .getDistance(dataTTP.getNodes()
                            .get(dataTTP.getNodes()
                                    .indexOf(node2))
                    );
            tspCost += distance / (dataTTP.getMaxSpeed() - currentWeight * dataTTP.getVelocityConst());
        }
        return  knapsackValue - tspCost;
    }

    private Double[] getItemsWeightAndProfitForNode(Specimen specimen, int nodeId) {
        Double[] result = new Double[]{0d, 0d};

        if (dataTTP.getItems().stream()
                .anyMatch(itemTTP -> itemTTP.getAssignedNodeId() == nodeId)) {
            for (var item : dataTTP.getItems().stream()
                    .filter(itemTTP -> itemTTP.getAssignedNodeId() == nodeId)
                    .collect(Collectors.toList())) {

                if (!specimen.getKnapsack().contains(item)) {
                    result[0] += item.getWeight();
                    result[1] += item.getProfit();
                }
            }
        }
        return result;
    }
}
