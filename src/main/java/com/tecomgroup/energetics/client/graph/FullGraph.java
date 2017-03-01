package com.tecomgroup.energetics.client.graph;

public class FullGraph {
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
}
