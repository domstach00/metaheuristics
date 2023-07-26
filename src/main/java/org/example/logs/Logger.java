package org.example.logs;

import org.example.config.ConfigLog;
import org.example.support.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {

    private static final boolean summaryLog = true;
    private static final String summaryLogFileName = "common.csv";

    public synchronized static void log(ICsvRecord csvRecord, ConfigLog configLog) {
        File file = new File(configLog.getLogsPath() + "/" + csvRecord.getFileName());

        boolean addHeader = !file.exists();

        // Create path if not exist
        try {
            if (addHeader && !new File(configLog.getLogsPath()).isDirectory())
                Files.createDirectories(Paths.get(configLog.getLogsPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Append to log file
        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(csvRecord.getHeader());
            writer.write(csvRecord.getLine().replace('.', ','));

            if (summaryLog)
                logCommon(csvRecord, configLog);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static void logCommon(ICsvRecord csvRecord, ConfigLog configLog) {
        File file = new File(
                configLog.getLogsPath() + "/" + Utils.getInputFileNameNoExtension(configLog.getCurrentFileName()) +
                "/" + Logger.summaryLogFileName
        );
        boolean addHeader = !file.exists();

        try {
            if (addHeader && !new File(configLog.getLogsPath() + "/" + Utils.getInputFileNameNoExtension(configLog.getCurrentFileName())).isDirectory())
                Files.createDirectories(Paths.get(configLog.getLogsPath() + "/" + Utils.getInputFileNameNoExtension(configLog.getCurrentFileName())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(csvRecord.getHeader());
            writer.write(csvRecord.getLine().replace('.', ','));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
