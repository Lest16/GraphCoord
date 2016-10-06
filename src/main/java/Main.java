import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Graph graph = new Graph();
        int[][] g = graph.readGraph();
        Params params = new Params();
        Models models = new Models(g, params);
        PhysSys physSys = new PhysSys(models);
        ArrayList<Double> energy = new ArrayList<Double>();
        for (int i = 0; i < 256; i++)
        {
            physSys.step();
            energy.add(models.SpringChargeEnergy(physSys.r));
        }

        Visual visual = new Visual(physSys.centralize(400, 400), g);
        visual.Draw();
    }
}
