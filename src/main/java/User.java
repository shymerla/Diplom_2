import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User extends BurgerRestClient{

    public String email;
    public String password;
    public String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Make correct credentials of User")
    public static User getCorrectUser() {
        final String email = (RandomStringUtils.randomAlphabetic(10) + "@yandex.ru").toLowerCase();
        final String password = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        return new User(email, password, name);
    }

    @Step("Make user without name")
    public static User getUserWithoutName() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, null);
    }

    @Step("Make user without password")
    public static User getUserWithoutPassword() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, null, name);
    }

    @Step("Make user without mail")
    public static User getUserWithoutEmail() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(null, password, name);
    }

}