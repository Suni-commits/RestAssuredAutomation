package ChainingPractice.Example004;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class UpdateStudent {

    RequestSpecification rs;
    Response r;

    @Test
    void test_updateStudent(ITestContext context) {
        String id = (String) context.getSuite().getAttribute("student_id");
        Faker fake = new Faker();

        JSONObject js = new JSONObject();
        js.put("firstName", fake.name().firstName());
        js.put("lastName", fake.name().lastName());
        js.put("age", fake.number().randomDigit());
        js.put("email", fake.internet().emailAddress());

        // Creating a JSONArray for courses
        JSONArray arr = new JSONArray();
        arr.put("Python");
        arr.put("Java");
        arr.put("Java Script");  // You can add as many random courses as needed

        // Adding the courses JSONArray to the student data JSONObject
        js.put("courses", arr);

        rs = RestAssured.given();
        rs.contentType("application/json");
        rs.pathParam("id",id);
        rs.body(js.toString());
        r = rs.when().patch("http://localhost:3000/students/{id}");
        r.then().log().all();


    }
}
