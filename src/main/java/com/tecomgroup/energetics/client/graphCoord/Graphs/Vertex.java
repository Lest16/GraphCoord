package com.tecomgroup.energetics.client.graphCoord.Graphs;

public class Vertex {
    public final int size;
    public final String caption;
    public final int id;

    public Vertex(int size, String caption, int id) {
        this.size = size;
        this.caption = caption;
        this.id = id;
    }

    public Vertex(int id, String caption) {
        this.size = 9;
        this.caption = caption;
        this.id = id;
    }

    public Vertex() {
        this.size = 9;
        this.caption = getRandomWord();
        this.id = -1;
    }

    private String getRandomWord() {
        String dict = "abcdefghijklmnopqrstuvwxyz1234567890";
        int topBound = 3 + (int)(Math.random() * 4);
        int countWordBound = 1 + (int)(Math.random() * 4);
        String randomWord = "";
        for(int j = 0; j < countWordBound; j++) {
            for (int i = 0; i < topBound; i++) {
                randomWord += dict.charAt((int) (Math.random() * dict.length()));
            }

            randomWord += " ";
        }

        return randomWord;
    }

    public int GetMaxLength(String[] caption) {
        int maxLenght = caption[0].length();
        for (int i = 1; i < caption.length; i++) {
            if (caption[i].length() > maxLenght) {
                maxLenght = caption[i].length();
            }
        }

        return maxLenght;
    }
}
