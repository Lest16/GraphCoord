package com.tecomgroup.energetics.client.graphCoord.run;

import com.tecomgroup.energetics.client.graphCoord.*;
import com.tecomgroup.energetics.client.graphCoord.Graphs.IGraph;
import com.tecomgroup.energetics.client.graphCoord.Services.GraphProduceService;
import com.tecomgroup.energetics.client.graphCoord.Services.PackagingService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        DbReader dbReader = new DbReader();
        GraphUtils.createMatrix(dbReader.GetGraphs());
        Params params = Params.createFromFile("src/main/resources/config");
        //ArrayList<int[][]> graphList = GraphUtils.readGraphs(String.valueOf(k));
        ArrayList<int[][]> graphList = new ArrayList<int[][]>();
        //ArrayList<int[]> sizesVertex = GraphUtils.getSizesVertex();
        ArrayList<int[]> sizesVertex = new ArrayList<int[]>();
        ArrayList<ArrayList<Vertex>> allVertices = new ArrayList<ArrayList<Vertex>>();
        for (int i = 0; i < sizesVertex.size(); i++) {
            ArrayList<Vertex> vertices = new ArrayList<Vertex>();
            for (int j = 0; j < sizesVertex.get(i).length; j++) {
                vertices.add(new Vertex(sizesVertex.get(i)[j]));
            }

            allVertices.add(vertices);
        }

        //ArrayList<Integer> sizeDotList = GraphUtils.readDot(String.valueOf(k));
        ArrayList<Integer> sizeDotList = new ArrayList<Integer>();
        int indent = 0;
        if (sizeDotList.size() != 0) {
            indent = 70;
        }

        ArrayList<int[][]> adjacencyMatrixList = new ArrayList<int[][]>();
        adjacencyMatrixList.addAll(graphList);
        GraphProduceService graphProduceService = new GraphProduceService(params, indent);
        List<IGraph> graphs = graphProduceService.GetCoordGraphs(adjacencyMatrixList);
        PackagingService packagingService = new PackagingService(params, indent);
        List<IGraph> packedGraphs = packagingService.PackageGraphs(graphs);
        SvgWriter svgWriter = new SvgWriter();
        Visualizer visualizer = new Visualizer(params.width, params.height, svgWriter);
        for (IGraph graph: packedGraphs) {
            graph.Visualize(visualizer);
        }
        visualizer.DrawFreeDots(sizeDotList);
        //visualizer.WriteSvg(String.valueOf(k));
        //System.out.println(String.valueOf(k));


        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println(timeConsumedMillis);
    }
}
