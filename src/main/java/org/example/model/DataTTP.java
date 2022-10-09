package org.example.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DataTTP {
    private String problemName;
    private String dataType;
    private int dimension;
    private int numberOfItems;
    private int knapsackCapacity;
    private double minSpeed;
    private double maxSpeed;
    private double rentingRatio;
    private String edgeWeightType;

    private ArrayList<NodeTTP> nodes;
    private ArrayList<ItemTTP> items;

    private double velocityConst;

    private ArrayList<ArrayList<Double>> nodeAdjacencyMatrix;
}
