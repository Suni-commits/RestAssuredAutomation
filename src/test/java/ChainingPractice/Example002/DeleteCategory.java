package ChainingPractice.Example002;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteCategory {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;

    @Description("TC_04 : Verify the category details are deleting successfully")
    @Test
    void test_deleteCategory(ITestContext context){
        String id=(String)context.getSuite().getAttribute("categoryId");
        rs= RestAssured.given();
        rs.contentType("application/json");
        rs.pathParam("id",id);
        // rs.body(data.toString());
        r= rs.when().delete("https://api.escuelajs.co/api/v1/categories/{id}");
        r.then().statusCode(200).log().all();

    }

}
