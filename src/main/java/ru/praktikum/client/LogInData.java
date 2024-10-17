package ru.praktikum.client;



public class LogInData {
    private String email;
    private String password;

    public LogInData() {

    }

    public LogInData(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static LogInData from(ClientData client) {
        return new LogInData(client.getEmail(), client.getPassword());
    }

}
