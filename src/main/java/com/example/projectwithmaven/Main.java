package com.example.projectwithmaven;

import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.BinaryRepository.BinaryFileRepositoryInchiriere;
import com.example.projectwithmaven.Repository.BinaryRepository.BinaryFileRepositoryMasina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;
import com.example.projectwithmaven.Repository.SQLRepository.InchiriereRepositorySQL;
import com.example.projectwithmaven.Repository.SQLRepository.MasinaRepositorySQL;
import com.example.projectwithmaven.Repository.TextFileRepository.TextFileRepositoryInchiriere;
import com.example.projectwithmaven.Repository.TextFileRepository.TextFileRepositoryMasina;
import com.example.projectwithmaven.Service.Service;
import com.example.projectwithmaven.Tests.Tests;
import com.example.projectwithmaven.UserInterface.UI;

import java.io.IOException;

public class Main  {
    public static void main(String[] args) throws RepositoryException, IOException {
        System.out.println("TESTE!");

        Tests.allTests();

        System.out.println("AM TRECUT TESTELE CU SUCCES");

        String repositoryType = Settings.getRepositoryType();

        Service<Masina> serviceMasini;
        Service<Inchiriere> serviceInchirieri;

        if ("binary".equals(repositoryType)) {
            GenericRepository<Masina> masinaRepository = new BinaryFileRepositoryMasina<>("Masini.bin");
            serviceMasini = new Service<>(masinaRepository);
            GenericRepository<Inchiriere> inchiriereRepository = new BinaryFileRepositoryInchiriere<>("MasiniInchiriate.bin");
            serviceInchirieri = new Service<>(inchiriereRepository);
        } else if ("text".equals(repositoryType)) {
            GenericRepository<Masina> masinaRepository = new TextFileRepositoryMasina<>("Masini.txt");
            serviceMasini = new Service<>(masinaRepository);
            GenericRepository<Inchiriere> inchiriereRepository = new TextFileRepositoryInchiriere<>("MasiniInchiriate.txt");
            serviceInchirieri = new Service<>(inchiriereRepository);

        } else if ("db".equals(repositoryType)) {
            System.out.println(
                    "Repo SQL"
            );
            GenericRepository<Masina> masinaRepository = new MasinaRepositorySQL<>();
            serviceMasini = new Service<>(masinaRepository);
            GenericRepository<Inchiriere> inchiriereRepository = new InchiriereRepositorySQL<>();
            serviceInchirieri = new Service<>(inchiriereRepository);

        }
//        else if ("json".equals(repositoryType)) {
//            Repository.GenericRepository<Domain.Masina> masinaRepository = new JsonFileRepository<>("Masini.json", Domain.Masina.class);
//            serviceMasini = new Service.Service<>(masinaRepository);
//            Repository.GenericRepository<Domain.Inchiriere> inchiriereRepository = new JsonFileRepository<>("MasiniInchiriate.json", Domain.Inchiriere.class);
//            serviceInchirieri = new Service.Service<>(inchiriereRepository);
//
//        }
        else {
            throw new IllegalArgumentException("Invalid repository type: " + repositoryType);
        }
        UI ui = new UI(serviceMasini, serviceInchirieri);
        ui.start();
    }
}