package com.tecomgroup.energetics.client.graphCoord.Graphs;

import com.tecomgroup.energetics.client.graphCoord.Rectangle;
import com.tecomgroup.energetics.client.graphCoord.Visualizer;

public class Graph implements IGraph{
    public final int[][] adjacencyMatrix;
    public int[] coords;

    public Graph(int[][] adjacencyMatrix, int[] coords) {

        this.adjacencyMatrix = adjacencyMatrix;
        this.coords = coords;
    }


    public void Visualize(Visualizer visualizer) {
        visualizer.DrawGraph(this);
    }

    public Rectangle GetDelineateRectangle() {
        int minX = this.coords[0];
        int minY = this.coords[1];
        int maxX = this.coords[0];
        int maxY = this.coords[1];
        for (int j = 1; j < this.coords.length / 2; j++) {
            if (minX > this.getX(j)) {
                minX = this.getX(j);
            }
            if (minY > this.getY(j)) {
                minY = this.getY(j);
            }
            if (maxX < this.getX(j)) {
                maxX = this.getX(j);
            }
            if (maxY < this.getY(j)) {
                maxY = this.getY(j);
            }
        }

        return new Rectangle(minX, minY, maxX, maxY);
    }

    public void Move(int x, int y) {
        for (int i = 0; i < coords.length / 2; i++){
            this.coords[2 * i] = this.getX(i) + x;
            this.coords[2 * i + 1] = this.getY(i) + y;
        }
    }

    public int getX(int i){
        return this.coords[2 * i];
    }

    public int getY(int i){
        return this.coords[2 * i + 1];
    }
}
