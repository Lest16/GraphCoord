import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Graph graph = new Graph();
        Params params = new Params();
        ArrayList<int[][]> graphList = graph.readGraphs();
        int[][] mainMatr = graph.createMatrix(graphList.size());
        int countDot = graph.readCountDot();
        int indent = 70;
        params.meanSpringLength = ((params.height + params.width - indent) / 2) / 2;
        double[] coordVertexMainGraph = graph.getCoord(mainMatr, params, params.width / 2, (params.height + indent) / 2, 0, 0);
        ArrayList<double[]> r = new ArrayList<double[]>();
        ArrayList<int[][]> gr = new ArrayList<int[][]>();
        for (int l = 0; l < mainMatr.length; ++l) {
            params.meanSpringLength = ((params.height + params.width - indent) / 4) / (graphList.get(l).length);
            r.add(graph.getCoord(graphList.get(l), params, 0, 0, (int)coordVertexMainGraph[2 * l],
                    (int)coordVertexMainGraph[2 * l + 1]));
            gr.add(graphList.get(l));
        }

        Visual visual = new Visual(params.width, params.height);
        visual.Draw(r, gr, countDot, params.width / countDot);
    }
}
