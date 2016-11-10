import javax.swing.*;
import java.awt.*;
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
            public ArrayList<Integer> listCoordCaptionX = new ArrayList<Integer>();
            public ArrayList<Integer> listCoordCaptionY = new ArrayList<Integer>();

            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                for(int k = 0; k < coordList.size(); k++) {
                    for (int i = 0; i < adjacencyMatrixList.get(k).length; ++i)
                        for (int j = i + 1; j < adjacencyMatrixList.get(k).length; ++j)
                            if (adjacencyMatrixList.get(k)[i][j] == 1)
                                g.drawLine((int) coordList.get(k)[2 * i], (int) coordList.get(k)[2 * i + 1], (int) coordList.get(k)[2 * j],
                                        (int) coordList.get(k)[2 * j + 1]);

                    for (int l = 0; l < adjacencyMatrixList.get(k).length; ++l) {
                        g.drawOval((int) coordList.get(k)[2 * l], (int) coordList.get(k)[2 * l + 1],
                                allVertices.get(k).get(l).size, allVertices.get(k).get(l).size);
                        g.fillOval((int) coordList.get(k)[2 * l], (int) coordList.get(k)[2 * l + 1],
                                allVertices.get(k).get(l).size, allVertices.get(k).get(l).size);
                        String[] caption = allVertices.get(k).get(l).caption.split(" ");
                        //int y = (int) rArray.get(k)[2 * l + 1] + 10;
                        this.maxLenght = GetMaxLenght(caption);
                        this.radius = GetRadius(caption, maxLenght);
                        int x = this.getCoordX(maxLenght, radius, (int)coordList.get(k)[2 * l], (int)coordList.get(k)[2 * l + 1]);
                        int y = this.getCoordY(caption.length, radius, (int)coordList.get(k)[2 * l], (int)coordList.get(k)[2 * l + 1]);
                        for (int m = 0; m < caption.length; m++)
                        {
                            //g.drawString(caption[m], (int) rArray.get(k)[2 * l] + 10, y);
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
            }



            private int getCoordX(int maxLenght, int radius, int x, int y)
            {
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

                for (int i = 0; i < listCoordCaptionX.size(); i++)
                {
                    double e = Math.abs(listCoordCaptionX.get(i) - x);
                    if (e <= radius)
                    {
                        listCoordX.add(this.listCoordCaptionX.get(i));
                    }
                }

                if (isLeft)
                {
                    this.listCoordCaptionX.add(x + 10);
                    return x + 10;
                }

                if (isRight)
                {
                    this.listCoordCaptionX.add(x - (maxLenght * 7));
                    return x - (maxLenght * 7);
                }



                for(int i = 0; i < listCoordX.size(); i++)
                {
                    for(int j = 0; j < listCoordX.size(); j++)
                    {
                        if (((listCoordX.get(i) < x) && (listCoordX.get(j) > x)) || ((listCoordX.get(i) > x) && (listCoordX.get(j) < x)))
                        {
                            int distance = Math.abs(listCoordX.get(i) - listCoordX.get(j));
                            if (distance > (maxLenght * 7))
                            {
                                int rangeX = (distance - (maxLenght * 7)) / 2;
                                int leftCoord = 0;
                                if (listCoordX.get(i) < listCoordX.get(j))
                                {
                                    leftCoord = listCoordX.get(i);
                                }
                                else
                                {
                                    leftCoord = listCoordX.get(j);
                                }

                                this.listCoordCaptionX.add(leftCoord + rangeX);
                                return leftCoord + rangeX;

                            }
                        }
                    }
                }

                this.listCoordCaptionX.add(x - ((maxLenght*7) / 2));
                return x - ((maxLenght*7) / 2);
            }






            private int getCoordY(int maxLenght, int radius,  int x, int y)
            {
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
      /*          for (int i = 0; i < listCoordCaptionX.size(); i++)
                {
                    if (Math.abs(listCoordCaptionX.get(i) - x) <= radius)
                    {
                        listCoordX.add(this.listCoordCaptionX.get(i));
                    }
                }

                for (int i = 0; i < listCoordCaptionY.size(); i++)
                {
                    if (Math.abs(listCoordCaptionY.get(i) - y) <= radius)
                    {
                        listCoordY.add(this.listCoordCaptionY.get(i));
                    }
                }*/
               // listCoordY.addAll(this.listCoordCaptionY);


                if (isTop)
                {
                    this.listCoordCaptionY.add(y + 20);
                    return y + 20;
                }

                if (isBottom)
                {
                    this.listCoordCaptionY.add(y - (maxLenght * 7));
                    return y - (maxLenght * 7);
                }

                for(int i = 0; i < listCoordY.size(); i++)
                {
                    if((listCoordX.get(i) > x) && (listCoordX.get(i) < (x + (maxLenght * 7))))
                    {
                        if(listCoordY.get(i) > (y + (maxLenght*7)))
                        {
                            this.listCoordCaptionY.add(y + 20);
                            return y + 20;
                        }
                        else if((listCoordY.get(i) < (y - (maxLenght*7))))
                        {
                            this.listCoordCaptionY.add(y - (maxLenght*7));
                            return y - (maxLenght*7);
                        }
                    }
                    else
                    {
                        this.listCoordCaptionY.add(y + 20);
                        return y + 20;
                    }
                }

                this.listCoordCaptionY.add(y + 20);
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
