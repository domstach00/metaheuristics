package org.example.initialization;

import lombok.RequiredArgsConstructor;
import org.example.itemselector.IItemSelector;
import org.example.model.NodeTTP;
import org.example.model.Specimen;

import java.util.*;

@RequiredArgsConstructor
public class InitializationGreedy implements IInitialization{
    Random random = new Random();

    @Override
    public void startInitializationNode(Specimen specimen) {
        ArrayList<NodeTTP> possibleNodes = new ArrayList<>(List.copyOf(specimen.getDataTTP().getNodes()));

        // Random Node order
        int index = 0;
        while (possibleNodes.size() != 0) {
            NodeTTP chosenNode = possibleNodes.get(random.nextInt(possibleNodes.size()));
            specimen.getNodeGenome()[index] = chosenNode.getId();
            possibleNodes.remove(chosenNode);
            index++;
        }
    }

    @Override
    public void startInitializationItems(IItemSelector itemSelector, Specimen specimen) {
        itemSelector.select(specimen);
    }

    private Integer[] greedyAlg(ArrayList<NodeTTP> nodes, NodeTTP startNode,  ArrayList<ArrayList<Double>> nodeAdjacencyMatrix) {
        Integer[] nodeGenom = new Integer[nodes.size()];
        ArrayList<Integer> nodeGenomArray = new ArrayList<>();
        nodeGenomArray.add(startNode.getId());
        nodeGenom[0] = startNode.getId();

        int nextNodeIndex = startNode.getId();
        while (nodeGenomArray.size() < nodes.size()) {
            ArrayList<Double> nodeAdjacency = new ArrayList<>(nodeAdjacencyMatrix.get(nextNodeIndex));
            for (int i = nodeAdjacency.size() - 1; i >= 0; i--)
                if (nodeGenomArray.contains(i) || nodeAdjacency.get(i) == 0)
                    nodeAdjacency.set(i, 0d);

            nextNodeIndex = nodeAdjacency.indexOf(
                    nodeAdjacency.stream()
                            .filter(aDouble -> aDouble != 0)
                            .min(Double::compareTo)
                            .orElseThrow()
            );
            nodeGenomArray.add(nextNodeIndex);
        }

        for (int i = 0; i < nodeGenomArray.size(); i++)
            nodeGenom[i] = nodeGenomArray.get(i);
        return nodeGenom;
    }
}
