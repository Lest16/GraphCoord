import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Graph graph = new Graph();
        Params params = new Params();
        ArrayList<int[][]> graphList = graph.readGraphs();
        ArrayList<int []> sizesVertex = graph.getSizesVertex();
        int[][] mainMatr = graph.createMatrix(graphList.size());
        ArrayList<Integer> sizeDotList = graph.readDot();
        int indent = 0;
        if (sizeDotList.size() != 0)
        {
            indent = 70;
        }
        params.meanSpringLength = ((params.height + params.width - indent) / 2) / 2;
        double[] coordVertexMainGraph = graph.getCoord(mainMatr, params, params.width / 2, (params.height + indent) / 2, 0, 0);
        ArrayList<double[]> r = new ArrayList<double[]>();
        ArrayList<int[][]> gr = new ArrayList<int[][]>();
        for (int l = 0; l < mainMatr.length; ++l) {
            params.meanSpringLength = ((params.height + params.width - indent)) / (graphList.get(l).length * graphList.size());
            r.add(graph.getCoord(graphList.get(l), params, 0, 0, (int)coordVertexMainGraph[2 * l],
                    (int)coordVertexMainGraph[2 * l + 1]));
            gr.add(graphList.get(l));
        }

        Visual visual = new Visual(params.width, params.height);
        int distance = 0;
        if (sizeDotList.size() != 0)
        {
            distance = params.width / sizeDotList.size();
        }

        visual.Draw(r, gr, sizeDotList, distance, sizesVertex);
    }
}
