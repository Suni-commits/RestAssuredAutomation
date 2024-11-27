package JsonresponseExamples;

import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;  // Import the JsonSchemaValidator class

import static io.restassured.RestAssured.given;

public class Jsonschemavalidateexample {

    @Test
    void jsoSchemaValidation() {
        given()
                .when()
                .get("http://localhost:3000/store")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("bookstoreschema.json"));
    }
}
