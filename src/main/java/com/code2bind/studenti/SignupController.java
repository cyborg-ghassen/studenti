package com.code2bind.studenti;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField tf_password;

    @FXML private ComboBox<String> cb_role;

    @FXML
    private Button button_sign_up;

    @FXML
    private Button button_log_in;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cb_role.setItems(DBUtils.getGroups());
            cb_role.getItems().remove("admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        button_sign_up.setOnAction(event -> {
            if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !cb_role.getSelectionModel().isEmpty()) {
                try {
                    DBUtils.signUpUser(event, tf_username.getText(), tf_email.getText(), tf_password.getText(), cb_role.getSelectionModel().getSelectedItem());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Please fill in all information.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information.");
                alert.show();
            }
        });

        button_log_in.setOnAction(event -> DBUtils.changeScene(event, "login.fxml", "Log In", null));
    }
}
