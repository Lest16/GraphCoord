package com.tecomgroup.energetics.client.graph;

import java.util.ArrayList;

public class Visualizer {
    private final int width;
    private final int height;
    private ArrayList<int[][]> adjacencyMatrixList;
    private ArrayList<double[]> coordList;
    public int radius;
    public int maxLenght;

    public Visualizer(int width, int height, ArrayList<int[][]> adjacencyMatrixList, ArrayList<double[]> coordList) {
        this.width = width;
        this.height = height;
        this.adjacencyMatrixList = adjacencyMatrixList;
        this.coordList = coordList;
    }

    public void Draw(final ArrayList<Integer> sizeDotList, final int distance,
                     final ArrayList<ArrayList<Vertex>> allVertices, String filename) throws Exception {
        GraphUtils graphUtils = new GraphUtils();
        SvgWriter svgWriter = new SvgWriter();
        for (int k = 0; k < coordList.size(); k++) {
            double[] coordGraph = coordList.get(k);
            int[][] adjacencyMatrix = adjacencyMatrixList.get(k);
            if (graphUtils.IsFullGraph(adjacencyMatrix)) {
                int indent = this.width / (coordGraph.length * coordList.size()) + coordGraph.length;
                int lastCoordX = (int) coordGraph[0] - indent * adjacencyMatrix.length / 2;
                int rightX = lastCoordX + (indent * adjacencyMatrix.length);
                svgWriter.addLine(lastCoordX, (int)coordGraph[1], rightX, (int)coordGraph[1]);
                for (int i = 0; i < adjacencyMatrix.length - 1; ++i) {
                    Vertex vertex = new Vertex(9);
                    if (i % 2 == 0) {
                        svgWriter.addLine(lastCoordX + indent, (int) coordGraph[1], lastCoordX + indent,
                                (int) ((coordGraph[1] + indent) - vertex.size));
                        svgWriter.addCircle(lastCoordX + indent, (int)coordGraph[1] + indent, vertex.size);
                        int y = (int) (coordGraph[1] + indent);
                        String[] caption = vertex.caption.split(" ");
                        for (int m = 0; m < caption.length; m++) {
                            svgWriter.addText(((lastCoordX + indent) + 10), y, caption[m], " font-size=\"13px\"");
                            y += 10;
                        }
                    } else {
                        svgWriter.addLine(lastCoordX + indent, (int) coordGraph[1], lastCoordX + indent,
                                (int) ((coordGraph[1] - indent) + vertex.size));
                        svgWriter.addCircle(lastCoordX + indent, (int)coordGraph[1] - indent, vertex.size);
                        int y = (int) (coordGraph[1] - indent);
                        String[] caption = vertex.caption.split(" ");
                        for (int m = 0; m < caption.length; m++) {
                            svgWriter.addText(((lastCoordX + indent) + 10), y, caption[m], " font-size=\"13px\"");
                            y += 10;
                        }
                    }
                    lastCoordX += indent;
                }
            } else {
                for (int i = 0; i < adjacencyMatrix.length; ++i)
                    for (int j = i + 1; j < adjacencyMatrix.length; ++j)
                        if (adjacencyMatrix[i][j] == 1) {
                            svgWriter.addLine((int) coordGraph[2 * i], (int) coordGraph[2 * i + 1],
                                    (int) coordGraph[2 * j], (int) coordGraph[2 * j + 1]);
                        }

                for (int l = 0; l < adjacencyMatrix.length; ++l) {
                    Vertex vertex = new Vertex(9);
                    svgWriter.addCircle((int) coordGraph[2 * l], (int) coordGraph[2 * l + 1], vertex.size);
                    String[] caption = vertex.caption.split(" ");
                    this.maxLenght = GetMaxLength(caption);
                    this.radius = GetRadius(caption, maxLenght);
                    int x = this.getCoordX(maxLenght, radius, (int) coordGraph[2 * l], (int) coordGraph[2 * l + 1], l, k);
                    int y = this.getCoordY(caption.length, radius, (int) coordGraph[2 * l], (int) coordGraph[2 * l + 1], l, k);
                    for (int m = 0; m < caption.length; m++) {
                        svgWriter.addText(x, y, caption[m], " font-size=\"13px\"");
                        y += 10;
                    }
                }
            }
        }

        int position = 10;
        for (int l = 0; l < sizeDotList.size(); ++l) {
            Vertex vertex = new Vertex(9);
            int y = 18;
            String[] caption = vertex.caption.split(" ");
            for (int m = 0; m < caption.length; m++) {
                svgWriter.addText(position + 10, y, caption[m], " font-size=\"13px\"");
                y += 10;
            }
            Integer size = sizeDotList.get(l);
            svgWriter.addCircle(position, 18, size);
            position += distance;
        }

        svgWriter.addText(10, 70, sizeDotList.size() + " free vertex",
                " font-size=\"14px\" font-weight=\"bold\"");
        svgWriter.writeSvg(filename, this.width, this.height);
    }

    private int getCoordX(int maxLenght, int radius, int x, int y, int idxCurrentVertex, int idxCurrentMatrix) {
        int centralVertex = 0;
        int count = 0;
        for (int j = 0; j < adjacencyMatrixList.get(idxCurrentMatrix).length; ++j)
            if (adjacencyMatrixList.get(idxCurrentMatrix)[idxCurrentVertex][j] == 1) {
                centralVertex = j;
                count++;
            }

        if (count == 1) {
            if (x > coordList.get(idxCurrentMatrix)[2 * centralVertex]) {
                return x + 10;
            } else {
                return x - (maxLenght * 9);
            }
        }


        ArrayList<Integer> listCoordX = new ArrayList<Integer>();
        ArrayList<Integer> listCoordY = new ArrayList<Integer>();
        boolean isLeft = true;
        boolean isRight = true;
        for (int i = 0; i < coordList.size(); i++) {
            for (int j = 0; j < adjacencyMatrixList.get(i).length; j++) {
                double a = Math.abs(coordList.get(i)[2 * j] - x);
                double b = Math.abs(coordList.get(i)[2 * j + 1] - y);
                int c = (int) coordList.get(i)[2 * j];
                int d = (int) coordList.get(i)[2 * j + 1];
                if ((a <= radius) && (b <= radius) && (c != x) && (d != y)) {
                    listCoordX.add((int) coordList.get(i)[2 * j]);
                    listCoordY.add((int) coordList.get(i)[2 * j + 1]);
                }

                if (x < (int) coordList.get(i)[2 * j]) {
                    isLeft = false;
                }

                if (x > (int) coordList.get(i)[2 * j]) {
                    isRight = false;
                }
            }
        }

        if (isLeft) {
            return x + 10;
        }

        if (isRight) {
            return x - (maxLenght * 9);
        }

        for (int i = 0; i < listCoordX.size(); i++) {
            for (int j = 0; j < listCoordX.size(); j++) {
                if (((listCoordX.get(i) < x) && (listCoordX.get(j) > x)) || ((listCoordX.get(i) > x) && (listCoordX.get(j) < x))) {
                    int distance = Math.abs(listCoordX.get(i) - listCoordX.get(j));
                    if (distance > (maxLenght * 9)) {
                        int rangeX = (distance - (maxLenght * 9)) / 2;
                        int leftCoord = 0;
                        if (listCoordX.get(i) < listCoordX.get(j)) {
                            leftCoord = listCoordX.get(i);
                        } else {
                            leftCoord = listCoordX.get(j);
                        }

                        return leftCoord + rangeX;

                    }
                }
            }
        }

        return x - ((maxLenght * 9) / 2);
    }

    private int getCoordY(int maxLenght, int radius, int x, int y, int idxCurrentVertex, int idxCurrentMatrix) {
        int count = 0;
        int centralVertex = 0;
        for (int j = 0; j < adjacencyMatrixList.get(idxCurrentMatrix).length; ++j)
            if (adjacencyMatrixList.get(idxCurrentMatrix)[idxCurrentVertex][j] == 1) {
                centralVertex = j;
                count++;
            }

        if (count == 1) {
            if (y > coordList.get(idxCurrentMatrix)[2 * centralVertex + 1]) {
                return y + 15;
            } else {
                return y - (maxLenght * 7);
            }
        }

        ArrayList<Integer> listCoordX = new ArrayList<Integer>();
        ArrayList<Integer> listCoordY = new ArrayList<Integer>();
        boolean isTop = true;
        boolean isBottom = true;
        for (int i = 0; i < coordList.size(); i++) {
            for (int j = 0; j < adjacencyMatrixList.get(i).length; j++) {
                double a = Math.abs(coordList.get(i)[2 * j] - x);
                double b = Math.abs(coordList.get(i)[2 * j + 1] - y);
                int c = (int) coordList.get(i)[2 * j];
                int d = (int) coordList.get(i)[2 * j + 1];
                if ((a <= radius) && (b <= radius) && (c != x) && (d != y)) {
                    listCoordX.add((int) coordList.get(i)[2 * j]);
                    listCoordY.add((int) coordList.get(i)[2 * j + 1]);
                }

                if (y < (int) coordList.get(i)[2 * j + 1]) {
                    isTop = false;
                }

                if (y > (int) coordList.get(i)[2 * j + 1]) {
                    isBottom = false;
                }
            }
        }

        if (isTop) {
            return y + 20;
        }

        if (isBottom) {
            return y - (maxLenght * 7);
        }

        for (int i = 0; i < listCoordY.size(); i++) {
            if ((listCoordX.get(i) > x) && (listCoordX.get(i) < (x + (maxLenght * 7)))) {
                if (listCoordY.get(i) > (y + (maxLenght * 7))) {
                    return y + 20;
                } else if ((listCoordY.get(i) < (y - (maxLenght * 7)))) {
                    return y - (maxLenght * 7);
                }
            } else {
                return y + 20;
            }
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



