package com.tecomgroup.energetics.client.graphCoord.run;

import com.tecomgroup.energetics.client.graphCoord.*;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Edge;
import com.tecomgroup.energetics.client.graphCoord.Graphs.IGraph;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Vertex;
import com.tecomgroup.energetics.client.graphCoord.Services.GraphProduceService;
import com.tecomgroup.energetics.client.graphCoord.Services.PackagingService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        DbReader dbReader = new DbReader();
        Params params = Params.createFromFile("src/main/resources/config");
        ArrayList<Edge> edges = dbReader.ReadEdges();
        ArrayList<Vertex> vertices = dbReader.ReadVertex();
        ArrayList<Vertex> DotList = new ArrayList<>();
        ArrayList<Integer> allVertices = new ArrayList<>();
        for (Edge edge: edges) {
            allVertices.add(edge.firstVertex);
            allVertices.add(edge.secondVertex);
        }

        for (Vertex vertex: vertices) {
            if (!allVertices.contains(vertex.id)){
                DotList.add(vertex);
            }
        }

        for (Vertex vertex: DotList) {
            vertices.remove(vertex);
        }
        int indent = 0;
        if (DotList.size() != 0) {
            indent = 70;
        }
        GraphProduceService graphProduceService = new GraphProduceService(params, indent);
        List<IGraph> graphs = graphProduceService.GetCoordGraphs(graphProduceService.DevideEdges(edges), vertices);
        PackagingService packagingService = new PackagingService(params, indent);
        List<IGraph> packedGraphs = packagingService.PackageGraphs(graphs);
        SvgWriter svgWriter = new SvgWriter();
        Visualizer visualizer = new Visualizer(params.width, params.height, svgWriter);
        for (IGraph graph: packedGraphs) {
            graph.Visualize(visualizer);
        }

        visualizer.DrawFreeDots(DotList);
        visualizer.WriteSvg("result");


        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println(timeConsumedMillis);
    }
}
