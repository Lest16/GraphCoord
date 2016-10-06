import javax.swing.*;
import java.awt.*;

public class Visual
{
    private double[] r;
    private int[][] gr;

    public Visual(double[] r, int[][] g)
    {
        this.r = r;
        this.gr = g;
    }

    public void Draw()
    {
        JFrame jf = new JFrame("Graph"){
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                for(int i = 0; i < gr.length; ++i)
                       for(int j = i + 1; j < gr.length; ++j)
                           if(gr[i][j] == 1)
                              g.drawLine((int)r[2*i], (int)r[2*i+1], (int)r[2*j], (int)r[2*j+1]);

                for(int k = 0; k < gr.length; ++k) {
                    g.drawOval((int) r[2 * k], (int) r[2 * k + 1], 12, 12);
                    g.fillOval((int) r[2 * k], (int) r[2 * k + 1], 12, 12);
                }
            }
        };

        jf.setSize(800, 800);
        jf.setVisible(true);

    }

}
