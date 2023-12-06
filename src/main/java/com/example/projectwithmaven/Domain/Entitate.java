package com.example.projectwithmaven.Domain;

import java.io.Serializable;

public abstract class Entitate implements Serializable {
    public Entitate(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

