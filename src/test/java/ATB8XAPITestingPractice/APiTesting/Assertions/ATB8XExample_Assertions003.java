package ATB8XAPITestingPractice.APiTesting.Assertions;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ATB8XExample_Assertions003 {

    RequestSpecification Rs;
    Response r;
    ValidatableResponse Vr;
    String bookingId;
    String tokenId;

    @BeforeTest
    public void setUp(){

        String bookingId=getBookingId();
        String tokenId=getTokenId();
    }

    public String getBookingId() {
        String payload="{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Carlon\",\n" +
                "    \"totalprice\" : 1111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2021-01-01\",\n" +
                "        \"checkout\" : \"2024-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";
        Rs=RestAssured.given();
        Rs.contentType("application/json");
        Rs.body(payload);
        r = Rs.when().post("https://restful-booker.herokuapp.com/booking");
        Vr=r.then().statusCode(200).log().body();
        bookingId=r.jsonPath().getString("bookingid");
        System.out.println(bookingId);
        return bookingId;
    }

    public  String getTokenId() {
        String payload="{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        Rs=RestAssured.given();
        Rs.contentType("application/json");
        Rs.body(payload);
        r = Rs.when().post("https://restful-booker.herokuapp.com/auth");
        Vr=r.then().statusCode(200).log().body();
        tokenId=r.jsonPath().getString("token");
        System.out.println(tokenId);
        return tokenId;
    }
   @Description("TC_01 : verifying token id creation" )
    @Test(priority = 1)
   void test_createTokenid(){
        System.out.println(tokenId);

        //assertion using Assert J
       assertThat(tokenId).isNotEmpty().isNotBlank().isAlphanumeric();

   }

   @Description("TC_02: verifying creation of booking id ")
   @Test(priority = 2)
   void test_createBookingId(){
        System.out.println(bookingId);

        // Assertions
       assertThat(bookingId).isNotEmpty().isNotBlank().isNotNull();
       assertThat(bookingId).isNotNull();

   }
  @Description("TC_03 : Update booking details")
@Test(priority =3)
   void test_updatebookingid() {

           String payload = "{\n" +
                   "    \"firstname\" : \"Ronald\",\n" +
                   "    \"lastname\" : \"Ross\",\n" +
                   "    \"totalprice\" : 1221,\n" +
                   "    \"depositpaid\" : true,\n" +
                   "    \"bookingdates\" : {\n" +
                   "        \"checkin\" : \"2021-01-01\",\n" +
                   "        \"checkout\" : \"2024-01-01\"\n" +
                   "    },\n" +
                   "    \"additionalneeds\" : \"Dinner\"\n" +
                   "}";
           Rs = RestAssured.given();
           Rs.contentType("application/json");
           Rs.cookie("token",tokenId);
           Rs.body(payload);
           r = Rs.when().put("https://restful-booker.herokuapp.com/booking/" + bookingId);
           Vr = r.then().statusCode(200).log().body();

      String firstname = r.jsonPath().getString("firstname");
      String lastname = r.jsonPath().getString("lastname");
      boolean depositPaid = r.jsonPath().getBoolean("depositpaid");
      int totalprice = r.jsonPath().getInt("totalprice");
      String checkin=r.jsonPath().getString("bookingdates.checkin");
      String checkout=r.jsonPath().getString("bookingdates.checkout");


      //Assert J

      assertThat(firstname).isEqualTo("Ronald").isNotNull().isNotNull();
      assertThat(lastname).isEqualTo("Ross").isNotNull().isNotNull();
      assertThat(depositPaid).isNotNull();
      assertThat(totalprice).isEqualTo(1221).isNotNull().isPositive();
      assertThat(checkin).isNotNull().isNotEmpty();
      assertThat(checkout).isNotNull().isNotEmpty();



      // Perform assertions to verify the updated details
      Assert.assertEquals(firstname, "Ronald");
      Assert.assertEquals(lastname, "Ross");
      Assert.assertTrue(depositPaid);
      Assert.assertEquals(totalprice, 1221);
      Assert.assertEquals(checkin,"2021-01-01");
      Assert.assertEquals(checkout,"2024-01-01");

    }
    @Description("TC_04 : Verify booking details  after update")
    @Test(priority=4)
void test_getUpdatedBookingdetails(){
        Rs = RestAssured.given();
        Rs.contentType("application/json");
       // Rs.cookie("tokenId");
       // Rs.body(payload);
        r = Rs.when().get("https://restful-booker.herokuapp.com/booking/" + bookingId);
        Vr = r.then().statusCode(200).log().all();


        boolean depositPaid=r.then().extract().equals("depositPaid");


        Vr.body("firstname",Matchers.equalTo("Ronald"));
        Vr.body("lastname",Matchers.equalTo("Ross"));
        Vr.body("totalprice",Matchers.equalTo(1221));

            // assert j

        assertThat(depositPaid).isNotNull();


}

@Description("TC_05: Verifying booking details  are deleted successfully")
@Test(priority=5)
void test_deletebookingdetails(){
    Rs = RestAssured.given();
    Rs.contentType("application/json");
    Rs.cookie("token",tokenId);
    //Rs.body(payload);
    r = Rs.when().delete("https://restful-booker.herokuapp.com/booking/" + bookingId);
    Vr = r.then().statusCode(201).log().all();
    Vr.extract().response().getHeaders();

}

}
