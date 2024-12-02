package ATB8XAPitesting;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Examplle001 {
    // https://api.zippopotam.us/IN/110001

    @Description("TC01-Verify the pincode is available with valid data")
    @Test(priority = 1)
    void getPinCode_BDDstyle_positive()
    {
        //String pin_code="110001";
        given()
                .baseUri("https://api.zippopotam.us/")
                .basePath("/IN/110001")
                .when()
                .get()
                .then()
                .statusCode(200)
                .log().body()
                .log().headers();

    }
    @Description("TC02-Verify the pincode is available with invalid data")
    @Test(priority = 2)
    void getPinCode_BDDstyle_Negative()
    {
        //String pin_code="-1";
        given()
                .baseUri("https://api.zippopotam.us/")
                .basePath("/IN/-1")
                .when()

                .get()
                .then()
                .statusCode(404)
                .log().body()
                .log().headers();

    }

}
