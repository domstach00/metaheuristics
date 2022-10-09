package org.example.loader;

import org.example.model.DataTTP;
import org.example.model.ItemTTP;
import org.example.model.NodeTTP;

import java.io.*;
import java.util.ArrayList;

public class LoaderTTP {

    private DataTTP dataTTP;

    public LoaderTTP(DataTTP dataTTP) {
        this.dataTTP = dataTTP;
    }

    public void readAllProperties(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            dataTTP.setProblemName(getPropertyValue(bufferedReader.readLine()));
            dataTTP.setDataType(getPropertyValue(bufferedReader.readLine()));
            dataTTP.setDimension(Integer.parseInt(getPropertyValue(bufferedReader.readLine())));
            dataTTP.setNumberOfItems(Integer.parseInt(getPropertyValue(bufferedReader.readLine())));
            dataTTP.setKnapsackCapacity(Integer.parseInt(getPropertyValue(bufferedReader.readLine())));
            dataTTP.setMinSpeed(Double.parseDouble(getPropertyValue(bufferedReader.readLine())));
            dataTTP.setMaxSpeed(Double.parseDouble(getPropertyValue(bufferedReader.readLine())));
            dataTTP.setRentingRatio(Double.parseDouble(getPropertyValue(bufferedReader.readLine())));
            dataTTP.setEdgeWeightType(getPropertyValue(bufferedReader.readLine()));

            dataTTP.setNodes(readNodeCoordSection(bufferedReader));
            dataTTP.setItems(readItemSection(bufferedReader));

            initNodeAdjacencyMatrix();
            initVelocity();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initNodeAdjacencyMatrix() {
        if (dataTTP.getNodeAdjacencyMatrix() == null)
            dataTTP.setNodeAdjacencyMatrix(new ArrayList<>());
        for (int i = 0; i < dataTTP.getNodes().size(); i++) {
            dataTTP.getNodeAdjacencyMatrix().add(new ArrayList<>());
            for (int j = 0; j < dataTTP.getNodes().size(); j++) {
                double dist;
                if (i == j)
                    dist = 0;
                else
                    dist = dataTTP.getNodes().get(i).getDistance(dataTTP.getNodes().get(j));
                dataTTP.getNodeAdjacencyMatrix().get(i).add(dist);
            }
        }
    }

    private void initVelocity() {
        dataTTP.setVelocityConst(
                (dataTTP.getMaxSpeed() - dataTTP.getMinSpeed()) / dataTTP.getKnapsackCapacity()
        );
    }

    private ArrayList<NodeTTP> readNodeCoordSection(BufferedReader bufferedReader) throws IOException {
        ArrayList<NodeTTP> nodeList = new ArrayList<>();
        String line = bufferedReader.readLine();
        if (!line.contains("NODE_COORD_SECTION"))
            return nodeList;

        while (true) {
            line = bufferedReader.readLine();
            if (!line.contains("ITEMS SECTION"))
                nodeList.add(getNodeFromLine(line));
            else
                return nodeList;
        }
    }

    private ArrayList<ItemTTP> readItemSection(BufferedReader bufferedReader) throws IOException {
        ArrayList<ItemTTP> itemList = new ArrayList<>();
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null)
                return itemList;
            else
                itemList.add(getItemFromLine(line));
        }
    }

    private String getPropertyValue(String line) {
        return line.split(":")[1].trim();
    }

    private NodeTTP getNodeFromLine(String line) {
        String[] nodeLineData = line.split("\t");
        return new NodeTTP(
                Integer.parseInt(nodeLineData[0]),
                Double.parseDouble(nodeLineData[1]),
                Double.parseDouble(nodeLineData[2])
        );
    }

    private ItemTTP getItemFromLine(String line) {
        String[] itemLineData = line.split("\t");
        return new ItemTTP(
                Integer.parseInt(itemLineData[0]),
                Integer.parseInt(itemLineData[1]),
                Integer.parseInt(itemLineData[2]),
                Integer.parseInt(itemLineData[3])
        );
    }

}
