package com.tecomgroup.energetics.client.graphCoord;

public class Rectangle {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;

    public Rectangle(int x1, int y1, int x2, int y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int GetWidth(){
        return this.x2 - this.x1;
    }

    public int GetHeight(){
        return this.y2 - this.y1;
    }
}
