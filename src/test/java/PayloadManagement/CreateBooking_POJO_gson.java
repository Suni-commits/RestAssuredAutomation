package PayloadManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateBooking_POJO_gson {

    RequestSpecification rs;
    Response r;
    ValidatableResponse vr;
    String bookingid;
    String token;  // Declare the bookingid and token at the class level.

    @BeforeClass
    void setup() {
        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com/");
        rs.basePath("booking");
        rs.contentType("application/json");
    }

    @Test(priority=1)
    void test_createToken() {
        // Set up the JSON body for authentication request
        String tokenPayload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        // Send the authentication request and get the response
        r = rs.body(tokenPayload).when().post("https://restful-booker.herokuapp.com/auth");

        // Verify the status code
        r.then().statusCode(200).log().all();

        // Extract the token from the response
        token = r.jsonPath().getString("token");
        System.out.println("Creation of token: " + token);
    }

    @Test(dependsOnMethods = "test_createToken",priority=2)
    void test_POSTBooking() {
        // Prepare the booking details
        Booking booking = new Booking();
        booking.setFirstname("Srujana");
        booking.setLastname("Chowdhary");
        booking.setTotalprice(2002);
        booking.setDepositpaid(true);

        Bookingdates Bd = new Bookingdates();
        Bd.setCheckin("2024-01-02");
        Bd.setCheckout("2024-02-02");
        booking.setBookingdates(Bd);
        booking.setAdditionalneeds("Dinner & Lunch");

        // Send the booking request
        rs.body(booking).log().all();
        r = rs.when().post("");  // Use the base path to automatically append the endpoint

        // Extract bookingid from the response
        bookingid = r.jsonPath().getString("bookingid");

        // Verify that the bookingid is returned in the response
        vr = r.then().statusCode(200).log().all();
        vr.body("bookingid", Matchers.not(Matchers.isEmptyOrNullString()));

        System.out.println("Booking ID: " + bookingid);
    }

    @Test(dependsOnMethods = "test_POSTBooking",priority=4)
    void test_getbookingId() {
        // Use the bookingid from the previous test
        rs.pathParam("bookingid", bookingid);

        // Get the booking details
        r = rs.when().get("booking/{bookingid}");

        // Verify the response
       r.then().log().all();
    }

    @Test(priority=3)
    void test_putbookingdetails() {
        // Prepare the updated booking details
        Booking booking = new Booking();
        booking.setFirstname("Narendra");
        booking.setLastname("Modi");
        booking.setTotalprice(20032);
        booking.setDepositpaid(true);

        Bookingdates Bd = new Bookingdates();
        Bd.setCheckin("2024-03-02");
        Bd.setCheckout("2024-04-02");
        booking.setBookingdates(Bd);
        booking.setAdditionalneeds("Dinner & Lunch");

        // Send the PUT request to update the booking
        rs.body(booking).log().all();
        rs.pathParam("bookingid", bookingid);
        rs.cookie("token", token);
         r = rs.when().put("booking/{bookingid}");

        // Verify the response
        r.then().log().all();
    }

    @Test(dependsOnMethods = "test_POSTBooking")
    void test_deletebooking() {
        // Send the DELETE request to remove the booking
        rs.pathParam("bookingid", bookingid);
        rs.cookie("token", token);
        r = rs.when().delete("https://restful-booker.herokuapp.com/booking/{bookingid}");

        // Verify the response
        r.then().statusCode(201).log().all(); // Status code 201 is returned when the resource is deleted
    }
}
