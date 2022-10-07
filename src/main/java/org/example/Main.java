package org.example;

import org.example.logs.CsvRecord;
import org.example.logs.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.log(new CsvRecord(1, 5.5, 3.5, 2.0));
    }
}