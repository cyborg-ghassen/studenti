module com.code2bind.studenti {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.code2bind.studenti to javafx.fxml;
    exports com.code2bind.studenti;
}