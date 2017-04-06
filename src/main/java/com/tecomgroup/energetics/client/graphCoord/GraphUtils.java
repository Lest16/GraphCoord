package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Graphs.Edge;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphUtils {
    public static ArrayList<int[][]> readGraphs(String filename) throws Exception {
        int[][] matrix;
        ArrayList<int[][]> graphList = new ArrayList<int[][]>();
        BufferedReader fin = new BufferedReader(
                new InputStreamReader(new FileInputStream("src/main/resources/data/Graphs/" + filename)));
        List<String> fileContent = new ArrayList<String>();
        String str;
        while((str = fin.readLine()) != null) {
            fileContent.add(str);
        }
        fin.close();
        int k = 0;
        while ((k - 1) != fileContent.size()) {
            String[] splitLineInit = fileContent.get(k).split(" ");
            matrix = new int[splitLineInit.length][splitLineInit.length];
            for (int i = k; i < splitLineInit.length + k; i++) {
                String line = fileContent.get(i);
                String[] splitLine = line.split(" ");
                for (int j = 0; j < splitLineInit.length; j++) {
                    matrix[i - k][j] = Integer.parseInt(splitLine[j]);
                }
            }
            graphList.add(matrix);
            k += splitLineInit.length + 1;
        }
        return graphList;
    }

    public static ArrayList<Integer> readDot(String filename) throws Exception {
        BufferedReader fin = new BufferedReader(
                new InputStreamReader(new FileInputStream("src/main/resources/data/Dot/" + filename)));
        List<String> fileContent = new ArrayList<String>();
        String str;
        while((str = fin.readLine()) != null) {
            fileContent.add(str);
        }

        fin.close();
        if (fileContent.size() == 0) {
            return new ArrayList<Integer>();
        }
        String[] splitSizeDot = fileContent.get(0).split(" ");
        ArrayList<Integer> sizeDotList = new ArrayList<Integer>();
        for (String size: splitSizeDot) {
            sizeDotList.add(Integer.parseInt(size));
        }

        return sizeDotList;
    }

    public static boolean IsFullGraph(int[][] graph) throws Exception {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if ((i != j) && (graph[i][j] != 1)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static ArrayList<Integer> GetDistinctVertex(ArrayList<Edge> edges)
    {
        ArrayList<Integer> distinctVertex = new ArrayList<Integer>();
        for (Edge edge: edges) {
            if (!distinctVertex.contains(edge.firstVertex)) {
                distinctVertex.add(edge.firstVertex);
            }

            if (!distinctVertex.contains(edge.secondVertex)) {
                distinctVertex.add(edge.secondVertex);
            }
        }
        return distinctVertex;
    }

    public static int[][] createAdjacencyMatrix(ArrayList<Edge> edges, ArrayList<Integer> distinctVertex) {
        int[][] resultMatrix = new int[distinctVertex.size()][distinctVertex.size()];
        for (int i = 0; i < distinctVertex.size(); i++) {
            for (int j = 0; j < distinctVertex.size(); j++) {
                resultMatrix[i][j] = 0;
            }
        }
        for (int i = 0; i < edges.size(); i++){
            resultMatrix[distinctVertex.indexOf(edges.get(i).firstVertex)][distinctVertex.indexOf(edges.get(i).secondVertex)] = 1;
            resultMatrix[distinctVertex.indexOf(edges.get(i).secondVertex)][distinctVertex.indexOf(edges.get(i).firstVertex)] = 1;
        }

        return resultMatrix;
    }

    public static ArrayList<int []> getSizesVertex() throws Exception {
        BufferedReader fin = new BufferedReader( new InputStreamReader(new FileInputStream("src/main/resources/sizesVertex")));
        List<String> fileContent = new ArrayList<String>() ;
        String str;
        while((str = fin.readLine() ) != null) {
            fileContent.add(str);
        }

        fin.close();
        ArrayList<int []> listSizes = new ArrayList<int[]>();
        for (int i = 0; i < fileContent.size(); i++) {
            String[] splitSizeVertex = fileContent.get(i).split(" ");
            int [] sizesVertex = new int[splitSizeVertex.length];
            for(int j = 0; j < splitSizeVertex.length; j++) {
                sizesVertex[j] = Integer.parseInt(splitSizeVertex[j]);
            }

            listSizes.add(sizesVertex);
        }

        return listSizes;
    }

    public static double[] getCoord(int[][] matr, Params params, double centX, double centY, int deviationX, int deviationY) {
        Models models = new Models(matr, params);
        PhysSys physSys = new PhysSys(models);
        ArrayList<Double> energy = new ArrayList<Double>();
        for (int i = 0; i < 256; i++) {
            physSys.step();
            energy.add(models.SpringChargeEnergy(physSys.r));
        }
        return physSys.centralize(centX, centY, deviationX, deviationY);
    }
}
