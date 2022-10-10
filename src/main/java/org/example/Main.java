package org.example;

import org.example.evaluator.Evaluator;
import org.example.initialization.InitializationRandom;
import org.example.model.DataTTP;

public class Main {
    public static void main(String[] args) {
        DataTTP dataTTP = new DataTTP();
        WorkFlow workFlow = new WorkFlow(dataTTP, new InitializationRandom(), new Evaluator(dataTTP));
        workFlow.start();
    }
}