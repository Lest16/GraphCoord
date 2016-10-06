import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Graph graph = new Graph();
        Params params = new Params();
        ArrayList<int[][]> graphList = graph.readGraphs();
        int[][] mainMatr = graph.createMatrix(graphList.size());
        params.meanSpringLength = ((params.height + params.width) / 2) / 2;
        double[] coordVertexMainGraph = graph.getCoord(mainMatr, params, params.width / 2, params.height / 2, 0, 0);



        int countDot = graph.readCountDot();

        ArrayList<double[]> r = new ArrayList<double[]>();
        ArrayList<int[][]> gr = new ArrayList<int[][]>();
        params.meanSpringLength = ((params.height + params.width) / 2) / graphList.size();
        for (int l = 0; l < mainMatr.length; ++l) {
            r.add(graph.getCoord(mainMatr, params, params.width / 200, params.height / 200, (int)coordVertexMainGraph[2 * l],
                    (int)coordVertexMainGraph[2 * l + 1]));
            gr.add(mainMatr);
        }

/*
        for (int[][] matr: graphList)
        {
            r.add(graph.getCoord(matr, params));
            gr.add(matr);
        }
*/
        Visual visual = new Visual(params.width, params.height);
        visual.Draw(r, gr);
    }
}
