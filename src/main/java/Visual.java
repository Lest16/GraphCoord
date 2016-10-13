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

    public void Draw(final ArrayList<double[]> rArray, final ArrayList<int[][]> grArray, final ArrayList<Integer> sizeDotList, final int distance, final ArrayList<int[]> sizesVertex)
    {
        JFrame jf = new JFrame("Graph"){
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                for(int k = 0; k < rArray.size(); k++) {
                    for (int i = 0; i < grArray.get(k).length; ++i)
                        for (int j = i + 1; j < grArray.get(k).length; ++j)
                            if (grArray.get(k)[i][j] == 1)
                                g.drawLine((int) rArray.get(k)[2 * i], (int) rArray.get(k)[2 * i + 1], (int) rArray.get(k)[2 * j],
                                        (int) rArray.get(k)[2 * j + 1]);

                    for (int l = 0; l < grArray.get(k).length; ++l) {
                        g.drawOval((int) rArray.get(k)[2 * l], (int) rArray.get(k)[2 * l + 1], sizesVertex.get(k)[l], sizesVertex.get(k)[l]);
                        g.fillOval((int) rArray.get(k)[2 * l], (int) rArray.get(k)[2 * l + 1], sizesVertex.get(k)[l], sizesVertex.get(k)[l]);
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
        };

        jf.setSize(this.width, this.height);
        jf.setVisible(true);

    }

}
