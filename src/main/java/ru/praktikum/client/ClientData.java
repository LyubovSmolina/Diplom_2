package ru.praktikum.client;


import org.apache.commons.lang3.RandomStringUtils;


public class ClientData {
    private String email;
    private String password;
    private String name;


    public ClientData(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public ClientData() {
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    //Метод с данными рандомного пользователя, все обязательные значения заполненны
    public static ClientData newRandomClient() {
        return new ClientData("kosmoDiplom@pochta.ru" + RandomStringUtils.randomAlphabetic(5), "123456", "Petya");
    }

    //Метод со значением Null в ключе Email
    public static ClientData newClientWithNullEmail() {
        return new ClientData(null, "123456", "Petya");
    }

    //Метод со значением Null в ключе Password
    public static ClientData newClientWithNullPassword() {
        return new ClientData("kosmoDiplom@pochta.ru" + RandomStringUtils.randomAlphabetic(5), null, "Petya");
    }

    //Метод со значением Null в ключе Name
    public static ClientData newClientWithNullName() {
        return new ClientData("kosmoDiplom@pochta.ru" + RandomStringUtils.randomAlphabetic(5), "123456", null);
    }



}
