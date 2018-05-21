package com.synergics.ishom.englishapps.Model;

public class ListGamesCategory {

    private String id;
    private String name;
    private float done;
    private int jumlah;
    private int size;

    public ListGamesCategory(String id, String name, float done, int jumlah, int size) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.jumlah = jumlah;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
