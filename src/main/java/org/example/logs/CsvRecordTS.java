package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import org.example.config.ConfigLog;
import org.example.config.ConfigTS;
import org.example.model.Specimen;
import org.example.support.Utils;
import org.example.tabulistmanager.Tabu;

public class CsvRecordTS implements ICsvRecord {

    public static final String[] FIELDS_ORDER = {"iteracja", "najlepsza_ocena", "średnia_ocena", "najgorsza_ocena", "obecna_ocena"};

    private final ConfigTS configTS;
    private final ConfigLog configLog;

    @CsvBindByPosition(position = 0)
    private final int iteration;

    @CsvBindByPosition(position = 1)
    private final double bestScore;

    @CsvBindByPosition(position = 2)
    private final double averageScore;

    @CsvBindByPosition(position = 3)
    private final double worstScore;

    @CsvBindByPosition(position = 4)
    private final double currentScore;

    public CsvRecordTS(int iteration, Specimen bestSpecimen, Specimen currentSpecimen, Tabu tabu, ConfigTS configTS, ConfigLog configLog) {
        this.iteration = iteration;
        this.bestScore = bestSpecimen.getFitness();
        this.averageScore = tabu.getAverageFitness();
        this.worstScore  = tabu.findWorst().getFitness();
        this.currentScore = currentSpecimen.getFitness();
        this.configTS = configTS;
        this.configLog = configLog;
    }

    public CsvRecordTS(int iteration, Specimen bestSpecimenGlobal, Specimen best, Specimen worst, double avg, ConfigTS configTS, ConfigLog configLog) {
        this.iteration = iteration;
        this.bestScore = bestSpecimenGlobal.getFitness();
        this.averageScore = avg;
        this.worstScore  = worst.getFitness();
        this.currentScore = best.getFitness();
        this.configTS = configTS;
        this.configLog = configLog;
    }

    @Override
    public String getHeader() {
        return String.format(
                "%s; %s; %s; %s; %s\n",
                CsvRecordTS.FIELDS_ORDER[0],
                CsvRecordTS.FIELDS_ORDER[1],
                CsvRecordTS.FIELDS_ORDER[2],
                CsvRecordTS.FIELDS_ORDER[3],
                CsvRecordTS.FIELDS_ORDER[4]
        );
    }

    @Override
    public String getLine() {
        return String.format(
                "%d; %s; %s; %s; %f\n",
                iteration,
                bestScore,
                averageScore,
                worstScore,
                currentScore
        );
    }

    @Override
    public String getFileName() {
        return Utils.getInputFileNameNoExtension(configLog.getCurrentFileName()) + "-logger_" + configTS.configToFileName() + "_" + configLog.getUniDate() + ".csv";
    }
}
