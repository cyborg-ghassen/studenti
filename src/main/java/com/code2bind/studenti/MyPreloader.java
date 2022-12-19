package com.code2bind.studenti;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class MyPreloader extends Preloader {
    @FXML
    private Label label_status;

    @FXML
    private ProgressBar pb_loader;

    private Stage preloaderStage;
    private Scene scene;

    @FXML
    private Label progress;

    public MyPreloader(){

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.preloaderStage = stage;
        preloaderStage.setResizable(false);
        preloaderStage.setIconified(false);
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }

    @Override
    public void init() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("preloader.fxml")));
        scene = new Scene(root);
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            PreloaderController.label.setText(((ProgressNotification) info).getProgress() + "%");
            PreloaderController.progressBar.setProgress(((ProgressNotification) info).getProgress() / 100);
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        // Handle state change notifications.
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD ->
                // Called after MyPreloader#start is called.
                    System.out.println(Studenti.STEP() + "BEFORE_LOAD");
            case BEFORE_INIT ->
                // Called before MyApplication#init is called.
                    System.out.println(Studenti.STEP() + "BEFORE_INIT");
            case BEFORE_START -> {
                // Called after MyApplication#init and before MyApplication#start is called.
                System.out.println(Studenti.STEP() + "BEFORE_START");
                preloaderStage.hide();
            }
        }
    }
}
