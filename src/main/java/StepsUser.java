import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;


public class StepsUser extends ApiConstants {

    public static RequestSpecification getSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }

    @Step("Создание юзера")
    public ValidatableResponse createUser(User user) {
        return given().log().all().spec(getSpecification()).body(user).when().post(URL_FOR_AUTH_REGISTER).then().log().all();
    }

    @Step("Авторизациця юзера")
    public ValidatableResponse loginUser(Login login) {
        return given().log().all().spec(getSpecification()).body(login).when().post(URL_FOR_AUTH_LOGIN).then().log().all();
    }

    @Step("Удаление юзера")
    public ValidatableResponse deleteUser(String accessToken) {
        return given().log().all().spec(getSpecification()).header("Authorization", accessToken).when().delete(URL_FOR_AUTH_LOGIN).then().log().all();

    }

    @Step("Обновление юзера")
    public ValidatableResponse updateUser(String accessToken, User user) {
        return given().log().all().spec(getSpecification()).header("Authorization", accessToken).body(user).when().patch(URL_FOR_AUTH_USER).then().log().all();
    }

    @Step("Получение данных юзера")
    public ValidatableResponse getUser(String accessToken) {
        return given().log().all().spec(getSpecification()).header("Authorization", accessToken).when().get(URL_FOR_AUTH_USER).then().log().all();
    }

    @Step("Создание заказа не авторезированого юзера")
    public ValidatableResponse createOrderNoAuth() {
        return given().spec(getSpecification()).when().body("{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa70\",\"61c0c5a71d1f82001bdaaa73\"]\n}").post(URL_FOR_ORDERS).then().log().all();
    }

    @Step("Создание заказа авторезированого юзера")
    public ValidatableResponse createOrderAuth(String accessToken) {
        return given().spec(getSpecification()).header("Authorization", accessToken).when().body("{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa70\",\"61c0c5a71d1f82001bdaaa73\"]\n}").post(URL_FOR_ORDERS).then().log().all();
    }

    @Step("Создание заказа авторезированого юзера без ингридиентов")
    public ValidatableResponse createOrderAuthNoIngredients(String accessToken) {
        return given().spec(getSpecification()).header("Authorization", accessToken).when().post(URL_FOR_ORDERS).then().log().all().assertThat().statusCode(400).and().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Получение ингридиентов")
    public ValidatableResponse getIngredients() {
        return given().spec(getSpecification()).when().get(URL_FOR_INGREDIENT_DATA).then().log().all();
    }

    @Step("Получение заказа")
    public ValidatableResponse getOrders(String accessToken) {
        return given().spec(getSpecification()).header("Authorization", accessToken).when().get(URL_FOR_ORDERS).then().log().all().assertThat().statusCode(200).body("success", is(true));
    }

    @Step("Получение заказа без регстрации")
    public ValidatableResponse getNoUserOrders() {
        return given().spec(getSpecification()).when().get(URL_FOR_ORDERS).then().log().all();
    }
}


