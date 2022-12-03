module com.code2bind.studenti {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;

    opens com.code2bind.studenti to javafx.fxml;
    exports com.code2bind.studenti;
    exports com.code2bind.studenti.managers;
    opens com.code2bind.studenti.managers to javafx.fxml;
    exports com.code2bind.studenti.auth;
    opens com.code2bind.studenti.auth to javafx.fxml;
    exports com.code2bind.studenti.exceptions;
    opens com.code2bind.studenti.exceptions to javafx.fxml;
}