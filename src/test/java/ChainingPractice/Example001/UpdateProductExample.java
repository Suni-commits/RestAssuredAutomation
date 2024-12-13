package ChainingPractice.Example001;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateProductExample {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    @Test
    void test_updateProduct(ITestContext context){
        String id = (String) context.getSuite().getAttribute("product_id");

        String payload = "{\n" +
                "  \"title\": \"Fruits\",\n" +
                "  \"price\": 40,\n" +
                "  \"description\": \"A fruits are healthy to body\",\n" +
                "  \"categoryId\": 1,\n" +
                "  \"images\": [\"https://placeimg.com/640/480/any\"]\n" +
                "}";

        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.pathParam("id", id);
        rs.body(payload);
        r = rs.when().put("https://api.escuelajs.co/api/v1/products/{id}");
        vr = r.then().statusCode(200).log().body();
    }

}
