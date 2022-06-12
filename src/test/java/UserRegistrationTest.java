import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationTest {

    User user;
    UserClient userClient;
    UserCredentials userCredentials;
    String accessToken;

    @Before
    public void init() {
        userClient = new UserClient();
        userCredentials = new UserCredentials();
        user = User.getCorrectUser();
        accessToken = UserCredentials.getUserAccessToken(user);
    }

    @Test
    @DisplayName("Success user registration")
    public void successRegistrationUserTest() {
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Fail registration exist user")
    public void failRegistrationExistedUserTest() {
        User user = User.getCorrectUser();
        userClient.userRegistration(user);
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Fail registration user without NAME")
    public void failRegistrationUserWithoutRequiredNameTest() {
        User user = User.getUserWithoutName();
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Fail registration user without EMAIL")
    public void failRegistrationUserWithoutRequiredEmailTest() {
        User user = User.getUserWithoutEmail();
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Fail registration user without PASSWORD")
    public void failRegistrationUserWithoutRequiredPasswordTest() {
        User user = User.getUserWithoutPassword();
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

}