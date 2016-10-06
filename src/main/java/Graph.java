import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Graph
{
    public int[][] readGraph() throws Exception
    {
        int[][] matrix;
        BufferedReader fin = new BufferedReader( new InputStreamReader(new FileInputStream("src/main/resources/data")));
        List<String> fileContent = new ArrayList<String>() ;
        String str ;
        while( (str = fin.readLine() ) != null )
        {
            fileContent.add(str);
        }

        fin.close() ;
        String[] splitLineInit = fileContent.get(0).split(" ");
        matrix = new int[fileContent.size()][splitLineInit.length];
        for (int i = 0; i < fileContent.size(); i++)
        {
            for (int j = 0; j < splitLineInit.length; j++)
            {
                String line = fileContent.get(i);
                String[] splitLine = line.split(" ");
                matrix[i][j] = Integer.parseInt(splitLine[j]);
            }
        }
        return matrix;
    }
}
