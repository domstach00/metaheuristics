package org.example.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTTP {
    private int id;
    private int profit;
    private int weight;
    private int assignedNodeId;

    public double getProfitByWeight() {
        return (double) profit / (double) weight;
    }
}


