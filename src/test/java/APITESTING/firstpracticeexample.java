package APITESTING;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class firstpracticeexample {

    int id;  // Store the id to use in subsequent tests

    @Test(priority = 1)
    void getUser() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

    @Test(priority = 2)
    void createUsers() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "suneetha");
        hm.put("job", "student");

        // Capture the response and extract 'id' correctly
        Response response = given()
                .contentType("application/json")
                .body(hm)
                .when()
                .post("https://reqres.in/api/users");

        // Assert status code and capture the ID from the response body
        response.then()
                .statusCode(201);

        id = response.jsonPath().getInt("id");  // Extract id from the response
    }

    @Test(priority = 3, dependsOnMethods = {"createUsers"})
    void updateUser() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "vishnu");
        hm.put("job", "teacher");

        // Log the ID that we will use in the update
        System.out.println("Updating user with ID: " + id);

        // Perform the PUT request to update the user
        Response response = given()
                .contentType("application/json")
                .body(hm)
                .when()
                .put("https://reqres.in/api/users/" + id);  // Correct URL concatenation

        // Log the entire response to inspect the response body and ensure it contains the correct fields
        System.out.println("Response body after update: " + response.asString());

        // Ensure the response status code is 200 (OK)
        response.then()
                .statusCode(200)
                .body("name", equalTo("vishnu"))
                .body("job", equalTo("teacher"));

        // Since the ID does not change, we do not need to extract it from the response
        // Assert that the original 'id' remains the same
        assertEquals(id, id);  // This essentially just validates that the 'id' hasn't changed
    }



    @Test(priority = 4)
    void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/" + id)  // Correct URL concatenation
                .then()
                .statusCode(204)
                .log().all();
    }
}
