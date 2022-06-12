import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class Ingredients extends BurgerRestClient {

    public ArrayList<String> ingredients;
    private static final String INGREDIENTS_PATH = "ingredients";

    public Ingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }


    @Step("Make burger with ingredients")
    public static Ingredients getRandomBurger() {
        ValidatableResponse response = given()
                .spec(getSpecification())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .statusCode(200);

        ArrayList<String> ingredients = new ArrayList<>();

        List<String> bunIngredient = response.extract().jsonPath().getList("data.findAll{it.type =='bun'}._id");
        List<String> sauceIngredient = response.extract().jsonPath().getList("data.findAll{it.type =='sauce'}._id");
        List<String> mainIngredient = response.extract().jsonPath().getList("data.findAll{it.type =='main'}._id");

        ingredients.add(bunIngredient.get(nextInt(0,bunIngredient.size())));
        ingredients.add(sauceIngredient.get(nextInt(0,sauceIngredient.size())));
        ingredients.add(mainIngredient.get(nextInt(0,mainIngredient.size())));

        return new Ingredients(ingredients);
    }

    @Step("Make burger without ingredients")
    public static Ingredients getEmptyBurger() {
        ArrayList<String> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }

    @Step("Make burger with random ingredients")
    public static Ingredients getIncorrectBurger() {
        ArrayList<String> ingredients = new ArrayList<>();
        String someIngredient = (RandomStringUtils.randomAlphabetic(3));
        ingredients.add(someIngredient);
        return new Ingredients(ingredients);
    }

}