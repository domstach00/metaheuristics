package org.example.initialization;

import org.example.itemselector.IItemSelector;
import org.example.model.NodeTTP;
import org.example.model.Specimen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitializationRandom implements IInitialization {

    private final Random random = new Random();

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
}
