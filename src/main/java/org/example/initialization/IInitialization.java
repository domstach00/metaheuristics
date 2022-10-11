package org.example.initialization;

import org.example.itemselector.IItemSelector;
import org.example.model.Specimen;

public interface IInitialization {
    void startInitializationNode(Specimen specimen);
    void startInitializationItems(IItemSelector itemSelector, Specimen specimen);
}
