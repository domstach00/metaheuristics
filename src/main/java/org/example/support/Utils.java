package org.example.support;

import org.example.config.ConfigEA;
import org.example.config.ConfigHybEA;
import org.example.config.ConfigSA;
import org.example.config.ConfigTS;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static ConfigEA getSuggestedConfigEA() {
        return new ConfigEA(100, 5000, 0.6, 0.01, 10);
    }

    public static ConfigHybEA getSuggestedConfigHybridEA() {
        return new ConfigHybEA(100, 5000, 0.7, 0.01, 10, 15);
    }

    public static ConfigTS getSuggestedConfigTS() {
        return new ConfigTS(10000, 10, 100);
    }

    public static ConfigSA getSuggestedConfigSA() {
        return new ConfigSA(150_000, 10, 1_000_000, 1, 0.9999);
    }

    public static String getCurrentWorkingDirectory() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
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

    public static String getInputFileNameNoExtension(String name) {
        return name.substring(0, name.lastIndexOf('.'));
    }

    public synchronized static String getCurrentFormatDate() {
        String pattern = "yyyyMMdd-HHmmssSSS";
        DateFormat formatter = new SimpleDateFormat(pattern);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date) + getRandomString(3);
    }

    public synchronized static String getRandomString(int stringSize) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < stringSize; i++) {
            int index = ThreadLocalRandom.current().nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
