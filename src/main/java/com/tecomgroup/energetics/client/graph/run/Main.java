package com.tecomgroup.energetics.client.graph.run;

import com.tecomgroup.energetics.client.graph.*;
import com.tecomgroup.energetics.client.graph.Services.CalculateCoordService;
import com.tecomgroup.energetics.client.graph.Services.PackagingService;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        for (int k = 1; k <= 50; k++) {
            GraphUtils graphUtils = new GraphUtils();
            Params params = Params.createFromFile("src/main/resources/config");
            ArrayList<int[][]> graphList = graphUtils.readGraphs(String.valueOf(k));
            ArrayList<int[]> sizesVertex = graphUtils.getSizesVertex();
            ArrayList<ArrayList<Vertex>> allVertices = new ArrayList<ArrayList<Vertex>>();
            for (int i = 0; i < sizesVertex.size(); i++) {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (int j = 0; j < sizesVertex.get(i).length; j++) {
                    vertices.add(new Vertex(sizesVertex.get(i)[j]));
                }

                allVertices.add(vertices);
            }

            //int[][] mainMatr = graphUtils.createMatrix(graphList.size());
            ArrayList<Integer> sizeDotList = graphUtils.readDot(String.valueOf(k));
            int indent = 0;
            if (sizeDotList.size() != 0) {
                indent = 70;
            }
            //params.meanSpringLength = ((params.height + params.width - indent) / 2) / 2;
           // double[] coordVertexMainGraph = graphUtils.getCoord(mainMatr, params, params.width / 2,
            //        (params.height + indent) / 2, 0, 0);
            ArrayList<double[]> coordList;
            ArrayList<int[][]> adjacencyMatrixList = new ArrayList<int[][]>();
            adjacencyMatrixList.addAll(graphList);
            CalculateCoordService calculateCoordService = new CalculateCoordService(graphUtils, graphList, params);
            coordList = calculateCoordService.CalculateCoord(indent);
            calculateCoordService.GetCoordFullGraph(coordList);
            PackagingService packagingService = new PackagingService();
            Visualizer visualizer = new Visualizer(params.width, params.height, adjacencyMatrixList, coordList, calculateCoordService.fullGraphs);
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
