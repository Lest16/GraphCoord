import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Graph graph = new Graph();
        ArrayList<int[][]> graphList = graph.readGraphs();
        int countDot = graph.readCountDot();
        Params params = new Params();
        ArrayList<double[]> r = new ArrayList<double[]>();
        ArrayList<int[][]> gr = new ArrayList<int[][]>();
        int j = 0;
        int m = 0;
        for (int[][] matr: graphList)
        {
            Models models = new Models(matr, params);
            PhysSys physSys = new PhysSys(models);
            ArrayList<Double> energy = new ArrayList<Double>();
            for (int i = 0; i < 256; i++)
            {
                physSys.step();
                energy.add(models.SpringChargeEnergy(physSys.r));
            }

            r.add(physSys.centralize(200, 200, j, m));
            j+=400;

            gr.add(matr);
           // visual.Draw(physSys.centralize(400, 400), matr);
        }
        Visual visual = new Visual();
        visual.Draw(r, gr);
    }
}
