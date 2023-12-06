package com.example.projectwithmaven;

import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class HelloController {

    private Service<Masina> serviceMasini;
    private Service<Inchiriere> serviceInchirieri;
    @FXML
    public ListView<String> carListView;

    public HelloController(Service<Masina> serviceMasini, Service<Inchiriere> serviceInchirieri) {
        this.serviceMasini = serviceMasini;
        this.serviceInchirieri = serviceInchirieri;
    }

    protected void afisareMasiniPrezenteInSQLite(){

        // Done: Afisam masinile sortate crescator dupa ID-ul fiecareia

        ObservableList<String> carNames = FXCollections.observableArrayList();

        List<Masina> listaMasini = serviceMasini.getAllEntities().stream().sorted((p1, p2) -> (String.valueOf(p1.getId())).compareTo(String.valueOf(p2.getId()))).toList();

        for(Masina masina : listaMasini){
            carNames.add("Masina cu ID-ul: " + masina.getId() + ", Marca: " + masina.getMarca() + ", Model: " + masina.getModel());
            System.out.println(masina);
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
        System.out.println("AddCar button clicked!");

        int id = Integer.parseInt(ID.getText());
        String marca = Marca.getText();
        String model = Model.getText();

        Masina masina = new Masina(id, marca, model);

        // TODO Daca nu exista, aruncam o Alerta
        serviceMasini.update(masina);
        afisareMasiniPrezenteInSQLite();
    }

    @FXML
    private TextField ID_Delete;
    @FXML
    private void onDeleteCarButtonClick() throws RepositoryException {
        // Implement logic to add a new car, for example, show a dialog or navigate to another scene
        System.out.println("AddCar button clicked!");

        int id = Integer.parseInt(ID_Delete.getText());

        // TODO Daca nu exista, aruncam o Alerta
        serviceMasini.delete(id);
        afisareMasiniPrezenteInSQLite();
    }

    // Preparing the GUI for rented cars
    @FXML
    public ListView<String> rentedCarsListView;

    protected void afisareMasiniInchiriateInSQLite(){

        // Done: Afisam masinile sortate crescator dupa ID-ul fiecareia

        ObservableList<String> rentedCarsNames = FXCollections.observableArrayList();

        List<Inchiriere> listaMasini = serviceInchirieri.getAllEntities().stream().sorted((p1, p2) -> (String.valueOf(p1.getId())).compareTo(String.valueOf(p2.getId()))).toList();

        System.out.println("Masini inchiriate");
        for(Inchiriere inchiriere : listaMasini){
            rentedCarsNames.add("Inchirierea cu ID-ul: " + inchiriere.getId() + ", " +
                    "Masina cu ID-ul: " + inchiriere.getMasina().getId() + ", " +
                    "Marca: " + inchiriere.getMasina().getMarca() + ", " +
                    "Model: " + inchiriere.getMasina().getModel() + ", " +
                    "Data Inceput: " + inchiriere.getDataInceput() + ", " +
                    "Data Sfarsit: " + inchiriere.getDataSfarsit());

            System.out.println(inchiriere);
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
        System.out.println("Am reusit sa updatam");

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
        System.out.println("UpdateRentedCar button clicked!");

        int id_Inchiriere = Integer.parseInt(ID_InchiriereDelete.getText());

        // TODO Daca nu exista, aruncam o Alerta
        serviceInchirieri.delete(id_Inchiriere);
        afisareMasiniInchiriateInSQLite();
    }
}