import java.util.ArrayList;


public class Main
{
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        for (int k = 1; k <= 50; k++) {
            Graph graph = new Graph();
            Params params = new Params();
            ArrayList<int[][]> graphList = graph.readGraphs(String.valueOf(k));
            ArrayList<int[]> sizesVertex = graph.getSizesVertex();
            ArrayList<ArrayList<Vertex>> allVertices = new ArrayList<ArrayList<Vertex>>();
            for (int i = 0; i < sizesVertex.size(); i++) {
                ArrayList<Vertex> vertices = new ArrayList<Vertex>();
                for (int j = 0; j < sizesVertex.get(i).length; j++) {
                    vertices.add(new Vertex(sizesVertex.get(i)[j]));
                }

                allVertices.add(vertices);
            }

            int[][] mainMatr = graph.createMatrix(graphList.size());
            ArrayList<Integer> sizeDotList = graph.readDot(String.valueOf(k));
            int indent = 0;
            if (sizeDotList.size() != 0) {
                indent = 70;
            }
            params.meanSpringLength = ((params.height + params.width - indent) / 2) / 2;
            double[] coordVertexMainGraph = graph.getCoord(mainMatr, params, params.width / 2,
                    (params.height + indent) / 2, 0, 0);
            ArrayList<double[]> coordList = new ArrayList<double[]>();
            ArrayList<int[][]> adjacencyMatrixList = new ArrayList<int[][]>();
            for (int l = 0; l < mainMatr.length; ++l) {
                params.meanSpringLength = ((params.height + params.width - indent)) / (graphList.get(l).length * graphList.size());
                coordList.add(graph.getCoord(graphList.get(l), params, 70, 30, (int) coordVertexMainGraph[2 * l],
                        (int) coordVertexMainGraph[2 * l + 1]));
                adjacencyMatrixList.add(graphList.get(l));
            }

            Visual visual = new Visual(params.width, params.height, adjacencyMatrixList, coordList);
            int distance = 0;
            if (sizeDotList.size() != 0) {
                distance = params.width / sizeDotList.size();
            }

            visual.Draw(sizeDotList, distance, allVertices, String.valueOf(k));
            System.out.println(k);
        }

        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println(timeConsumedMillis);
    }
}
