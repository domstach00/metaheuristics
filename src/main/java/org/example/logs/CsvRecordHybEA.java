package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.config.ConfigHybEA;
import org.example.config.ConfigLog;
import org.example.model.Specimen;
import org.example.support.Utils;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Builder
public class CsvRecordHybEA implements ICsvRecord {

    public static final String[] FIELDS_ORDER = {"nr_pokolenia", "najlepsza_ocena", "średnia_ocen", "najgorsza_ocena"};

    public static ArrayList<Double> globalAverageScoreList = new ArrayList<>();

    @CsvBindByPosition(position = 0)
    private int generationNr;

    @CsvBindByPosition(position = 1)
    private double bestScore = -1 * Double.MAX_VALUE;
    @Getter
    private static double globalBestScore = -1 * Double.MAX_VALUE;

    @CsvBindByPosition(position = 2)
    private double averageScore;

    @CsvBindByPosition(position = 3)
    private double worstScore = Double.MAX_VALUE;
    @Getter
    private static double globalWorstScore = Double.MAX_VALUE;

    private ConfigHybEA configHybEA;
    private ConfigLog configLog;

    public CsvRecordHybEA(int generationNr, ArrayList<? extends Specimen> population, Specimen bestSpecimen, ConfigHybEA configHybEA, ConfigLog configLog) {
        this.configHybEA = configHybEA;
        this.configLog = configLog;
        this.generationNr = generationNr;
        this.bestScore = bestSpecimen.getFitness();
        ArrayList<Double> fitnessList = new ArrayList<>();
        for (Specimen specimen: population){
            if (worstScore > specimen.getFitness())
                worstScore = specimen.getFitness();

            fitnessList.add(specimen.getFitness());
        }

        averageScore = fitnessList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();

        globalAverageScoreList.add(averageScore);
        if (bestScore > globalBestScore)
            globalBestScore = bestScore;
        if (worstScore < globalWorstScore)
            globalWorstScore = worstScore;
    }

    @Override
    public String getHeader() {
        return String.format(
                "%s; %s; %s; %s\n",
                CsvRecordHybEA.FIELDS_ORDER[0],
                CsvRecordHybEA.FIELDS_ORDER[1],
                CsvRecordHybEA.FIELDS_ORDER[2],
                CsvRecordHybEA.FIELDS_ORDER[3]
        );
    }

    @Override
    public String getLine() {
        return String.format(
                "%d; %s; %s; %s\n",
                generationNr,
                bestScore,
                averageScore,
                worstScore
        );
    }

    @Override
    public String getFileName() {
        return Utils.getInputFileNameNoExtension(configLog.getCurrentFileName()) + "-logger_" + configHybEA.configToFileName() + "_" + configLog.getUniDate() + ".csv";
    }

}
