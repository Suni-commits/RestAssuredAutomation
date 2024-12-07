package ChainingPractice.Example004;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GetStudent {

    RequestSpecification rs;
    Response r;

    @Test
    void test_getStudent(ITestContext context){

        String id=(String)context.getSuite().getAttribute("student_id");

        rs= RestAssured.given();
        rs.contentType("application/json");
       // rs.body(js.toString());
        r= rs.when().get("http://localhost:3000/students/"+id);
        r.then().extract().response();
        r.then().statusCode(200).log().all();
       String firstName= r.jsonPath().getString("firstName");

        assertThat(firstName).isNotNull().isNotBlank();

    }

}
