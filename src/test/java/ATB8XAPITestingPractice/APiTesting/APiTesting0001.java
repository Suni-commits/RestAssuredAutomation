package ATB8XAPITestingPractice.APiTesting;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class APiTesting0001 {
// Test case integration
    //Create a token
    // Create a booking
    //perform PUT request
    // Verify that PUT is success by GET request
    // Delete the ID
    // verify the ID after deletion by GET Request

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;
    String token;
    String bookingId;


    public String getToken(){
        String payload="{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
       rs=RestAssured.given();
       rs.baseUri("https://restful-booker.herokuapp.com");
       rs.basePath("/auth");
       rs.contentType("application/json");
       rs.body(payload);
       r=rs.when().post();
       vr=r.then().statusCode(200);
       token=r.jsonPath().getString("token");
       System.out.println(token);
       return token;
    }
    public String getBookingId() {
        String payload="{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        rs=RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/booking");
        rs.contentType("application/json");
        rs.body(payload);
        r=rs.when().post();
        vr=r.then().statusCode(200);
        bookingId=r.jsonPath().getString("bookingid");
        System.out.println(bookingId);

        return bookingId;
    }


@Description("TC_01: Verify the token  is getting created with valid data")
   @Test(priority = 1)
    void test_createToken_nonBDDstyle()
    {
    String token=getToken();
    System.out.println(token);

    }
    @Description("TC_02: Verify the booking id  is getting created with valid json data")
    @Test(priority = 2)
    void test_CreateBookingID()
    {
        String bookingId=getBookingId();
        System.out.println(bookingId);
    }

    @Description("TC_03: Verify the booking details are updated after providing booking id and token number")
    @Test(priority = 3)
    public void test_UpdateBookingDetails()
    {
        String token=getToken();
        System.out.println(token);

        String bookingId=getBookingId();
        System.out.println(bookingId);

        String payload="{\n" +
                "    \"firstname\" : \"Rambo\",\n" +
                "    \"lastname\" : \"Jambo\",\n" +
                "    \"totalprice\" : 121,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        rs=RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com/");
        rs.basePath("/booking/"+bookingId);
        rs.cookie("token",token);
        rs.contentType("application/json");
        rs.body(payload).log().all();
        r=rs.when().put();
        vr=r.then().log().all().statusCode(200);
    }
    @Description("TC_04: verify the updated booking details are displaying correctly")
    @Test(priority = 4)
    void test_getUpdatedBookingDetails(){

        rs=RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com/booking/"+bookingId);

        rs.contentType("application/json");
        r=rs.when().get();
        vr=r.then().log().all().statusCode(200);
    }
    @Description("TC_05: verify the updated booking details are deleted successfully")
    @Test(priority = 5)
    void test_deleteUpdatedBookingDetails(){

        System.out.println(bookingId);
        System.out.println(token);
        rs=RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com/booking/"+bookingId);

        rs.contentType("application/json");
        rs.cookie("token",token);
        r=rs.when().delete();
        vr=r.then().log().all().statusCode(201);
    }

}
