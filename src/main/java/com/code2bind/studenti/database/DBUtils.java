package com.code2bind.studenti.database;

import com.code2bind.studenti.LoggedInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;
import com.code2bind.studenti.account.User.*;

import static com.code2bind.studenti.account.User.get_SHA_256_SecurePassword;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role){
        Parent root = null;
        if (username != null && role != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(role);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root, 1280, 832));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, String email) {
        ResultSet set;
        boolean UserExists;
        try{
            Database database = new Database();
            set = database.SelectData("account_user", "*", "username=" + username);
            if (set.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                Dictionary<String, String> dictionary = new Hashtable<>();
                dictionary.put("username", username);
                dictionary.put("password", get_SHA_256_SecurePassword(password));
                dictionary.put("email", email);
                database.InsertData("account_user", dictionary);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
