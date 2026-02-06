module com.alessandro.caracciolo.catchit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.alessandro.caracciolo.catchit to javafx.fxml;
    exports com.alessandro.caracciolo.catchit;
}