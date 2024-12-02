package ATB8XAPitesting;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostReqBddandnonBddstyle {

    @Description("TC_01 - Verify the token is getting created using BDD style")
    @Test
    void createToken_bddStyle()
    {
        String payload="{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .log().all();

    }
    @Description("TC_02 - Verify the token is getting created using Non BDD style")
    @Test
    void createToken_nonBddStyle()
    {
        String payload="{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        RequestSpecification r= RestAssured.given();
        r.contentType("application/json").body(payload);
        r.when().post("https://restful-booker.herokuapp.com/auth");
        r.then().statusCode(200).log().body();

    }
}
