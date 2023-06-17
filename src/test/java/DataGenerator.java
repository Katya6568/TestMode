import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import com.google.gson.Gson;

import com.github.javafaker.Faker;

import java.util.Locale;

import lombok.Data;
import lombok.Value;

import static io.restassured.RestAssured.given;
@Data
public class DataGenerator {
    private DataGenerator() {
    }
    // спецификация нужна для того, чтобы переиспользовать настройки в разных запросах

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static Faker faker = new Faker(new Locale("en"));


    public static void sendRequest(UserInfo user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(user)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String generateLogin(){
        return faker.name().username();
    }
    public static String generatePassword(){
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }
        public static UserInfo generateUser(String status) {
        return new UserInfo(generateLogin(), generatePassword(), status);
    }

        public static UserInfo getRegisteredUser (String status) {
        UserInfo registeredUser = generateUser(status);
        sendRequest(registeredUser);
        return  registeredUser;
        }
    }
        @Value
        public static class UserInfo {
            String login;
            String password;
            String status;
        }
}

