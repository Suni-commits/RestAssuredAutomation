package JsonresponseExamples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DifftypesofJSONexamples {

    @Test (priority=1)
    void getBooks() {
        given()
                .contentType("application/json")
                .when()
                .get("http://localhost:3000/store")
                .then()
                .statusCode(200)
                .log().headers()
                .log().body()
                .body("books[0].title",equalTo("The Great Adventure"));


    }
//Approach 2
    @Test(priority = 2)
    void getBooks1()
    {
   Response res=     given()
                .contentType("application/json")
                .when()
                .get("http://localhost:3000/store");
        Assert.assertEquals(res.statusCode(),200);
        Assert.assertEquals(res.header("Content-Type"), "application/json");
                 String bookname = res.jsonPath().get("books[0].title").toString();
                 Assert.assertEquals(bookname,"The Great Adventure");
    }

    //Approach 3
@Test(priority = 3)
    void getBooks2(){
        Response res=     given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store");
        JSONObject jo=new JSONObject(res.asString());//converting response to JSON type

        for(int i=0;i<jo.getJSONArray("books").length();i++)
        {
            String bookTitle=
            jo.getJSONArray("books").getJSONObject(i).get("title").toString();

            System.out.println(bookTitle);
        }

    }
    @Test (priority=4)
    void getBooks3()
    {
        Response res=     given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store");
        JSONObject jo=new JSONObject(res.asString());//converting response to JSON type

        boolean status=false;
        for(int i=0;i<jo.getJSONArray("books").length();i++)
        {
            String bookTitle= jo.getJSONArray("books").getJSONObject(i).get("title").toString();
            if(bookTitle.equals("Learn JavaScript")){
                status = true;
                break;
            }

        }
        Assert.assertEquals(status,true);
    }

    @Test (priority = 5)
    void getBooks4(){
        Response res=     given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store");
        JSONObject jo=new JSONObject(res.asString());//converting response to JSON type

        boolean status=false;
        for(int i=0;i<jo.getJSONArray("books").length();i++)
        {
            String bookTitle= jo.getJSONArray("books").getJSONObject(i).get("title").toString();
            if(bookTitle.equals("Learn JavaScript")){
                status = true;
                break;
            }

        }
        Assert.assertEquals(status,true);

        //validating total price
        double totalprice=0;
        for(int i=0;i<jo.getJSONArray("books").length();i++)
        {
            String bookprice= jo.getJSONArray("books").getJSONObject(i).get("price").toString();
            totalprice= totalprice+Double.parseDouble(bookprice);
        }
        System.out.println("totalprice" + totalprice);
        Assert.assertEquals(totalprice,58.97);

    }

}