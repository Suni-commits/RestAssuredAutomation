package APITESTING;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateRequest4thExample {

    // create request using external json file
    @Test(priority = 1)
void createUser4() throws FileNotFoundException {
    File f=new File(".//body.json");
    FileReader fr=new FileReader(f);

        JSONTokener jt=new JSONTokener(fr);
        JSONObject jo=new JSONObject(jt);


    given()
            .contentType("application/json")
            .body(jo.toString())
            .when()
            .post("http://localhost:3000/students")
            .then()
            .statusCode(201)
            .body("firstName",equalTo("Ragini"))
            .body("lastName",equalTo("Ramdan"))
            .body("email",equalTo("teees@test.com"))
            .body("courses[0]",equalTo("Automation"))
            .body("courses[1]",equalTo("Python"))
            .log().all();

}
@Test(priority = 2)
void deleteUser4()
{
    given()

            .when()
            .delete("http://localhost:3000/students/5")
            .then()
            .statusCode(200)
            .log().all();
}

}
