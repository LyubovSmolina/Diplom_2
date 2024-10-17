import io.qameta.allure.junit4.DisplayName;
import ru.praktikum.client.ClientData;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.LogInData;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.CONST.*;
import static ru.praktikum.client.StepsForClientTests.*;

public class LogInClientTest {
    public String tokenForDel;

    @Before
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    @DisplayName("Удаление учетной записи пользователя")
    public void deleteAccount() {
        if (tokenForDel != null) {
            given().log().all()
                    .header("Content-type", "application/json")
                    .auth().oauth2(tokenForDel)
                    .baseUri(baseURI)
                    .when()
                    .delete(DELETE_ACCOUNT)
                    .then().log().all().assertThat()
                    .statusCode(ACCEPT_202);
            System.out.println("Учетная запись клиента успешно удалена");
        }
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    public void successLogInAccount() {
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        //String json = "{\"login\": \"" + client.getEmail() + "\"\"password\": " + client.getPassword() "}";
        Response responseLogIn = logInAccount(client);
        responseStatusAndBodyValidData(responseLogIn);


    }

    @Test
    @DisplayName("Ошибка при авторизации с невалидным адресом почтового ящика")
    public void errorLogInInvalidEmail() {
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        Response responseLogIn = logInInvalidEmail(client);
        responseStatusBodyLogInInvalidData(responseLogIn);
    }

    @Test
    @DisplayName("Ошибка при авторизации с невалидным паролем")
    public void errorLogInInvalidPassword() {
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        Response responseLogIn = logInInvalidPassword(client);
        responseStatusBodyLogInInvalidData(responseLogIn);
    }



}
