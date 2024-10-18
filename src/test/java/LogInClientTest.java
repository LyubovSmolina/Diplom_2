import io.qameta.allure.junit4.DisplayName;
import ru.praktikum.client.ClientData;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.StepsForClientTests;

import static io.restassured.RestAssured.baseURI;
import static ru.praktikum.client.StatusCodeAndBodySteps.responseStatusAndBodyValidData;
import static ru.praktikum.client.StatusCodeAndBodySteps.responseStatusBodyLogInInvalidData;


public class LogInClientTest extends StepsForClientTests {


    @Before
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    @DisplayName("Удаление учетной записи пользователя")
    public void deleteAccount() {
        if (tokenForDel != null) {
            deleteUser();
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
