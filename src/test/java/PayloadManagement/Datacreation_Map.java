package PayloadManagement;

import com.google.gson.internal.LinkedTreeMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.codehaus.groovy.util.ListHashMap;
import org.testng.annotations.Test;

import java.util.*;

public class Datacreation_Map {

    RequestSpecification Rs;
    Response r;

    @Test
    void test_Postbooking(){

        // Data creation using Map class

        HashMap<String,Object> map=new LinkedHashMap<>();

        map.put("firstname","Jasmine");
        map.put("lastname","Rise");
        map.put("totalprice",233);
        map.put("depositpaid",true);

         HashMap<String,Object> hm=new LinkedHashMap<>();
        hm.put("checkin","2021-01-01");
        hm.put("checkout","2023-01-01");
        map.put("bookingdates",hm);
        map.put("additionalneeds", "Lunch");

        Rs= RestAssured.given();
        Rs.contentType("application/json");
        Rs.body(map);
       r= Rs.when().post("https://restful-booker.herokuapp.com/booking");
        ValidatableResponse vr=r.then().statusCode(200).log().all();

    }
}
