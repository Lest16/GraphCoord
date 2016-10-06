import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Visual
{
    public void Draw(final ArrayList<double[]> rArray, final ArrayList<int[][]> grArray)
    {
        JFrame jf = new JFrame("Graph"){
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                for(int k = 0; k < rArray.size(); k++) {
                    for (int i = 0; i < grArray.get(k).length; ++i)
                        for (int j = i + 1; j < grArray.get(k).length; ++j)
                            if (grArray.get(k)[i][j] == 1)
                                g.drawLine((int) rArray.get(k)[2 * i], (int) rArray.get(k)[2 * i + 1], (int) rArray.get(k)[2 * j], (int) rArray.get(k)[2 * j + 1]);

                    for (int l = 0; l < grArray.get(k).length; ++l) {
                        g.drawOval((int) rArray.get(k)[2 * l], (int) rArray.get(k)[2 * l + 1], 12, 12);
                        g.fillOval((int) rArray.get(k)[2 * l], (int) rArray.get(k)[2 * l + 1], 12, 12);
                    }
                }
            }
        };

        jf.setSize(800, 800);
        jf.setVisible(true);

    }

}
