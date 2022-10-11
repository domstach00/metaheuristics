package org.example;

import org.example.evaluator.Evaluator;
import org.example.initialization.InitializationGreedy;
import org.example.itemselector.ItemSelectorPrice;
import org.example.model.DataTTP;

public class Main {
    public static void main(String[] args) {
        DataTTP dataTTP = new DataTTP();
        WorkFlow workFlow = new WorkFlow(dataTTP, new InitializationGreedy(), new Evaluator(dataTTP), new ItemSelectorPrice());
        workFlow.start();
    }
}