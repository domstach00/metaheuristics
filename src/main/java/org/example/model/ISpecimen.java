package org.example.model;

import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;
import org.example.itemselector.IItemSelector;

public interface ISpecimen {
    void init(IInitialization initialization, IItemSelector itemSelector);
    void eval(IEvaluator evaluator);
    void fix();
}
