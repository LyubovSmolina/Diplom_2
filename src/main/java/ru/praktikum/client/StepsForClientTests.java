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
                .and().assertThat().body("user", notNullValue());
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
}
