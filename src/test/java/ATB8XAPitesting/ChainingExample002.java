package ATB8XAPitesting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChainingExample002 {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    String token = "79abd5bc3a2374a20c5275751f8a593640a0a4a0e81edbccd4789bf677cd0dac";
    String userId; // Class-level variable to store the created user ID

    @BeforeClass
    void setUp() {
        // Create a user once before the tests start and reuse the ID
        userId = getId();
    }

    public String getId() {
        String payload = "{\n" +
                "    \"name\": \"Tenali Rameshsss\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"rameshtesting112@test.com\",\n" +
                "    \"status\": \"active\"\n" +
                "}";
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.header("Authorization", "Bearer " + token);
        rs.body(payload);
        r = rs.when().post("https://gorest.co.in/public/v2/users");
        vr = r.then().statusCode(201).log().body();  // Logs the response body for debugging
        String userId = r.jsonPath().getString("id");
        System.out.println("Created User ID: " + userId);
        return userId;
    }

    @Test(priority = 1)
    void test_CreateUser() {
       // String userId=getId();
        System.out.println("User created with ID: " + userId);
        // Assert that userId is not null or empty
        assert userId != null && !userId.isEmpty() : "User creation failed";
    }

    @Test(priority = 2,dependsOnMethods = {"test_CreateUser"})
    void test_getCreatedUserdetails() {
       // String userId=getId();
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.header("Authorization", "Bearer " + token);
        r = rs.when().get("https://gorest.co.in/public/v2/users/" + userId);
        vr = r.then().statusCode(200).log().body();
    }

    @Test(priority = 3,dependsOnMethods = {"test_CreateUser"})
    void test_UpdateUserDetails() {
      //  String userId=getId();
        String payload = "{\n" +
                "    \"name\": \"Pawan Kalyan\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"email\": \"pavan.kalyan1@test.com\",\n" +
                "    \"status\": \"active\"\n" +
                "}";
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.header("Authorization", "Bearer " + token);
        rs.body(payload);
        r = rs.when().put("https://gorest.co.in/public/v2/users/" + userId);
        vr = r.then().statusCode(200).log().body();
    }

    @Test(priority = 4,dependsOnMethods = {"test_CreateUser"})
    void test_deleteUserdetails() {
      // String userId=getId();
        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.header("Authorization", "Bearer " + token);
        r = rs.when().delete("https://gorest.co.in/public/v2/users/" + userId);
        vr = r.then().statusCode(204).log().body();
    }
}
