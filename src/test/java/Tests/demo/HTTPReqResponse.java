package Tests.demo;


import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.*;

import static javax.swing.UIManager.getInt;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.assertEquals;

import java.net.URL;


public class HTTPReqResponse {

    int id;
    @Test(priority=1)
    void getUser(){
       given()
                .when()
                .get("https://reqres.in/api/users?page=2")

                .then()
                .statusCode(200)
                .body("page",equalTo(2))
                .log().all();
    }
@Test(priority = 2)
    void createUsers()
    {
        HashMap hm=new HashMap();
        hm.put("name" ,"suneetha");
        hm.put("job","student");

       id= given()
                .contentType("application/json")
                .body(hm)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
               .hashCode();

    }
@Test (priority = 3,dependsOnMethods = {"createUsers"})
    void updateUser()
    {
        HashMap hm=new HashMap();
        hm.put("name","vishnu");
        hm.put("job","teacher");

   /*    given()
               .contentType("application/json")
               .body(hm)
               .when()
                     .put("https://reqres.in/api/users"+id)
               .then()
                   .statusCode(200)
                     .body("name", equalTo("vishnu"))
                      .body("job", equalTo("teacher"));  */

        Response response = given()
                .contentType("application/json")
                .body(hm)
                .when()
                .put("https://reqres.in/api/users/" + id);

        // Assert status code and check the response body directly
        response.then()
                .statusCode(200)
                .body("name", equalTo("vishnu"))
                .body("job", equalTo("teacher"));

        // Extract id from the response using jsonPath()
        int updatedUserId = response.jsonPath().getInt("id");

        // Ensure that the user ID remains unchanged after the update
        assertEquals(String.valueOf(updatedUserId), id, "User ID should be unchanged after update");



    }
    @Test (priority = 4)
    void deleteUser()
    {
        given()

                .when()
                .delete("https://reqres.in/api/users"+id)
                .then()
                .statusCode(204)
                .log().all();

    }
}
