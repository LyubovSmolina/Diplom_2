package ru.praktikum.order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.client.StepsForClientTests;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.CONST.*;

public class OrderList extends StepsForClientTests{
    private List<String>  ingredients;


    public List<String> getIngredients() {
        List<String> ingredients = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .when()
                .get(GET_INGREDIENTS)
                .then().extract().path("data._id");
        return new ArrayList<>();
    }

    @Step("Запрос на получение хэша ингредиента")
    public String getIngredientHash() {
        List<String> ingredients = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .auth().oauth2(tokenForDel)
                .when()
                .get(GET_INGREDIENTS)
                .then().extract().path("data._id");
        String ingredientForORDER = ingredients.get(1);
        System.out.println(ingredientForORDER);
        return ingredientForORDER;
    }


    @Step("Запрос на создание заказа. Переданы: токен пользователя, хэш код ингредиента")
    public static Response createOrderWithAuthAndIngredientHash(String json) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(CREATE_ORDER_POST);
        return response;
    }

    @Step ("Проверка статуса и кода ответа, 200 Ok. Тело ответа содержит: success true; информацию о заказе")
    public static void responseStatusAndBodyOrderWithHasgAndAuth(Response response) {
        response.then().statusCode(OK_200)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("order", notNullValue())
                .and().assertThat().body("name", notNullValue());
    }

    @Step("Запрос на создание заказа, токен не передан. В теле запроса передан хэш код ингредиента")
    public static Response createOrderWithoutAuthAndIngredientHash(String json) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(CREATE_ORDER_POST);
        return response;
    }

    @Step ("Проверка статуса и кода ответа, 301 Redirect.")
    public static void responseStatusAndBodyOrderWithoutAuth(Response response) {
        response.then().statusCode(REDIRECT_301);
    }

    @Step ("Проверка статуса и кода ответа, 400 Bad request.")
    public static void responseStatusAndBodyOrderWithoutHash(Response response) {
        response.then().statusCode(BAD_REQUEST_400)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }


    @Step("Получение невалидного хеша ингридиента")
    public static String responseOrderWithInvalidHash() {
        List<String> ingredients = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .auth().oauth2(tokenForDel)
                .when()
                .get(GET_INGREDIENTS)
                .then().extract().path("data._id");
        String ingredientForORDER = ingredients.get(1);
        String invalidHash = ingredientForORDER.replace("a", "h");
        return invalidHash;
    }

    @Step ("Проверка статуса и кода ответа, 500 Internal server error.")
    public static void responseStatusBodyInvalidHash(String json) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(CREATE_ORDER_POST);
        response.then().statusCode(INTERNAL_SERVER_ERROR_500);
    }



}
