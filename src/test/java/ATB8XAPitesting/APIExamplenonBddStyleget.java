package ATB8XAPitesting;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.specification.*;
import org.testng.annotations.Test;

public class APIExamplenonBddStyleget {
    RequestSpecification r= RestAssured.given();

    @Description("TC_01-Verify the pin code with valid data")
    @Test
    void getpincode_nonbddstyle_positive()
    {
         r.baseUri("https://api.zippopotam.us/");
         r.basePath("/IN/110001");
         r.when().get();
         r.then().statusCode(200).log().body();

    }
    @Description("TC_02-Verify the pin code with invalid data")
    @Test
    void getpincode_nonbddstyle_negative()
    {
        r.baseUri("https://api.zippopotam.us/");
        r.basePath("/IN/-1");
        r.when().get();
        r.then().statusCode(200).log().body();

    }

}
