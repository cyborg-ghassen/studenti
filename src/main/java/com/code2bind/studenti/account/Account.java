package com.code2bind.studenti.account;

import javafx.beans.property.SimpleStringProperty;

public class Account {
    private SimpleStringProperty id;
    private SimpleStringProperty username;
    private SimpleStringProperty phone;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleStringProperty role;

    public Account(String id, String username, String firstName, String lastName, String email, String phone, String role){
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.phone = new SimpleStringProperty(phone);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getUsername() {
        return username.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public Account() {

    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getRole() {
        return role.get();
    }
}
