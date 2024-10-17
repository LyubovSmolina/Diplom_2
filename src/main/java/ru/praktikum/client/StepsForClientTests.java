package ru.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.CONST.*;
import static ru.praktikum.CONST.FORBIDDEN_403;

public class StepsForClientTests {

    @Step("Регистрация новой учетной записи пользователя, POST запрос")
    public static Response createNewClient(ClientData client) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(client)
                .when()
                .post(CREATE_ACCOUNT);
        return response;
    }

    @Step ("Проверка статуса и кода ответа, 200 Ok. Тело ответа содержит: success true, информация о пользователе.")
    public static void responseStatusAndBodyValidData(Response response) {
        response.then().statusCode(OK_200)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue())
                .and().assertThat().body("refreshToken", notNullValue());
    }

    @Step ("Получение токена учетной записи пользователя")
    public static String getTokenForDel(String token) {
        return StringUtils.substringAfter(token, " ");
    }

    @Step ("Приведение токена к необходимому формату для последующего удаления тестового аккаунта")
    public static String getToken(Response response) {
        String token = response.then().extract().path("accessToken");
        return token;
    }

    @Step ("Проверка статуса и кода ответа, 403 Forbidden. Тело ответа содержит: false, User already exists.")
    public static void responseStatusAndBodyCreateIndenticalAccount(Response response) {
        response.then().statusCode(FORBIDDEN_403)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("User already exists"));
    }

    @Step ("Проверка статуса и кода ответа, 403 Forbidden. Тело ответа содержит: false, Email, password and name are required fields.")
    public static void responseStatusBodyCreatedNullData(Response response) {
        response.then().statusCode(FORBIDDEN_403)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Step ("Вход в существующую учетную запись пользователя")
    public static Response logInAccount(ClientData client) {
        Response responseLogIn = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(LogInData.from(client))
                .when()
                .post(LOG_IN_ACCOUNT);
        return responseLogIn;
    }

    @Step ("Вход в учетную запись с невалидным значением ключа Email")
    public static Response logInInvalidEmail(ClientData client) {
        String json = "{\"login\": \"abra@pochta.ru\"," + "\"password\": \"" + client.getPassword() +"\" " +"}";
        Response responseLogIn = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(LOG_IN_ACCOUNT);
        return responseLogIn;
    }
    @Step ("Проверка статуса и кода ответа, 401 UNAUTHORIZED. Тело ответа содержит: false, email or password are incorrect.")
    public static void responseStatusBodyLogInInvalidData(Response responseLogIn) {
        responseLogIn.then().statusCode(UNAUTHORIZED_401)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Step ("Вход в учетную запись с невалидным значением ключа Password")
    public static Response logInInvalidPassword(ClientData client) {
        String json = "{\"login\": \"" + client.getEmail() + "\", \"password\": \"111111\"" +"}";
        Response responseLogIn = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(LOG_IN_ACCOUNT);
        return responseLogIn;
    }
}
