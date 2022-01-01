import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		
		int sum=0;
		JsonPath js = new JsonPath(Payload.CoursePrice());
		int courseCount = js.getInt("courses.size()");
		
		for(int i=0; i<courseCount; i++) {
			int prices = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			
			int amount = prices * copies;
			System.out.println("Amount: "+ amount);
			
			sum = sum+amount;
			System.out.println("sum: "+ sum);
			}
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		
		Assert.assertEquals(sum ,purchaseAmount);
			
		
		
	}
}
