package com.code2bind.studenti;

import com.code2bind.studenti.account.Account;
import com.code2bind.studenti.student.Absence;
import com.code2bind.studenti.student.Class;
import com.code2bind.studenti.student.Correspondence;
import com.code2bind.studenti.student.Material;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    public Label label_fullname;
    public ImageView avatar_img;
    @FXML
    private TableView<Account> users_table;

    @FXML
    public TableColumn<Account, String> id;

    @FXML
    public TableColumn<Account, String> firstName;

    @FXML
    public TableColumn<Account, String> lastName;

    @FXML
    public TableColumn<Account, String> email;

    @FXML
    public TableColumn<Account, String> phone;

    @FXML
    public TableColumn<Account, String> role;
    @FXML
    private Button button_dashboard;
    @FXML private FontAwesomeIconView icon_dashboard;
    @FXML
    private Button button_account;
    @FXML private FontAwesomeIconView icon_user;
    @FXML
    private Button button_class;
    @FXML private FontAwesomeIconView icon_class;
    @FXML
    private Button button_material;
    @FXML private FontAwesomeIconView icon_material;
    @FXML
    private Button button_absence;
    @FXML private FontAwesomeIconView icon_absence;
    @FXML
    private Button button_correspondence;
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
    @FXML private BarChart<String, Number> absence_chart;
    @FXML private Label students_number;
    @FXML private Label teacher_number;
    @FXML private Label classes_number;
    @FXML private Label number_materials;
    @FXML
    private Pane account_pane;
    @FXML private TextField tf_username;
    @FXML private TextField tf_first_name;
    @FXML private TextField tf_last_name;
    @FXML private TextField tf_phone;
    @FXML private TextField tf_email;
    @FXML private ListView<String> lv_groups;
    @FXML private Button btn_account_save;


    @FXML
    private Pane class_pane;
    @FXML private TableView<Class> class_table;
    @FXML private TableColumn<Class, Integer> classId;
    @FXML private TableColumn<Class, String> label;
    @FXML private TableColumn<Class, String> field;
    @FXML private TableColumn<Class, String> level;
    @FXML private TextField tf_label;
    @FXML private TextField tf_field;
    @FXML private TextField tf_level;
    @FXML private Button btn_class_save;
    @FXML
    private Pane material_pane;
    @FXML private TableView<Material> material_table;
    @FXML private TableColumn<Material, Integer> idMaterial;
    @FXML private TableColumn<Material, Integer> materialLabel;
    @FXML private TextField tf_label_material;
    @FXML private Button btn_material_save;
    @FXML
    private Pane absence_pane;
    @FXML private Pane absenceForm;
    @FXML private TableView<Absence> absence_table;
    @FXML private TableColumn<Absence, Integer> idAbsence;
    @FXML private TableColumn<Absence, String> absenceUser;
    @FXML private TableColumn<Absence, String> absenceMaterial;
    @FXML private TableColumn<Absence, String> absenceDate;
    @FXML private TableColumn<Absence, String> lesson;
    @FXML private ComboBox<String> cb_user_absence;
    @FXML private ComboBox<String> cb_absence_material;
    @FXML private DatePicker dp_date;
    @FXML private TextField tf_lesson;
    @FXML private Button btn_absence_save;
    @FXML private Button print_absence;

    @FXML
    private Pane correspond_pane;
    @FXML private TableView<Correspondence> correspond_table;
    @FXML private TableColumn<Correspondence, Integer> correspondId;
    @FXML private TableColumn<Correspondence, String> userCorrespond;
    @FXML private TableColumn<Correspondence, String> correspondMaterial;
    @FXML private TableColumn<Correspondence, String> correspondClass;
    @FXML private ComboBox<String> cb_userCorrespond;
    @FXML private ComboBox<String> cb_materialCorrespond;
    @FXML private ComboBox<String> cb_classCorrespond;
    @FXML private Button btn_correspond_save;
    private String username;

    void setUsername(String username){
        this.username = username;
    }

    String getUsername(){
        return username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account_pane.setVisible(false);
        class_pane.setVisible(false);
        material_pane.setVisible(false);
        absence_pane.setVisible(false);
        correspond_pane.setVisible(false);
        dashboard_pane.setVisible(true);
        button_dashboard.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");



        button_dashboard.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(true);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            correspond_pane.setVisible(false);
            button_dashboard.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
            button_correspondence.setStyle("-fx-background-color: #ffffff");

        });

        button_account.setOnAction(event -> {
            account_pane.setVisible(true);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            correspond_pane.setVisible(false);
            button_account.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
            button_correspondence.setStyle("-fx-background-color: #ffffff");
            users_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            lv_groups.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));

            try {
                users_table.setItems(DBUtils.getAccountInformation());
                lv_groups.setItems(DBUtils.getGroups());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            users_table.setRowFactory(accountTableView -> {
                final TableRow<Account> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(actionEvent -> {
                    Account account = row.getItem();
                    // TODO: Delete the account object
                    DBUtils.deleteAccount(account);
                    users_table.refresh();
                });
                contextMenu.getItems().addAll(deleteMenuItem);
                // Only display the context menu for non-null items
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(contextMenu)
                                .otherwise((ContextMenu) null));
                return row;
            });

            users_table.getSelectionModel().selectedItemProperty().addListener(((observableValue, account, t1) -> {
                if (t1 != null){
                    tf_username.setText(observableValue.getValue().getUsername());
                    tf_email.setText(observableValue.getValue().getEmail());
                    tf_first_name.setText(observableValue.getValue().getFirstName());
                    tf_last_name.setText(observableValue.getValue().getLastName());
                    tf_phone.setText(observableValue.getValue().getPhone());
                    lv_groups.getSelectionModel().select(observableValue.getValue().getRole());

                }

            }));

            btn_account_save.setOnAction(actionEvent -> {
                Account selectedAccount = users_table.getSelectionModel().getSelectedItem();
                String email = tf_email.getText();
                String username = tf_username.getText();
                String firstname = tf_first_name.getText();
                String lastname = tf_last_name.getText();
                String phone = tf_phone.getText();
                ObservableList<String> role = lv_groups.getSelectionModel().getSelectedItems();
                if (selectedAccount != null){
                    DBUtils.updateAccount(selectedAccount, email, username, firstname, lastname, phone, role);
                } else {
                    DBUtils.addAccount(email, username, firstname, lastname, phone, role);
                }

                users_table.refresh();
            });

        });

        button_absence.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(true);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            correspond_pane.setVisible(false);
            button_absence.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
            button_correspondence.setStyle("-fx-background-color: #ffffff");

            absence_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            idAbsence.setCellValueFactory(new PropertyValueFactory<>("id"));
            absenceUser.setCellValueFactory(new PropertyValueFactory<>("user"));
            absenceMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
            absenceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            lesson.setCellValueFactory(new PropertyValueFactory<>("lesson"));

            try {
                absence_table.setItems(DBUtils.getAbsenceInformation());
                cb_user_absence.setItems(DBUtils.getStudentUsers());
                cb_absence_material.setItems(DBUtils.getMaterials());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            absence_table.getSelectionModel().selectedItemProperty().addListener(((observableValue, account, t1) -> {
                if (t1 != null){
                    cb_user_absence.getSelectionModel().select(observableValue.getValue().getUser());
                    cb_absence_material.getSelectionModel().select(observableValue.getValue().getMaterial());
                    tf_lesson.setText(observableValue.getValue().getLesson());
                    dp_date.setValue(LocalDate.parse(observableValue.getValue().getDate()));
                }

            }));

            Dotenv dotenv = Dotenv.load();

            btn_absence_save.setOnAction(actionEvent -> {
                Absence selectedAbsence = absence_table.getSelectionModel().getSelectedItem();
                String user = cb_user_absence.getSelectionModel().getSelectedItem();
                String material = cb_absence_material.getSelectionModel().getSelectedItem();
                String lesson = tf_lesson.getText();
                String date = String.valueOf(dp_date.getValue());
                if (selectedAbsence != null){
                    DBUtils.updateAbsence(selectedAbsence, user, material, lesson, date);
                } else {
                    DBUtils.addAbsence(user, material, lesson, date);
                    Properties props = new Properties();
                    props.put("mail.smtp.host", dotenv.get("MAIL_HOSTNAME"));
                    props.put("mail.transport.protocol", "smtp");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");

                    Session session = Session.getInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(dotenv.get("AWS_SMTP_USERNAME"),
                                            dotenv.get("AWS_SMTP_PASSWORD"));
                                }
                            });

                    Message message = new MimeMessage(session);

                    try {
                        message.setFrom(new InternetAddress("ghassen.bougacha@gmail.com"));
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    try {
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(DBUtils.getMailAddress(user)));
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    try {
                        message.setSubject("Absenteeism Alert");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    try {
                        message.setText("You have been marked as absent in the attendance records. Please contact your instructor for further information.");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    try {
                        Transport.send(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }

                absence_table.refresh();
            });

            print_absence.setOnAction(event1 -> {
                DBUtils.export(absence_table, "export.pdf");
            });

        });

        button_class.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(true);
            correspond_pane.setVisible(false);
            button_class.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_material.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_correspondence.setStyle("-fx-background-color: #ffffff");

            class_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            try {
                class_table.setItems(DBUtils.getClassInformation());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            classId.setCellValueFactory(new PropertyValueFactory<>("id"));
            label.setCellValueFactory(new PropertyValueFactory<>("label"));
            field.setCellValueFactory(new PropertyValueFactory<>("field"));
            level.setCellValueFactory(new PropertyValueFactory<>("level"));

            class_table.setRowFactory(classTableView -> {
                final TableRow<Class> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(actionEvent -> {
                    Class aClass = row.getItem();
                    // TODO: Delete the account object
                    DBUtils.deleteClass(aClass);
                    class_table.refresh();
                });
                contextMenu.getItems().addAll(deleteMenuItem);
                // Only display the context menu for non-null items
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(contextMenu)
                                .otherwise((ContextMenu) null));
                return row;
            });

            class_table.getSelectionModel().selectedItemProperty().addListener(((observableValue, account, t1) -> {
                if (t1 != null){
                    tf_label.setText(observableValue.getValue().getLabel());
                    tf_field.setText(observableValue.getValue().getField());
                    tf_level.setText(observableValue.getValue().getLevel());
                }

            }));

            btn_class_save.setOnAction(actionEvent -> {
                Class selectedClass = class_table.getSelectionModel().getSelectedItem();
                String level = tf_level.getText();
                String label = tf_label.getText();
                String field = tf_field.getText();
                if (selectedClass != null){
                    DBUtils.updateClass(selectedClass, label, level, field);
                    class_table.refresh();
                } else {
                    DBUtils.addClass(label, level, field);
                    class_table.refresh();
                }

            });
        });

        button_material.setOnAction(event -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(true);
            class_pane.setVisible(false);
            correspond_pane.setVisible(false);
            button_material.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
            button_correspondence.setStyle("-fx-background-color: #ffffff");

            material_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            try {
                material_table.setItems(DBUtils.getMaterialInformation());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            idMaterial.setCellValueFactory(new PropertyValueFactory<>("id"));
            materialLabel.setCellValueFactory(new PropertyValueFactory<>("label"));

            material_table.getSelectionModel().selectedItemProperty().addListener(((observableValue, account, t1) -> {
                if (t1 != null){
                    tf_label_material.setText(observableValue.getValue().getLabel());
                }

            }));

            material_table.setRowFactory(materialTableView -> {
                final TableRow<Material> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(actionEvent -> {
                    Material material = row.getItem();
                    // TODO: Delete the account object
                    DBUtils.deleteMaterial(material);
                    material_table.refresh();
                });
                contextMenu.getItems().addAll(deleteMenuItem);
                // Only display the context menu for non-null items
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(contextMenu)
                                .otherwise((ContextMenu) null));
                return row;
            });

            btn_material_save.setOnAction(actionEvent -> {
                Material selectedMaterial = material_table.getSelectionModel().getSelectedItem();
                String label = tf_label_material.getText();
                if (selectedMaterial != null){
                    DBUtils.updateMaterial(selectedMaterial, label);
                    material_table.refresh();
                } else {
                    DBUtils.addMaterial(label);
                    material_table.refresh();
                }

            });

        });

        button_correspondence.setOnAction(actionEvent -> {
            account_pane.setVisible(false);
            dashboard_pane.setVisible(false);
            absence_pane.setVisible(false);
            material_pane.setVisible(false);
            class_pane.setVisible(false);
            correspond_pane.setVisible(true);
            button_material.setStyle("-fx-background-color: #ffffff");
            button_dashboard.setStyle("-fx-background-color: #ffffff");
            button_account.setStyle("-fx-background-color: #ffffff");
            button_absence.setStyle("-fx-background-color: #ffffff");
            button_class.setStyle("-fx-background-color: #ffffff");
            button_correspondence.setStyle("-fx-background-color: #B7B7B71A; -fx-background-radius: 35px; -fx-border-radius: 35px");

            correspond_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            correspondId.setCellValueFactory(new PropertyValueFactory<>("id"));
            correspondMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
            correspondClass.setCellValueFactory(new PropertyValueFactory<>("classe"));
            userCorrespond.setCellValueFactory(new PropertyValueFactory<>("user"));

            try {
                correspond_table.setItems(DBUtils.getCorrespondInformation());
                cb_userCorrespond.setItems(DBUtils.getNonStudentUsers());
                cb_materialCorrespond.setItems(DBUtils.getMaterials());
                cb_classCorrespond.setItems(DBUtils.getClasses());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            correspond_table.getSelectionModel().selectedItemProperty().addListener(((observableValue, account, t1) -> {
                if (t1 != null){
                    cb_userCorrespond.getSelectionModel().select(observableValue.getValue().getUser());
                    cb_materialCorrespond.getSelectionModel().select(observableValue.getValue().getMaterial());
                    cb_classCorrespond.getSelectionModel().select(observableValue.getValue().getClasse());
                }

            }));

            correspond_table.setRowFactory(correspondenceTableView -> {
                final TableRow<Correspondence> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(event -> {
                    Correspondence correspondence = row.getItem();
                    // TODO: Delete the account object
                    DBUtils.deleteCorrespond(correspondence);
                    correspond_table.refresh();
                });
                contextMenu.getItems().addAll(deleteMenuItem);
                // Only display the context menu for non-null items
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(contextMenu)
                                .otherwise((ContextMenu) null));
                return row;
            });

            btn_correspond_save.setOnAction(event -> {
                Correspondence selectedCorrespond = correspond_table.getSelectionModel().getSelectedItem();
                String user = cb_userCorrespond.getSelectionModel().getSelectedItem();
                String material = cb_materialCorrespond.getSelectionModel().getSelectedItem();
                String classe = cb_classCorrespond.getSelectionModel().getSelectedItem();
                if (selectedCorrespond != null){
                    DBUtils.updateCorrespond(selectedCorrespond, user, material, classe);
                } else {
                    DBUtils.addCorrespond(user, material, classe);
                }

                correspond_table.refresh();
            });

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
        setUsername(username);
        label_fullname.setText(DBUtils.getUserFullName(username));
        button_account.setVisible(DBUtils.hasPermission(username).contains("view_user"));
        icon_user.setVisible(DBUtils.hasPermission(username).contains("view_user"));
        button_absence.setVisible(DBUtils.hasPermission(username).contains("view_absence"));
        icon_absence.setVisible(DBUtils.hasPermission(username).contains("view_absence"));
        print_absence.setVisible(DBUtils.hasPermission(username).contains("print_absence"));
        absenceForm.setVisible(DBUtils.hasPermission(username).contains("add_absence"));
        button_class.setVisible(DBUtils.hasPermission(username).contains("view_class"));
        icon_class.setVisible(DBUtils.hasPermission(username).contains("view_class"));
        button_material.setVisible(DBUtils.hasPermission(username).contains("view_material"));
        icon_material.setVisible(DBUtils.hasPermission(username).contains("view_material"));
        button_correspondence.setVisible(DBUtils.hasPermission(username).contains("view_correspondence"));
        if (DBUtils.hasPermission(username).contains("delete_absence"))
            absence_table.setRowFactory(absenceTableView -> {
                final TableRow<Absence> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(actionEvent -> {
                    Absence absence = row.getItem();
                    // TODO: Delete the account object
                    DBUtils.deleteAbsence(absence);
                    absence_table.refresh();
                });
                contextMenu.getItems().addAll(deleteMenuItem);
                // Only display the context menu for non-null items
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(contextMenu)
                                .otherwise((ContextMenu) null));
                return row;
            });

        Map<String, Double> absenteeismData = DBUtils.getAbsenteeismDataFromDatabase();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Class");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Absenteeism Rate");

        absence_chart.setTitle("Absenteeism Rate by Class");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Absenteeism Rate");
        for (Map.Entry<String, Double> entry : absenteeismData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        absence_chart.getData().add(series);

        number_materials.setText(DBUtils.MaterialsNumber());
        classes_number.setText(DBUtils.ClassesNumber());
        students_number.setText(DBUtils.StudentsNumber());
        teacher_number.setText(DBUtils.TeachersNumber());

        avatar_img.setImage(DBUtils.getUserAvatar(username));

    }


}
