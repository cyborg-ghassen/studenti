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

public class PreloaderController extends Preloader {

    @FXML
    private Label label_status;

    @FXML
    private ProgressBar pb_loader;

    private Stage preloaderStage;

    @FXML
    private Label progress;


    public PreloaderController() {
        // Constructor is called before everything.
        System.out.println(Studenti.STEP() + "MyPreloader constructor called, thread: " + Thread.currentThread().getName());
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(Studenti.STEP() + "MyPreloader#start (showing preloader stage), thread: " + Thread.currentThread().getName());

        Parent root = FXMLLoader.load(getClass().getResource("preloader.fxml"));
        this.preloaderStage = stage;
        preloaderStage.setResizable(false);
        preloaderStage.setIconified(false);
        preloaderStage.setScene(new Scene(root, 1280, 400));
        preloaderStage.show();
    }

    @Override
    public void init() throws Exception {
        System.out.println(Studenti.STEP() + "MyPreloader#init (could be used to initialize preloader view), thread: " + Thread.currentThread().getName());

        // If preloader has complex UI it's initialization can be done in MyPreloader#init
        Platform.runLater(() -> {
            label_status.setText("Preparing application and database! Loading, please wait...");
            progress.setText("0%");
            pb_loader.setProgress(0);
        });
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        // Handle application notification in this point (see MyApplication#init).
        if (info instanceof ProgressNotification) {
            progress.setText(((ProgressNotification) info).getProgress() + "%");
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
