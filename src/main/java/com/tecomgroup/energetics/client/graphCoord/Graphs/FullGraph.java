package com.tecomgroup.energetics.client.graphCoord.Graphs;

import com.tecomgroup.energetics.client.graphCoord.Rectangle;
import com.tecomgroup.energetics.client.graphCoord.Visualizer;

public class FullGraph implements IGraph{
    public final int leftX;
    public final int rightX;
    public final int basicY;
    public final int indent;

    public FullGraph(int leftX, int rightX, int basicY, int indent)
    {
        this.leftX = leftX;
        this.rightX = rightX;
        this.basicY = basicY;
        this.indent = indent;
    }

    public void Visualize(Visualizer visualizer) {

    }

    public Rectangle GetDelineateRectangle() {
        return new Rectangle(this.leftX, this.basicY - this.indent,
                this.rightX, this.basicY + this.indent);
    }
}
