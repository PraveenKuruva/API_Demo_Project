package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;
public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			RestAssured.baseURI = "http://localhost:8080";
			SessionFilter session = new SessionFilter();
			
			//Login scenario
			//relaxedHTTPSValiadation: In case if any https certificateIssue is there we can use this method 
			
			String response =  given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
				.body("{ \r\n"
						+ "	\"username\": \"praveenkumar\", \r\n"
						+ "	\"password\": \"143@praveen\" \r\n"
						+ "	\r\n"
						+ "}")
				.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
			
		String expectMessage = "Hi How are you";
			
			// Add the comment
	String addCommnetResponse =	given().pathParam("key", "10003").log().all().header("Content-Type","application/json")
			   .body("{\r\n"
			   		+ "	\"body\": \""+expectMessage+"\",\r\n"
			   		+ "       \"visibility\": {\r\n"
			   		+ "        \"type\": \"role\",\r\n"
			   		+ "        \"value\": \"Administrators\"\r\n"
			   		+ "      }\r\n"
			   		+ "}")
			   .log().all().filter(session)
			   .when()
			   .post("/rest/api/2/issue/{key}/comment")
			   .then().log().all() 
			   .assertThat().statusCode(201).extract().response().asString();
	JsonPath js = new JsonPath(addCommnetResponse);
		String commentId =js.getString("id");
			
			
			// add attachment to the comment
			given().header("X-Atlassian-Token","no-check")
			.filter(session)
			.pathParam("key", "10003").log().all()
			.multiPart("file",new File("Jira.txt")).when()
			.post("/rest/api/2/issue/{key}/attachments")
			.then().log().all().assertThat().statusCode(200);
			
			
			//get details
			String issueDetails = given().filter(session).pathParam("key", "10003")
					.queryParam("fields","comment")
					.log().all()
					.when().get("/rest/api/2/issue/{key}")
					.then().log().all().extract().response().asString();
			
			
			System.out.println("Issue Details: "+ issueDetails);
			
			JsonPath js1 = new JsonPath(issueDetails);
			int commentCount = js1.getInt("fields.comment.comments.size()");
			
			for(int i=0;i<commentCount;i++) {
				String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
				if(commentIdIssue.equalsIgnoreCase(commentId)) {
					String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println("Message: "+ message);
				Assert.assertEquals(message, expectMessage);
				
				}
			}
			
			
			
			
			
			
			   
			   
			   
			   
			   
			   
			   
	}

}
