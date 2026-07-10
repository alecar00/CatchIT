module com.alessandro.caracciolo.catchit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;
    requires com.sothawo.mapjfx;
    requires com.google.gson;
    requires org.checkerframework.checker.qual;
    requires java.logging;
    requires java.net.http;
    opens com.alessandro.caracciolo.catchit to javafx.fxml;
    exports com.alessandro.caracciolo.catchit;
    exports com.alessandro.caracciolo.catchit.controller;
    exports com.alessandro.caracciolo.catchit.bean;
    exports com.alessandro.caracciolo.catchit.model;
    exports com.alessandro.caracciolo.catchit.exceptions;
    opens com.alessandro.caracciolo.catchit.view.controller to javafx.fxml;
    opens com.alessandro.caracciolo.catchit.model to com.google.gson;
}