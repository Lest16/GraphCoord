package com.tecomgroup.energetics.client.graphCoord;

public class Vertex {
    public final int size;
    public final String caption;

    public Vertex(int size, String caption) {
        this.size = size;
        this.caption = caption;
    }

    public Vertex(int size) {
        this.size = size;
        this.caption = getRandomWord();
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
