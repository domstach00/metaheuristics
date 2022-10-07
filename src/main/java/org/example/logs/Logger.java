package org.example.logs;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static String logDirectory = "src/main/resources/logs";
    private static String fileName = "logger.csv";

    public static void log(CsvRecord csvRecord) {
        String fullPath = logDirectory + "/" + fileName;
        File file = new File(fullPath);

        boolean addHeader = !file.exists();

        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            if (addHeader)
                writer.write(CsvRecord.getHeader());

            StatefulBeanToCsv<CsvRecord> csv = new StatefulBeanToCsvBuilder<CsvRecord>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(false)
                    .build();
            csv.write(csvRecord);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
    }

}
