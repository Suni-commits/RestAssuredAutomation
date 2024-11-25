package Tests.demo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TestExample {

    @Test
    public void test_1(){
        Response response= get("https://reqres.in/api/users?page=2");
        System.out.println("Status code of the response body : "+response.getStatusCode());
        System.out.println("Response Body as follows : "+response.getBody().toString());
        System.out.println("Time taken to run the test: "+response.getTime());
        System.out.println("Content Type of the body : "+response.getContentType());

    }

    @Test
    void test_2(){

        baseURI ="https://reqres.in/api";
                given().get("/users?page=2")
        .then().statusCode(200);
    System.out.println("The base url is " +baseURI);
    }

    @Test
    void test_3(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

}
