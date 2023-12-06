package com.example.projectwithmaven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\tonyc\\Downloads\\Anul2\\MAP\\Laborator\\a3-dumitrutony03_Tema\\Lab3_Tema\\Lab3_Tema\\settings.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRepositoryType() {
        return properties.getProperty("Repository");
    }
}
