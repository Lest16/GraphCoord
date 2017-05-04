package com.tecomgroup.energetics.client.graphCoord.Services;

import com.tecomgroup.energetics.client.graphCoord.Graphs.*;
import com.tecomgroup.energetics.client.graphCoord.GraphUtils;
import com.tecomgroup.energetics.client.graphCoord.Params;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GraphProduceService {
    private final Params params;
    private final int indent;

    public GraphProduceService(Params params, int indent){

        this.params = params;
        this.indent = indent;
    }

    public IGraph GetCoordGraph(ArrayList<Edge> edges, int countGraphs, ArrayList<Vertex> allVertex) throws Exception {
        final ArrayList<Integer> distinctVertex = GraphUtils.GetDistinctVertex(edges);
        ArrayList<Vertex> vertices = new ArrayList<>(allVertex.stream().filter((x) -> distinctVertex.contains(x.id)).
                collect(Collectors.toList()));
        int[][] adjacencyMatrix = GraphUtils.createAdjacencyMatrix(edges, distinctVertex);
        params.meanSpringLength = ((params.height + params.width - indent)) /
                (adjacencyMatrix.length * countGraphs);
        double[] coordsDouble = GraphUtils.getCoord(adjacencyMatrix, params, params.width / 2,
                (params.height + indent) / 2, 0, 0);
        int[] coordsInt = this.CastArrayToInt(coordsDouble);
        if (GraphUtils.IsFullGraph(adjacencyMatrix)) {
            return CreateFullGraph(coordsInt, countGraphs, adjacencyMatrix, vertices);
        } else {
            return new Graph(adjacencyMatrix, coordsInt, vertices);
        }

    }

    public ArrayList<IGraph> GetCoordGraphs(ArrayList<ArrayList<Edge>> edgeList, ArrayList<Vertex> allVertex) throws Exception {
        ArrayList<IGraph> graphList = new ArrayList<>();
        for (int i = 0; i < edgeList.size(); i++) {
            graphList.add(this.GetCoordGraph(edgeList.get(i), edgeList.size(), allVertex));
        }
        return graphList;
    }

    public ArrayList<ArrayList<Edge>> DevideEdges(ArrayList<Edge> allEdges){
        ArrayList<ArrayList<Edge>> devidedAllEdges = new ArrayList<>();
        while (allEdges.size() != 0){
            int i = 0;
            boolean IsFirst = true;
            ArrayList<Edge> edgeList = new ArrayList<>();
            while (i != allEdges.size()){

                if (IsFirst){
                    edgeList.add(allEdges.get(i));
                    allEdges.remove(i);
                    IsFirst = false;
                    continue;
                }

                ArrayList<Integer> verticesEdgeList = new ArrayList<>();
                for (Edge edge : edgeList) {
                    verticesEdgeList.add(edge.firstVertex);
                    verticesEdgeList.add(edge.secondVertex);
                }

                if (verticesEdgeList.contains(allEdges.get(i).firstVertex) ||
                        verticesEdgeList.contains(allEdges.get(i).secondVertex)){
                    edgeList.add(allEdges.get(i));
                    allEdges.remove(i);
                    continue;
                }

                i++;
            }

            devidedAllEdges.add(edgeList);
        }

        return devidedAllEdges;
    }

    private int[] CastArrayToInt(double[] array) {
        int[] castedArray = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            castedArray[i] = (int)array[i];
        }
        return castedArray;
    }

    private FullGraph CreateFullGraph(int[] coordsInt, int countGraphs, int[][] adjacencyMatrix, ArrayList<Vertex> distinctVertex){
        int fullGraphIndent = this.params.width / (coordsInt.length * countGraphs) + coordsInt.length;
        int leftX = coordsInt[0] - fullGraphIndent * adjacencyMatrix.length / 2;
        int rightX = leftX + (fullGraphIndent * (adjacencyMatrix.length + 1));
        int basicY = coordsInt[1];
        return new FullGraph(leftX, rightX, basicY, fullGraphIndent, adjacencyMatrix.length, distinctVertex);
    }
}
