package com.code2bind.studenti;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private Button button_dashboard;
    @FXML
    private Button button_account;
    @FXML
    private Button button_class;
    @FXML
    private Button button_material;
    @FXML
    private Button button_absence;
    @FXML
    private MenuItem button_logout;
    @FXML
    private MenuItem profile_link;
    @FXML
    private MenuButton menu_settings;
    @FXML
    private Label label_welcome;

    @FXML
    private Pane dashboard_pane;
    @FXML
    private Pane account_pane;
    @FXML
    private Pane class_pane;
    @FXML
    private Pane material_pane;
    @FXML
    private Pane absence_pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account_pane.setVisible(false);
        class_pane.setVisible(false);
        material_pane.setVisible(false);
        absence_pane.setVisible(false);
        dashboard_pane.setVisible(true);

        button_dashboard.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(true);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
        });

        button_account.setOnAction(event -> {
            account_pane.setVisible(true);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
        });

        button_absence.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(true);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
        });

        button_class.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(true);
        });

        button_material.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(true);
            class_pane.setVisible(false);
        });

        button_logout.setOnAction(event -> {
            MenuItem m = (MenuItem) event.getSource();
            while (m.getParentPopup() == null) {
                m = m.getParentMenu();
            }
            Scene s = m.getParentPopup().getOwnerWindow().getScene();
            DBUtils.changeScene(s, "login.fxml", "Login", null);
        });
    }

    public void setUserInformation(String username){
        label_welcome.setText(username + " Hub");
    }
}
