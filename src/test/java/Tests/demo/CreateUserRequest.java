package Tests.demo;
import java.util.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class CreateUserRequest {

    @Test
    void createUser(){
        HashMap hm=new HashMap();
        hm.put("name" , "Suneetha");
        hm.put("job", "Software Student");

        given()
                .contentType("application/json")
                .body(hm)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log().all();
    }
}
