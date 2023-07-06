package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.ContentType;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserGenerator {
    private UserGenerator() {
    }
    private static Faker faker = new Faker(new Locale("en"));;


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(io.restassured.http.ContentType.JSON)
            .setContentType(io.restassured.http.ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static void newUser(User registration) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registration) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String userLogin () {
        return faker.name().username();
    }

    public static String userPas() {
        return faker.internet().password();
    }

    public static User regUser(String status) {
        String login = faker.name().username();;
        String password = faker.internet().password();
        User registration = new User(login, password, status);
        newUser(registration);
        return registration;
    }
}