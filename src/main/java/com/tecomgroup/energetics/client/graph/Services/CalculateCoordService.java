package com.tecomgroup.energetics.client.graph.Services;

import com.tecomgroup.energetics.client.graph.FullGraph;
import com.tecomgroup.energetics.client.graph.GraphUtils;
import com.tecomgroup.energetics.client.graph.Params;

import java.util.ArrayList;

public class CalculateCoordService {
    private final GraphUtils graphUtils;
    private ArrayList<int[][]> graphList;
    private final Params params;
    public final ArrayList<FullGraph> fullGraphs = new ArrayList<FullGraph>();

    public CalculateCoordService(GraphUtils graphUtils, ArrayList<int[][]> graphList, Params params) {
        this.graphUtils = graphUtils;
        this.graphList = graphList;
        this.params = params;
    }

    public ArrayList<double[]> CalculateCoord(int indent) throws Exception {
        ArrayList<double[]> coordList = new ArrayList<double[]>();
        for (int i = 0; i < graphList.size(); i++) {
            params.meanSpringLength = ((params.height + params.width - indent)) /
                    (graphList.get(i).length * graphList.size());
            coordList.add(graphUtils.getCoord(graphList.get(i), params, params.width / 2,
                    (params.height + indent) / 2, 0, 0));
        }
        return coordList;
    }

    public void GetCoordFullGraph(ArrayList<double[]> coordList) throws Exception {
        for (int i = 0; i < coordList.size(); i++) {
            double[] coordGraph = coordList.get(i);
            if (graphUtils.IsFullGraph(graphList.get(i))) {
                int indent = this.params.width / (coordGraph.length * coordList.size()) + coordGraph.length;
                int leftX = (int) coordGraph[0] - indent * graphList.get(i).length / 2;
                int rightX = leftX + (indent * graphList.get(i).length);
                int basicY = (int) coordGraph[1];
                this.fullGraphs.add(new FullGraph(leftX, rightX, basicY, indent));
            }
        }
    }

}
