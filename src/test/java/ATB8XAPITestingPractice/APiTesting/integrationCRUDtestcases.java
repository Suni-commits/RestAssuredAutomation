package ATB8XAPITestingPractice.APiTesting;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class integrationCRUDtestcases {
    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;
    String userId;

    @BeforeClass
    void setUp() {
        // Create a user once before the tests start and reuse the ID
        userId = getUserId();
    }

    public String getUserId() {

        String payload = "{\n" +
                "  \"name\": \"New Category\",\n" +
                "  \"image\": \"https://placeimg.com/640/480/any\"\n" +
                "}";
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload);
        r = rs.when().post("https://api.escuelajs.co/api/v1/categories/");
        vr = r.then().statusCode(201).log().body();  // Logs the response body for debugging
        String userId = r.jsonPath().getString("id");
        System.out.println("Created User ID: " + userId);
        return userId;
    }

    @Test(priority = 1)
    void test_CreateCategory(){
        System.out.println(userId);
    }

    @Test(priority = 2)
    void test_getCategory(){
        rs = RestAssured.given();
        rs.contentType("application/json");
        r = rs.when().get("https://api.escuelajs.co/api/v1/categories/"+userId);
        vr = r.then().statusCode(200).log().body();
    }

    @Test(priority = 3)
    void test_UpdateCategory(){
        String payload = "{\n" +
                "  \"name\": \"change titlees\",\n" +
                "  \"image\": \"https://placeimg.com/640/480/any\"\n" +
                "}";
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload);
        r = rs.when().put("https://api.escuelajs.co/api/v1/categories/"+userId);
        vr = r.then().statusCode(200).log().body();  // Logs the response body for debugging
    }

    @Test(priority = 4)
    void test_deleteCateory()
    {
        rs = RestAssured.given();
        rs.contentType("application/json");
        r = rs.when().delete("https://api.escuelajs.co/api/v1/categories/"+userId);
        vr = r.then().statusCode(200).log().body();

    }
}

