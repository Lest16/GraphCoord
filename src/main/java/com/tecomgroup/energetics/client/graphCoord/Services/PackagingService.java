package com.tecomgroup.energetics.client.graphCoord.Services;

import com.tecomgroup.energetics.client.graphCoord.Graphs.IGraph;
import com.tecomgroup.energetics.client.graphCoord.Params;
import com.tecomgroup.energetics.client.graphCoord.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class PackagingService {

    private Params params;

    public PackagingService( Params params){
        this.params = params;
    }

    public void PackageGraphs(List<IGraph> graphs) {
        ArrayList<Rectangle> delineateRectangles = new ArrayList<Rectangle>();
        for (IGraph graph: graphs) {
            delineateRectangles.add(graph.GetDelineateRectangle());
        }
        this.Package(delineateRectangles, graphs);
    }

    private void Package(List<Rectangle> delineateRectangles, List<IGraph> graphs){

    }

}
