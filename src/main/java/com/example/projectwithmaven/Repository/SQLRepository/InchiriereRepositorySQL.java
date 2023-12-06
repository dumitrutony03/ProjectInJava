package com.example.projectwithmaven.Repository.SQLRepository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Domain.Inchiriere;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Iterator;

public class InchiriereRepositorySQL<T extends Entitate> extends GenericRepository<Inchiriere> implements AutoCloseable {
    /**
     * Repository pentru entitatea Domain.Masina folosind SQL
     */
    private static final String JDBC_URL =
            "jdbc:sqlite:MasiniInchiriate.db";  // Protocol:sqlite:loculUndeAvemDB

    private Connection conn = null;

    public InchiriereRepositorySQL() {
        // 1. Stabilit conexiunea cu baza de data
        openConnection();
//        // 2. Daca nu exista tabela Domain.Masina, o creeam
//        // 'CREATE IF NOT EXISTS ...'
        createSchema();
//
//        // 3. Incarca obiectele din baza de date (citeste tabela Domain.Masina)
//        // 'SELECT * FROM Domain.Masina'
        getAll();

        // de parcurs ResultSet si adaugat in data

    }

    public void add(Inchiriere inchiriere) {
        // 1. Adaugat in baza de date
        // PreparedStatement (impotriva SQL Injection)
        // 'INSERT INTO Domain.Masina VALUES (?,?,?,...)'
        // 2. Daca nu avem exceptii, adaugat in data

        System.out.println("Adaugam o inchiriere in BAZA DE DATE");


        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Inchiriere VALUES (?, ?, ?, ?, ?, ?)")) {
                statement.setInt(1, inchiriere.getId());
                statement.setInt(2, inchiriere.getMasina().getId());
                statement.setString(3, inchiriere.getMasina().getMarca());
                statement.setString(4, inchiriere.getMasina().getModel());
                statement.setString(5, inchiriere.getDataInceput());
                statement.setString(6, inchiriere.getDataSfarsit());
                statement.executeUpdate();

                entitati.add(inchiriere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int ID) throws RepositoryException {
        // 2. Sters din baza de data
        // 'DELETE Domain.Masina WHERE ...'

        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Inchiriere WHERE idInchiriere =" + ID)) {
                statement.executeUpdate();
                super.delete(ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        // inchidem conexiunea spre baza de date
    }

    @Override
    public Iterator iterator() {
        return entitati.iterator();
    }

    private void openConnection() {
        try {
            // with DriverManager
//            if (conn == null || conn.isClosed())
//                conn = DriverManager.getConnection(JDBC_URL);

            System.out.println(
                    "Conexiune cu Baza de date pentru Domain.Inchiriere Masini"
            );

            // with DataSource
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Inchiriere(idInchiriere int PRIMARY KEY, idMasina int, marca varchar(200), model varchar(200), dataInceput varchar(200), dataSfarsit varchar(200));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    @Override
    public void update(Inchiriere inchiriere) throws RepositoryException {
        System.out.println("Suntem pe UPDATE la clasa Inchiriere");

        try {
            String updateSql = "UPDATE Inchiriere SET idMasina = ?, marca = ?, model = ?, dataInceput = ?, dataSfarsit = ? WHERE idInchiriere = ?";

            try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
                statement.setInt(1, inchiriere.getMasina().getId());
                statement.setString(2, inchiriere.getMasina().getMarca());
                statement.setString(3, inchiriere.getMasina().getModel());
                statement.setString(4, inchiriere.getDataInceput());
                statement.setString(5, inchiriere.getDataSfarsit());
                statement.setInt(6, inchiriere.getId());

                // Step 4: Execute the update statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Inchiriere updated successfully.");
                } else {
                    System.out.println("Inchiriere with ID " + inchiriere.getId() + " not found.");
                }
                super.update(inchiriere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAll() {
        System.out.println(
                "Luam inchirierile din baza de date"
        );
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from Inchiriere"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Inchiriere inchiriere = new Inchiriere(rs.getInt("idInchiriere"), new Masina(rs.getInt("idMasina"), rs.getString("marca"), rs.getString("model")), rs.getString("dataInceput"), rs.getString("dataSfarsit"));
                    entitati.add(inchiriere);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

