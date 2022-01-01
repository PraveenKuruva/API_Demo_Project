package files;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		String[] courseTitles = { "Selenium Webdriver Java", "Cypress" ,"Protractor"};
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//To get the Authorization code we have to use selenium here
//		System.setProperty("webdriver.chrome.driver","C://chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		
//		driver.findElement(By.xpath("//div[@class='BHzsHc']")).click();
//		Thread.sleep(3000);
//		
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("praveenkumarkuruba@gmail.com");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("143@praveen");
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		
//		String url = driver.getCurrentUrl();
		
		// GET = https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php 
		
		// After Entering the above url in the chrome then it will ask username and password after that you will get the below url which we have passed in url string
		
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWi-xlNU4x_k7t9Lx4-tXM7t06tqh47kJrarZ8i6Q8dN_eTchH9T5zQFAzpL70LEFA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		
		
		String particiacode = url.split("code=")[1];
		String code = particiacode.split("&scope")[0];
		
		System.out.println("Code is:"+ code);
			
		
		//to get the access token
		String accessTokenResponse = given().urlEncodingEnabled(false)
				.queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = 	js.getString("access_token");
		
		
		// get the course details
		GetCourse gc =  given().log().all().queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println("Instructor: "+gc.getInstructor());
		System.out.println("LinkedIn link: "+gc.getLinkedIn());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		 List<Api> apiCourses =  gc.getCourses().getApi();
		
		for (int i=0; i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
			
		}
		
		
		//get the courses of webAutomation
		ArrayList<String> actual = new ArrayList<String>();
				
		 List<WebAutomation> w = gc.getCourses().getWebAutomation();
		 
		 for(int j=0;j<w.size();j++) {
			 actual.add(w.get(j).getCourseTitle());
		 }
		
		List<String>  expectedList =  Arrays.asList(courseTitles);
		
		Assert.assertTrue(actual.equals(expectedList));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
