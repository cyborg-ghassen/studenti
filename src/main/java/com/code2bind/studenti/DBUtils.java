package com.code2bind.studenti;

import com.code2bind.studenti.account.*;
import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Group;
import com.code2bind.studenti.auth.GroupPermissions;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.database.DatabaseConnection;
import com.code2bind.studenti.student.Absence;
import com.code2bind.studenti.student.Class;
import com.code2bind.studenti.student.Correspondence;
import com.code2bind.studenti.student.Material;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import static com.code2bind.studenti.account.User.make_password;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;
        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root, 1280, 732));
        stage.show();
    }

    public static void changeScene(Scene scene, String fxmlFile, String title, String username) {
        Parent root = null;
        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root, 1280, 732));
        stage.show();
    }

    public static String getUserFullName(String username){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement("select first_name, last_name" +
                        " from account_user where username=? limit 1");
                ) {
            preparedStatement.setString(1, username);
            ResultSet userInfo = preparedStatement.executeQuery();
            if (userInfo.next())
                return userInfo.getString("first_name") + " " + userInfo.getString("last_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static Image getUserAvatar(String username){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement("select avatar" +
                        " from account_user where username=? limit 1");
        ) {
            preparedStatement.setString(1, username);
            ResultSet userAvatar = preparedStatement.executeQuery();
            if (userAvatar.next()){
                byte[] avatar = userAvatar.getBytes("avatar");
                if (avatar != null)
                    return new Image(new ByteArrayInputStream(avatar));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String StudentsNumber(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select count(*) as st from account_user " +
                        "where class_id is not null");
                ){
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getString("st");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String TeachersNumber(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select count(*) as t from account_user " +
                        "where class_id is null");
        ){
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getString("t");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String ClassesNumber(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select count(*) as c from student_class");
        ){
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getString("c");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String MaterialsNumber(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select count(*) as m from student_material");
        ){
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getString("m");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static void signUpUser(ActionEvent event, String username, String email, String password, String role) throws SQLException {
        Database database = new Database();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (ResultSet set = database.SelectData("account_user", "*", "username='" + username + "'")) {
            if (set.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                try (
                        Connection connection = databaseConnection.connect();
                        ){
                    connection.setAutoCommit(false);
                    PreparedStatement statement = connection.prepareStatement("insert into " +
                            "account_user(username, email, password) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, username);
                    statement.setString(2, email);
                    statement.setString(3, make_password(password));
                    statement.executeUpdate();
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()){
                        try (
                                PreparedStatement preparedStatement = connection.prepareStatement("insert into " +
                                        "account_usergroups(user_id, group_id) select (select id from account_user" +
                                        " where username=?), (select id from auth_group where name=?)");
                        ){
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, role);
                            preparedStatement.execute();
                        }
                    }
                    connection.commit();
                }

                changeScene(event, "logged-in.fxml", "Welcome", username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            database.close();
        }
    }

    public static void loginUser(ActionEvent event, String username, String password) throws SQLException {
        Database database = new Database();
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
                    if (retreivedPassword.equals(make_password(password))) {
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
            e.printStackTrace();
        }
        finally {
            database.close();
        }
    }

    public static void prepareDB() throws SQLException {
        try {
            ContentType type = new ContentType();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Permission permission = new Permission();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Group group = new Group();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            GroupPermissions group = new GroupPermissions();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            User user = new User();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            UserGroups userGroups = new UserGroups();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            UserPermissions userPermissions = new UserPermissions();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            UserEmail userEmail = new UserEmail();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Class aClass = new Class();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Material material = new Material();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Absence absence = new Absence();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Correspondence correspondence = new Correspondence();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ObservableList<Account> getAccountInformation() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<Account> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select account_user.id, " +
                        "account_user.username, account_user.first_name, account_user.last_name, account_user.email, " +
                        "account_user.number, account_user.created_at, auth_group.name from account_user " +
                        "inner join account_usergroups on account_user.id=account_usergroups.user_id" +
                        " inner join auth_group on auth_group.id=account_usergroups.group_id");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new Account(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        String.valueOf(resultSet.getInt("number")),
                        resultSet.getString("name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<Class> getClassInformation() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<Class> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from student_class ");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new Class(
                        resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getString("field"),
                        resultSet.getString("level")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<Material> getMaterialInformation() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<Material> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from student_material ");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new Material(
                        resultSet.getInt("id"),
                        resultSet.getString("label")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<Absence> getAbsenceInformation() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<Absence> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from student_absence " +
                        "inner join account_user on account_user.id=user_id " +
                        "inner join student_material on student_material.id=material_id");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new Absence(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("label"),
                        resultSet.getString("date"),
                        resultSet.getString("lesson")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<String> getGroups() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<String> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from auth_group");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void updateAccount(@NotNull Account account, String email, String username, String firstname, String lastname,
                                     String phone, ObservableList<String> role){
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("update account_user set " +
                        "email=?, username=?, first_name=?, last_name=?, number=? where " +
                        "id=" + Integer.parseInt(account.getId()));
                ) {
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            statement.setInt(5, Integer.parseInt(phone));
            statement.execute();
            account.setEmail(email);
            account.setUsername(username);
            account.setFirstName(firstname);
            account.setLastName(lastname);
            account.setPhone(phone);
            // update account roles
            // iterate over roles list
            for (String item: role){
                // select the id of this role
                try (
                        PreparedStatement preparedStatement = connection1.prepareStatement("select id from " +
                                "auth_group where name=?");
                ){
                    preparedStatement.setString(1, item);
                    ResultSet r = preparedStatement.executeQuery();
                    // add this role to table
                    try (
                            PreparedStatement statement1 = connection1.prepareStatement("update account_usergroups " +
                                    "set group_id=? where user_id=?");
                            ){
                        if (r.next()){
                            statement1.setInt(1 ,Integer.parseInt(account.getId()));
                            statement1.setInt(2 , r.getInt("id"));
                            statement1.execute();
                        }
                    }
                }
                // set the role to the selected item
                account.setRole(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAccount(String email, String username, String firstname, String lastname,
                                  String phone, ObservableList<String> role){
        DatabaseConnection connection = new DatabaseConnection();
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("insert into account_user(email, " +
                        "username, first_name, last_name, number) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ){
            connection1.setAutoCommit(false);
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            statement.setInt(5, Integer.parseInt(phone));
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();

            for (String item: role){
                // select the id of this role
                try (
                        PreparedStatement preparedStatement = connection1.prepareStatement("select id from " +
                                "auth_group where name=?");
                ){
                    preparedStatement.setString(1, item);
                    ResultSet r = preparedStatement.executeQuery();
                    // add this role to table
                    try (
                            PreparedStatement statement1 = connection1.prepareStatement("insert into " +
                                    "account_usergroups(user_id, group_id) values (?, ?)");
                    ){
                        if (set.next()){
                            if (r.next()){
                                statement1.setInt(1 , set.getInt(1));
                                statement1.setInt(2 , r.getInt("id"));
                                statement1.execute();
                            }
                        }
                    }
                }
            }
            connection1.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> getStudentUsers(){
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<String> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from account_user where " +
                        "class_id is not null");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<String> getNonStudentUsers(){
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<String> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from account_user where " +
                        "class_id is null");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<String> getMaterials(){
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<String> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from student_material");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(resultSet.getString("label"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void updateClass(Class selectedClass, String label, String level, String field) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("update student_class set " +
                        "label=?, level=?, field=? where id=" + selectedClass.getId());
        ) {
            statement.setString(1, label);
            statement.setString(2, level);
            statement.setString(3, field);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addClass(String label, String level, String field) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("insert into student_class(label, level, field)" +
                        " values(?, ?, ?)");
        ) {
            statement.setString(1, label);
            statement.setString(2, level);
            statement.setString(3, field);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMaterial(Material selectedMaterial, String label) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("update student_material set " +
                        "label=? where id=" + selectedMaterial.getId());
        ) {
            statement.setString(1, label);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMaterial(String label) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("insert into student_material(label)" +
                        " values(?)");
        ) {
            statement.setString(1, label);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAbsence(Absence selectedAbsence, String user, String material, String lesson, String date) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("update student_absence set " +
                        "user_id=?, material_id=?, date=?, lesson=? where id=" + selectedAbsence.getId());
        ) {
            // get the selected user_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from account_user" +
                            " where username=? limit 1");
                    ){
                statement1.setString(1, user);
                ResultSet userId = statement1.executeQuery();
                if (userId.next()){
                    statement.setInt(1, userId.getInt("id"));
                }
            }
            // get the selected material_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from student_material " +
                            "where label=? limit 1")
                    ){
                statement1.setString(1, material);
                ResultSet materialId = statement1.executeQuery();
                if (materialId.next()){
                    statement.setInt(2, materialId.getInt("id"));
                }
            }
            statement.setDate(3, Date.valueOf(date));
            statement.setString(4, lesson);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAbsence(String user, String material, String lesson, String date) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("insert into " +
                        "student_absence(user_id, material_id, date, lesson) values(?, ?, ? ,?)");
        ) {
            // get the selected user_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from account_user" +
                            " where username=? limit 1");
            ){
                statement1.setString(1, user);
                ResultSet userId = statement1.executeQuery();
                if (userId.next()){
                    statement.setInt(1, userId.getInt("id"));
                }
            }
            // get the selected material_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from student_material " +
                            "where label=? limit 1")
            ){
                statement1.setString(1, material);
                ResultSet materialId = statement1.executeQuery();
                if (materialId.next()){
                    statement.setInt(2, materialId.getInt("id"));
                }
            }
            statement.setDate(3, Date.valueOf(date));
            statement.setString(4, lesson);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Correspondence> getCorrespondInformation() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<Correspondence> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select student_correspondence.id, account_user.username," +
                        " student_material.label as materialName, student_class.label as className from student_correspondence " +
                        "inner join account_user on account_user.id=student_correspondence.user_id " +
                        "inner join student_material on student_material.id=student_correspondence.material_id " +
                        "inner join student_class on student_class.id=student_correspondence.class_id");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new Correspondence(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("materialName"),
                        resultSet.getString("className")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<String> getClasses(){
        DatabaseConnection connection = new DatabaseConnection();
        ObservableList<String> data = null;
        try (
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("select * from student_class");
        ) {
            ResultSet resultSet = statement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(resultSet.getString("label"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void updateCorrespond(Correspondence selectedCorrespond, String user, String material, String classe) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("update student_correspondence set " +
                        "user_id=?, material_id=?, class_id=? where id=" + selectedCorrespond.getId());
        ) {
            // get the selected user_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from account_user" +
                            " where username=? limit 1");
            ){
                statement1.setString(1, user);
                ResultSet userId = statement1.executeQuery();
                if (userId.next()){
                    statement.setInt(1, userId.getInt("id"));
                }
            }
            // get the selected material_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from student_material " +
                            "where label=? limit 1")
            ){
                statement1.setString(1, material);
                ResultSet materialId = statement1.executeQuery();
                if (materialId.next()){
                    statement.setInt(2, materialId.getInt("id"));
                }
            }
            // get the selected class_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from student_class " +
                            "where label=? limit 1")
            ){
                statement1.setString(1, classe);
                ResultSet classId = statement1.executeQuery();
                if (classId.next()){
                    statement.setInt(3, classId.getInt("id"));
                }
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCorrespond(String user, String material, String classe) {
        DatabaseConnection connection = new DatabaseConnection();
        try(
                Connection connection1 = connection.connect();
                PreparedStatement statement = connection1.prepareStatement("insert into " +
                        "student_correspondence(user_id, material_id, class_id) values(?, ?, ?)");
        ) {
            // get the selected user_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from account_user" +
                            " where username=? limit 1");
            ){
                statement1.setString(1, user);
                ResultSet userId = statement1.executeQuery();
                if (userId.next()){
                    statement.setInt(1, userId.getInt("id"));
                }
            }
            // get the selected material_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from student_material " +
                            "where label=? limit 1")
            ){
                statement1.setString(1, material);
                ResultSet materialId = statement1.executeQuery();
                if (materialId.next()){
                    statement.setInt(2, materialId.getInt("id"));
                }
            }
            // get the selected class_id
            try(
                    PreparedStatement statement1 = connection1.prepareStatement("select id from student_class " +
                            "where label=? limit 1")
            ){
                statement1.setString(1, classe);
                ResultSet classId = statement1.executeQuery();
                if (classId.next()){
                    statement.setInt(3, classId.getInt("id"));
                }
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String hasPermission(String username){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        StringBuilder sb = new StringBuilder();
        try (
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select auth_permission.codename from account_user " +
                        "inner join account_usergroups on account_usergroups.user_id=account_user.id " +
                        "inner join auth_group_permissions on auth_group_permissions.group_id=account_usergroups.group_id" +
                        " inner join auth_permission on auth_permission.id=auth_group_permissions.permission_id" +
                        " where username=?");
                ) {
            statement.setString(1, username);
            ResultSet groupPermissions = statement.executeQuery();
            while (groupPermissions.next()){
                for (int i = 1; i <= groupPermissions.getMetaData().getColumnCount(); i++) {
                    sb.append(groupPermissions.getString(i));
                    if (i < groupPermissions.getMetaData().getColumnCount()) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select auth_permission.codename from account_user " +
                        "inner join account_userpermissions on account_userpermissions.user_id=account_user.id" +
                        " inner join auth_permission on auth_permission.id=account_userpermissions.permission_id" +
                        " where username=?");
        ) {
            statement.setString(1, username);
            ResultSet userPermissions = statement.executeQuery();
            while (userPermissions.next()){
                for (int i = 1; i <= userPermissions.getMetaData().getColumnCount(); i++) {
                    sb.append(userPermissions.getString(i));
                    if (i < userPermissions.getMetaData().getColumnCount()) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void deleteAccount(Account account){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("delete from account_user where username=?");
                ){
            statement.setString(1, account.getUsername());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteClass(Class aClass){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("delete from student_class where id=?");
        ){
            statement.setInt(1, aClass.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAbsence(Absence absence){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("delete from student_absence where id=?");
        ){
            statement.setInt(1, absence.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteMaterial(Material material){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("delete from student_material where id=?");
        ){
            statement.setInt(1, material.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCorrespond(Correspondence correspondence){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("delete from student_correspondence where id=?");
        ){
            statement.setInt(1, correspondence.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void export(TableView tableView, String fileName) {
        ObservableList<TableColumn> columns = tableView.getColumns();
        int size = columns.size();
        PdfPTable pdfTable = new PdfPTable(size);
        for (TableColumn column : columns) {
            pdfTable.addCell(column.getText());
        }
        for (int i = 0; i < tableView.getItems().size(); i++) {
            for (TableColumn column : columns) {
                pdfTable.addCell(column.getCellData(i).toString());
            }
        }
        Document document = new Document();
        try {
            File file = new File(System.getProperty("user.home") + "/Documents/" + fileName);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(pdfTable);
            document.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("File saved to " + System.getProperty("user.home") + "/Documents/");
            alert.show();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Double> getAbsenteeismDataFromDatabase() {
        Map<String, Double> absenteeismData = new TreeMap<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select student_class.label, " +
                        "count(sa.material_id)/(select count(*) from student_class)*100 as perc " +
                        "from student_class inner join student_correspondence sc on student_class.id = sc.class_id " +
                        "inner join student_absence sa on sc.material_id = sa.material_id group by label, sa.material_id");
                ResultSet set = statement.executeQuery();
                ){
            while (set.next()) {
                String className = set.getString("label");
                double absenteeismRate = set.getDouble("perc");
                absenteeismData.put(className, absenteeismRate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absenteeismData;
    }

    public static String getMailAddress(String user){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (
                Connection connection = databaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement("select email from account_user " +
                        "where username=?");
                ){
            statement.setString(1, user);
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getString("email");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
