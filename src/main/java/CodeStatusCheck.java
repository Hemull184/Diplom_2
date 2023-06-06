import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CodeStatusCheck {

    @Step("Проверка кода ответа")
    public void createUserResponse(ValidatableResponse response, int codeStatus, Boolean statusValue) {
        response.assertThat().statusCode(200).body("success", is(true));
    }

    @Step("Проверка кода ответа при создании юзера без email, password and name  ")
    public void correctCreateUserResponse(ValidatableResponse response) {
        response.statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка кода ответа при неправильном email или password")
    public void emailOrPasswordIncorrect(ValidatableResponse response) {
        response.statusCode(401).and().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Step("Проверка кода ответа при создании  дубль юзера")
    public void correctUserDoubleResponse(ValidatableResponse response) {
        response.statusCode(403).and().assertThat().body("message", equalTo("User already exists"));
    }

    @Step("Проверка кода ответа при получения заказа без юзера")
    public void updateNoUser(ValidatableResponse response) {
        response.statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));

    }

    @Step("Проверка кода ответа при создании заказа без юзера")
    public void orderNoUser(ValidatableResponse response) {
        response.statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }

    @Step("проверяет код создания заказа")
    public static void createOrderResponse(ValidatableResponse response, int codeStatus, Boolean statusValue) {
        response.assertThat().statusCode(200).body("success", is(true));
    }
}
