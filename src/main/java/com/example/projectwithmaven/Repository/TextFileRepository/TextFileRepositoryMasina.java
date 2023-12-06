package com.example.projectwithmaven.Repository.TextFileRepository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;

import java.io.*;

// TextFileRepository - implementarea Repository-ului pentru stocarea datelor în fișiere text
public class TextFileRepositoryMasina<T extends Entitate> extends GenericRepository<Masina> {
    private String fileName;

    public TextFileRepositoryMasina(String fileName) {
//        super();
        this.fileName = fileName;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Se citeste mereu fisierul cand rulam programul
        readFromFile();
    }

    @Override
    public Masina findById(int id) {
        return super.findById(id);
    }

    @Override
    public int returnPosition(int id) {
        return super.returnPosition(id);
    }

    @Override
    public void update(Masina entitate) throws RepositoryException {
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
    public void add(Masina o) throws RepositoryException {
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
                Masina masina = convertReadLineToEntity(linie);
                entitati.add(masina);
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
            for (Masina entitate : entitati) {
                String linie = convertEntityInLine(entitate);
//                System.out.println("writeInFile " + linie + " " + fileName);

                bw.write(linie);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Masina convertReadLineToEntity(String linie) {

        String[] parts = linie.split(" "); // Presupunem că datele din fișier sunt separate prin virgulă

        int idMasina = Integer.parseInt(parts[0]);
        String marca = parts[1];
        String model = parts[2];

        Masina masina = new Masina(idMasina, marca, model);
        return masina;
    }

    // Convertirea unei entități într-o linie pentru fișier
    private String convertEntityInLine(Masina entitate) {
        String result = entitate.getId() + " " + entitate.getMarca() + " " + entitate.getModel();
        return result; // Trebuie implementat conform logicii entității specifice
    }

}
