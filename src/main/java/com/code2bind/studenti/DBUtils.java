package com.code2bind.studenti;

import com.code2bind.studenti.account.User;
import com.code2bind.studenti.account.UserEmail;
import com.code2bind.studenti.account.UserGroups;
import com.code2bind.studenti.account.UserPermissions;
import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Group;
import com.code2bind.studenti.auth.GroupPermissions;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.student.Absence;
import com.code2bind.studenti.student.Class;
import com.code2bind.studenti.student.Correspondence;
import com.code2bind.studenti.student.Material;
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

public class DBUtils {
    static Database database;

    static {
        try {
            database = new Database();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        Parent root = null;
        if (username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root, 1280, 732));
        stage.show();
    }

    public static void changeScene(Scene scene, String fxmlFile, String title, String username){
        Parent root = null;
        if (username != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root, 1280, 732));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String email,  String password) throws SQLException {

        try (ResultSet set = database.SelectData("account_user", "*", "username='" + username + "'")) {
            if (set.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                Dictionary<String, String> dictionary = new Hashtable<>();
                dictionary.put("username", username);
                dictionary.put("email", email);
                dictionary.put("password", password);
                database.InsertData("account_user", dictionary);
                changeScene(event, "logged-in.fxml", "Welcome", username);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loginUser(ActionEvent event, String username, String password) throws SQLException {
        try (ResultSet set = database.SelectData("account_user", "*", "username='" + username + "'")) {
            if (!set.isBeforeFirst()) {
                System.out.println("user not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credential are incorrect.");
                alert.show();
            } else {
                while (set.next()) {
                    String retreivedPassword = set.getString("password");
                    String retreivedUsername = set.getString("username");
                    if (retreivedPassword.equals(password)) {
                        changeScene(event, "logged-in.fxml", "Welcome", retreivedUsername);
                    } else {
                        System.out.println("Password did not match.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password did not match.");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void prepareDB() throws SQLException {
        try{
            ContentType type = new ContentType();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            Permission permission = new Permission();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            Group group = new Group();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            GroupPermissions group = new GroupPermissions();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            User user = new User();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            UserGroups userGroups = new UserGroups();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            UserPermissions userPermissions = new UserPermissions();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            UserEmail userEmail = new UserEmail();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            Class aClass = new Class();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            Material material = new Material();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            Absence absence = new Absence();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            Correspondence correspondence = new Correspondence();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
