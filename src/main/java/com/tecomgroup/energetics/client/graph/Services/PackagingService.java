package com.tecomgroup.energetics.client.graph.Services;

import com.tecomgroup.energetics.client.graph.DelineateRectangle;
import com.tecomgroup.energetics.client.graph.FullGraph;
import com.tecomgroup.energetics.client.graph.Params;

import java.util.ArrayList;

public class PackagingService {
    private ArrayList<double[]> coordList;
    private ArrayList<FullGraph> fullGraphs;
    private Params params;
    private final ArrayList<DelineateRectangle> delineateRectangles = new ArrayList<DelineateRectangle>();


    public PackagingService(ArrayList<double[]> coordList, ArrayList<FullGraph> fullGraphs, Params params){

        this.coordList = coordList;
        this.fullGraphs = fullGraphs;
        this.params = params;
    }

    public void PackagingGraphs() {
        this.GetDelineateRectangles();
        int widthGraph = delineateRectangles.get(0).x2 - delineateRectangles.get(0).x1;
        for (DelineateRectangle delineateRectangle: this.delineateRectangles) {

        }
    }

    private void GetDelineateRectangles() {
        for (int i = 0; i < coordList.size(); i++){
            double[] coordGraph = coordList.get(i);
            int minX = (int)coordGraph[0];
            int minY = (int)coordGraph[1];
            int maxX = (int)coordGraph[0];
            int maxY = (int)coordGraph[1];
            for (int j = 1; j < coordGraph.length / 2; j++){
                if(minX > coordGraph[2 * j]){
                    minX = (int)coordGraph[2 * j];
                }
                if(minY > coordGraph[2 * j + 1]){
                    minY = (int)coordGraph[2 * j + 1];
                }
                if(maxX < coordGraph[2 * j]){
                    maxX = (int)coordGraph[2 * j];
                }
                if(maxY < coordGraph[2 * j + 1]){
                    maxY = (int)coordGraph[2 * j + 1];
                }
            }
            delineateRectangles.add(new DelineateRectangle(minX, minY, maxX, maxY));
        }

        this.GetDelineateRectanglesForFullGraph();
    }

    private void GetDelineateRectanglesForFullGraph() {
        for (FullGraph fullGraph: this.fullGraphs) {
            this.delineateRectangles.add(new DelineateRectangle(fullGraph.leftX, fullGraph.basicY - fullGraph.indent,
                    fullGraph.rightX, fullGraph.basicY + fullGraph.indent));
        }
    }

}
