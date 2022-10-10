package org.example.initialization;

import org.example.model.Specimen;

public interface IInitialization {
    void startInitializationNode(Specimen specimen);
    void startInitializationItems(Specimen specimen);
}
