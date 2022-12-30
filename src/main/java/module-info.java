module com.example.fileprotection {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.controlsfx.controls;
    requires java.sql;
    requires java.mail;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires jbcrypt;
    requires org.apache.commons.io;

    opens com.example.fileprotection to javafx.fxml;
    opens com.example.fileprotection.Controllers to javafx.fxml;
    opens com.example.fileprotection.Entities to org.hibernate.orm.core;
    exports com.example.fileprotection;
}