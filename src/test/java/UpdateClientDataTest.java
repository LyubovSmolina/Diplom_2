import io.qameta.allure.junit4.DisplayName;
import ru.praktikum.client.ClientData;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.StepsForClientTests;
import static io.restassured.RestAssured.baseURI;
import static ru.praktikum.client.StatusCodeAndBodySteps.*;


public class UpdateClientDataTest extends StepsForClientTests {


    @Before
    @DisplayName("Регистрация учетной записи пользователя")
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site/";
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);
    }

    @After
    @DisplayName("Удаление учетной записи пользователя")
    public void deleteAccount() {
        if (tokenForDel != null) {
            deleteUser();
        }
    }


    @Test
    @DisplayName("Успешное изменение электронного адреса авторизованного пользователя")
    public void successUpdateClientEmail() {
        Response responseUpdateData = updateClientEmail();
        responseStatusAndBodyValidUpdateData(responseUpdateData);
    }

    @Test
    @DisplayName("Успешное изменение пароля учетной записи авторизованного пользователя")
    public void successUpdateClientPassword() {
        Response responseUpdateData = updateClientPassword();
        responseStatusAndBodyValidUpdateData(responseUpdateData);
    }

    @Test
    @DisplayName("Успешное изменение имени учетной записи авторизованного пользователя")
    public void successUpdateClientName() {
        Response responseUpdateData = updateClientName();
        responseStatusAndBodyValidUpdateData(responseUpdateData);
    }

    @Test
    @DisplayName("Ошибка 403 при передаче в теле запроса электронного адреса, уже зарегистрированного за пользователем, пользователь авторизован")
    public void errorUpdateClientSameEmail() {
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);
        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        Response responseUpdateData = responseUpdateIdenticalEmail(client);
        statusCodeBodyUserAlreadyExist(responseUpdateData);
    }

    @Test
    @DisplayName("Ошибка 401 при направлении запроса без токена на изменение адреса электронной почты пользователя")
    public void errorUpdateClientEmailUnauthorized() {
        Response responseUpdateData = responseUpdateEmailUnauthorized();
        statusCodeBodyUnauthorized(responseUpdateData);
    }

    @Test
    @DisplayName("Ошибка 401 при направлении запроса без токена на изменение пароля учетой записи пользователя")
    public void errorUpdateClientPasswordUnauthorized() {
        Response responseUpdateData = responseUpdatePasswordUnauthorized();
        statusCodeBodyUnauthorized(responseUpdateData);
    }

    @Test
    @DisplayName("Ошибка 401 при направлении запроса без токена на изменение имени пользователя учетной записи")
    public void errorUpdateClientNameUnauthorized() {
        Response responseUpdateData = responseUpdateNameUnauthorized();
        statusCodeBodyUnauthorized(responseUpdateData);
    }

}
