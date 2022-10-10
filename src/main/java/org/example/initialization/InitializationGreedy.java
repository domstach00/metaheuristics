package org.example.initialization;

import lombok.RequiredArgsConstructor;
import org.example.model.ItemTTP;
import org.example.model.NodeTTP;
import org.example.model.Specimen;

import java.util.*;

@RequiredArgsConstructor
public class InitializationGreedy implements IInitialization{

    private static ArrayList<NodeTTP> usedStartNodes = new ArrayList<>();

    private static boolean flag = true;

    Random random = new Random();

    @Override
    public void startInitializationNode(Specimen specimen) {
        ArrayList<NodeTTP> possibleNodes = new ArrayList<>(List.copyOf(specimen.getDataTTP().getNodes()));

        if (flag) {
            if (usedStartNodes.size() >= specimen.getDataTTP().getNodes().size())
                return;
            for (NodeTTP nodeTTP : possibleNodes) {
                if (!usedStartNodes.contains(nodeTTP)) {
                    usedStartNodes.add(nodeTTP);
                    specimen.setNodeGenome(greedyAlg(possibleNodes, nodeTTP, specimen.getDataTTP().getNodeAdjacencyMatrix()));
                    break;
                }
            }
        }
        else {
            // Random Node order
            int index = 0;
            while (possibleNodes.size() != 0) {
                NodeTTP chosenNode = possibleNodes.get(random.nextInt(possibleNodes.size()));
                specimen.getNodeGenome()[index] = chosenNode.getId();
                possibleNodes.remove(chosenNode);
                index++;
            }
        }
    }

    @Override
    public void startInitializationItems(Specimen specimen) {
        // Select random items
        for (ItemTTP item : specimen.getDataTTP().getItems()) {
            if (random.nextDouble() <= 0.3) {
                specimen.addToKnapsack(item);
            }
        }
    }

    private Integer[] greedyAlg(ArrayList<NodeTTP> nodes, NodeTTP startNode,  ArrayList<ArrayList<Double>> nodeAdjacencyMatrix) {
        Integer[] nodeGenome = new Integer[nodes.size()];
        nodeGenome[0] = startNode.getId();
        int bestNextNodeId = nodeGenome[0];
        int lastIndex = 0;

        ArrayList<Double> list = nodeAdjacencyMatrix.get(nodeGenome[lastIndex]);
        System.out.println(Arrays.toString(list.toArray()));
        bestNextNodeId = list.indexOf(
                list.stream()
                        .filter(aDouble -> aDouble != 0)
                        .min(Double::compareTo)
                        .orElseThrow());
        nodeGenome[++lastIndex] = bestNextNodeId;


        return nodeGenome;
    }

    private boolean contain(Integer[] listOfNodes, int value) {
        for (Integer val : listOfNodes) {
            if (val == value)
                return true;
        }
        return false;
    }
}
