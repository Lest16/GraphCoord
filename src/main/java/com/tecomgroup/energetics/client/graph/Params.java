package com.tecomgroup.energetics.client.graph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Params {
    public static double gamma;
    public static double meanSpringLength;
    public static double spring;
    public static int q2;
    public static int width;
    public static int height;

    private static FileInputStream fis;

    public static Params createFromFile(String path) throws IOException {
        Params params = new Params();
        Properties property = new Properties();
        fis = new FileInputStream(path);
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