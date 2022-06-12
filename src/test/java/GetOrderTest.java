import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest {

    private UserClient userClient;
    User user;
    Order order;
    String accessToken;

    @Before
    public void init() {
        UserCredentials userCredentials = new UserCredentials();
        userClient = new UserClient();
        user = User.getCorrectUser();
        order = new Order();
        userClient.userRegistration(user);
        accessToken = UserCredentials.getUserAccessToken(user);
    }

    @After
    public void delete() {
        userClient.userDelete(accessToken);
    }

    @Test
    @DisplayName("Get a list of user orders with authorization")
    public void getUserOrdersWithAuthTest() {
        String token = UserCredentials.getUserAccessToken(user);
        Response response = Order.getOrdersOfUserWithAuth(token);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("orders.total", notNullValue());
    }

    @Test
    @DisplayName("Get a list of user orders without authorization")
    public void getUserOrdersWithoutAuthTest() {
        Response response = Order.getOrdersOfUserWithoutAuth();
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}