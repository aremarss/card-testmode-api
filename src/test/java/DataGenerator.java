import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void regUser(RegistrationByCardInfo regInfo) {
        given()
                .spec(requestSpec)
                .body(regInfo)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationByCardInfo regActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        RegistrationByCardInfo activeUser = new RegistrationByCardInfo(
                faker.name().username(),
                faker.internet().password(),
                "active");
        regUser(activeUser);
        return activeUser;
    }

    public static RegistrationByCardInfo regBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        RegistrationByCardInfo blockedUser = new RegistrationByCardInfo(
                faker.name().username(),
                faker.internet().password(),
                "blocked");
        regUser(blockedUser);
        return blockedUser;
    }
}