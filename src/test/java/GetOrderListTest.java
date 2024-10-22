import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.ClientData;
import ru.praktikum.client.StepsForClientTests;
import ru.praktikum.order.OrderList;
import static io.restassured.RestAssured.baseURI;
import static ru.praktikum.client.StatusCodeAndBodySteps.responseStatusAndBodyValidData;
import static ru.praktikum.order.OrderList.*;


public class GetOrderListTest extends StepsForClientTests{
    OrderList order = new OrderList();

    @Before
    @DisplayName("Регистрация учетной записи пользователя, получение хэша ингредиента и запрос на создание заказа")
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site/";
        // Создание клиента
        ClientData client = ClientData.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        //Получение токена
        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        //Создание заказа с актуальным хэшом ингридиента, клиент авторизован
        String json = "{\"ingredients\": [\"" + order.getIngredientHash() + "\"" + "]}";
        Response responseOrder = OrderList.createOrderWithAuthAndIngredientHash(json);
        responseStatusAndBodyOrderWithHasgAndAuth(responseOrder);
    }

    @After
    @DisplayName("Удаление учетной записи пользователя")
    public void deleteAccount() {
        if (tokenForDel != null) {
            deleteUser();
        }
    }

    @Test
    @DisplayName("Успешное получение заказа пользователя, токен передан в запросе")
    public void getAuthorisedUserOrderList () {
        Response responseGetOrder = getUserOrderListWithAuthorization();
        responseStatusBodyOrderListWithAuth(responseGetOrder);

    }

    @Test
    @DisplayName("Проверка получения ошибки 401 Unauthorized, при направлении Get запроса на получение списка заказов")
    public void errorGetUnauthorisedUserOrderList () {
        Response responseGetOrder = getUserOrderListWithoutAuthorization();
        responseStatusBodyOrderListWithoutAuth(responseGetOrder);
    }

}

