package org.example.logs;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.support.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {
    private static String logDirectory = Utils.getLogsPath;

    public static void logger(CsvRecordEA csvRecordEA) {
        String fullPath = logDirectory + "/" + csvRecordEA.getFileName();
        File file = new File(fullPath);

        boolean addHeader = !file.exists();

        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(csvRecordEA.getHeader());

            StatefulBeanToCsv<CsvRecordEA> csv = new StatefulBeanToCsvBuilder<CsvRecordEA>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(false)
                    .build();
            csv.write(csvRecordEA);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void log(ICsvRecord iCsvRecord) {
        File file = new File(logDirectory + "/" + iCsvRecord.getFileName());

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
                writer.write(iCsvRecord.getHeader());
            writer.write(iCsvRecord.getLine().replace('.', ','));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
