module com.example.cpuemulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.xerial.sqlitejdbc;

    opens com.example.cpuemulator to javafx.fxml;
    exports com.example.cpuemulator;
}