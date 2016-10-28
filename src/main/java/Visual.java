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

                        int x = this.getCoordX(caption, (int)coordList.get(k)[2 * l], (int)coordList.get(k)[2 * l + 1]);
                        int y = this.getCoordY(caption, x, (int)coordList.get(k)[2 * l + 1]);
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

            private int getCoordX(String[] caption, int x, int y)
            {
                int maxLenght = caption[0].length();

                for (int i = 1; i < caption.length; i++)
                {
                    if(caption[i].length() > maxLenght)
                    {
                        maxLenght = caption[i].length();
                    }
                }
                int radius = 0;
                if (maxLenght > caption.length)
                {
                    radius = maxLenght * 10;
                }
                else
                {
                    radius = caption.length * 10;
                }

                ArrayList<Integer> listCoordX = new ArrayList<Integer>();
                ArrayList<Integer> listCoordY = new ArrayList<Integer>();
                for(int i = 0; i < coordList.size(); i++)
                {
                    for (int j = 0; j < adjacencyMatrixList.get(i).length; j++)
                    {
                        if ((Math.abs(coordList.get(i)[2 * j] - x) <= radius) && (Math.abs(coordList.get(i)[2 * j + 1] - y) <= radius)
                                && ((int)coordList.get(i)[2 * j] != x) && ((int)coordList.get(i)[2 * j + 1] != y))
                        {
                            listCoordX.add((int)coordList.get(i)[2 * j]);
                            listCoordY.add((int)coordList.get(i)[2 * j + 1]);
                        }
                    }
                }

                for (int i = 0; i < listCoordCaptionX.size(); i++)
                {
                    if (Math.abs(listCoordCaptionX.get(i) - x) <= radius)
                    {
                        listCoordX.add(this.listCoordCaptionX.get(i));
                    }
                }








                boolean isLeft = true;
                for(int i = 0; i < listCoordX.size(); i++)
                {
                   if (x < listCoordX.get(i))
                   {
                       isLeft = false;
                       break;
                   }
                }
                if (isLeft)
                {
                    this.listCoordCaptionX.add(x + 10);
                    return x + 10;
                }

                boolean isRight = true;
                for(int i = 0; i < listCoordX.size(); i++)
                {
                    if (x > listCoordX.get(i))
                    {
                        isRight = false;
                        break;
                    }
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

            /*    for(int i = 0; i < listCoordX.size() - 1; i++)
                {
                        int distance = Math.abs(listCoordX.get(i) - listCoordX.get(i + 1));
                        if (distance > (maxLenght * 7))
                        {
                            int rangeX = (distance - (maxLenght * 7)) / 2;
                            int leftCoord = 0;
                            if (listCoordX.get(i) < listCoordX.get(i + 1))
                            {
                                leftCoord = listCoordX.get(i);
                            }
                            else
                            {
                                leftCoord = listCoordX.get(i + 1);
                            }

                            return leftCoord + rangeX + 10;
                        }
                }*/
                this.listCoordCaptionX.add(x - ((maxLenght*7) / 2));
                return x - ((maxLenght*7) / 2);
            }






            private int getCoordY(String[] caption, int x, int y)
            {
                int maxLenght = caption[0].length();

                for (int i = 1; i < caption.length; i++)
                {
                    if(caption[i].length() > maxLenght)
                    {
                        maxLenght = caption[i].length();
                    }
                }
                int radius = 0;
                if (maxLenght > caption.length)
                {
                    radius = maxLenght * 10;
                }
                else
                {
                    radius = caption.length * 10;
                }

                ArrayList<Integer> listCoordX = new ArrayList<Integer>();
                ArrayList<Integer> listCoordY = new ArrayList<Integer>();
                for(int i = 0; i < coordList.size(); i++)
                {
                    for (int j = 0; j < adjacencyMatrixList.get(i).length; j++)
                    {
                        if ((Math.abs(coordList.get(i)[2 * j] - x) <= radius) && (Math.abs(coordList.get(i)[2 * j + 1] - y) <= radius)
                                && ((int)coordList.get(i)[2 * j] != x) && ((int)coordList.get(i)[2 * j + 1] != y))
                        {
                            listCoordX.add((int)coordList.get(i)[2 * j]);
                            listCoordY.add((int)coordList.get(i)[2 * j + 1]);
                        }
                    }
                }
                for (int i = 0; i < listCoordCaptionX.size(); i++)
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
                }
               // listCoordY.addAll(this.listCoordCaptionY);





                boolean isTop = true;
                for(int i = 0; i < listCoordY.size(); i++)
                {
                    if (y < listCoordY.get(i))
                    {
                        isTop = false;
                        break;
                    }
                }
                if (isTop)
                {
                    this.listCoordCaptionY.add(y + 20);
                    return y + 20;
                }

                boolean isBottom = true;
                for(int i = 0; i < listCoordY.size(); i++)
                {
                    if (y > listCoordY.get(i))
                    {
                        isBottom = false;
                        break;
                    }
                }
                if (isBottom)
                {
                    this.listCoordCaptionY.add(y - (caption.length * 7));
                    return y - (caption.length * 7);
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
                            this.listCoordCaptionY.add(y - (caption.length*7));
                            return y - (caption.length*7);
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

        /*    private int getCoordsInRadius(String[] caption, int x, int y)
            {

            }*/
        };

        jf.setSize(this.width, this.height);
        jf.setVisible(true);

    }


}
