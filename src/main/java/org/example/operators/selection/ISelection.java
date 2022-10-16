package org.example.operators.selection;

import org.example.model.Specimen;

import java.util.ArrayList;

public interface ISelection {
    Specimen selection(ArrayList<Specimen> population, int n);
}
