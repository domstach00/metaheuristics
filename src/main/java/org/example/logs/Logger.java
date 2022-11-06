package org.example.logs;

import org.example.support.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {
    private static final String logDirectory = Utils.getLogsPath;

    private static final boolean summaryLog = true;
    private static final String summaryLogFileName = "common.csv";

    public static void log(ICsvRecord csvRecord) {
        File file = new File(logDirectory + "/" + csvRecord.getFileName());

        boolean addHeader = !file.exists();

        // Create path if not exist
        try {
            if (addHeader && !new File(logDirectory).isDirectory())
                Files.createDirectories(Paths.get(logDirectory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Append to log file
        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(csvRecord.getHeader());
            writer.write(csvRecord.getLine().replace('.', ','));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void logCommon(ICsvRecord csvRecord) {
        
    }
}
