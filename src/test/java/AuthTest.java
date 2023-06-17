import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogIn() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        open("http://localhost:9999");
        $("[data-test-id= 'login'] input").setValue(validUser.getLogin());
        $("[data-test-id= 'password'] input").setValue(validUser.getPassword());
        $("[type = 'button']").click();
        $("[id= 'root']").shouldBe(Condition.visible).shouldHave(Condition.text("Личный кабинет"));
    }
    @Test
    void shouldNotLogInIfBlocked(){
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        open("http://localhost:9999");
        $("[data-test-id= 'login'] input").setValue(validUser.getLogin());
        $("[data-test-id= 'password'] input").setValue(validUser.getPassword());
        $("[type = 'button']").click();
        $("[data-test-id= 'error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }
    @Test
    void shouldNotLogInIfNewUser() {
        var user = DataGenerator.Registration.generateUser("active");
        open("http://localhost:9999");
        $("[data-test-id= 'login'] input").setValue(user.getLogin());
        $("[data-test-id= 'password'] input").setValue(user.getPassword());
        $("[type = 'button']").click();
        $("[data-test-id= 'error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
    @Test
    void shouldNotLogInIfWrongLogin() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        var user = DataGenerator.Registration.generateUser("active");
        open("http://localhost:9999");
        $("[data-test-id= 'login'] input").setValue(user.getLogin());
        $("[data-test-id= 'password'] input").setValue(validUser.getPassword());
        $("[type = 'button']").click();
        $("[data-test-id= 'error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
    @Test
    void shouldNotLogInIfWrongPassword() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        var user = DataGenerator.Registration.generateUser("active");
        open("http://localhost:9999");
        $("[data-test-id= 'login'] input").setValue(validUser.getLogin());
        $("[data-test-id= 'password'] input").setValue(user.getPassword());
        $("[type = 'button']").click();
        $("[data-test-id= 'error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}
