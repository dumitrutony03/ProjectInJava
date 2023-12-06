package com.example.projectwithmaven.Domain;

public class Masina extends Entitate {
    private String marca;
    private String model;

    public Masina(int id, String marca, String model) {
        super(id);
        this.marca = marca;
        this.model = model;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String toString() {
        return "Domain.Masina [ID=" + this.getId() + ", Marca=" + this.getMarca() + ", Model=" + this.getModel() + "]";
    }
}
