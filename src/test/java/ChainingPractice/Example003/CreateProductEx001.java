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

public class CreateProductEx001 {
    RequestSpecification rs;
    Response r;
@Description("TC_01 :Creation of product id" )
    @Test
    void test_createUser001(ITestContext itc) {
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

        // Send the POST request and capture the response
        r = rs.when().post("https://api.escuelajs.co/api/v1/products/");
        r.then().statusCode(201).log().all();

        // Extract the product id from the response using jsonPath
        String productId = r.jsonPath().getString("id");

        // Print the extracted product id
        System.out.println("Created Product ID: " + productId);

        itc.getSuite().setAttribute("product_Id",productId);
    }
}

