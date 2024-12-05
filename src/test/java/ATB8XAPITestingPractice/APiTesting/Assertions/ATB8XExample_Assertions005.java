package ATB8XAPITestingPractice.APiTesting.Assertions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ATB8XExample_Assertions005 {

    RequestSpecification rs;
    Response r;
    ValidatableResponse Vr;
    Integer categoryId;  // Change this from String to Integer

    public Integer getCategoryId() {
        // JSON payload for the request
        String payload = "{\n" +
                "  \"name\": \"Mobile Phones\",\n" +
                "  \"image\": \"https://placeimg.com/640/480/any\"\n" +
                "}";

        // Create the request specification
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload);  // Attach the payload to the request

        // Send the POST request and store the response
        r = rs.when().post("https://api.escuelajs.co/api/v1/categories/");

        // Validate the response status code
        r.then().statusCode(201).log().body(); // Expecting 201 Created status code

        // Extract the 'id' from the response (Integer type)
        categoryId = r.then().extract().path("id");

        // Log and return the categoryId
        System.out.println("Created Category ID: " + categoryId);
        return categoryId;
    }

    @Test(priority = 1)
    void test_createCategory() {
        // Call getCategoryId() to create a category and get its ID
        categoryId = getCategoryId();
        // Output the categoryId to the console
        System.out.println("Category ID from test: " + categoryId);

       String name= r.then().extract().body().path("name");
        categoryId=r.then().extract().body().path("id");

       assertThat(name).isNotNull().isEqualTo("Mobile Phones");
       assertThat(categoryId).isPositive().isNotNull().isNotZero();
    }

    @Test(priority = 3)
    void test_getCategories(){
        categoryId = getCategoryId();
        rs = RestAssured.given();
        rs.contentType("application/json");
        // Send the get request
        r = rs.when().get("https://api.escuelajs.co/api/v1/categories/"+categoryId);

        // Validate the response status code
        r.then().statusCode(200).log().body();
        String name=r.then().extract().path("name");
        String creationAt=r.then().extract().path("creationAt");
        String updatedAt=r.then().extract().path("updatedAt");

        // TestNg assertions

        assertThat(name).isNotNull().isNotEmpty().isNotBlank();
        assertThat(creationAt).isNotBlank().isNotEmpty().isNotNull();
        assertThat(updatedAt).isNotBlank().isNotEmpty().isNotNull();
    }

    @Test(priority = 2)
    void test_updateCategory(){
        categoryId = getCategoryId();
        String payload = "{\n" +
                "  \"name\": \"Cell Store\",\n" +
                "  \"image\": \"https://placeimg.com/640/480/any\"\n" +
                "}";
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload);
        r = rs.when().put("https://api.escuelajs.co/api/v1/categories/"+categoryId);

        // Validate the response status code
        r.then().statusCode(200).log().body();
        String name=r.then().extract().path("name");
        // aaserts
        Assert.assertNotNull(name);
        Assert.assertEquals(name,"Cell Store");

    }

    @Test(priority = 4)
    void test_deleteCategory(){
        categoryId = getCategoryId();
        rs = RestAssured.given();
        rs.contentType("application/json");
        // Send the put request
        r = rs.when().delete("https://api.escuelajs.co/api/v1/categories/"+categoryId);

        // Validate the response status code
        r.then().statusCode(200).log().body();
    }

}
