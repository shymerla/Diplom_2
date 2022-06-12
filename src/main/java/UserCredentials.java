import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserCredentials extends BurgerRestClient{

    private static String baseURI = "/auth/login";

    @Step("Getting token ")
    public static String getUserAccessToken(User user) {
        String accessToken = "";
        Response response = given()
                .spec(getSpecification())
                .and()
                .body(user)
                .when()
                .post(baseURI);

        if (response.statusCode() == 200) {
            JsonPath path = response.jsonPath();

            String fullAccessToken = path.get("accessToken");
            int lastFileSeparatorIndex = fullAccessToken.lastIndexOf("Bearer ");
            accessToken = fullAccessToken.substring(lastFileSeparatorIndex + 7);
        }
        return accessToken;
    }

    @Step("Getting refresh token")
    public String getUserRefreshToken(User user) {
        String refreshToken = "";
        Response response = given()
                .spec(getSpecification())
                .and()
                .body(user)
                .when()
                .post(baseURI);

        if (response.statusCode() == 200) {
            JsonPath path = response.jsonPath();
            refreshToken = path.get("refreshToken");
        }
        return refreshToken;
    }
}
