package com.tecomgroup.energetics.client.graphCoord.Graphs;

import com.tecomgroup.energetics.client.graphCoord.Rectangle;
import com.tecomgroup.energetics.client.graphCoord.Visualizer;

public class FullGraph implements IGraph{
    public int leftX;
    public int rightX;
    public int basicY;
    public final int indent;
    public final int countVertex;
    private Rectangle delineateRectangle;

    public FullGraph(int leftX, int rightX, int basicY, int indent, int countVertex)
    {
        this.leftX = leftX;
        this.rightX = rightX;
        this.basicY = basicY;
        this.indent = indent;
        this.countVertex = countVertex;
    }

    public void Visualize(Visualizer visualizer) {
        visualizer.DrawFullGraph(this);
    }
    
    public Rectangle GetDelineateRectangle(){
        if(this.delineateRectangle == null){
            this.delineateRectangle = this.CalculateDelineateRectangle();
        }
        
        return this.delineateRectangle;
    }

    private Rectangle CalculateDelineateRectangle() {
        return new Rectangle(this.leftX, this.basicY - this.indent,
                this.rightX, this.basicY + this.indent);
    }

    public void Move(int x, int y) {
        this.leftX += x;
        this.rightX += x;
        this.basicY += y;
    }
}
