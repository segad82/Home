package com.jdk.qwerty.home.Objects;

/**
 * Created by Administrador on 10/12/2017.
 */

public class user {

    private String email;
    private String displayName;
    private String password;

    public user(String email, String displayName, String password){
        this.setEmail(email);
        this.setDisplayName(displayName.equals("") ? email.split("@")[0].toUpperCase() : displayName);
        this.setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
