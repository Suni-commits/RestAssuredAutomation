package APITESTING;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateRequestFirstexample {

    //Creating request using "Hashmap"
String id;
Response r;
ValidatableResponse vr;

    RequestSpecification rs;
    @Description("Verify the creation of user using Hashmap")
    @Test
    void createUser1()
    {
        HashMap hm=new HashMap();
        hm.put("firstName", "Sweety");
        hm.put("lastName","Fruity");
        hm.put("age",27);
        hm.put("email","test1@test.com");

        String[] coursesarr={"Computer Science","Mathematics"};
        hm.put("courses",coursesarr);

       RestAssured.given();
                rs.contentType("application/json");
                rs.body(hm);
               r= rs.when().post("http://localhost:3000/students");
               vr=r.then().statusCode(201).log().all();
               id=r.jsonPath().getString("id");
               System.out.println(id);

    }
@Test
    void deleteUser1()
    {

        given()

                .when()
                .delete("http://localhost:3000/students/"+id)
                .then()
                .statusCode(200);
    }

}
