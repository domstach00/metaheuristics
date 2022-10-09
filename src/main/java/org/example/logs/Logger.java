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

public class Logger {
    private static String logDirectory = Utils.getLogsPath;

    public static void logger(CsvRecord csvRecord) {
        String fullPath = logDirectory + "/" + csvRecord.getFileName();
        File file = new File(fullPath);

        boolean addHeader = !file.exists();

        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(csvRecord.getHeader());

            StatefulBeanToCsv<CsvRecord> csv = new StatefulBeanToCsvBuilder<CsvRecord>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(false)
                    .build();
            csv.write(csvRecord);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void log(ICsvRecord iCsvRecord) {
        File file = new File(logDirectory + "/" + iCsvRecord.getFileName());

        boolean addHeader = !file.exists();

        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(iCsvRecord.getHeader());
            writer.write(iCsvRecord.getLine());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
