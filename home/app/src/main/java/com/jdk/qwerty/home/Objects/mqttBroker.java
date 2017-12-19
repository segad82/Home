package com.jdk.qwerty.home.Objects;

/**
 * Created by Administrador on 16/12/2017.
 */

public class mqttBroker {

    private String url;
    private String port;
    private String username;
    private String password;

    public String getFullURL(){
        return this.getUrl() + ":" + this.getPort();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
