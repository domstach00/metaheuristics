package org.example.support;

import org.example.config.ConfigEA;
import org.example.config.ConfigTS;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static ConfigEA getSuggestedConfigEA() {
        return new ConfigEA(100, 100, 0.7, 0.1, 5);
    }

    public static ConfigTS getSuggestedConfigTS() {
        return new ConfigTS(1000, 10, 100);
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

    public static final String inputFileName = "hard_3.ttp";

    public static final String getInputPath = "src/main/resources/input/" + inputFileName;

    public static String getInputFileNameNoExtension() {
        return inputFileName.substring(0, inputFileName.lastIndexOf('.'));
    }

    public static final String getLogsPath = "src/main/resources/logs/swapts";


}
