package org.example.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ConfigSA {
    private int iteration;
    private int nSize;
    private int startTemp;
    private int endTemp;
    private double annealingRate;

    public String getConfigSAFileName() {
        return String.format("iter-%d_nSize-%d_temp-%d-%d_cooling-%f",
                iteration, nSize, startTemp, endTemp, annealingRate);
    }
}
