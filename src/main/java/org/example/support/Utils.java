package org.example.support;

import org.example.config.ConfigEA;

public class Utils {

    public static ConfigEA getSuggestedConfigEA() {
        return new ConfigEA(100, 100, 0.7, 0.1, 5);
    }

    public static final String getInputPath = "src/main/resources/input/trivial_0.ttp";

    public static final String getLogsPath = "src/main/resources/logs";


}
