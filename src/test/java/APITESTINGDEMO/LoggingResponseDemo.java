package APITESTINGDEMO;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoggingResponseDemo {

    @Test
    void getLogging()
    {
        given()

                .when()
                .get("https://simple-books-api.glitch.me/books")
                .then()
                .log().headers()
                .log().body()
             //   .log().cookies()
                .statusCode(200);

    }
}
