package org.example.support;

import org.example.Main;
import org.example.config.Config;
import org.example.config.ConfigEA;
import org.example.config.ConfigSA;
import org.example.config.ConfigTS;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static ConfigEA getSuggestedConfigEA() {
        return new ConfigEA(100, 100, 0.3, 0.01, 5);
    }

    public static ConfigTS getSuggestedConfigTS() {
        return new ConfigTS(10000, 10, 100);
    }

    public static ConfigSA getSuggestedConfigSA() {
        return new ConfigSA(150_000, 10, 1_000_000, 1, 0.9999);
    }

    public static Config getUsedConfig() {
        return Main.workFlow.getConfig();
    }

    public static int[] getStartAndFinishValues(int maxValueExcluded) {
        int indexToStart = ThreadLocalRandom.current().nextInt(maxValueExcluded);
        int indexToFinish = ThreadLocalRandom.current().nextInt(maxValueExcluded);

        while (indexToStart == indexToFinish)
            indexToFinish = ThreadLocalRandom.current().nextInt(maxValueExcluded);

        if (indexToStart > indexToFinish) {
            // Swap values
            indexToStart = indexToStart + indexToFinish;
            indexToFinish = indexToStart - indexToFinish;
            indexToStart = indexToStart - indexToFinish;
        }

        return new int[] {indexToStart, indexToFinish};
    }

    public static final String inputFileName = Main.currentFile;

    public static final String getInputPath = "src/main/resources/input/" + inputFileName;

    public static String getInputFileNameNoExtension() {
        return inputFileName.substring(0, inputFileName.lastIndexOf('.'));
    }

    public static final String getLogsPath = "src/main/resources/logs/" + Main.workFlow.getAlgName().toLowerCase() + "/swap";


}
