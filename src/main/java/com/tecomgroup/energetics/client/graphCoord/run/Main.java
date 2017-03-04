package com.tecomgroup.energetics.client.graphCoord.run;

import com.tecomgroup.energetics.client.graphCoord.*;
import com.tecomgroup.energetics.client.graphCoord.Graphs.IGraph;
import com.tecomgroup.energetics.client.graphCoord.Services.CalculateCoordService;
import com.tecomgroup.energetics.client.graphCoord.Services.GraphProduceService;
import com.tecomgroup.energetics.client.graphCoord.Services.PackagingService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        for (int k = 1; k <= 50; k++) {
            Params params = Params.createFromFile("src/main/resources/config");
            ArrayList<int[][]> graphList = GraphUtils.readGraphs(String.valueOf(k));
            ArrayList<int[]> sizesVertex = GraphUtils.getSizesVertex();
            ArrayList<ArrayList<Vertex>> allVertices = new ArrayList<ArrayList<Vertex>>();
            for (int i = 0; i < sizesVertex.size(); i++) {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (int j = 0; j < sizesVertex.get(i).length; j++) {
                    vertices.add(new Vertex(sizesVertex.get(i)[j]));
                }

                allVertices.add(vertices);
            }

            ArrayList<Integer> sizeDotList = GraphUtils.readDot(String.valueOf(k));
            int indent = 0;
            if (sizeDotList.size() != 0) {
                indent = 70;
            }

            ArrayList<int[][]> adjacencyMatrixList = new ArrayList<int[][]>();
            adjacencyMatrixList.addAll(graphList);
            GraphProduceService graphProduceService = new GraphProduceService(params, indent);
            List<IGraph> graphs = graphProduceService.GetCoordGraphs(adjacencyMatrixList);
            //coordList = calculateCoordService.CalculateCoord(indent);
            //calculateCoordService.GetCoordFullGraph(coordList);
            PackagingService packagingService = new PackagingService(params);
            packagingService.PackageGraphs(graphs);
            Visualizer visualizer = new Visualizer(params.width, params.height,
                    adjacencyMatrixList, coordList, calculateCoordService.fullGraphs);
            int distance = 0;
            if (sizeDotList.size() != 0) {
                distance = params.width / sizeDotList.size();
            }

            visualizer.Draw(sizeDotList, distance, allVertices, String.valueOf(k));
            System.out.println(k);
        }

        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println(timeConsumedMillis);
    }
}