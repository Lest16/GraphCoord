package com.tecomgroup.energetics.client.graphCoord.Graphs;

import com.tecomgroup.energetics.client.graphCoord.Rectangle;
import com.tecomgroup.energetics.client.graphCoord.Visualizer;

public interface IGraph {
    void Visualize(Visualizer visualizer);

    Rectangle GetDelineateRectangle();

    void Move(int x, int y);
}
