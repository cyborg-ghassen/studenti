package com.code2bind.studenti;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MyPreloader extends Preloader {
    private static final double WIDTH = 400;
    private static final double HEIGHT = 400;

    private Stage preloaderStage;
    private Scene scene;

    private Label progress;

    public MyPreloader() {
        // Constructor is called before everything.
        System.out.println(Studenti.STEP() + "MyPreloader constructor called, thread: " + Thread.currentThread().getName());
    }

    @Override
    public void init() throws Exception {
        System.out.println(Studenti.STEP() + "MyPreloader#init (could be used to initialize preloader view), thread: " + Thread.currentThread().getName());

        // If preloader has complex UI it's initialization can be done in MyPreloader#init
        Platform.runLater(() -> {
            Label title = new Label("Preparing application and database!\nLoading, please wait...");
            title.setTextAlignment(TextAlignment.CENTER);
            progress = new Label("0%");

            VBox root = new VBox(title, progress);
            root.setAlignment(Pos.CENTER);

            scene = new Scene(root, WIDTH, HEIGHT);
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(Studenti.STEP() + "MyPreloader#start (showing preloader stage), thread: " + Thread.currentThread().getName());

        this.preloaderStage = primaryStage;

        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.show();
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
