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

    public String getConfigEAFileName() {
        return String.format("popSize-%d_gen-%d_pX-%f_pM-%f_tour-%d",
                popSize, gen, pX, pM, tour);
    }
}

