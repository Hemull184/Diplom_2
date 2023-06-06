import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {

    private StepsUser stepsUser;
    private User user;
    private CodeStatusCheck codeStatusCheck;
    private String accessToken;
    private int codeStatus;
    private boolean statusValue;

    @Before
    public void setUp() {
        user = RandomUser.randomGenerate();
        stepsUser = new StepsUser();
        codeStatusCheck = new CodeStatusCheck();

    }

    @Test
    @DisplayName("Создание корректного Юзера")
    public void createCorrectUser() {
        ValidatableResponse response = stepsUser.createUser(user);
        accessToken = response.extract().path("accessToken").toString();
        codeStatusCheck.createUserResponse(response, codeStatus, statusValue);

    }

    @Test
    @DisplayName("Создание Юзера без name")
    public void createUserWithoutNameTest() {
        user.setName("");
        ValidatableResponse response = stepsUser.createUser(user);
        codeStatusCheck.correctCreateUserResponse(response);

    }

    @Test
    @DisplayName("Создание двух одинаковых Юзеров")
    public void createDoubleUsersTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.createUser(user);
        codeStatusCheck.correctUserDoubleResponse(response);
    }

    @Test
    @DisplayName("Создание Юзера без email")
    public void createUserWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = stepsUser.createUser(user);
        codeStatusCheck.correctCreateUserResponse(response);

    }

    @Test
    @DisplayName("Создание Юзера без password")
    public void createUserWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = stepsUser.createUser(user);
        codeStatusCheck.correctCreateUserResponse(response);
    }

    @After
    public void clean() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }
}
