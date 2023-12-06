package com.example.projectwithmaven.Repository.TextFileRepository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;

import java.io.*;

// TextFileRepository - implementarea Repository-ului pentru stocarea datelor în fișiere text
public class TextFileRepositoryInchiriere<T extends Entitate> extends GenericRepository<Inchiriere> {
    private String fileName;

    public TextFileRepositoryInchiriere(String fileName) {
//        super();
        this.fileName = fileName;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // Citim din fisierul Text, si incarcam Lista noastra cu elemente
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFromFile();
    }

    @Override
    public Inchiriere findById(int id) {
        return super.findById(id);
    }

    @Override
    public int returnPosition(int id) {
        return super.returnPosition(id);
    }

    @Override
    public void update(Inchiriere entitate) throws RepositoryException {
        try {
            super.update(entitate);
            writeInFile();
        } catch (RepositoryException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            throw new RepositoryException("Error saving file " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        super.delete(id);
        try {
            writeInFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving file " + e.getMessage());
        }
    }

    @Override
    public void add(Inchiriere o) throws RepositoryException {
        // Apeleaza method add din MemoryRepository
        super.add(o);

        // saveFile se executa doar daca super.add() nu a aruncat exceptie
        try {
            writeInFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving file " + e.getMessage());
        }
    }

    // Se citeste din fisier
    private void readFromFile() {

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String linie;
                while ((linie = br.readLine()) != null) {

                    // Procesează linia și convertește datele în obiecte
                    Inchiriere entitate = convertReadLineToEntity(linie);

//                    System.out.println(entitate);

                    //Adaugam informatiile din fisier, in lista noastra de elemente
                    entitati.add(entitate);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    // Se suprascrie fisierul fileName de fiecare cand adaugam o Domain.Entitate noua
    // Se scrie in fisier
    private void writeInFile() throws IOException {
        // FIXME try with resources
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Inchiriere inchiriere : entitati) {
                String linie = convertEntityInLine(inchiriere);

//                System.out.println("writeInFile " + linie + " " + fileName);

                bw.write(linie);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO id, Domain.Masina, dataInceput, dataSfarsit
    // Convertirea unei entități într-o linie pentru fișier
    private String convertEntityInLine(Inchiriere inchiriere) {
//        System.out.println("ConvEntityInLine");

        // Input in the format that we have got
        int idInchiriere = inchiriere.getId();
        int idMasina = inchiriere.getMasina().getId();
        String marca = inchiriere.getMasina().getMarca();
        String model = inchiriere.getMasina().getModel();


        String dataInceput = inchiriere.getDataInceput();
        String dataSfarsit = inchiriere.getDataSfarsit();

        String result = idInchiriere + " " + idMasina + " " + marca + " " + model + " " + dataInceput + " " + dataSfarsit;

//        System.out.println(result);

        return result; // Trebuie implementat conform logicii entității specifice

    }

    //TODO id, Domain.Masina, dataInceput, dataSfarsit
    private Inchiriere convertReadLineToEntity(String linie){

        String[] parts = linie.split(" "); // Presupunem că datele din fișier sunt separate prin virgulă
        Inchiriere masinaInchiriata = null;

//        System.out.println("ConvReadLineToEntity");

        int idInchiriere = Integer.parseInt(parts[0]);

        Masina masina;
        int idMasina = Integer.parseInt(parts[1]); // ID-ul este primul element
        String marca = parts[2];
        String model = parts[3];
        masina = new Masina(idMasina, marca, model);

        String dataInceput = parts[4];
        String dataFinal = parts[5];

        masinaInchiriata = new Inchiriere(idInchiriere, masina,
                dataInceput.toString(), dataFinal.toString());
        return masinaInchiriata;
    }
}
