import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {

    private User user;
    private User userForDataChang;
    private StepsUser stepsUser;
    private Login login;
    private CodeStatusCheck codeStatusCheck;
    private String accessToken;

    @Before
    public void setUp() {
        user = RandomUser.randomGenerate();
        userForDataChang = RandomUser.randomGenerate();
        stepsUser = new StepsUser();
        codeStatusCheck = new CodeStatusCheck();
        login = new Login(user);
    }

    @Test
    @DisplayName("Изменение зарегестрированого пользователя")
    public void dataChangUserTest() {
        User firstUser = RandomUser.randomGenerate();
        User userForDataChang = firstUser.Copy();
        userForDataChang.setEmail(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru.com");
        String accessToken = stepsUser.createUser(firstUser).extract().header("Authorization");
        stepsUser.updateUser(accessToken, userForDataChang);
        ValidatableResponse updatedUserResponse = stepsUser.getUser(accessToken);
        updatedUserResponse.body("user.name", equalTo(firstUser.getName())).and().body("user.email", equalTo(userForDataChang.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Изменение не зарегестрированого пользователя")
    public void dataChangNoUserTest() {
        userForDataChang.setEmail(RandomStringUtils.randomAlphabetic(10) + "@newexample.com");
        ValidatableResponse response = stepsUser.updateUser("", userForDataChang);
        codeStatusCheck.updateNoUser(response);
    }

    @After
    public void clean() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }
}
