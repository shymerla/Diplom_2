import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserEditTest {

    UserClient userClient;
    UserCredentials userCredentials;
    User user, newUser;
    String accessToken;

    @Before
    public void init() {
        userClient = new UserClient();
        userCredentials = new UserCredentials();
        user = User.getCorrectUser();
        newUser = User.getCorrectUser();
        userClient.userRegistrationAndLogin(user);
        accessToken = UserCredentials.getUserAccessToken(user);
    }

    @After
    public void delete() {
        userClient.userDelete(accessToken);
    }

    @Test
    @DisplayName("Edit user credentials with authorization")
    public void editUserWithAuthTest() {
        Response response = userClient.userEdit(newUser, accessToken);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(newUser.email))
                .body("user.name", equalTo(newUser.name));
    }

    @Test
    @DisplayName("Edit user credentials without authorization")
    public void editUserWithoutAuthTest() {
        Response response = userClient.userEditWithOutAuth(newUser);
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}