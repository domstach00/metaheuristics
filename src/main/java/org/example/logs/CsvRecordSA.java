package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import org.example.config.ConfigLog;
import org.example.config.ConfigSA;
import org.example.support.Utils;

public class CsvRecordSA implements ICsvRecord {
    public static final String[] FIELDS_ORDER = {"iteracja", "najlepsza_ocena", "obecna_ocena"};

    private final ConfigSA configSA;
    private final ConfigLog configLog;

    @CsvBindByPosition(position = 0)
    private final int iteration;

    @CsvBindByPosition(position = 1)
    private final double bestScore;

    @CsvBindByPosition(position = 2)
    private final double currentScore;

    public CsvRecordSA(int iteration, double bestScore, double currentScore, ConfigSA configSA, ConfigLog configLog) {
        this.iteration = iteration;
        this.bestScore = bestScore;
        this.currentScore = currentScore;
        this.configSA = configSA;
        this.configLog = configLog;
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
        return Utils.getInputFileNameNoExtension(configLog.getCurrentFileName()) + "-logger_" + configSA.configToFileName() + "_" + configLog.getUniDate() +".csv";
    }
}
