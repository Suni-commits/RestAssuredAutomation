package GSON_Demo;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GSON_Serilization {

    RequestSpecification Rs;
    Response r;
    ValidatableResponse vr;

    @Test
    void test_createBooking_serilization(){

        Booking booking=new Booking();

        booking.setFirstname("Pranathi");
        booking.setLastname("Mohan");
        booking.setTotalprice(292);
        booking.setDepositpaid(true);

        Bookingdates bookingdates=new Bookingdates();

        bookingdates.setCheckin("2024-04-04");
        bookingdates.setCheckout("2024-11-08");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast & Lunch");

        Gson gson=new Gson();
        String createbooking=gson.toJson(booking);

        Rs= RestAssured.given();
        Rs.contentType("application/json");
        Rs.baseUri("https://restful-booker.herokuapp.com/");
        Rs.basePath("booking");
        Rs.body(booking).log().all();
        r=Rs.when().post();
            String responseS=r.asString();
            vr= r.then().log().all();

            Booking_response_deSerilization bookingresponse=gson.fromJson(responseS,Booking_response_deSerilization.class);

            assertThat(bookingresponse.getBookingid()).isNotNull().isNotZero();
            assertThat(bookingresponse.getBooking().getFirstname()).isEqualTo("Pranathi");
            assertThat(bookingresponse.getBooking().getLastname()).isEqualTo("Mohan");
            assertThat(bookingresponse.getBooking().getTotalprice()).isNotZero().isPositive().isEqualTo(292);
            assertThat(bookingresponse.getBooking().getDepositpaid()).isTrue();
            assertThat(bookingresponse.getBooking().getBookingdates().getCheckin()).isNotBlank().isEqualTo("2024-04-04");


        Assert.assertEquals(bookingresponse.getBooking().getBookingdates().getCheckout(),"2024-11-08");



    }


}
