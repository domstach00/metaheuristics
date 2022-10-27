package org.example.operators.mutation;

import org.example.initialization.InitializationRandom;
import org.example.itemselector.ItemSelectorPriceAndWeight;
import org.example.model.Specimen;
import org.example.support.Utils;

public class MutationSwapTS implements IMutation {

    @Override
    public Specimen mutation(Specimen specimen, double probability) {
        Specimen newSpecimen = new Specimen(specimen);

        int[] indexStartAndFinish = Utils.getStartAndFinishValues(newSpecimen.getNodeGenome().length);
        int index = indexStartAndFinish[0];
        int swapWith = indexStartAndFinish[1];

        newSpecimen.initNewItems(new InitializationRandom(), new ItemSelectorPriceAndWeight());

        Integer valueFromIndex = newSpecimen.getNodeGenome()[index];
        newSpecimen.getNodeGenome()[index] = newSpecimen.getNodeGenome()[swapWith];
        newSpecimen.getNodeGenome()[swapWith] = valueFromIndex;
        return newSpecimen;
    }

}
