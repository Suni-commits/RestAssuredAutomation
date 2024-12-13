package ChainingPractice.Example001;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class CreateProductExample{

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    @Test
    void test_createProduct(ITestContext context){
        String payload = "{\n" +
                "  \"title\": \"Electronic Gadgets\",\n" +
                "  \"price\": 50,\n" +
                "  \"description\": \"A Upgradable\",\n" +
                "  \"categoryId\": 1,\n" +
                "  \"images\": [\"https://placeimg.com/640/480/any\"]\n" +
                "}";
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload);
        r = rs.when().post("https://api.escuelajs.co/api/v1/products/");
        vr = r.then().statusCode(201);

        String id = r.jsonPath().getString("id");
        System.out.println(id);

        context.getSuite().setAttribute("product_id", id);
    }



}