package org.example.operators.crossover;

import org.example.model.Specimen;

public interface ICrossover {
    <T extends Specimen> T crossover(T specimen1, T specimen2);
//    Specimen crossover(Specimen specimen1, Specimen specimen2);
    default String getCrossoverName() {
        return this.getClass().getSimpleName();
    }
}
