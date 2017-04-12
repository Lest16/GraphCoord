package com.tecomgroup.energetics.client.graphCoord.Services;

import com.tecomgroup.energetics.client.graphCoord.Graphs.Edge;
import com.tecomgroup.energetics.client.graphCoord.Graphs.FullGraph;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Graph;
import com.tecomgroup.energetics.client.graphCoord.GraphUtils;
import com.tecomgroup.energetics.client.graphCoord.Graphs.IGraph;
import com.tecomgroup.energetics.client.graphCoord.Params;

import java.util.ArrayList;

public class GraphProduceService {
    private final Params params;
    private final int indent;

    public GraphProduceService(Params params, int indent){

        this.params = params;
        this.indent = indent;
    }

    public IGraph GetCoordGraph(int[][] adjacencyMatrix, int countGraphs) throws Exception {
        params.meanSpringLength = ((params.height + params.width - indent)) /
                (adjacencyMatrix.length * countGraphs);
        double[] coordsDouble = GraphUtils.getCoord(adjacencyMatrix, params, params.width / 2,
                (params.height + indent) / 2, 0, 0);
        int[] coordsInt = this.CastArrayToInt(coordsDouble);
        if (GraphUtils.IsFullGraph(adjacencyMatrix)) {
            return CreateFullGraph(coordsInt, countGraphs, adjacencyMatrix);
        } else {
            return new Graph(adjacencyMatrix, coordsInt);
        }

    }

    public IGraph GetCoordGraph(ArrayList<Edge> edges, int countGraphs) throws Exception {
        ArrayList<Integer> distinctVertex = GraphUtils.GetDistinctVertex(edges);
        int[][] adjacencyMatrix = GraphUtils.createAdjacencyMatrix(edges, distinctVertex);
        params.meanSpringLength = ((params.height + params.width - indent)) /
                (adjacencyMatrix.length * countGraphs);
        double[] coordsDouble = GraphUtils.getCoord(adjacencyMatrix, params, params.width / 2,
                (params.height + indent) / 2, 0, 0);
        int[] coordsInt = this.CastArrayToInt(coordsDouble);
        if (GraphUtils.IsFullGraph(adjacencyMatrix)) {
            return CreateFullGraph(coordsInt, countGraphs, adjacencyMatrix, distinctVertex);
        } else {
            return new Graph(adjacencyMatrix, coordsInt, distinctVertex);
        }

    }

    public ArrayList<IGraph> GetCoordGraphsByMatrix(ArrayList<int[][]> adjacencyMatrixList) throws Exception {
        ArrayList<IGraph> graphList = new ArrayList<IGraph>();
        for (int i = 0; i < adjacencyMatrixList.size(); i++) {
            graphList.add(this.GetCoordGraph(adjacencyMatrixList.get(i), adjacencyMatrixList.size()));
        }
        return graphList;
    }

    public ArrayList<IGraph> GetCoordGraphs(ArrayList<ArrayList<Edge>> edgesList) throws Exception {
        ArrayList<IGraph> graphList = new ArrayList<IGraph>();
        for (int i = 0; i < edgesList.size(); i++) {
            graphList.add(this.GetCoordGraph(edgesList.get(i), edgesList.size()));
        }
        return graphList;
    }

    private int[] CastArrayToInt(double[] array) {
        int[] castedArray = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            castedArray[i] = (int)array[i];
        }
        return castedArray;
    }

    private FullGraph CreateFullGraph(int[] coordsInt, int countGraphs, int[][] adjacencyMatrix){
        int fullGraphIndent = this.params.width / (coordsInt.length * countGraphs) + coordsInt.length;
        int leftX = coordsInt[0] - fullGraphIndent * adjacencyMatrix.length / 2;
        int rightX = leftX + (fullGraphIndent * adjacencyMatrix.length);
        int basicY = coordsInt[1];
        return new FullGraph(leftX, rightX, basicY, fullGraphIndent, adjacencyMatrix.length - 1);
    }

    private FullGraph CreateFullGraph(int[] coordsInt, int countGraphs, int[][] adjacencyMatrix, ArrayList<Integer> distinctVertex){
        int fullGraphIndent = this.params.width / (coordsInt.length * countGraphs) + coordsInt.length;
        int leftX = coordsInt[0] - fullGraphIndent * adjacencyMatrix.length / 2;
        int rightX = leftX + (fullGraphIndent * adjacencyMatrix.length);
        int basicY = coordsInt[1];
        return new FullGraph(leftX, rightX, basicY, fullGraphIndent, adjacencyMatrix.length - 1, distinctVertex);
    }
}
