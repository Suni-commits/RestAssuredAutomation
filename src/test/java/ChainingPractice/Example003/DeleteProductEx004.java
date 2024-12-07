package ChainingPractice.Example003;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class DeleteProductEx004 {

    RequestSpecification rs;
    Response r;

    @Description("TC_04: deletion of product ")
    @Test
    void test_deleteProduct001(ITestContext itc){
        String id=(String)itc.getSuite().getAttribute("product_Id");
        rs = RestAssured.given();
        rs.contentType("application/json");
        // rs.body(data.toString());
        rs.pathParam("id",id);

        // Send the POST request and capture the response
        r = rs.when().delete("https://api.escuelajs.co/api/v1/products/{id}");
        r.then().statusCode(200).extract().response();
    }
}
