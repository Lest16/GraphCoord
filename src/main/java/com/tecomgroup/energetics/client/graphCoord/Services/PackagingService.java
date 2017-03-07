package com.tecomgroup.energetics.client.graphCoord.Services;

import com.tecomgroup.energetics.client.graphCoord.Graphs.IGraph;
import com.tecomgroup.energetics.client.graphCoord.Params;
import com.tecomgroup.energetics.client.graphCoord.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class PackagingService {

    private Params params;
    private int indent;

    public PackagingService( Params params, int indent){
        this.params = params;
        this.indent = indent;
    }

    public List<IGraph> PackageGraphs(List<IGraph> graphs) {
        ArrayList<Rectangle> delineateRectangles = new ArrayList<Rectangle>();
        for (IGraph graph: graphs) {
            delineateRectangles.add(graph.GetDelineateRectangle());
        }
        return this.Package(delineateRectangles, graphs);
    }

    private List<IGraph> Package(List<Rectangle> delineateRectangles, List<IGraph> graphs){
        //сортировка, вынести в отдельный метод
        for (int i = 0; i < delineateRectangles.size(); i++) {
            int idxMostWideRectangle = i;
            boolean IsSwap = false;
            int maxWidth = delineateRectangles.get(i).x2 - delineateRectangles.get(i).x1;
            for (int j = i + 1; j < delineateRectangles.size(); j++) {
                if ((delineateRectangles.get(j).x2 - delineateRectangles.get(j).x1) > maxWidth) {
                    maxWidth = delineateRectangles.get(j).x2 - delineateRectangles.get(j).x1;
                    idxMostWideRectangle = j;
                    IsSwap = true;
                }
            }
            if (IsSwap) {
                Rectangle tempRectangle = delineateRectangles.get(i);
                delineateRectangles.set(i, delineateRectangles.get(idxMostWideRectangle));
                delineateRectangles.set(idxMostWideRectangle, tempRectangle);
                IGraph graph = graphs.get(i);
                graphs.set(i, graphs.get(idxMostWideRectangle));
                graphs.set(idxMostWideRectangle, graph);
            }
        }


        List<IGraph> packedGraphs = new ArrayList<IGraph>();
        int lastIndentY = indent + 40;
        while (delineateRectangles.size() != 0) {
            int availableWide = params.width;
            int indentX = 0;
            boolean IsFullLine = false;
            int mostHighY = 0;
            while(!IsFullLine){
                int i = 0;
                while (i != delineateRectangles.size() ){
                    Rectangle currentRect = delineateRectangles.get(i);
                    if (availableWide > currentRect.x2 - currentRect.x1){
                        graphs.get(i).Move(indentX + 30 - currentRect.x1,
                                lastIndentY - currentRect.y1);
                        availableWide -= (currentRect.x2 - currentRect.x1) + 100;
                        if (mostHighY <= currentRect.y2 - currentRect.y1){
                            mostHighY = currentRect.y2 - currentRect.y1;
                        }
                        indentX += currentRect.x2 - currentRect.x1 + 100;
                        packedGraphs.add(graphs.get(i));
                        graphs.remove(i);
                        delineateRectangles.remove(i);
                        continue;
                    }
                    IsFullLine = true;
                    lastIndentY += mostHighY + 70;
                    break;
                }

                if (!IsFullLine){
                    IsFullLine = true;
                    lastIndentY += mostHighY + 40;
                }
            }
        }

        return packedGraphs;
    }
}
