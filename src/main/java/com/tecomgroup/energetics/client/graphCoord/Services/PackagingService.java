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
        this.SortGraph(graphs);
        List<IGraph> packedGraphs = new ArrayList<IGraph>();
        int lastIndentY = indent + 40;
        while (graphs.size() != 0) {
            int availableWidth = params.width;
            int indentX = 0;
            boolean IsFullLine = false;
            int lineHeight = 0;
            while(!IsFullLine){
                int i = 0;
                while (i != graphs.size() ){
                    Rectangle currentRect = graphs.get(i).GetDelineateRectangle();
                    if (availableWidth > currentRect.x2 - currentRect.x1){
                        graphs.get(i).Move(indentX + 30 - currentRect.x1,
                                lastIndentY - currentRect.y1);
                        availableWidth -= (currentRect.x2 - currentRect.x1) + 100;
                        if (lineHeight <= currentRect.y2 - currentRect.y1){
                            lineHeight = currentRect.y2 - currentRect.y1;
                        }
                        indentX += currentRect.x2 - currentRect.x1 + 100;
                        packedGraphs.add(graphs.get(i));
                        graphs.remove(i);
                        continue;
                    }
                    IsFullLine = true;
                    lastIndentY += lineHeight + 70;
                    break;
                }

                if (!IsFullLine){
                    IsFullLine = true;
                    lastIndentY += lineHeight + 40;
                }
            }
        }

        return packedGraphs;
    }

    private void SortGraph(List<IGraph> graphs){
        for (int i = 0; i < graphs.size(); i++) {
            int idxMostWideRectangle = i;
            boolean IsSwap = false;
            int maxWidth = graphs.get(i).GetDelineateRectangle().x2 - graphs.get(i).GetDelineateRectangle().x1;
            for (int j = i + 1; j < graphs.size(); j++) {
                if ((graphs.get(j).GetDelineateRectangle().x2 - graphs.get(j).GetDelineateRectangle().x1) > maxWidth) {
                    maxWidth = graphs.get(j).GetDelineateRectangle().x2 - graphs.get(j).GetDelineateRectangle().x1;
                    idxMostWideRectangle = j;
                    IsSwap = true;
                }
            }
            if (IsSwap) {
                IGraph graph = graphs.get(i);
                graphs.set(i, graphs.get(idxMostWideRectangle));
                graphs.set(idxMostWideRectangle, graph);
            }
        }
    }
}
