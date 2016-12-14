import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LinearGraph {

    private final int width;
    private final int height;
    public int[][] linearMatrix;

    public LinearGraph(int width, int height) throws Exception {
        this.width = width;
        this.height = height;
        this.readGraphs();
    }

    public LinearGraph(int width, int height, int[][] graph) throws Exception {
        this.width = width;
        this.height = height;
        this.linearMatrix = graph;
    }
    public void readGraphs() throws Exception
    {
        BufferedReader fin = new BufferedReader( new InputStreamReader(new FileInputStream("src/main/resources/linearData")));
        List<String> fileContent = new ArrayList<String>() ;
        String str ;
        while( (str = fin.readLine() ) != null )
        {
            fileContent.add(str);
        }

        fin.close();
        this.linearMatrix = new int[fileContent.size()][fileContent.size()];
        for (int i = 0; i < fileContent.size(); i++)
        {
            String[] splitLine = fileContent.get(i).split(" ");
            for (int j = 0; j < splitLine.length; j++)
            {
                this.linearMatrix[i][j] = Integer.parseInt(splitLine[j]);
            }
        }

    }

    public void Draw() throws Exception
    {
        File file = new File("outputLinear.html");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print("<html>\n" +
                        "\n" +
                        "<svg viewBox=\"0 0 1100 950\" width=\"1100\" height=\"950\" preserveAspectRatio=\"xMidYMid meet\"  " +
                        "xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
                out.print("\t<line x1=\""+ 0 + "\" y1=\"" + (int) this.height / 2 +
                        "\" x2=\"" + this.width + "\" y2=\"" + (int) this.height / 2 +
                        "\" stroke-width=\"2\" stroke=\"rgb(0,0,0)\"/>  \n" +
                        "   \n");
                int distance = this.width / this.linearMatrix.length;
                int lastCoordX = 0;
                    for (int i = 0; i < this.linearMatrix.length - 1; ++i)
                    {
                        Vertex vertex = new Vertex(9);
                        if(i % 2 == 0)
                        {
                            out.print("\t<line x1=\""+ (lastCoordX + distance) + "\" y1=\"" + (int) this.height / 2 +
                                    "\" x2=\"" + (lastCoordX + distance) + "\" y2=\"" + (int) (((this.height / 2) + distance) - vertex.size) +
                                    "\" stroke-width=\"2\" stroke=\"rgb(0,0,0)\"/>  \n" +
                                    "   \n");
                            out.print("<circle cx=\"" + (int) (lastCoordX + distance) + "\" cy=\"" + ((this.height / 2) + distance) + "\"  " +
                                    "r=\"" + vertex.size  + "\" style=\"fill:black; fill-opacity:0.4; stroke-width:4px;\" />");
                            int y = ((this.height / 2) + distance);
                            String[] caption = vertex.caption.split(" ");
                            for (int m = 0; m < caption.length; m++)
                            {
                                out.print("<text  x=\"" + ((lastCoordX + distance) + 10) + "\" y=\"" + y + "\" font-size=\"13px\"> " + caption[m] + " </text>");
                                y += 10;
                            }
                        }
                        else
                        {
                            out.print("\t<line x1=\""+ (lastCoordX + distance) + "\" y1=\"" + (int) this.height / 2 +
                                    "\" x2=\"" + (lastCoordX + distance) + "\" y2=\"" + (int) (((this.height / 2) - distance) + vertex.size) +
                                    "\" stroke-width=\"2\" stroke=\"rgb(0,0,0)\"/>  \n" +
                                    "   \n");
                            out.print("<circle cx=\"" + (int) (lastCoordX + distance) + "\" cy=\"" + ((this.height / 2) - distance) + "\"  " +
                                    "r=\"" + vertex.size  + "\" style=\"fill:black; fill-opacity:0.4; stroke-width:4px;\" />");
                            int y = ((this.height / 2) - distance);
                            String[] caption = vertex.caption.split(" ");
                            for (int m = 0; m < caption.length; m++)
                            {
                                out.print("<text  x=\"" + ((lastCoordX + distance) + 10) + "\" y=\"" + y + "\" font-size=\"13px\"> " + caption[m] + " </text>");
                                y += 10;
                            }
                        }
                        lastCoordX += distance;

                    }

                out.print("\n" +
                        "</svg>\n" +
                        "\n" +
                        "</html>");
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }
}
