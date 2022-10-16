package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NodeTTP {
    private int id;
    private double x;
    private double y;

    public double getDistance(NodeTTP otherNode) {
        double xDist = Math.pow(this.getX() - otherNode.getX(), 2);
        double yDist = Math.pow(this.getY() - otherNode.getY(), 2);
        return Math.pow(xDist + yDist, 0.5);
    }
}
