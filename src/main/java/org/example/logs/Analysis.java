package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import org.example.model.Specimen;

import java.util.ArrayList;

public class Analysis implements ICsvRecord{

    @Override
    public String getFileName() {
        return "analysis.csv";
    }

    @CsvBindByPosition(position = 0)
    private double bestScore;
    @CsvBindByPosition(position = 1)
    private double worstScore;
    @CsvBindByPosition(position = 2)
    private double avgScore;

    @CsvBindByPosition(position = 3)
    private double std = 0d;

    public Analysis() {
        this.bestScore = -1 * Double.MAX_VALUE;
        this.worstScore = Double.MAX_VALUE;
    }

    public void fetchDataFromGlobalCsvRecord() {
        bestScore = CsvRecord.getGlobalBestScore();
        worstScore = CsvRecord.getGlobalWorstScore();

        ArrayList<Double> avgList = CsvRecord.globalAverageScoreList;
        avgScore = avgList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();

        std = calcStd(CsvRecord.globalAverageScoreList);
    }

    public void analysisPopulation(ArrayList<Specimen> population) {
        ArrayList<Double> fitnessList = new ArrayList<>();
        for (Specimen specimen: population){
            if (bestScore < specimen.getFitness())
                bestScore = specimen.getFitness();
            if (worstScore > specimen.getFitness())
                worstScore = specimen.getFitness();

            fitnessList.add(specimen.getFitness());
        }

        avgScore = fitnessList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();
    }

    private double calcStd(ArrayList<Double> avgScoreList) {
        double sum = 0.0, standard_deviation = 0.0;
        for(double tmp : avgScoreList)
            sum += tmp;

        double mean = sum/ avgScoreList.size();

        for(double temp: avgScoreList)
            standard_deviation += Math.pow(temp - mean, 2);

        return Math.sqrt(standard_deviation/ avgScoreList.size());
    }

    @Override
    public String getHeader() {
        return String.format(
                "%s; %s; %s; %s\n",
                "best",
                "worst",
                "avg",
                "std"
        );
    }

    public String getBestScore() {
        return String.valueOf(bestScore).replace(',', '.');
    }

    public String getWorstScore() {
        return String.valueOf(worstScore).replace(',', '.');
    }

    public String getAvgScore() {
        return String.valueOf(avgScore).replace(',', '.');
    }

    public String getStd() {
        return String.valueOf(std).replace(',', '.');
    }

    @Override
    public String getLine() {
        return String.format(
                "%s; %s; %s; %s\n",
//                getBestScore(),
//                getWorstScore(),
//                getAvgScore(),
//                getStd()
                bestScore,
                worstScore,
                avgScore,
                std
        );
    }
}
