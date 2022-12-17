package com.code2bind.studenti;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button button_log_in;

    @FXML
    private Button button_sign_up;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.loginUser(event, tf_username.getText(), tf_password.getText());
            }
        });

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "signup.fxml", "Sign Up", null, null);
            }
        });
    }
}
