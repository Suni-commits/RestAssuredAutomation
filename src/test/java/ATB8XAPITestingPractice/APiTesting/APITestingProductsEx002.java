package ATB8XAPITestingPractice.APiTesting;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITestingProductsEx002 {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;
    int productid;

    @Description("Tc01_Verify the all products are getting fetched and GET method is working fine")
@Test(priority = 1)
    void test_getAllProducts()
    {
        rs= RestAssured.given();
        rs.contentType("application/json");
        rs.when().get("https://api.escuelajs.co/api/v1/products");
        rs.then().statusCode(200).log().all();

    }

   public  int getProductid()
    {
        String payload="{\n" +
                "  \"title\": \"New Product\",\n" +
                "  \"price\": 10,\n" +
                "  \"description\": \"A description\",\n" +
                "  \"categoryId\": 1,\n" +
                "  \"images\": [\"https://placeimg.com/640/480/any\"]\n" +
                "}";

        rs= RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload);
        r= rs.when().post("https://api.escuelajs.co/api/v1/products");
        vr= r.then().statusCode(201).log().body();
        productid=r.jsonPath().getInt("id");

        System.out.println(productid);
        return productid;
    }

    @Description("Tc02_Verify the new product is created")
    @Test(priority = 2)
    void test_AddingProduct()
    {
        int productid=getProductid();
        System.out.print(productid);

    }
    @Description("TC03_Verify the update existing product details successfully")
    @Test(priority = 3)
    void test_UpdateProductdetails_put()
    {
        String payload="{\n" +
                "  \"title\": \"Change title\",\n" +
                "  \"price\": 100\n" +
                "}";
        rs= RestAssured.given();
        rs.contentType("application/json");
        rs.body(payload).log().all();
        r= rs.when().put("https://api.escuelajs.co/api/v1/products/"+productid);
        vr= r.then().statusCode(200).log().body();
    }

    @Description("TC03_Verify the update existing product details successfully")
    @Test(priority = 4)
    void test_deleteproductdetails()
    {

        rs= RestAssured.given();
        rs.contentType("application/json");
        r= rs.when().delete("https://api.escuelajs.co/api/v1/products/"+productid);
        vr= r.then().statusCode(200).log().body();
    }

}
