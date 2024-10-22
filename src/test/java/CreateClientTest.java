import io.qameta.allure.junit4.DisplayName;
import ru.praktikum.client.ClientData;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.StepsForClientTests;
import static io.restassured.RestAssured.baseURI;
import static ru.praktikum.client.StatusCodeAndBodySteps.*;


public class CreateClientTest extends StepsForClientTests {


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
    @DisplayName("Успешное создание аккаунта пользователя")
    public void successCreatedAccount() {
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);
    }

    @Test
    @DisplayName("Получение ошибки при создании пользователя, ранее зарегистрированного в системе")
    public void errorCreateIdenticalAccount() {
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        Response doubleResponse = createNewClient(client);
        responseStatusAndBodyCreateIndenticalAccount(doubleResponse);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);
    }

    @Test
    @DisplayName("Получение ошибки регистрации пользователя со значением null в ключе email")
    public void errorCreatedAccountWithNullEmail() {
        ClientData client = ClientData.newClientWithNullEmail();
        Response response = createNewClient(client);

        responseStatusBodyCreatedNullData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

    }
    @Test
    @DisplayName("Получение ошибки регистрации пользователя со значением null в ключе password")
    public void errorCreatedAccountWithNullPassword() {
        ClientData client = ClientData.newClientWithNullPassword();
        Response response = createNewClient(client);

        responseStatusBodyCreatedNullData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

    }

    @Test
    @DisplayName("Получение ошибки регистрации пользователя со значением null в ключе name")
    public void errorCreatedAccountWithNullName() {
        ClientData client = ClientData.newClientWithNullName();
        Response response = createNewClient(client);

        responseStatusBodyCreatedNullData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

    }

}

