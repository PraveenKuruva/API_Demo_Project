
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.ReUsableMethods;

public class AddPlace {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Hello World !");
		// Validate if add place api is working as expected
		
		//Add place - update place with the new address - get place to validate if the new address is present in response
		
		//Given: all inputs details
		//when: Submit the API
		//then: Validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response =  given().log().all().queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
			.body(new String(Files.readAllBytes(Paths.get("D:\\Rest_API_Practice\\AddPlace.json"))))
			.when().log().all().post("maps/api/place/add/json")
			.then().log().all().assertThat().statusCode(200)
			.body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)")
			.extract().response().asString(); 
		
		
		JsonPath js = new JsonPath(response);
		String place_id = js.getString("place_id");
		
		String newAddress = "70 winter walk, USA";
		
		given().log().all().queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().log().all().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
	String getPlace =	given().log().all().queryParam("key", "qaclick123")
			.queryParam("place_id", place_id)
		.when().log().all().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString(); 
		
		//JsonPath js1 = new JsonPath(getPlace);
	    JsonPath js1 =	ReUsableMethods.rawToJson(getPlace);
	    
		String actualAddress = js1.getString("address");
		
		Assert.assertEquals(actualAddress, newAddress);
		
		
		
		
		
		
		
		
		
		
		
		

	}

}

