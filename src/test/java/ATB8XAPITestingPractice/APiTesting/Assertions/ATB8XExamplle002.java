package ATB8XAPITestingPractice.APiTesting.Assertions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ATB8XExamplle002 {

    RequestSpecification Rs;
    Response r;
    ValidatableResponse Vr;
    String userId;

    @BeforeClass
    void setUp(){
         userId=getUserId();
    }

    public  String getUserId()
    {
        String payload="{\n" +
                "  \"title\": \"Comic Books\",\n" +
                "  \"price\": 100,\n" +
                "  \"description\": \"A description\",\n" +
                "  \"categoryId\": 1,\n" +
                "  \"images\": [\"https://placeimg.com/640/480/any\"]\n" +
                "}";

        Rs= RestAssured.given();
        Rs.contentType("application/json");
        Rs.body(payload);
        r= Rs.when().post("https://api.escuelajs.co/api/v1/products/");
        Vr=r.then().statusCode(201).log().all();
        userId =r.jsonPath().getString("id");
        System.out.println("Newly Created user "+ userId);

        return userId;
    }

    @Test(priority=1)
    void test_createUser(){
        System.out.println(userId);
    }

    @Test(priority=3)
    void test_getCreatedusers(){
        Rs= RestAssured.given();
        Rs.contentType("application/json");
        r= Rs.when().get("https://api.escuelajs.co/api/v1/products/"+userId);
        Vr=r.then().statusCode(200).log().all();
        String title=r.jsonPath().getString("title");
        int price=r.jsonPath().getInt("price");
        String description=r.jsonPath().getString("description");
        int category_id=r.jsonPath().getInt("category.id");
        String category_name=r.jsonPath().getString("category.name");

        // TestNg assertions
        Assert.assertEquals(title,"Horror & Books");
        Assert.assertEquals(price,102);
        Assert.assertEquals(description,"A horror");
        Assert.assertEquals(category_id,1);
        Assert.assertEquals(category_name,"Ropa");

    }

    @Test(priority=2)
    void test_updateProduct(){
        String payload="{\n" +
                "  \"title\": \"Horror & Books\",\n" +
                "  \"price\": 102,\n" +
                "  \"description\": \"A horror\",\n" +
                "  \"categoryId\": 1,\n" +
                "  \"images\": [\"https://placeimg.com/640/480/any\"]\n" +
                "}";

        Rs= RestAssured.given();
        Rs.contentType("application/json");
        Rs.body(payload);
        r= Rs.when().put("https://api.escuelajs.co/api/v1/products/"+userId);
        Vr=r.then().statusCode(200).log().all();

        // Validable response
       Vr.body("title",Matchers.equalTo("Horror & Books"));
       Vr.body("price",Matchers.equalTo(102));
        Vr.body("description",Matchers.equalTo("A horror"));
        Vr.body("category.id",Matchers.equalTo(1));

    }
@Test(priority = 4)
    void test_deleteProduct(){
    Rs= RestAssured.given();
    Rs.contentType("application/json");
    r= Rs.when().delete("https://api.escuelajs.co/api/v1/products/"+userId);
    Vr=r.then().statusCode(200).log().all();

}

}
