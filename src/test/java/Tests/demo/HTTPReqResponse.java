package Tests.demo;


import org.testng.annotations.Test;
import java.util.*;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
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

       given()
               .contentType("application/json")
               .body(hm)
               .when()
                     .put("https://reqres.in/api/users"+id)
               .then()
                   .statusCode(200)
                     .log().all();


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
