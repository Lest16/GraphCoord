import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Graph
{
    public ArrayList<int[][]> readGraphs() throws Exception
    {
        int[][] matrix;
        ArrayList<int[][]> graphList = new ArrayList<int[][]>();
        BufferedReader fin = new BufferedReader( new InputStreamReader(new FileInputStream("src/main/resources/data")));
        List<String> fileContent = new ArrayList<String>() ;
        String str ;
        while( (str = fin.readLine() ) != null )
        {
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

    public ArrayList<Integer> readDot() throws Exception {
        BufferedReader fin = new BufferedReader( new InputStreamReader(new FileInputStream("src/main/resources/countDot")));
        List<String> fileContent = new ArrayList<String>() ;
        String str;
        while( (str = fin.readLine() ) != null )
        {
            fileContent.add(str);
        }

        fin.close() ;
        if (fileContent.size() == 0)
        {
            return new ArrayList<Integer>();
        }
        String[] splitSizeDot = fileContent.get(0).split(" ");
        ArrayList<Integer> sizeDotList = new ArrayList<Integer>();
        for (String size: splitSizeDot)
        {
            sizeDotList.add(Integer.parseInt(size));
        }

        return sizeDotList;
    }

    public int[][] createMatrix(int countVertex)
    {
        int[][] resultMatrix = new int[countVertex][countVertex];
        for (int i = 0; i < countVertex; i++)
        {
            for (int j = 0; j < countVertex; j++)
            {
                if (i == j)
                {
                    resultMatrix[i][j] = 0;
                }
                else
                {
                    resultMatrix[i][j] = 1;
                }
            }
        }

        return resultMatrix;
    }

    public ArrayList<int []> getSizesVertex() throws Exception
    {
        BufferedReader fin = new BufferedReader( new InputStreamReader(new FileInputStream("src/main/resources/sizesVertex")));
        List<String> fileContent = new ArrayList<String>() ;
        String str;
        while( (str = fin.readLine() ) != null )
        {
            fileContent.add(str);
        }

        fin.close();
        ArrayList<int []> listSizes = new ArrayList<int[]>();
        for (int i = 0; i < fileContent.size(); i++)
        {
            String[] splitSizeVertex = fileContent.get(i).split(" ");
            int [] sizesVertex = new int[splitSizeVertex.length];
            for(int j = 0; j < splitSizeVertex.length; j++)
            {
                sizesVertex[j] = Integer.parseInt(splitSizeVertex[j]);
            }

            listSizes.add(sizesVertex);
        }

        return listSizes;
    }

    public double[] getCoord(int[][] matr,Params params, double centX, double centY, int deviationX, int deviationY)
    {
        Models models = new Models(matr, params);
        PhysSys physSys = new PhysSys(models);
        ArrayList<Double> energy = new ArrayList<Double>();
        for (int i = 0; i < 256; i++)
        {
            physSys.step();
            energy.add(models.SpringChargeEnergy(physSys.r));
        }
        return physSys.centralize(centX, centY, deviationX, deviationY);
    }
}
