import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;

public class CardOrderTestV1 {

    @BeforeAll
    static void setUp() {
        Configuration.headless = true;
    }

    Faker faker = new Faker(new Locale("en"));

    @Test
    void shouldReturnActiveUser() {
        RegistrationByCardInfo info = DataGenerator.regActiveUser();
        open("http://localhost:9999/");
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login'] .button__content").click();
        $(byText("Личный кабинет")).shouldBe(visible, ofSeconds(15));
    }

    @Test
    void shouldReturnBlockedUser() {
        RegistrationByCardInfo info = DataGenerator.regBlockedUser();
        open("http://localhost:9999/");
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login'] .button__content").click();
        $(byText("Пользователь заблокирован")).shouldBe(visible, ofSeconds(15));
    }

    @Test
    void shouldReturnFailWithInvalidPassword() {
        RegistrationByCardInfo info = DataGenerator.regActiveUser();
        open("http://localhost:9999/");
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(faker.internet().password());
        $("[data-test-id='action-login'] .button__content").click();
        $(byText("Ошибка")).shouldBe(visible, ofSeconds(15));
    }

    @Test
    void shouldReturnFailWithInvalidLogin() {
        RegistrationByCardInfo info = DataGenerator.regActiveUser();
        open("http://localhost:9999/");
        $("[data-test-id='login'] input").setValue(faker.name().username());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login'] .button__content").click();
        $(byText("Ошибка")).shouldBe(visible, ofSeconds(15));
    }

    @Test
    void shouldReturnFailWithInvalidLoginAndPassword() {
        open("http://localhost:9999/");
        $("[data-test-id='login'] input").setValue(faker.name().username());
        $("[data-test-id='password'] input").setValue(faker.internet().password());
        $("[data-test-id='action-login'] .button__content").click();
        $(byText("Ошибка")).shouldBe(visible, ofSeconds(15));
    }

    @Test
    void shouldReturnFailWithEmptyForms() {
        open("http://localhost:9999/");
        $("[data-test-id='action-login'] .button__content").click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible, ofSeconds(15));
    }
}