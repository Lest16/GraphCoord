import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Visual
{
    private final int width;
    private final int height;

    public Visual(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void Draw(final ArrayList<double[]> coordList, final ArrayList<int[][]> adjacencyMatrixList, final ArrayList<Integer> sizeDotList, final int distance, final ArrayList<ArrayList<Vertex>> allVertices)
    {
        JFrame jf = new JFrame("Graph"){
            public int radius;
            public int maxLenght;

            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);

                File file = new File("output.html");
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
                        for(int k = 0; k < coordList.size(); k++) {
                            for (int i = 0; i < adjacencyMatrixList.get(k).length; ++i)
                                for (int j = i + 1; j < adjacencyMatrixList.get(k).length; ++j)
                                    if (adjacencyMatrixList.get(k)[i][j] == 1) {
                                        out.print("\t<line x1=\""+ (int) coordList.get(k)[2 * i] + "\" y1=\"" + (int) coordList.get(k)[2 * i + 1] +
                                                "\" x2=\"" + (int) coordList.get(k)[2 * j] + "\" y2=\"" + (int) coordList.get(k)[2 * j + 1] +
                                                "\" stroke-width=\"2\" stroke=\"rgb(0,0,0)\"/>  \n" +
                                                "   \n");
                                        g.drawLine((int) coordList.get(k)[2 * i], (int) coordList.get(k)[2 * i + 1], (int) coordList.get(k)[2 * j],
                                                (int) coordList.get(k)[2 * j + 1]);
                                    }

                            for (int l = 0; l < adjacencyMatrixList.get(k).length; ++l) {
                                out.print("<circle cx=\"" + (int) coordList.get(k)[2 * l] + "\" cy=\"" + (int) coordList.get(k)[2 * l + 1] + "\"  " +
                                        "r=\"" + allVertices.get(k).get(l).size  + "\" style=\"fill:black; fill-opacity:0.4; stroke-width:4px;\" />");
                                g.drawOval((int) coordList.get(k)[2 * l], (int) coordList.get(k)[2 * l + 1],
                                        allVertices.get(k).get(l).size, allVertices.get(k).get(l).size);
                                g.fillOval((int) coordList.get(k)[2 * l], (int) coordList.get(k)[2 * l + 1],
                                        allVertices.get(k).get(l).size, allVertices.get(k).get(l).size);
                                String[] caption = allVertices.get(k).get(l).caption.split(" ");
                                this.maxLenght = GetMaxLenght(caption);
                                this.radius = GetRadius(caption, maxLenght);
                                int x = this.getCoordX(maxLenght, radius, (int)coordList.get(k)[2 * l], (int)coordList.get(k)[2 * l + 1], l);
                                int y = this.getCoordY(caption.length, radius, (int)coordList.get(k)[2 * l], (int)coordList.get(k)[2 * l + 1], l);
                                for (int m = 0; m < caption.length; m++)
                                {
                                    out.print("<text  x=\"" + x + "\" y=\"" + y + "\" font-size=\"13px\"> " + caption[m] + " </text>");

                                    g.drawString(caption[m], x, y);
                                    y += 10;
                                }
                            }
                        }

                        int startPos = 10;
                        for (int l = 0; l < sizeDotList.size(); ++l) {
                            Integer size = sizeDotList.get(l);
                            g.drawOval(startPos, 35, size, size);
                            g.fillOval(startPos, 35, size, size);
                            startPos += distance;
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



            private int getCoordX(int maxLenght, int radius, int x, int y, int idxCurrentVertex)
            {
                int centralVertex = 0;
                for(int k = 0; k < coordList.size(); k++) {
                    int count = 0;
                    for (int j = 0; j < adjacencyMatrixList.get(k).length; ++j)
                        if (adjacencyMatrixList.get(k)[idxCurrentVertex][j] == 1)
                        {
                            centralVertex = j;
                            count ++;
                        }

                    if(count == 1)
                    {
                        if(x > coordList.get(k)[2 * centralVertex])
                        {
                            return x + 10;
                        }
                        else
                        {
                            return x - (maxLenght * 9);
                        }
                    }
                }

                ArrayList<Integer> listCoordX = new ArrayList<Integer>();
                ArrayList<Integer> listCoordY = new ArrayList<Integer>();
                boolean isLeft = true;
                boolean isRight = true;
                for(int i = 0; i < coordList.size(); i++)
                {
                    for (int j = 0; j < adjacencyMatrixList.get(i).length; j++)
                    {
                        double a = Math.abs(coordList.get(i)[2 * j] - x);
                        double b = Math.abs(coordList.get(i)[2 * j + 1] - y);
                        int c = (int)coordList.get(i)[2 * j];
                        int d = (int)coordList.get(i)[2 * j + 1];
                        if ((a <= radius) && (b <= radius) && (c != x) && (d != y))
                        {
                            listCoordX.add((int)coordList.get(i)[2 * j]);
                            listCoordY.add((int)coordList.get(i)[2 * j + 1]);
                        }

                        if (x < (int)coordList.get(i)[2 * j])
                        {
                            isLeft = false;
                        }

                        if (x > (int)coordList.get(i)[2 * j])
                        {
                            isRight = false;
                        }
                    }
                }

                if (isLeft)
                {
                    return x + 10;
                }

                if (isRight)
                {
                    return x - (maxLenght * 9);
                }

                for(int i = 0; i < listCoordX.size(); i++)
                {
                    for(int j = 0; j < listCoordX.size(); j++)
                    {
                        if (((listCoordX.get(i) < x) && (listCoordX.get(j) > x)) || ((listCoordX.get(i) > x) && (listCoordX.get(j) < x)))
                        {
                            int distance = Math.abs(listCoordX.get(i) - listCoordX.get(j));
                            if (distance > (maxLenght * 9))
                            {
                                int rangeX = (distance - (maxLenght * 9)) / 2;
                                int leftCoord = 0;
                                if (listCoordX.get(i) < listCoordX.get(j))
                                {
                                    leftCoord = listCoordX.get(i);
                                }
                                else
                                {
                                    leftCoord = listCoordX.get(j);
                                }

                                return leftCoord + rangeX;

                            }
                        }
                    }
                }

                return x - ((maxLenght*9) / 2);
            }

            private int getCoordY(int maxLenght, int radius,  int x, int y, int idxCurrentVertex)
            {
                int count = 0;
                int centralVertex = 0;
                for(int k = 0; k < coordList.size(); k++) {

                        for (int j = 0; j < adjacencyMatrixList.get(k).length; ++j)
                            if (adjacencyMatrixList.get(k)[idxCurrentVertex][j] == 1)
                            {
                                centralVertex = j;
                                count ++;
                            }

                    if(count == 1)
                    {
                        if(y > coordList.get(k)[2 * centralVertex + 1])
                        {
                            return y + 15;
                        }
                        else
                        {
                            return y - (maxLenght * 7);
                        }
                    }
                }

                ArrayList<Integer> listCoordX = new ArrayList<Integer>();
                ArrayList<Integer> listCoordY = new ArrayList<Integer>();
                boolean isTop = true;
                boolean isBottom = true;
                for(int i = 0; i < coordList.size(); i++)
                {
                    for (int j = 0; j < adjacencyMatrixList.get(i).length; j++)
                    {
                        double a = Math.abs(coordList.get(i)[2 * j] - x);
                        double b = Math.abs(coordList.get(i)[2 * j + 1] - y);
                        int c = (int)coordList.get(i)[2 * j];
                        int d = (int)coordList.get(i)[2 * j + 1];
                        if ((a <= radius) && (b <= radius) && (c != x) && (d != y))
                        {
                            listCoordX.add((int)coordList.get(i)[2 * j]);
                            listCoordY.add((int)coordList.get(i)[2 * j + 1]);
                        }

                        if (y < (int)coordList.get(i)[2 * j + 1])
                        {
                            isTop = false;
                        }

                        if (y > (int)coordList.get(i)[2 * j + 1])
                        {
                            isBottom = false;
                        }
                    }
                }

                if (isTop)
                {
                    return y + 20;
                }

                if (isBottom)
                {
                    return y - (maxLenght * 7);
                }

                for(int i = 0; i < listCoordY.size(); i++)
                {
                    if((listCoordX.get(i) > x) && (listCoordX.get(i) < (x + (maxLenght * 7))))
                    {
                        if(listCoordY.get(i) > (y + (maxLenght*7)))
                        {
                            return y + 20;
                        }
                        else if((listCoordY.get(i) < (y - (maxLenght*7))))
                        {
                            return y - (maxLenght*7);
                        }
                    }
                    else
                    {
                        return y + 20;
                    }
                }

                return y + 20;
            }

            private int GetMaxLenght(String[] caption)
            {
                int maxLenght = caption[0].length();
                for (int i = 1; i < caption.length; i++)
                {
                    if(caption[i].length() > maxLenght)
                    {
                        maxLenght = caption[i].length();
                    }
                }

                return maxLenght;
            }

            private int GetRadius(String[] caption, int maxLenght)
            {
                int radius = 0;
                if (maxLenght > caption.length)
                {
                    radius = maxLenght * 8;
                }
                else
                {
                    radius = caption.length * 8;
                }

                return radius;
            }
        };

        jf.setSize(this.width, this.height);
        jf.setVisible(true);

    }


}
