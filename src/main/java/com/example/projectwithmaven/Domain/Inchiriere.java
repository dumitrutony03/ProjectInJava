package com.example.projectwithmaven.Domain;

public class Inchiriere extends Entitate {
    private Masina masina;
    private String dataInceput;
    private String dataSfarsit;

    public Inchiriere(int id, Masina masina, String dataInceput, String dataSfarsit) {
        super(id);
        this.masina = masina;

        // In cazul in care avem un Input gresit de la User, trebuie sa facem ceva de genul

        if(dataInceput == null || dataSfarsit == null)
        {
            this.dataInceput = "01-01-2021";
            this.dataSfarsit = "01-02-2021";
            System.out.println("Trebuie introdusa o data valida de tipul Zi-Luna-An");
            return;
        }


        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;

//        int comparamDatele = dataInceput.compareTo(dataSfarsit);
//
//        if(comparamDatele <= 0) {
////            System.out.println("Datele sunt introduse corect");
//              this.dataInceput = dataInceput;
//              this.dataSfarsit = dataSfarsit;
//        }
//        else{
//            this.dataInceput = dataSfarsit;
//            this.dataSfarsit = dataInceput;
//        }
    }

    public Masina getMasina() {
        return masina;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }

    public String getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(String dataInceput) {
        this.dataInceput = dataInceput;
    }

    public String getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(String dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public String toString() {
        return "Inchiriere: [ID= " + getId() + ", Masina= " + getMasina() + ", DataInceput= " + getDataInceput() + ", DataSfarsit= " + getDataSfarsit();
    }
}