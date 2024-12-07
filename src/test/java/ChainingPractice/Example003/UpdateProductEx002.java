package ChainingPractice.Example003;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class UpdateProductEx002 {
    RequestSpecification rs;
    Response r;
    @Description("TC_03 :updation of product id" )
    @Test
    void test_updateUser001(ITestContext itc) {
        String id=(String)itc.getSuite().getAttribute("product_Id");
        Faker fake = new Faker();

        // Prepare the request data
        JSONObject data = new JSONObject();
        data.put("title", fake.commerce().productName());
        data.put("price", fake.commerce().price());
        data.put("description", fake.lorem().sentence());
        data.put("categoryId", fake.number().numberBetween(1, 10));

        // Add the images field (Example: List of URLs for images)
        JSONArray images = new JSONArray();
        images.put("https://example.com/image1.jpg");
        images.put("https://example.com/image2.jpg");
        data.put("images", images);

        // Set up the request specification
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.body(data.toString());
        rs.pathParam("id",id);

        // Send the POST request and capture the response
        r = rs.when().put("https://api.escuelajs.co/api/v1/products/{id}");
        r.then().statusCode(200);



    }
}
