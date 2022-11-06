package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import org.example.support.Utils;

public class CsvRecordSA implements ICsvRecord {
    public static final String[] FIELDS_ORDER = {"iteracja", "najlepsza_ocena", "obecna_ocena"};

    @CsvBindByPosition(position = 0)
    private int iteration;

    @CsvBindByPosition(position = 1)
    private double bestScore;

    @CsvBindByPosition(position = 2)
    private double currentScore;

    public CsvRecordSA(int iteration, double bestScore, double currentScore) {
        this.iteration = iteration;
        this.bestScore = bestScore;
        this.currentScore = currentScore;
    }



    @Override
    public String getHeader() {
        return String.format(
                "%s; %s; %s\n",
                CsvRecordSA.FIELDS_ORDER[0],
                CsvRecordSA.FIELDS_ORDER[1],
                CsvRecordSA.FIELDS_ORDER[2]
        );
    }

    @Override
    public String getLine() {
        return String.format(
                "%d; %f; %f\n",
                iteration,
                bestScore,
                currentScore
        );
    }

    @Override
    public String getFileName() {
        return Utils.getInputFileNameNoExtension() + "-logger_" + Utils.getUsedConfig().configToFileName() + ".csv";
    }
}
