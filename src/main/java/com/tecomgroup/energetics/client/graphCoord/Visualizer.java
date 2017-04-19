package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Graphs.FullGraph;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Graph;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Vertex;

import java.util.List;

public class Visualizer {
    private final int width;
    private final int height;
    private SvgWriter svgWriter;

    public Visualizer(int width, int height, SvgWriter svgWriter) {
        this.width = width;
        this.height = height;
        this.svgWriter = svgWriter;
    }

    public void WriteSvg( String filename)  {
        svgWriter.writeSvg(filename, this.width, this.height);
    }

    public void DrawFullGraph(FullGraph fullGraph){
        svgWriter.addLine(fullGraph.leftX, fullGraph.basicY, fullGraph.rightX, fullGraph.basicY);
        int lastCoordX = fullGraph.leftX;
        for (int i = 0; i < fullGraph.countVertex; ++i) {
            Vertex vertex = fullGraph.vertex.get(i);
            if (i % 2 == 0) {
                svgWriter.addLine(lastCoordX + fullGraph.indent, fullGraph.basicY, lastCoordX + fullGraph.indent,
                        (fullGraph.basicY + fullGraph.indent) - vertex.size);
                svgWriter.addCircle(lastCoordX + fullGraph.indent, fullGraph.basicY + fullGraph.indent, vertex.size);
                int y = fullGraph.basicY + fullGraph.indent;
                String[] caption = vertex.caption.split(" ");
                for (int m = 0; m < caption.length; m++) {
                    svgWriter.addText(((lastCoordX + fullGraph.indent) + 10), y, caption[m]);
                    y += 10;
                }
            } else {
                svgWriter.addLine(lastCoordX + fullGraph.indent, fullGraph.basicY, lastCoordX + fullGraph.indent,
                        (fullGraph.basicY - fullGraph.indent) + vertex.size);
                svgWriter.addCircle(lastCoordX + fullGraph.indent, fullGraph.basicY - fullGraph.indent, vertex.size);
                int y = fullGraph.basicY - fullGraph.indent;
                String[] caption = vertex.caption.split(" ");
                for (int m = 0; m < caption.length; m++) {
                    svgWriter.addText(((lastCoordX + fullGraph.indent) + 10), y, caption[m]);
                    y += 10;
                }
            }

            lastCoordX += fullGraph.indent;
        }
    }

    public void DrawGraph(Graph graph){
        for (int i = 0; i < graph.adjacencyMatrix.length; ++i)
            for (int j = i + 1; j < graph.adjacencyMatrix.length; ++j)
                if (graph.adjacencyMatrix[i][j] == 1) {
                    svgWriter.addLine(graph.getX(i), graph.getY(i),
                            graph.getX(j), graph.getY(j));
                }

        for (int i = 0; i < graph.adjacencyMatrix.length; ++i) {
            Vertex vertex = graph.vertex.get(i);
            svgWriter.addCircle(graph.getX(i), graph.getY(i), vertex.size);
            String[] caption = vertex.caption.split(" ");
            int maxLength = vertex.GetMaxLength(caption);
            int x = this.getCoordX(maxLength, graph, i);
            int y = this.getCoordY(caption.length, graph, i);
            for (int m = 0; m < caption.length; m++) {
                svgWriter.addText(x, y, caption[m]);
                y += 10;
            }
        }

    }

    public void DrawFreeDots(List<Vertex> sizeDotList){
        int position = 10;
        int distance = 0;
        if (sizeDotList.size() != 0) {
            distance = this.width / sizeDotList.size();
        }
        for (int l = 0; l < sizeDotList.size(); ++l) {
            int y = 18;
            String[] caption = sizeDotList.get(l).caption.split(" ");
            for (int m = 0; m < caption.length; m++) {
                svgWriter.addText(position + 10, y, caption[m]);
                y += 10;
            }
            Integer size = sizeDotList.get(l).size;
            svgWriter.addCircle(position, 18, size);
            position += distance;
        }

        svgWriter.addText(10, 70, sizeDotList.size() + " free vertex", 14, "font-weight=\"bold\"");

    }

    private int getCoordX(int maxLenght, Graph graph, int idxCurrentVertex) {
        int centralVertex = 0;
        int count = 0;
        for (int j = 0; j < graph.adjacencyMatrix.length; ++j)
            if (graph.adjacencyMatrix[idxCurrentVertex][j] == 1) {
                centralVertex = j;
                count++;
            }
        int x = graph.getX(idxCurrentVertex);

        if (count == 1) {
            if (x > graph.getX(centralVertex)) {
                return x + 10;
            } else {
                return x - (maxLenght * 9);
            }
        }

        boolean isLeft = true;
        boolean isRight = true;

            for (int j = 0; j < graph.adjacencyMatrix.length; j++) {
                if (x < graph.getX(j)) {
                    isLeft = false;
                }

                if (x > graph.getX(j)) {
                    isRight = false;
                }
            }

        if (isLeft) {
            return x + 10;
        }

        if (isRight) {
            return x - (maxLenght * 9);
        }

        return x - ((maxLenght * 9) / 2);
    }

    private int getCoordY(int maxLenght, Graph graph, int idxCurrentVertex) {
        int count = 0;
        int centralVertex = 0;
        for (int j = 0; j < graph.adjacencyMatrix.length; ++j)
            if (graph.adjacencyMatrix[idxCurrentVertex][j] == 1) {
                centralVertex = j;
                count++;
            }
        int y = graph.getY(idxCurrentVertex);

        if (count == 1) {
            if (y > graph.getY(centralVertex)) {
                return y + 15;
            } else {
                return y - (maxLenght * 7);
            }
        }

        boolean isTop = true;
        boolean isBottom = true;

            for (int j = 0; j < graph.adjacencyMatrix.length; j++) {

                if (y < graph.getY(j)) {
                    isTop = false;
                }

                if (y > graph.getY(j)) {
                    isBottom = false;
                }
            }

        if (isTop) {
            return y + 20;
        }

        if (isBottom) {
            return y - (maxLenght * 7);
        }

        return y + 20;
    }

}



