package com.code2bind.studenti.account;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty phone;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty userName;
    private SimpleStringProperty email;
    private SimpleStringProperty role;
    private SimpleStringProperty joinedAt;

    public Account(Integer phone, String firstName, String lastName, String userName, String email, String role, String joined){
        this.phone = new SimpleIntegerProperty(phone);
        this.userName = new SimpleStringProperty(userName);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.joinedAt = new SimpleStringProperty(joined);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public Account() {

    }

    public int getPhone() {
        return phone.get();
    }

    public void setPhone(int phone) {
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

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt.set(joinedAt);
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getJoinedAt() {
        return joinedAt.get();
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

    public String getUserName() {
        return userName.get();
    }

    public String getRole() {
        return role.get();
    }
}
