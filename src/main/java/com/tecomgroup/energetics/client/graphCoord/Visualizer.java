package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Graphs.FullGraph;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Graph;

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
            Vertex vertex = new Vertex(9);
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
            Vertex vertex = new Vertex(9);
            svgWriter.addCircle(graph.getX(i), graph.getY(i), vertex.size);
            String[] caption = vertex.caption.split(" ");
            int maxLength = GetMaxLength(caption);
            int radius = GetRadius(caption, maxLength);
            int x = this.getCoordX(maxLength, radius, graph.getX(i), graph.getY(i), i, graph.adjacencyMatrix, graph.coords);
            int y = this.getCoordY(caption.length, radius, graph.getX(i), graph.getY(i), i, graph.adjacencyMatrix, graph.coords);
            for (int m = 0; m < caption.length; m++) {
                svgWriter.addText(x, y, caption[m]);
                y += 10;
            }
        }

    }

    public void DrawFreeDots(List<Integer> sizeDotList){
        int position = 10;
        int distance = 0;
        if (sizeDotList.size() != 0) {
            distance = this.width / sizeDotList.size();
        }
        for (int l = 0; l < sizeDotList.size(); ++l) {
            Vertex vertex = new Vertex(9);
            int y = 18;
            String[] caption = vertex.caption.split(" ");
            for (int m = 0; m < caption.length; m++) {
                svgWriter.addText(position + 10, y, caption[m]);
                y += 10;
            }
            Integer size = sizeDotList.get(l);
            svgWriter.addCircle(position, 18, size);
            position += distance;
        }

        svgWriter.addText(10, 70, sizeDotList.size() + " free vertex", 14, "font-weight=\"bold\"");

    }

    private int getCoordX(int maxLenght, int radius, int x, int y, int idxCurrentVertex, int[][] adjacencyMatrix, int[] coords) {
        int centralVertex = 0;
        int count = 0;
        for (int j = 0; j < adjacencyMatrix.length; ++j)
            if (adjacencyMatrix[idxCurrentVertex][j] == 1) {
                centralVertex = j;
                count++;
            }

        if (count == 1) {
            if (x > coords[2 * centralVertex]) {
                return x + 10;
            } else {
                return x - (maxLenght * 9);
            }
        }

        boolean isLeft = true;
        boolean isRight = true;

            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (x < coords[2 * j]) {
                    isLeft = false;
                }

                if (x > coords[2 * j]) {
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

    private int getCoordY(int maxLenght, int radius, int x, int y, int idxCurrentVertex, int[][] adjacencyMatrix, int[] coords) {
        int count = 0;
        int centralVertex = 0;
        for (int j = 0; j < adjacencyMatrix.length; ++j)
            if (adjacencyMatrix[idxCurrentVertex][j] == 1) {
                centralVertex = j;
                count++;
            }

        if (count == 1) {
            if (y > coords[2 * centralVertex + 1]) {
                return y + 15;
            } else {
                return y - (maxLenght * 7);
            }
        }

        boolean isTop = true;
        boolean isBottom = true;

            for (int j = 0; j < adjacencyMatrix.length; j++) {

                if (y < coords[2 * j + 1]) {
                    isTop = false;
                }

                if (y > coords[2 * j + 1]) {
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

    private int GetMaxLength(String[] caption) {
        int maxLenght = caption[0].length();
        for (int i = 1; i < caption.length; i++) {
            if (caption[i].length() > maxLenght) {
                maxLenght = caption[i].length();
            }
        }

        return maxLenght;
    }

    private int GetRadius(String[] caption, int maxLenght) {
        int radius = 0;
        if (maxLenght > caption.length) {
            radius = maxLenght * 8;
        } else {
            radius = caption.length * 8;
        }

        return radius;
    }
}



