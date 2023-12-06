package com.example.projectwithmaven.Repository.SQLRepository;

import com.example.projectwithmaven.Domain.Entitate;
import com.example.projectwithmaven.Domain.Masina;
import com.example.projectwithmaven.Repository.ExceptionRepository.RepositoryException;
import com.example.projectwithmaven.Repository.GenericRepository;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Iterator;

public class MasinaRepositorySQL<T extends Entitate> extends GenericRepository<Masina> implements AutoCloseable {
    /**
     * Repository pentru entitatea Domain.Masina folosind SQL
     */
    private static final String JDBC_URL =
            "jdbc:sqlite:Masini.db";  // Protocol:sqlite:loculUndeAvemDB

    private Connection conn = null;

    public MasinaRepositorySQL() {
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

    public void add(Masina masina) throws RepositoryException {
        // 1. Adaugat in baza de date
        // PreparedStatement (impotriva SQL Injection)
        // 'INSERT INTO Domain.Masina VALUES (?,?,?,...)'
        // 2. Daca nu avem exceptii, adaugat in data

        System.out.println("Adaugam o Domain.Masina");

        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Masina VALUES (?, ?, ?)")) {
                statement.setInt(1, masina.getId());
                statement.setString(2, masina.getMarca());
                statement.setString(3, masina.getModel());

                statement.executeUpdate();

                super.add(masina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int ID) throws RepositoryException {
        // 2. Sters din baza de data
        // 'DELETE Domain.Masina WHERE ...'

        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Masina WHERE id =" + ID)) {
                statement.executeUpdate();

                // FIXME - Trebuie stearsa masina cu Id-ul = ID, fara sa mai luam toate masinile din Baza de date mereu si mereu
                super.delete(ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Masina masina) throws RepositoryException {
        System.out.println("Suntem pe UPDATE la clasa Masina");

        try {
            String updateSql = "UPDATE Masina SET marca = ?, model = ? WHERE id = ?";

            try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
                statement.setString(1, masina.getMarca());
                statement.setString(2, masina.getModel());
                statement.setInt(3, masina.getId());

                // Step 4: Execute the update statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Masina updated successfully.");
                } else {
                    System.out.println("Masina with ID " + masina.getId() + " not found.");
                }
                super.update(masina);
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Masina(id int PRIMARY KEY, marca varchar(200), model varchar(200));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    private void getAll() {
        entitati.clear();

        System.out.println(
                "Luam masinile din baza de date"
        );
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from Masina");
                 ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Masina masina = new Masina(rs.getInt("id"), rs.getString("marca"), rs.getString("model"));
                    entitati.add(masina);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

