package org.example.operators.mutation;

import org.example.model.Specimen;

public interface IMutation {
    <T extends Specimen> T mutation(T specimen, double probability);
//    Specimen mutation(Specimen specimen, double probability);

    default String getMutationName() {
        return this.getClass().getSimpleName();
    }
}
