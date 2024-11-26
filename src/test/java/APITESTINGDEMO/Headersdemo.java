package APITESTINGDEMO;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Headersdemo {

    @Test(priority = 1)
    void testheader()
    {
        given()

                .when()
                .get("https://www.google.com/")
                .then()
                .header("Content-Type","text/html; charset=ISO-8859-1")
                .header("Content-Encoding","gzip")
                .header("Server","gws")
                .log().headers();

    }
@Test(priority = 2)
    void getsingleHeader()
    {
       Response res= given()

                .when()
                .get("https://www.google.com/");

       // get single header information

       String header_res= res.getHeader("Content-Type");
              System.out.println("Singlle header nformation i.e. Content-Type : " + header_res);

    }
    @Test(priority = 3)
    void getAllHeaders() {
        Response res = given()
                            .when()
                                     .get("https://www.google.com/");

        Headers allheaders = res.getHeaders();

        for(Header h:allheaders)
        {
            System.out.println(h.getName()+"     "+h.getValue());

        }



    }

}
