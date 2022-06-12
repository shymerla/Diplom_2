import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Order extends BurgerRestClient{

    @Step("Make order with authorization")
    public static Response createOrderWithAuth(Ingredients ingredients, String accessToken) {
        return given()
                .spec(getSpecification())
                .headers(
                        "Authorization", "Bearer " + accessToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(ingredients)
                .when()
                .post("/orders");
    }

    @Step("Make order without authorisation")
    public static Response createOrderWithOutAuth(Ingredients ingredients) {
        return given()
                .spec(getSpecification())
                .body(ingredients)
                .when()
                .post("/orders");
    }

    @Step("Getting a list of user orders with authorization")
    public static Response getOrdersOfUserWithAuth(String authentication) {
        return given()
                .headers(
                        "Authorization", "Bearer " + authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(getSpecification())
                .when()
                .get("/orders");
    }

    @Step("Getting a list of user orders without authorization")
    public static Response getOrdersOfUserWithoutAuth() {

        return given()
                .spec(getSpecification())
                .when()
                .get("/orders");
    }
}