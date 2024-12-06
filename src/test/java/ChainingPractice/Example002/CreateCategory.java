package ChainingPractice.Example002;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class CreateCategory {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    @Description("TC_01 : Verify category id creation")
    @Test
    void test_createCategory(ITestContext context){

        Faker fake=new Faker();
        JSONObject data=new JSONObject();

        data.put("name",fake.name());
        data.put("image","https://placeimg.com/640/480/any");

        rs=RestAssured.given();
        rs.contentType("application/json");
        rs.body(data.toString());
       r= rs.when().post("https://api.escuelajs.co/api/v1/categories/");
       r.then().statusCode(201).log().all();
      String id= r.jsonPath().getString("id");

     // System.out.println(id);


    context.getSuite().setAttribute("categoryId",id);

    }

}
