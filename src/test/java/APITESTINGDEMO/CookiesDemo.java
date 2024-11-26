package APITESTINGDEMO;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import java.util.*;

public class CookiesDemo {


@Test
    void testCookies()
    {
        given()

                .when()
                .get("https://www.google.com/")
                .then()
                .log().all();

    }

    @Test
    void getCookiesinfo()
    {
       Response res= given()

                .when()
                .get("https://www.google.com/");

        // get single cookie information

     /*  String cookie_value = res.getCookie("AEC");
    System.out.println("Cookie information ----> "+ cookie_value); */

        Map<String,String> cookie_values=res.getCookies();

        System.out.println("cookies keys:  " + cookie_values.keySet());

        for(String s:cookie_values.keySet())
        {
            String cookie_value = res.getCookie(s);
            System.out.println(s +"         " +cookie_value);
        }


    }


}
