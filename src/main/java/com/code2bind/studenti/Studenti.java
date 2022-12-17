package com.code2bind.studenti;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Studenti extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    // Just a counter to create some delay while showing preloader.
    private static final double COUNT_LIMIT = 10000;

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

        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            notifyPreloader(new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(Studenti.STEP() + "Studenti#start (initialize and show primary application stage), thread: " + Thread.currentThread().getName());

        Label title = new Label("This is your application!");
        title.setTextAlignment(TextAlignment.CENTER);

        VBox root = new VBox(title);
        root.setAlignment(Pos.CENTER);

        // Create scene and show application stage.
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}