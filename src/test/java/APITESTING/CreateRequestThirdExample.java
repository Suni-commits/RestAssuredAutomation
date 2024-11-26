package APITESTING;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;  // Import Jackson's ObjectMapper
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateRequestThirdExample {
    //Creating request using "POJO"
    @Test(priority = 1)
    void createUser3() throws Exception {  // Add throws Exception for serialization
        pojoClass pc = new pojoClass();
        pc.setFirstName("Rajani");
        pc.setLastName("Rama");
        pc.setAge(26);
        pc.setEmail("teee@test.com");
        String[] coursesarr = {"Computer", "Chemistry"};
        pc.setCourses(coursesarr);

        // Serialize POJO to JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(pc);

        given()
                .contentType("application/json")
                .body(jsonBody)  // Use the serialized JSON string here
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("Rajani"))
                .body("lastName", equalTo("Rama"))
                .body("age", equalTo(26))
                .body("email", equalTo("teee@test.com"))
                .body("courses[0]", equalTo("Computer"))
                .body("courses[1]", equalTo("Chemistry"))
                .header("content-type", "application/json")
                .log().all();
    }

      @Test(priority = 2)
    void deleteUser3() {
        given()
                .when()
                .delete("http://localhost:3000/students/8")
                .then()
                .statusCode(200);
    }
}
