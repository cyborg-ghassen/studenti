package com.code2bind.studenti;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PreloaderController implements Initializable {
    @FXML
    private ProgressBar pb_loader;
    public static ProgressBar progressBar;

    @FXML
    private Label progress;
    public static Label label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label = progress;
        progressBar = pb_loader;
    }
}
