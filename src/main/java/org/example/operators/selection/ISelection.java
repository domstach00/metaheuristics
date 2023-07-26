package org.example.operators.selection;

import org.example.model.Specimen;

import java.util.ArrayList;

public interface ISelection {
    <T extends Specimen> T selection(ArrayList<T> population, int n);
//    Specimen selection(ArrayList<Specimen> population, int n);

    default String getSelectionName() {
        return this.getClass().getSimpleName();
    }
}
