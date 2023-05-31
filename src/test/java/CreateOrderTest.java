import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;

public class CreateOrderTest {

    private StepsUser stepsUser;
    private User user;
    private CodeStatusCheck codeStatusCheck;
    private Login login;
    private String accessToken;
    private int codeStatus;
    private boolean statusValue;

    @Before
    public void setOrder() {
        user = RandomUser.randomGenerate();
        stepsUser = new StepsUser();
        codeStatusCheck = new CodeStatusCheck();
        login = new Login(user);
    }

    @Test
    @DisplayName("Создание заказа не авторизованным пользователем")
    public void createOrderNoUserTest() {
        ValidatableResponse response = stepsUser.createOrderNoAuth();
        codeStatusCheck.createOrderResponse(response, codeStatus, statusValue);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами авторизованным пользователем")
    public void createOrderTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(login);
        accessToken = response.extract().path("accessToken").toString();
        stepsUser.createOrderAuth(accessToken);
        codeStatusCheck.createOrderResponse(response, codeStatus, statusValue);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов авторизованным пользователем")
    public void createOrderNoIngredientTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(login);
        accessToken = response.extract().path("accessToken").toString();
        stepsUser.createOrderAuthNoIngredients(accessToken);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }
}
