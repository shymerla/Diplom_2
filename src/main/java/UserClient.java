import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClient extends BurgerRestClient{

    @Step("Registration of user")
    public Response userRegistration(User user) {
        return given()
                .spec(getSpecification())
                .and()
                .body(user)
                .when()
                .post("/auth/register");
    }

    @Step("Login of user")
    public Response userLogIn(User user) {

        return given()
                .spec(getSpecification())
                .when()
                .and()
                .body(user)
                .post("/auth/login");
    }

    @Step("Edit of user with token")
    public Response userEdit(User user, String authentication) {
        return given()
                .headers(
                        "Authorization", "Bearer " + authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(getSpecification())
                .when()
                .and()
                .body(user)
                .patch("/auth/user");
    }

    @Step("Edit of user without token")
    public Response userEditWithOutAuth(User user) {
        return given()
                .spec(getSpecification())
                .when()
                .and()
                .body(user)
                .patch("/auth/user");
    }

    @Step("Logout of user")
    public void userLogOut(String token) {
        given()
                .spec(getSpecification())
                .when()
                .and()
                .body(token)
                .post("/auth/logout");
    }

    @Step("Registration and login User")
    public void userRegistrationAndLogin(User user) {
        userRegistration(user);
        userLogIn(user);
    }

    @Step("Deleting of user")
    public void userDelete(String authentication) {
        given()
                .headers(
                        "Authorization", "Bearer " + authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(getSpecification())
                .when()
                .delete("auth/user")
                .then()
                .statusCode(202);
    }
}