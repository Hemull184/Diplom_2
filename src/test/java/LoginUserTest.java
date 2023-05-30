import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {

    private User user;
    private StepsUser stepsUser;
    private Login login;
    private CodeStatusCheck codeStatusCheck;
    private String accessToken;
    private int codeStatus;
    private boolean statusValue;

    @Before
    public void setUp() {
        user = RandomUser.randomGenerate();
        stepsUser = new StepsUser();
        codeStatusCheck = new CodeStatusCheck();
        login = new Login(user);
    }

    @Test
    @DisplayName("Корректная авторизация пользователя")
    public void authLoginTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(login);
        accessToken = response.extract().path("accessToken").toString();
        codeStatusCheck.createUserResponse(response, codeStatus, statusValue);
    }

    @Test
    @DisplayName("Авторизация с пустым полем Login")
    public void authWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = stepsUser.loginUser(login);
        codeStatusCheck.emailOrPasswordIncorrect(response);
    }

    @Test
    @DisplayName("Авторизация с пустым полем Password")
    public void authWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = stepsUser.loginUser(login);
        codeStatusCheck.emailOrPasswordIncorrect(response);
    }

    @After
    public void clean() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }
}
