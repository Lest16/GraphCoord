package com.tecomgroup.energetics.client.graph;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class SvgWriter {

    private LinkedList<String> svgBuilder = new LinkedList<String>();

    public void addLine(int x1, int y1, int x2, int y2) {
        svgBuilder.add("\t<line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2 +
                "\" stroke-width=\"2\" stroke=\"rgb(0,0,0)\"/> \n" + " \n");
    }

    public void addCircle(int x, int y, int r) {
        svgBuilder.add("<circle cx=\"" + x + "\" cy=\"" + y + "\" " +
                "r=\"" + r + "\" style=\"fill:black; fill-opacity:0.4; stroke-width:4px;\" /> \n");
    }

    public void addText(int x, int y, String caption, String params) {
        svgBuilder.add("<text  x=\"" + x + "\" y=\"" + y +
                "\" " + params + "> " + caption + " </text> \n");
    }

    public void writeSvg(String filename, int width, int height) {
        File file = new File("outputSVG/" + filename + ".html");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print("<html>\n" +
                        "\n" +
                        "<svg viewBox=\"0 0 " + width + " " + height + "\" width=\" " + width + "\" " +
                        "height=\" " + height + "\" preserveAspectRatio=\"xMidYMid meet\"  " +
                        "xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
                for (String line: svgBuilder) {
                    out.print(line);
                }
                out.print("\n" + "</svg>\n" + "\n" + "</html>");
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
