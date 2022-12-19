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
    public TableColumn<Account, String> id;

    @FXML
    public TableColumn<Account, String> userName;

    @FXML
    public TableColumn<Account, String> firstName;

    @FXML
    public TableColumn<Account, String> lastName;

    @FXML
    public TableColumn<Account, String> email;

    @FXML
    public TableColumn<Account, String> phone;

    @FXML
    public TableColumn<Account, String> joinedAt;

    @FXML
    public TableColumn<Account, String> role;
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

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            joinedAt.setCellValueFactory(new PropertyValueFactory<>("joinedAt"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));

            try {
                users_table.setItems(DBUtils.getAccountInformation());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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


    }


    public void setUserInformation(String username){
        label_welcome.setText(username + " Hub");
    }
}
