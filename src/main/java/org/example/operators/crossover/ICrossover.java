package org.example.operators.crossover;

import org.example.model.Specimen;

public interface ICrossover {
    Specimen crossover(Specimen specimen1, Specimen specimen2);
}
