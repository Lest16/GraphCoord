package com.tecomgroup.energetics.client.graphCoord.Services;

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

    public ArrayList<IGraph> GetCoordGraphs(ArrayList<int[][]> adjacencyMatrixList) throws Exception {
        ArrayList<IGraph> graphList = new ArrayList<IGraph>();
        for (int i = 0; i < adjacencyMatrixList.size(); i++) {
            graphList.add(this.GetCoordGraph(adjacencyMatrixList.get(i), adjacencyMatrixList.size()));
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
        return new FullGraph(leftX, rightX, basicY, fullGraphIndent);
    }
}
