package org.example.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.model.SexEnum;

@Data
@AllArgsConstructor
@Builder
public class ConfigHybEA extends Config  {
    private int popSize;
    private int gen;
    private double pX;
    private double pM;
    private int tour;
    private int maxAge;

    public ConfigHybEA(ConfigEA configEA, int maxAge) {
        this.popSize = configEA.getPopSize();
        this.gen = configEA.getGen();
        this.pX = configEA.getPX();
        this.pM = configEA.getPM();
        this.tour = configEA.getTour();
        this.maxAge = maxAge;
    }

    @Override
    public String configToFileName() {
        return String.format("popSize-%d_gen-%d_pX-%f_pM-%f_tour-%d_maxAge-%d_sex-%d",
                popSize, gen, pX, pM, tour, maxAge, SexEnum.values().length);
    }
}

