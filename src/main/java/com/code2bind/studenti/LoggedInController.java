package com.code2bind.studenti;

import com.code2bind.studenti.account.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private TableView<Account> users_table;

    @FXML
    public TableColumn<Account, Integer> account_id;

    @FXML
    public TableColumn<Account, String> account_username;

    @FXML
    public TableColumn<Account, String> account_first_name;

    @FXML
    public TableColumn<Account, String> account_last_name;

    @FXML
    public TableColumn<Account, String> account_email;

    @FXML
    public TableColumn<Account, Integer> account_phone;

    @FXML
    public TableColumn<Account, String> account_joined;

    @FXML
    public TableColumn<Account, String> account_role;
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

    public LoggedInController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account_pane.setVisible(false);
        class_pane.setVisible(false);
        material_pane.setVisible(false);
        absence_pane.setVisible(false);
        dashboard_pane.setVisible(true);
        button_dashboard.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");

        button_dashboard.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(true);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            button_dashboard.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
        });

        button_account.setOnAction(event -> {
            account_pane.setVisible(true);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            button_account.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
        });

        button_absence.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(true);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            button_absence.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
        });

        button_class.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(true);
            button_class.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
        });

        button_material.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(true);
            class_pane.setVisible(false);
            button_material.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
        });

        button_logout.setOnAction(event -> {
            MenuItem m = (MenuItem) event.getSource();
            while (m.getParentPopup() == null) {
                m = m.getParentMenu();
            }
            Scene s = m.getParentPopup().getOwnerWindow().getScene();
            DBUtils.changeScene(s, "login.fxml", "Login", null);
        });

        account_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        account_username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        account_first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        account_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        account_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        account_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        account_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        account_joined.setCellValueFactory(new PropertyValueFactory<>("joinedAt"));
    }


    public void setUserInformation(String username){
        label_welcome.setText(username + " Hub");
    }
}
