package com.tecomgroup.energetics.client.graph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Params {
    public double gamma;
    public double meanSpringLength;
    public double spring;
    public int q2;
    public int width;
    public int height;

    public static Params createFromFile(String path) throws IOException {
        Params params = new Params();
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream(path);
        property.load(fis);
        params.gamma = Double.parseDouble(property.getProperty("gamma"));
        params.meanSpringLength = Double.parseDouble(property.getProperty("meanSpringLength"));
        params.spring = Double.parseDouble(property.getProperty("spring"));
        params.q2 = Integer.parseInt(property.getProperty("q2"));
        params.width = Integer.parseInt(property.getProperty("width"));
        params.height = Integer.parseInt(property.getProperty("height"));
        return params;
    }
}
