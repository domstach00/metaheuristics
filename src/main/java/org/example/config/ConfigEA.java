package org.example.config;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ConfigEA extends Config  {
    private int popSize;
    private int gen;
    private double pX;
    private double pM;
    private int tour;

    @Override
    public String configToFileName() {
        return String.format("popSize-%d_gen-%d_pX-%f_pM-%f_tour-%d",
                popSize, gen, pX, pM, tour);
    }
}

