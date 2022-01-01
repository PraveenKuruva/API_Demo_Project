package files;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.response.*;
import pojo.AddPlace;
import pojo.Location;

public class SerilizeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setName("Kpk praveen");
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		p.setTypes(mylist);
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		
		
		
		
	Response res=	given().queryParam("key ", "qaclick123")
			.body(p).log().all()	
			.when()
			.post("/maps/api/place/add/json")
			.then()
			.assertThat().statusCode(200).extract().response();
	
	String responseString = res.asString();
	System.out.println("responseString: "+responseString);

	}

}
