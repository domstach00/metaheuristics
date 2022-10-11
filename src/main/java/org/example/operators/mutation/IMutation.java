package org.example.operators.mutation;

import org.example.model.Specimen;

public interface IMutation {
    void mutation(Specimen specimen, double probability);
}
