package Tests.demo;


import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;


public class HTTPReqResponse {

   String id;
    @Test(priority=3,dependsOnMethods = {"createUsers"})
    void getUser(){

        given()
                .contentType("application/json")
                .pathParam("id",id)
                .when()
                .get("https://reqres.in/api/users/{id}")
                .then()
                .statusCode(200).log().all();
    }
@Test(priority =1)
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
                .statusCode(201).log().all().extract().response().path("id");

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
                .pathParam("id",id)
                .when()
                .put("https://reqres.in/api/users/{id}");

        // Assert status code and check the response body directly
        response.then()
                .statusCode(200)
                .body("name", equalTo("vishnu"))
                .body("job", equalTo("teacher"));

        // Extract id from the response using jsonPath()
        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");

        //assert J

        assertThat(name).isEqualTo("vishnu");
        assertThat(job).isEqualTo("teacher").isNotBlank();



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
