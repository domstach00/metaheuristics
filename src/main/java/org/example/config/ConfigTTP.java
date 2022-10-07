package org.example.config;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.*;

@ToString
@EqualsAndHashCode
public class ConfigTTP implements IConfig {
    private String problemName;
    private String dataType;
    private int dimension;
    private int numberOfItems;
    private int knapsackCapacity;
    private double minSpeed;
    private double maxSpeed;
    private double rentingRatio;
    private String edgeWeightType;

    @Override
    public void readProperties(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            problemName = getValue(bufferedReader.readLine());
            dataType = getValue(bufferedReader.readLine());
            dimension = Integer.parseInt(getValue(bufferedReader.readLine()));
            numberOfItems = Integer.parseInt(getValue(bufferedReader.readLine()));
            knapsackCapacity = Integer.parseInt(getValue(bufferedReader.readLine()));
            minSpeed = Double.parseDouble(getValue(bufferedReader.readLine()));
            maxSpeed = Double.parseDouble(getValue(bufferedReader.readLine()));
            rentingRatio = Double.parseDouble(getValue(bufferedReader.readLine()));
            edgeWeightType = getValue(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getValue(String line) {
        return line.split(":")[1].trim();
    }
}
