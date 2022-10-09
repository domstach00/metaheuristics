package org.example.model;

import org.example.support.Evaluator;

public interface ISpecimen {
    void init();
    void eval(Evaluator evaluator);
    void fix();
}
