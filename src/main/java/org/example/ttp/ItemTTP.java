package org.example.ttp;

import lombok.Data;

@Data
public class ItemTTP {
    private int id;
    private int profit;
    private int weight;
    private int assignedNodeId;
    private NodeTTP assignNode;
}
