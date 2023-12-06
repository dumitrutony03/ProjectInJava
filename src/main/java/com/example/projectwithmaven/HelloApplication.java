package com.example.projectwithmaven;

import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.GenericRepository;
import com.example.projectwithmaven.Repository.SQLRepository.InchiriereRepositorySQL;
import com.example.projectwithmaven.Repository.SQLRepository.MasinaRepositorySQL;
import com.example.projectwithmaven.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


// Folosim acest Main, pentru GUI si aplicatia conectata la Baza de date
public class HelloApplication extends Application {
    private GenericRepository<Masina> masinaRepository = new MasinaRepositorySQL<>();
    private Service<Masina> serviceMasini = new Service<>(masinaRepository);
    private GenericRepository<Inchiriere> inchiriereRepository = new InchiriereRepositorySQL<>();
    private Service<Inchiriere> serviceInchirieri = new Service<>(inchiriereRepository);

    //  fx:controller="com.example.projectwithmaven.HelloController"
    @Override
    public void start(Stage stage) throws IOException {

        HelloController hc = new HelloController(serviceMasini, serviceInchirieri);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Ne setam propriul controller, ca fiind Constructorul clasei HelloController
        fxmlLoader.setController(hc);
        Scene scene = new Scene(fxmlLoader.load(), 640, 320);

        hc.afisareMasiniPrezenteInSQLite();
        hc.afisareMasiniInchiriateInSQLite();

        stage.setTitle("Cars!");

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}