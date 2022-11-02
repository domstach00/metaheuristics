package org.example.itemselector;

import org.example.model.ItemTTP;
import org.example.model.Specimen;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ItemSelectorRandom implements IItemSelector {

    @Override
    public void select(Specimen specimen) {
        ArrayList<ItemTTP> items = specimen.getDataTTP().getItems();

        boolean flag = true;
        int counter = 0;
        while (flag) {
            if (specimen.addToKnapsack(items.get(ThreadLocalRandom.current().nextInt(items.size()))))
                counter++;
            if (counter > 10)
                flag = false;
        }
    }
}
