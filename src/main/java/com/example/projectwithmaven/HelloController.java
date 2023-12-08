package com.example.projectwithmaven;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HelloController {

    private Service<Masina> serviceMasini;
    private Service<Inchiriere> serviceInchirieri;
    @FXML
    public ListView<String> carListView;

    public HelloController(Service<Masina> serviceMasini, Service<Inchiriere> serviceInchirieri) {
        this.serviceMasini = serviceMasini;
        this.serviceInchirieri = serviceInchirieri;
    }

    protected void afisareMasiniPrezenteInSQLite() {

        // Done: Afisam masinile sortate crescator dupa ID-ul fiecareia

        ObservableList<String> carNames = FXCollections.observableArrayList();

        List<Masina> listaMasini = serviceMasini.getAllEntities().stream()
                .sorted(Comparator.comparing(Entitate::getId)).toList();

        for (Masina masina : listaMasini) {
            carNames.add("Masina cu ID-ul: " + masina.getId() + ", Marca: " + masina.getMarca() + ", Model: " + masina.getModel());
//            System.out.println(masina);
        }
        carListView.setItems(carNames);
    }

    @FXML
    private TextField ID;

    @FXML
    private TextField Marca;

    @FXML
    private TextField Model;

    // FIXME Add-ul si Update-ul merg pe aceleasi TextField-uri: ID, Marca si Model.
    @FXML
    private void onAddCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("AddCar button clicked!");

        int id = Integer.parseInt(ID.getText());
        String marca = Marca.getText();
        String model = Model.getText();

        Masina masina = new Masina(id, marca, model);

        // TODO Daca nu exista, aruncam o Alerta
        serviceMasini.add(masina);
        afisareMasiniPrezenteInSQLite();
    }

    @FXML
    private void onUpdateCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("UpdateCar button clicked!");

        int id = Integer.parseInt(ID.getText());
        String marca = Marca.getText();
        String model = Model.getText();

        Masina masina = new Masina(id, marca, model);

        // TODO Daca nu exista, aruncam o Alerta
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

        afisareMasiniPrezenteInSQLite();
        afisareMasiniInchiriateInSQLite();
    }

    @FXML
    private TextField ID_Delete;

    @FXML
    private void onDeleteCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("DeleteCar button clicked!");

        int id = Integer.parseInt(ID_Delete.getText());

        // TODO Daca nu exista, aruncam o Alerta
        serviceMasini.delete(id);

        // Delete in Cascada si pentru Inchirierile ale caror Masini au fost STERSE
        int contor = 0;
        while (!serviceInchirieri.getAllEntities().isEmpty()) {
            if (serviceInchirieri.getAllEntities().get(contor).getMasina().getId() == id) {
                int idInchiriere = serviceInchirieri.getAllEntities().get(contor).getId(); // Luam ID-ul inchirierii unde se regaseste Masina pe care am sters-o
                System.out.println("Inainte de stergerea inchirierii: " + serviceInchirieri.getAllEntities().size());

                serviceInchirieri.delete(idInchiriere);
                contor = 0;

                System.out.println("Dupa stergerea inchirierii: " + serviceInchirieri.getAllEntities().size());
            } else {
                contor++;
            }
        }

        afisareMasiniPrezenteInSQLite();
        afisareMasiniInchiriateInSQLite();
    }

    // Preparing the GUI for rented cars
    @FXML
    public ListView<String> rentedCarsListView;

    protected void afisareMasiniInchiriateInSQLite() {

        // Done: Afisam masinile sortate crescator dupa ID-ul fiecareia

        ObservableList<String> rentedCarsNames = FXCollections.observableArrayList();

        List<Inchiriere> listaMasiniInchiriate = serviceInchirieri.getAllEntities().stream().
                sorted(Comparator.comparing(Entitate::getId)).toList();

        System.out.println("Masini inchiriate");
        for (Inchiriere inchiriere : listaMasiniInchiriate) {
            rentedCarsNames.add("Inchirierea cu ID-ul: " + inchiriere.getId() + ", " +
                    "Masina cu ID-ul: " + inchiriere.getMasina().getId() + ", " +
                    "Marca: " + inchiriere.getMasina().getMarca() + ", " +
                    "Model: " + inchiriere.getMasina().getModel() + ", " +
                    "Data Inceput: " + inchiriere.getDataInceput() + ", " +
                    "Data Sfarsit: " + inchiriere.getDataSfarsit());

//            System.out.println(inchiriere);
        }
        rentedCarsListView.setItems(rentedCarsNames);
    }

    @FXML
    private TextField ID_Inchiriere;
    @FXML
    private TextField ID_Masina;

    @FXML
    private TextField Data_Inceput;

    @FXML
    private TextField Data_Sfarsit;

    @FXML
    private void onAddRentedCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("AddRentedCar button clicked!");


        int id_Inchiriere = Integer.parseInt(ID_Inchiriere.getText());
        int id_Masina = Integer.parseInt(ID_Masina.getText());
        Masina masina = serviceMasini.findById(id_Masina);
        String data_inceput = Data_Inceput.getText();
        String data_sfarsit = Data_Sfarsit.getText();

        Inchiriere inchiriere = new Inchiriere(id_Inchiriere, masina, data_inceput, data_sfarsit);

        // TODO Daca nu exista, aruncam o Alerta
        serviceInchirieri.add(inchiriere);
        System.out.println("Am reusit sa adaugam masina la INCHIRIERE");

        afisareMasiniInchiriateInSQLite();
    }

    @FXML
    private TextField ID_InchiriereUpdate;
    @FXML
    private TextField ID_MasinaUpdate;

    @FXML
    private TextField Data_InceputUpdate;

    @FXML
    private TextField Data_SfarsitUpdate;


    @FXML
    private void onUpdateRentedCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("UpdateRentedCar button clicked!");

        int id_Inchiriere = Integer.parseInt(ID_InchiriereUpdate.getText());
        int id_Masina = Integer.parseInt(ID_MasinaUpdate.getText());
        Masina masina = serviceMasini.findById(id_Masina);
        String data_inceput = Data_InceputUpdate.getText();
        String data_sfarsit = Data_SfarsitUpdate.getText();

        Inchiriere inchiriere = new Inchiriere(id_Inchiriere, masina, data_inceput, data_sfarsit);

        // TODO Daca nu exista, aruncam o Alerta
        serviceInchirieri.update(inchiriere);
        afisareMasiniInchiriateInSQLite();
    }

    @FXML
    private TextField ID_InchiriereDelete;

    @FXML
    private void onDeleteRentedCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("DeleteRentedCar button clicked!");

        int id_Inchiriere = Integer.parseInt(ID_InchiriereDelete.getText());

        // TODO Daca nu exista, aruncam o Alerta
        serviceInchirieri.delete(id_Inchiriere);
        afisareMasiniInchiriateInSQLite();
    }

    @FXML
    public ListView<String> mostRentedCarsListView;

    // Ideea e ca avem deja lista noastra de masini inchiriate, deci mai trebuie decat sa avem o lista separata cu toate masinile din aceasta lista,
    // Si pentru asta, folosim o structura in care Map-ăm Pair<Marca,Modelul> := Key-ul, masinii inchiriate, si de fiecare data, retinem de cate ori este prezenta
    // aceasta masina in lista noastra := Value
    @FXML
    private void onShowMostRentedCarsButtonClick() {
        System.out.println("onShowMostRentedCarsButtonClick");

        // (Marca, Model): nrAparitii
        Map<Pair<String, String>, Integer> nrAparitiiMasina = new HashMap<>();

        List<Inchiriere> listaMasiniInchiriate = serviceInchirieri.getAllEntities();

        for (Inchiriere inchiriere : listaMasiniInchiriate) {
            Masina masina = inchiriere.getMasina();

            // Luam Marca si Modelul fiecarei masini adaugate deja in lista noastra de inchirieri
            Pair<String, String> masinaInchiriata = new Pair<>(masina.getMarca(), masina.getModel());

            // Verificam daca masina din lista de inchirieri apare de mai multe ori in Map ul nostru
            if (containsKey(nrAparitiiMasina, masinaInchiriata)) {
                // Daca avem aceasta masina adaugata
                Integer value = nrAparitiiMasina.get(masinaInchiriata);
                nrAparitiiMasina.put(masinaInchiriata, value + 1);
            }
            else{
                // Daca masina pe care o avem la inchiriat, nu este inca adaugata in Map, o adaugam cu valoarea 1
                int value = 0;
                nrAparitiiMasina.put(masinaInchiriata, value + 1);
            }
        }

        // Formam o lista cu masinile inchiriate, sortate descrescator dupa numarul de inchirieri din lista
        List<Map.Entry<Pair<String, String>, Integer>> mostRentedCars = new ArrayList<>(nrAparitiiMasina.entrySet());

        mostRentedCars.sort(Map.Entry.<Pair<String, String>, Integer>comparingByValue().reversed());

        ObservableList<String> observabled_mostRentedCars = FXCollections.observableArrayList();

        // Iterate over the sorted list and print key-value pairs
        for (Map.Entry<Pair<String, String>, Integer> entry : mostRentedCars) {
            Pair<String, String> atributeMasina = entry.getKey();
            Integer nrAparitii = entry.getValue();
            observabled_mostRentedCars.add("Masina inchiriata: " + atributeMasina.getKey() + " " + atributeMasina.getValue() + ", cu numarul de inchirieri: " + nrAparitii);

            // Facem lista vizibila
            mostRentedCarsListView.setItems(observabled_mostRentedCars);
        }
    }

    // Verificam daca avem masina
    private boolean containsKey(Map<Pair<String, String>, Integer> map, Pair<String, String> key) {
        for (Pair<String, String> mapKey : map.keySet()) {
            if (mapKey.getKey().equals(key.getKey()) && mapKey.getValue().equals(key.getValue())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public ListView<String> mostRentedCarsPerMonthListView;

    // Ideea e ca avem deja lista noastra de masini inchiriate, deci mai trebuie decat sa avem o lista separata cu toate masinile din aceasta lista,
    // Si pentru asta, folosim o structura in care Map-ăm Pair<Luna,An> := Key-ul, masinii inchiriate, si de fiecare data, retinem cate masini sunt inchiriate
    // in aceasta luna := Value
    @FXML
    private void onShowNrOfRentedCarsPerMonthButtonClick() {

        // (Marca, Model): nrAparitii
        Map<Pair<String, String>, Integer> nrInchirieriMasiniPerLuna = new HashMap<>();

        List<Inchiriere> listaMasiniInchiriate = serviceInchirieri.getAllEntities();

        for (Inchiriere inchiriere : listaMasiniInchiriate) {

           // Ne intereseaza decat luna de inceput a inchirierii
           String[] elemente_dataInceput = inchiriere.getDataInceput().split("-");
           String luna = elemente_dataInceput[1];
           String an = elemente_dataInceput[2];

            // Luam luna si anul fiecarei masini inchiriate deja
            Pair<String, String> lunaAn = new Pair<>(luna, an);

            // Verificam daca masina din lista de inchirieri apare de mai multe ori in Map ul nostru
            if (containsKey(nrInchirieriMasiniPerLuna, lunaAn)) {
                // Daca avem aceasta masina adaugata
                Integer value = nrInchirieriMasiniPerLuna.get(lunaAn);
                nrInchirieriMasiniPerLuna.put(lunaAn, value + 1);
            }
            else{
                // Daca masina pe care o avem la inchiriat, nu este inca adaugata in Map, o adaugam cu valoarea 1
                int value = 0;
                nrInchirieriMasiniPerLuna.put(lunaAn, value + 1);
            }
        }

        // Formam o lista cu <luna, an>, sortate descrescator dupa numarul de inchirieri din lista
        List<Map.Entry<Pair<String, String>, Integer>> monthsWithNrOfCars = new ArrayList<>(nrInchirieriMasiniPerLuna.entrySet());

        monthsWithNrOfCars.sort(Map.Entry.<Pair<String, String>, Integer>comparingByValue().reversed());

        ObservableList<String> observabled_monthsWithNrOfCars = FXCollections.observableArrayList();

        // Iterate over the sorted list and print key-value pairs
        for (Map.Entry<Pair<String, String>, Integer> entry : monthsWithNrOfCars) {
            Pair<String, String> atributeMasina = entry.getKey();
            Integer nrAparitii = entry.getValue();
            observabled_monthsWithNrOfCars.add("Luna: " + atributeMasina.getKey() + ", anul: "  + " " + atributeMasina.getValue() + ", nr masini inchiriate: " + nrAparitii);

            // Facem lista vizibila
            mostRentedCarsPerMonthListView.setItems(observabled_monthsWithNrOfCars);
        }
    }

    @FXML
    public ListView<String> longestRentedCarsPerMonthListView;
    @FXML
    private void onShowLongestRentedCarsButtonClick() {
        List<Inchiriere> listaMasiniInchiriate = serviceInchirieri.getAllEntities();
        Map<Masina, Long> masinaPlusNrZileInchiriata = new HashMap<>();

        long nrZileMasinaInchiriata = 0;
        for (Inchiriere inchiriere : listaMasiniInchiriate) {
            Masina masina = inchiriere.getMasina();
            String dataInceput = inchiriere.getDataInceput();
            String dataSfarsit = inchiriere.getDataSfarsit();

            nrZileMasinaInchiriata = returneazaNrDeZile(dataInceput, dataSfarsit);

            masinaPlusNrZileInchiriata.put(masina,nrZileMasinaInchiriata);
        }

        // Formam o lista cu masinile inchiriate, sortate descrescator dupa numarul de inchirieri din lista
        List<Map.Entry<Masina, Long>> longestRentedCars = new ArrayList<>(masinaPlusNrZileInchiriata.entrySet());

        // Sortam descrescator lista dupa Values
        longestRentedCars.sort(Map.Entry.<Masina, Long>comparingByValue().reversed());

        ObservableList<String> observabled_mostRentedCars = FXCollections.observableArrayList();

        // Iterate over the sorted list and print key-value pairs
        for (Map.Entry<Masina, Long> entry : longestRentedCars) {
            Masina masina = entry.getKey();
            Long nrAparitii = entry.getValue();
//            System.out.println(masina + " " + nrAparitii);
            observabled_mostRentedCars.add("Masina inchiriata: " + masina.getId() + " " + masina.getMarca() + " " + masina.getModel() + ", cu numarul de zile inchiriata: " + nrAparitii);
//
//            // Facem lista vizibila
            longestRentedCarsPerMonthListView.setItems(observabled_mostRentedCars);
        }
    }
    private Long returneazaNrDeZile(String dataInceput,  String dataSfarsit){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parsarea string-urilor in obiecte LocalDate
        LocalDate date1 = LocalDate.parse(dataInceput, formatter);
        LocalDate date2 = LocalDate.parse(dataSfarsit, formatter);

        // Calcularea numărului de zile între cele două date
        long nrZile = Math.abs(date1.toEpochDay() - date2.toEpochDay());
        return nrZile;
    }
}
