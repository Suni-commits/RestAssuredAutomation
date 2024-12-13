package ATB8XAPITestingPractice.APiTesting.Assertions;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

public class Example001 {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;
    Integer id;


    @BeforeClass
    static void setUp() {
        // Set base URI for the API
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Description("TC_01: verifying all products are getting displayed by using GET Method")
    @Test
    void test_getAllproducts() {
        rs = RestAssured.given();
        r = rs.when().get("/products");
        r.then().log().all();

        id=r.jsonPath().getInt("[0].id");
        Assert.assertEquals(r.getStatusCode(),200);
        Assert.assertEquals(r.getContentType(),"application/json; charset=utf-8");

    }

    @Description("TC_02: Verify the single id details")
    @Test
    void test_getSingleProductById(){

        rs = RestAssured.given();
        rs.pathParam("id",id);
        r = rs.when().get("/products/{id}");
        r.then().log().all();

        Assert.assertEquals(r.getStatusCode(),200);
        Assert.assertEquals(r.getContentType(),"application/json; charset=utf-8");

    }
    @Description("TC_03 : verifying the product details with invalid id")
    @Test
void test_getValuebyinvalidid(){
    int id=10001;
    rs = RestAssured.given();
    r = rs.when().get("/products/"+id);
    r.then().log().body().statusCode(400);

}

@Description("TC_04: Test pagination functionality (limit and offset)")
@Test
public void testPagination() {
   rs=RestAssured.given();
            rs.param("limit", 10);
           rs .param("offset", 0);
           r=rs.when()
            .get("/products");
           vr=r.then()
            .statusCode(200)
            .body("size()", equalTo(10));// Expecting 10 products in the response
String size=r.jsonPath().getString("size");

    // Assert J- assertions
        assertThat(size).isNotBlank().isNotEmpty();
    }

    @Description("TC_05: Verify response format is JSON")
    @Test
    public void testResponseFormat() {
        rs=RestAssured.given();
                r=rs.when()
                .get("/products");
               vr= r.then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8"); // Check if content type is JSON
    }

}
