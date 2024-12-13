package ChainingPractice.Example001;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class GetProductxample {
    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    @Test
    void test_getProduct(ITestContext context){
        String id = (String)context.getSuite().getAttribute("product_id");

        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.pathParam("id", id);
        r = rs.when().get("https://api.escuelajs.co/api/v1/products/{id}",id);
        vr = r.then().statusCode(200).log().all();
    }


}
