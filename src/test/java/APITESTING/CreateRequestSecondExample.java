package APITESTING;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class CreateRequestSecondExample {

    //Creating request using "org.json"
    @Test
    void createUser2()
    {
        JSONObject jo=new JSONObject();
        jo.put("firstName", "Pinky");
        jo.put("lastName","Twinky");
        jo.put("age",29);
        jo.put("email","test2@test.com");

        String[] coursesarr={"C++","Python"};
        jo.put("courses",coursesarr);

        given()
                .contentType("application/json")
                .body(jo.toString())
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("firstName",equalTo("Pinky"))
                .body("lastName",equalTo("Twinky"))
                .body("age",equalTo(29))
                .body("email",equalTo("test2@test.com"))
                .body("courses[0]",equalTo("C++"))
                .body("courses[1]",equalTo("Python"))
                .log().all();

    }
    @Test
    void deleteUser2()
    {
        given()

                .when()
                .delete("http://localhost:3000/students/3")
                .then()
                .statusCode(200);
    }

}
