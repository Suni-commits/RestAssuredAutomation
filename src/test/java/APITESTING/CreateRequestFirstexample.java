package APITESTING;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateRequestFirstexample {

    //Creating request using "Hashmap"

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

        given()
                .contentType("application/json")
                .body(hm)
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("firstName",equalTo("Sweety"))
                .body("lastName",equalTo("Fruity"))
                .body("age",equalTo(27))
                .body("email",equalTo("test1@test.com"))
                .body("courses[0]",equalTo("Computer Science"))
                .body("courses[1]",equalTo("Mathematics"))
                .log().all();

    }
@Test
    void deleteUser1()
    {

        given()

                .when()
                .delete("http://localhost:3000/students/3")
                .then()
                .statusCode(200);
    }

}
