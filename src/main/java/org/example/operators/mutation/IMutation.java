package org.example.operators.mutation;

import org.example.model.Specimen;

public interface IMutation {
    Specimen mutation(Specimen specimen, double probability);

    default String getMutationName() {
        return this.getClass().getSimpleName();
    }
}
