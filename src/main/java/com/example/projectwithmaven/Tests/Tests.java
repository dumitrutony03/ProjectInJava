package com.example.projectwithmaven.Tests;

import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.BinaryRepository.BinaryFileRepositoryInchiriere;
import com.example.projectwithmaven.Repository.BinaryRepository.BinaryFileRepositoryMasina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;
import com.example.projectwithmaven.Repository.IRepository;
import com.example.projectwithmaven.Repository.TextFileRepository.TextFileRepositoryInchiriere;
import com.example.projectwithmaven.Repository.TextFileRepository.TextFileRepositoryMasina;
import com.example.projectwithmaven.Service.Service;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class Tests {
                                    /// REPOSITORIES TESTS
    @Test
    private static void CRUD_BinaryRepoMasina() throws RepositoryException, IOException {
        deleteFileContent("MasiniBinTest.bin");

        IRepository<Masina> masinaRepositoryBinary = new BinaryFileRepositoryMasina<>("MasiniBinTest.bin");
        Masina masina = new Masina(1, "Volvo", "XC90");
        masinaRepositoryBinary.add(masina);
        assertEquals(masinaRepositoryBinary.getAllEntities().size(), 1);

        masinaRepositoryBinary.update(new Masina(1, "Volkswagen", "Passat"));
        Masina masinaGasita = masinaRepositoryBinary.findById(1);

        //FIXME Compara atributele obiectelor
        assert (Objects.equals(masinaGasita, new Masina(1, "Volkswagen", "Passat")));
//        assertEquals(masinaGasita, new Masina(1, "Volkswagen", "Passat")); // Compara adresele obiectelor

        masinaRepositoryBinary.delete(1);
        assertEquals(masinaRepositoryBinary.getAllEntities().size(), 0);
    }

    @Test
    private static void CRUD_BinaryRepoInchiriere() throws RepositoryException {
        deleteFileContent("MasiniBinTest.bin");
        deleteFileContent("MasiniInchiriateBinTest.bin");

        IRepository<Masina> masinaRepositoryBinary = new BinaryFileRepositoryMasina<>("MasiniBinTest.bin");
        IRepository<Inchiriere> inchiriereRepositoryBinary = new BinaryFileRepositoryInchiriere<>("MasiniInchiriateBinTest.bin");

        Masina masina1 = new Masina(1, "Ferrari", "Berlinetta");
        Masina masina2 = new Masina(2, "Ferrari", "LaFerrari");
        masinaRepositoryBinary.add(masina1);
        masinaRepositoryBinary.add(masina2);

        inchiriereRepositoryBinary.add(new Inchiriere(1, masina1, "01-01-2020", "05-01-2020"));
        inchiriereRepositoryBinary.add(new Inchiriere(2, masina2, "01-03-2020", "05-03-2020"));

        assertEquals(inchiriereRepositoryBinary.getAllEntities().size(),2);

        Inchiriere inchiriere = inchiriereRepositoryBinary.findById(1);
        assertEquals(inchiriere.getDataInceput(), "01-01-2020");

        inchiriereRepositoryBinary.delete(1);
        assertEquals(inchiriereRepositoryBinary.getAllEntities().size(),1);

        inchiriereRepositoryBinary.update(new Inchiriere(2, masina1, "10-10-2021", "17-10-2021"));
        assert (Objects.equals(inchiriereRepositoryBinary.getAllEntities().get(0), new Inchiriere(2, masina1, "10-10-2021", "17-10-2021")));

    }

    @Test
    private static void CRUD_TextFileRepoMasina() throws RepositoryException, IOException {
        deleteFileContent("MasiniTest.txt");

        IRepository<Masina> masinaRepositoryTextFile = new TextFileRepositoryMasina<>("MasiniTest.txt");
        Masina masina = new Masina(1, "Volvo", "XC90");

        masinaRepositoryTextFile.add(masina);
        masinaRepositoryTextFile.add(masina);
        masinaRepositoryTextFile.update(new Masina (2, "Volvo", "XC90"));
        masinaRepositoryTextFile.delete(2);

        assertEquals(masinaRepositoryTextFile.getAllEntities().size(), 1);

        masinaRepositoryTextFile.update(new Masina(1, "Volkswagen", "Passat"));
        Masina masinaGasita = masinaRepositoryTextFile.findById(1);

        //FIXME Compara atributele obiectelor
        assert (Objects.equals(masinaGasita, new Masina(1, "Volkswagen", "Passat")));
//        assertEquals(masinaGasita, new Masina(1, "Volkswagen", "Passat")); // Compara adresele obiectelor

        Masina masinaFind = masinaRepositoryTextFile.findById(2);
        assertNull(masinaFind);

        masinaRepositoryTextFile.delete(1);
        assertEquals(masinaRepositoryTextFile.getAllEntities().size(), 0);
    }

    @Test
    private static void CRUD_TextFileRepoInchiriereMasina() throws RepositoryException {
        deleteFileContent("MasiniTest.txt");
        deleteFileContent("MasiniInchiriateTest.txt");

        IRepository<Masina> masinaRepositoryTextFile = new TextFileRepositoryMasina<>("MasiniTest.txt");
        IRepository<Inchiriere> inchiriereRepositoryTextFile = new TextFileRepositoryInchiriere<>("MasiniInchiriateTest.txt");

        Masina masina1 = new Masina(1, "Ferrari", "Berlinetta");
        Masina masina2 = new Masina(2, "Ferrari", "LaFerrari");
        masinaRepositoryTextFile.add(masina1);
        masinaRepositoryTextFile.add(masina2);

        inchiriereRepositoryTextFile.add(new Inchiriere(1, masina1, "01-01-2020", "05-01-2020"));
        inchiriereRepositoryTextFile.add(new Inchiriere(2, masina2, "01-03-2020", "05-03-2020"));

        assertEquals(inchiriereRepositoryTextFile.getAllEntities().size(),2);

        inchiriereRepositoryTextFile.delete(1);
        assertEquals(inchiriereRepositoryTextFile.getAllEntities().size(),1);

        inchiriereRepositoryTextFile.update(new Inchiriere(2, masina1, "10-10-2021", "17-10-2021"));
        assert (Objects.equals(inchiriereRepositoryTextFile.getAllEntities().get(0), new Inchiriere(2, masina1, "10-10-2021", "17-10-2021")));

    }


                    /// SERVICE TESTS

    @Test
    private static void ServiceTest() throws RepositoryException {
        GenericRepository<Masina> repo = new GenericRepository<>();
        Service<Masina> serviceMasini = new Service<>(repo);

        Masina masina1 = new Masina(1, "Ferrari", "Berlinetta");
        serviceMasini.add(masina1);

        serviceMasini.findById(1);
        serviceMasini.returnPosition(1);

        assertEquals(serviceMasini.getAllEntities().size(),1);

        serviceMasini.update(new Masina(1, "Infinity", "QX70"));
        assert(Objects.equals(serviceMasini.getAllEntities().get(0), new Masina(1, "Infinity", "QX70")));

        serviceMasini.delete(1);
        assertEquals(serviceMasini.getAllEntities().size(),0);
    }

                                    /// Masina

    @Test
    private static void MasinaTest(){
        Masina masina = new Masina(1, "Ferrari", "Berlinetta");

        assertEquals(masina.getId(), 1);
        assertEquals(masina.getMarca(), "Ferrari");
        assertEquals(masina.getModel(), "Berlinetta");

        masina.setModel("412");
        masina.setMarca("Pista");
        assertEquals(masina.getModel(), "412");
        assertEquals(masina.getMarca(), "Pista");

        assertEquals(masina.toString(), "Masina: [ID=" + masina.getId() + ", Marca=" + masina.getMarca() + ", Model=" + masina.getModel() + "]");

        masina.setId(2);
        assertEquals(masina.getId(), 2);

    }

                        /// Inchiriere

    @Test
    private static void InchiriereTest(){
        Masina masina = new Masina(1, "Ferrari", "Berlinetta");
        Inchiriere inchiriere = new Inchiriere(1, masina, "01-05-2020", "01-07-2020");

        assertEquals(inchiriere.getId(), 1);
        assertEquals(inchiriere.getMasina().getId(), 1);
        assertEquals(inchiriere.getMasina().getMarca(), "Ferrari");
        assertEquals(inchiriere.getMasina().getModel(), "Berlinetta");
        assertEquals(inchiriere.getDataInceput(), "01-05-2020");
        assertEquals(inchiriere.getDataSfarsit(), "01-07-2020");

        inchiriere.setDataInceput("01-06-2020");
        inchiriere.setDataSfarsit("01-08-2020");

        assertEquals(inchiriere.toString(), "Inchiriere: [ID= " + inchiriere.getId() + ", Masina= " + inchiriere.getMasina() + ", DataInceput= " +inchiriere.getDataInceput() + ", DataSfarsit= " + inchiriere.getDataSfarsit());

        inchiriere.setMasina(new Masina(1, "Ferrari", "412"));
        assertEquals(inchiriere.getMasina().getModel(), "412");
    }

    static void deleteFileContent(String fileName){
        try (BufferedWriter bf = Files.newBufferedWriter(Path.of(fileName),
                StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void allTests() throws RepositoryException, IOException {
        CRUD_BinaryRepoMasina();
        CRUD_BinaryRepoInchiriere();
        CRUD_TextFileRepoMasina();
        CRUD_TextFileRepoInchiriereMasina();

        ServiceTest();

        MasinaTest();
        InchiriereTest();
    }
}
