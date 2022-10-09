package org.example.config;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ConfigEA  {
    private int popSize;
    private int gen;
    private double pX;
    private double pM;
    private int tour;
}

