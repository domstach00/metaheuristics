package org.example.model;

import org.example.evaluator.IEvaluator;
import org.example.initialization.IInitialization;

public interface ISpecimen {
    void init(IInitialization initialization);
    void eval(IEvaluator evaluator);
    void fix();
}
