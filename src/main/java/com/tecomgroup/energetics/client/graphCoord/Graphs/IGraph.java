package com.tecomgroup.energetics.client.graphCoord.Graphs;

import com.tecomgroup.energetics.client.graphCoord.Rectangle;
import com.tecomgroup.energetics.client.graphCoord.Visualizer;

public interface IGraph {
    public void Visualize(Visualizer visualizer);

    public Rectangle GetDelineateRectangle();

    public void Move(int x, int y);
}
