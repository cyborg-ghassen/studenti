module com.code2bind.studenti {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    requires org.jetbrains.annotations;
    requires itextpdf;
    requires java.mail;

    opens com.code2bind.studenti to javafx.fxml;
    exports com.code2bind.studenti;
    exports com.code2bind.studenti.managers;
    opens com.code2bind.studenti.managers to javafx.fxml;
    exports com.code2bind.studenti.auth;
    opens com.code2bind.studenti.auth to javafx.fxml;
    exports com.code2bind.studenti.exceptions;
    opens com.code2bind.studenti.exceptions to javafx.fxml;
    exports com.code2bind.studenti.account;
    opens com.code2bind.studenti.account to javafx.fxml;
    exports com.code2bind.studenti.student;
    opens com.code2bind.studenti.student to javafx.fxml;
}