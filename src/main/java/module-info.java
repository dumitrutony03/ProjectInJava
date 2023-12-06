module com.example.projectwithmaven {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires junit;
    requires org.xerial.sqlitejdbc;

    opens com.example.projectwithmaven to javafx.fxml;
    exports com.example.projectwithmaven;
}