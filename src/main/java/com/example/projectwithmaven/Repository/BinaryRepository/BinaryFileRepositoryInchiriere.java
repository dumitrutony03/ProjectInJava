package com.example.projectwithmaven.Repository.BinaryRepository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileRepositoryInchiriere<T extends Entitate> extends GenericRepository<Inchiriere> {
    private String fileName;

    public BinaryFileRepositoryInchiriere(String fileName) {
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

    private void readFromFile()
    {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Read the entire list as a single object
            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                List<?> deserializedList = (List<?>) obj;

                if (!deserializedList.isEmpty() && deserializedList.get(0) instanceof Inchiriere) {
                    this.entitati = (List<Inchiriere>) obj;
                } else {
                    System.err.println("Unexpected object type in the file.");
                }
            }
        } catch (EOFException e) {
            // Sfârșitul fișierului
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    // Se suprascrie fisierul fileName de fiecare cand adaugam o Domain.Entitate noua
    private void writeInFile() throws IOException {
        // FIXME try with resources
        try {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
                outputStream.writeObject(new ArrayList<>(entitati.stream().toList()));
            }
        } catch (IOException e) {
            e.printStackTrace(); // sau tratarea erorilor aici
        }
    }
}