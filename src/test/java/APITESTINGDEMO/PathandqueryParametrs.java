package APITESTINGDEMO;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PathandqueryParametrs {

    @Test
    void getUser()
    {

        given()
                .pathParam("pathparam","users")
                .queryParam("page",2)
                .queryParam("id",8)
                .when()
                .get("https://reqres.in/api/{pathparam}")
                .then()
                .statusCode(200)
                .log().all();

    }
}
