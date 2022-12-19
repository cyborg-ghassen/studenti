package com.code2bind.studenti;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class Studenti extends Application {

    // Just a counter to create some delay while showing preloader.
    private static final double COUNT_LIMIT = 8000;

    private static int stepCount = 1;

    // Used to demonstrate step counts.
    public static String STEP() {
        return stepCount++ + ". ";
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", MyPreloader.class.getCanonicalName());
        launch(args);
    }

    public Studenti() {
        // Constructor is called after BEFORE_LOAD.
        System.out.println(Studenti.STEP() + "Studenti constructor called, thread: " + Thread.currentThread().getName());
    }

    @Override
    public void init() throws Exception {
        System.out.println(Studenti.STEP() + "Studenti#init (doing some heavy lifting), thread: " + Thread.currentThread().getName());

        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        DBUtils.prepareDB();
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            notifyPreloader(new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(Studenti.STEP() + "Studenti#start (initialize and show primary application stage), thread: " + Thread.currentThread().getName());

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 1280, 732));
        primaryStage.show();
    }
}