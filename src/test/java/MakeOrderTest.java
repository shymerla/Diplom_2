import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class MakeOrderTest {

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
    @DisplayName("Make order with authorization")
    public void createOrderWithAuthTest() {
        Response response = Order.createOrderWithAuth(Ingredients.getRandomBurger(), accessToken);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Make order without authorization")
    public void createOrderWithoutAuthTest() {
        Response response = Order.createOrderWithOutAuth(Ingredients.getRandomBurger());
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Make order without authorization and without ingredients")
    public void createOrderWithoutAuthAndWithoutIngredientsTest() {
        Response response = Order.createOrderWithOutAuth(Ingredients.getEmptyBurger());
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Make order with incorrect ingredients")
    public void createOrderWithIncorrectIngredientsTest() {
        Response response = Order.createOrderWithOutAuth(Ingredients.getIncorrectBurger());
        response.then()
                .assertThat()
                .statusCode(500);
    }
}