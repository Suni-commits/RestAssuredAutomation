package ChainingPractice.Example004;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteStudent {
    RequestSpecification rs;
    Response r;


    @Test
    void test_deleteStudent(ITestContext context) {

        String id = (String) context.getSuite().getAttribute("student_id");

        rs = RestAssured.given();
        rs.contentType("application/json");
        // rs.body(js.toString());
        r = rs.when().delete("http://localhost:3000/students/" + id);
        r.then().statusCode(200);
    }
}
