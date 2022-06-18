package com.example.valostats.Model;

public class User {
    private String UUID;
    private String Username;
    private String Password;
    private String Birthday;
    private String Email;

    public User() {
    }

    public User(String UUID, String username, String password, String birthday, String email) {
        this.UUID = UUID;
        Username = username;
        Password = password;
        Birthday = birthday;
        Email = email;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
