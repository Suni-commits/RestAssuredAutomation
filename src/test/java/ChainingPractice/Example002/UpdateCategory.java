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

public class UpdateCategory {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    @Description("TC_02 : Verify the category details are updating")
    @Test
    void test_updateCategory(ITestContext context){

        String id=(String)context.getSuite().getAttribute("categoryId");

        Faker fake=new Faker();
        JSONObject data=new JSONObject();

        data.put("name",fake.name());
        data.put("image","https://placeimg.com/640/480/any");

        rs= RestAssured.given();
        rs.contentType("application/json");
        rs.pathParam("id",id);
        rs.body(data.toString());
        r= rs.when().put("https://api.escuelajs.co/api/v1/categories/{id}");
        r.then().statusCode(200).log().all();
    }

}
