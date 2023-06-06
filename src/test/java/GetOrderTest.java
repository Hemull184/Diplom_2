import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrderTest {

    private User user;
    private StepsUser stepsUser;
    private CodeStatusCheck codeStatusCheck;
    private String accessToken;
    private Login login;

    @Before
    public void setUp() {
        user = RandomUser.randomGenerate();
        stepsUser = new StepsUser();
        codeStatusCheck = new CodeStatusCheck();
        login = new Login(user);
    }

    @Test
    @DisplayName("Получение заказов не авторизованным Юзером")
    public void getOrderNotAuthUserTest() {
        ValidatableResponse response = stepsUser.getNoUserOrders();
        codeStatusCheck.orderNoUser(response);
    }

    @Test
    @DisplayName("Получение заказов авторизованным Юзером")
    public void getOrderAuthUserTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(login);
        accessToken = response.extract().path("accessToken").toString();
        stepsUser.createOrderAuth(accessToken);
        stepsUser.getOrders(accessToken);
    }

    @After
    public void clean() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }
}
