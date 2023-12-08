package com.example.projectwithmaven.UserInterface;

import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Service.Service;

import java.util.*;

public class UI {

    Service<Masina> serviceMasini;
    Service<Inchiriere> serviceInchirieri;

    public UI(Service<Masina> serviceMasini, Service<Inchiriere> serviceInchirieri) {
        this.serviceMasini = serviceMasini;
        this.serviceInchirieri = serviceInchirieri;
    }

    private void startWith100Entities() throws RepositoryException {
        // Adăugăm 5 mașini si 5 inchirieri
        List<Integer> idUriMasina = Arrays.asList(0, 1, 2, 3, 4);
        List<String> marciMasina = Arrays.asList("Dacia", "Ford", "BMW", "Audi", "Mercedes");

        List<String> Dacia = Arrays.asList("Sandero", "Logan");
        List<String> Ford = Arrays.asList("Mondeo", "Mustang");
        List<String> BMW = Arrays.asList("Seria 1", "Seria 3");
        List<String> Audi = Arrays.asList("A3", "A4");
        List<String> Mercedes = Arrays.asList("C-Klasse", "CLK");

        Random numarRandom = new Random();
        int nrMasiniDisponibile = 5;
        for (int i = 0; i < 100; i++) {
            int valuareRandom = numarRandom.nextInt(nrMasiniDisponibile); // Numere de la 0-4

            int idMasina = idUriMasina.get(valuareRandom);
            String marcaMasina = marciMasina.get(idMasina);

            int nrModelePerMasiniDisponibile = 2;
            int modelMasinaAles = new Random().nextInt(nrModelePerMasiniDisponibile);
            String modelMasina;
            Masina masina;

            switch (marcaMasina) {
                case "Dacia":
                    modelMasina = Dacia.get(modelMasinaAles);
                    masina = new Masina(i, marcaMasina, modelMasina);
                    creeazaObiectInchirierePentru100DeEntitati(i, masina);

//                    System.out.println(new Masina(i, marcaMasina, modelMasina));
                    serviceMasini.add(new Masina(i, marcaMasina, modelMasina));
                    break;
                case "Ford":
                    modelMasina = Ford.get(modelMasinaAles);
                    masina = new Masina(i, marcaMasina, modelMasina);
                    creeazaObiectInchirierePentru100DeEntitati(i, masina);

//                    System.out.println(new Masina(i, marcaMasina, modelMasina));
                    serviceMasini.add(new Masina(i, marcaMasina, modelMasina));
                    break;
                case "BMW":
                    modelMasina = BMW.get(modelMasinaAles);
                    masina = new Masina(i, marcaMasina, modelMasina);
                    creeazaObiectInchirierePentru100DeEntitati(i, masina);

//                    System.out.println(new Masina(i, marcaMasina, modelMasina));
                    serviceMasini.add(new Masina(i, marcaMasina, modelMasina));
                    break;
                case "Audi":
                    modelMasina = Audi.get(modelMasinaAles);
                    masina = new Masina(i, marcaMasina, modelMasina);
                    creeazaObiectInchirierePentru100DeEntitati(i, masina);

//                    System.out.println(new Masina(i, marcaMasina, modelMasina));
                    serviceMasini.add(new Masina(i, marcaMasina, modelMasina));
                    break;
                case "Mercedes":
                    modelMasina = Mercedes.get(modelMasinaAles);
                    masina = new Masina(i, marcaMasina, modelMasina);
                    creeazaObiectInchirierePentru100DeEntitati(i, masina);

//                    System.out.println(new Masina(i, marcaMasina, modelMasina));
                    serviceMasini.add(new Masina(i, marcaMasina, modelMasina));
                    break;
            }
        }

//        serviceMasini.add(new Masina(1, "Dacia", "Logan"));
//        serviceMasini.add(new Masina(2, "Ford", "Focus"));
//        serviceMasini.add(new Masina(3, "BMW", "X5"));
//        serviceMasini.add(new Masina(4, "Audi", "A4"));
//        serviceMasini.add(new Masina(5, "Mercedes", "C-Class"));
//        // TODO Sa adaugam aceeasi masina la inchiriat, daca diferita data de inceput si cea de sfarsit
//
        // TODO De incercat sa nu adaugam o dataInceput > dataFinal, sau ceva caractere care nu s cifre
//        serviceInchirieri.add(new Inchiriere(1, serviceMasini.findById(1), "10-01-2023", "10-01-2023"));
//        serviceInchirieri.add(new Inchiriere(2, serviceMasini.findById(2), "11-01-2023", "20-01-2023"));
//        serviceInchirieri.add(new Inchiriere(3, serviceMasini.findById(3), "01-02-2023", "10-02-2023"));
//        serviceInchirieri.add(new Inchiriere(4, serviceMasini.findById(4), "21-01-2023", "30-01-2023"));
//        serviceInchirieri.add(new Inchiriere(5, serviceMasini.findById(5), "11-02-2023", "20-02-2023"));
    }

    private void creeazaObiectInchirierePentru100DeEntitati(int i, Masina masina) throws RepositoryException {
        int idInchiriere = i;
        String dataInceput, dataSfarsit;

        int zi_dataInceput = new Random().nextInt(1,32);
        int saptamana_dataInceput = new Random().nextInt(1,13);
        int an_dataInceput = new Random().nextInt(2000,2024);

        // Data de sfarsit, trebuie sa fie cel putin mai mare sau egala cu o zi fata de data de sfarsit.
        int zi_dataSfarsit = new Random().nextInt(zi_dataInceput,32);
        int saptamana_dataSfarsit = new Random().nextInt(saptamana_dataInceput,13);
        int an_dataSfarsit = new Random().nextInt(an_dataInceput,2024);

        dataInceput = zi_dataInceput + "-" + saptamana_dataInceput + "-" + an_dataInceput;
        dataSfarsit = zi_dataSfarsit + "-" + saptamana_dataSfarsit + "-" + an_dataSfarsit;

//        System.out.println(new Inchiriere(idInchiriere, masina, dataInceput, dataSfarsit));
        serviceInchirieri.add(new Inchiriere(idInchiriere, masina, dataInceput, dataSfarsit));
    }
    // Done
    public void addMasina() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("id de tip INTEGER");
        Integer id = 0;

        try {
            id = Integer.valueOf(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Eroare, nu se poate parsa in INTEGER acest input.");
            return;
        }

        System.out.println("marca de tip String");
        String marca = scanner.nextLine();

        System.out.println("model de tip String");
        String model = scanner.nextLine();

        Masina masina = new Masina(id, marca, model);
        serviceMasini.add(masina);
    }

    // Done
    private void afisareMasini() {
        List<Masina> listaMasini = serviceMasini.getAllEntities();

        System.out.println("Cate masini avem in lista: " + listaMasini.size());

        for (Masina m1 : listaMasini)
            System.out.println(m1.toString());
    }

    // Done
    private void deleteMasina() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti un id (Numar intreg)");

        Integer id = Integer.valueOf(scanner.nextLine());
        try {
            serviceMasini.delete(id);

            List<Inchiriere> listMasiniInchiriate = serviceInchirieri.getAllEntities();

            // Daca stergem masina, automat trebuie sa o stergem si din lista unde e inchiriata
            for (int i = 0; i < listMasiniInchiriate.size(); i++) {
                if (listMasiniInchiriate.get(i).getMasina().getId() == id) {

                    int idInchiriere = listMasiniInchiriate.get(i).getId();
                    serviceInchirieri.delete(idInchiriere);
                    // Resetam i-ul, pentru ca avem probleme cand stergem o masina, dimensiunea Listei scade cu '1' (o unitate)
                    // Si se pot suprapune, si apoi 'i-ul' sa treaca peste lungimea sirului,
                    // sau exista sansa sa rateze Masini cu ID-ul care trebuie sters
                    i = 0;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    // Done
    private void updateMasina() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("id de tip INTEGER");
        Integer id = 0;

        try {
            id = Integer.valueOf(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Eroare, nu se poate parsa in INTEGER acest input.");
        }

        System.out.println("marca de tip String");
        String marca = scanner.nextLine();

        System.out.println("model de tip String");
        String model = scanner.nextLine();

        Masina masina = new Masina(id, marca, model);

        try {
            serviceMasini.update(masina);

            List<Inchiriere> listMasiniInchiriate = serviceInchirieri.getAllEntities();

            // Daca UPDATAM masina, automat trebuie sa o UPDATAM si din lista unde e inchiriata
            for (int i = 0; i < listMasiniInchiriate.size(); i++) {
                if (listMasiniInchiriate.get(i).getMasina().getId() == id) {

                    int idInchiriere = listMasiniInchiriate.get(i).getId();
                    String dataInceput = listMasiniInchiriate.get(i).getDataInceput();
                    String dataSfarsit = listMasiniInchiriate.get(i).getDataSfarsit();

                    Inchiriere inchiriere = new Inchiriere(idInchiriere, masina, dataInceput, dataSfarsit);
                    serviceInchirieri.update(inchiriere);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    // FIXME
    //  Daca facem UPDATE sau DELETE unei masini, automat, ea va trebui sa fie afectata si in lista cu masini (deja) INCHIRIATE

    // Done
    public void addInchiriere() throws RepositoryException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("ID-ul masinii pe care vrem sa o inchiriem");

        // Luam ID ul masinii pe care vrem sa o inchiriem
        Integer id = 0;
        try {
            id = Integer.valueOf(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Eroare, nu se poate parsa in INTEGER acest input.");
            return;
        }

//      Sa existe deja adaugata aceasta masina
        Masina masina = serviceMasini.findById(id);
        if (masina == null) {
            System.out.println("Nu exista aceasta masina");
            return;
        }

        // TODO: Mereu dataSfarsit sa fie >= dataInceput
        System.out.println("Date format * dd-mm-yyyy *");
        System.out.println("Data inceput:");
        String dataInceput = scanner.nextLine();
        System.out.println("Data sfarsit:");
        String dataSfarsit = scanner.nextLine();


        List<Inchiriere> listaInchirieri = serviceInchirieri.getAllEntities();
        int idxUltimaInchiriere = listaInchirieri.size();

        int ultimaInchiriereID = 1;
        if (idxUltimaInchiriere != 0) {
            ultimaInchiriereID = listaInchirieri.get(idxUltimaInchiriere - 1).getId();
            ultimaInchiriereID++;
        }

        serviceInchirieri.add(new Inchiriere(ultimaInchiriereID, masina, dataInceput, dataSfarsit));
    }

    // Done
    private void afisareMasiniInchiriate() {
        for (Inchiriere i1 : serviceInchirieri.getAllEntities())
            System.out.println(i1.toString());
    }

    // Done
    private void deleteMasinaInchiriata() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti un id (Numar intreg)");

        Integer id = Integer.valueOf(scanner.nextLine());
        try {
            serviceInchirieri.delete(id);
        } catch (RepositoryException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Done
    private void updateMasinaInchiriata() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("id de tip INTEGER, al masinii inchiriate, pe care vrem sa o modificam, cu alta masina," +
                "sau sa o lasa NULL");

        // Luam ID ul inchirierii pe care vrem sa o updatam
        Integer id = 0;
        try {
            id = Integer.valueOf(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Eroare, nu se poate parsa in INTEGER acest input.");
            return;
        }
        /// Verificam daca avem acest id de inchiriere
        int existaInchirere = serviceInchirieri.returnPosition(id);
        if (existaInchirere == -1) {
            System.out.println("N avem inchirierea asta adaugata inca");
            return;
        }

        System.out.println("ID ul masinii pe care vrem sa o adaugam la inchiriere," +
                "in cazul in care, clientul doreste alta masina");
        Integer idMasina = 0;
        try {
            idMasina = Integer.valueOf(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Eroare, nu se poate parsa in INTEGER acest input.");
            return;
        }
        // Luam ID ul masini inchiriate pe care vrem sa o updatam
        Masina masina = serviceMasini.findById(idMasina);
        if (masina == null) {
            System.out.println("Nu exista aceasta masina");
            return;
        }

        // TODO: Mereu dataSfarsit sa fie >= dataInceput
        System.out.println("Date format * dd-mm-yyyy *");
        System.out.println("Data inceput:");
        String dataInceput = scanner.nextLine();
        System.out.println("Data sfarsit:");
        String dataSfarsit = scanner.nextLine();

        serviceInchirieri.update(new Inchiriere(id, masina, dataInceput, dataSfarsit));

    }

    public void menuChoices() {
        // ToDo stergere masina, update
        System.out.println("1. Adauga o masina");
        System.out.println("2. Afisare masini adaugate");
        System.out.println("3. Stergere masina");
        System.out.println("4. Update masina");
        System.out.println("5. Adauga o inchiriere");
        System.out.println("6. Afisare masini inchiriate");
        System.out.println("7. Stergere masina inchiriata");
        System.out.println("8. Update masina inchiriata");
    }

    public void start() throws RepositoryException {
        startWith100Entities();

        int choice = 0;
        while (true) {

            menuChoices();

            Scanner scanner = new Scanner(System.in);
            String readLine = scanner.nextLine();
            try {
                choice = Integer.parseInt(readLine);
            } catch (NumberFormatException e) {
                System.err.println("Introduceti va rog un numar valid");
            }
            switch (choice) {
                case 1: {
                    addMasina();
                    break;
                }
                case 2: {
                    afisareMasini();
                    break;
                }
                case 3: {
                    deleteMasina();
                    break;
                }
                case 4: {
                    updateMasina();
                    break;
                }
                case 5: {
                    addInchiriere();
                    break;
                }
                case 6: {
                    afisareMasiniInchiriate();
                    break;
                }
                case 7: {
                    deleteMasinaInchiriata();
                    break;
                }
                case 8: {
                    updateMasinaInchiriata();
                    break;
                }
            }
        }
    }
}
