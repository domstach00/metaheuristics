package org.example.itemselector;

import org.example.model.ItemTTP;
import org.example.model.Specimen;

import java.util.ArrayList;
import java.util.Comparator;

public class ItemSelectorPrice implements IItemSelector{
    @Override
    public void select(Specimen specimen) {
        ArrayList<ItemTTP> items = new ArrayList<>(specimen.getDataTTP().getItems());

        items.sort(Comparator.comparing(ItemTTP::getProfit).reversed());

        for (ItemTTP item : items)
            specimen.addToKnapsack(item);
    }
}
