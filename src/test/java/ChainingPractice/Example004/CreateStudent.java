package ChainingPractice.Example004;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class CreateStudent {
    RequestSpecification rs;
    Response r;

    @Test
    void test_createStudent(ITestContext context){

       Faker fake=new Faker();

        JSONObject js=new JSONObject();
        js.put("firstName",fake.name().firstName());
        js.put("lastName",fake.name().lastName());
        js.put("age",fake.number().randomDigit());
        js.put("email",fake.internet().emailAddress());

        // Creating a JSONArray for courses
        JSONArray arr = new JSONArray();
        arr.put("Mathematics");
        arr.put("Chemistry");
        arr.put("Physics");  // You can add as many random courses as needed

        // Adding the courses JSONArray to the student data JSONObject
        js.put("courses", arr);

        rs= RestAssured.given();
        rs.contentType("application/json");
        rs.body(js.toString());
       r= rs.when().post("http://localhost:3000/students");
       r.then().statusCode(201).log().all();
       String id= r.jsonPath().getString("id");

      // System.out.println(id);


        context.getSuite().setAttribute("student_id",id);


    }

}
