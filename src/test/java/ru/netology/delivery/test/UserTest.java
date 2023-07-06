package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.User;
import ru.netology.delivery.data.UserGenerator;
import java.time.Duration;


import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class UserTest {
    @BeforeEach
    void shouldOpenWebApp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldValidUser() {
        User user = UserGenerator.regUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(user.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe( Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBlockedUser() {
        User user = UserGenerator.regUser("blocked");
        $("[data-test-id=login] [class = input__control]").setValue(user.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidLoginUser() {
        User user = UserGenerator.regUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(UserGenerator.userLogin());
        $("[data-test-id=password] [class = input__control]").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidPasswordUser() {
        User user = UserGenerator.regUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(user.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(UserGenerator.userPas());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

}
