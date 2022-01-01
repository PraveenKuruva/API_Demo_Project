import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		//Count the Courses in an array
		int courseCount = js.getInt("courses.size()");
		System.out.println("Courses count: "+ courseCount);
		
		//Print the purchase amount
		
		int totalPurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("purchaseAmount: "+ totalPurchaseAmount);
		
		//Print Title of the first course
		String courseTitle1 = js.get("courses[0].title");
		System.out.println("Title of the course: "+ courseTitle1);
		
		//Print All course titles and their respective Prices
		for(int i=0; i<courseCount; i++) {
			
			System.out.println("Courses --> "+ js.get("courses["+i+"].title").toString() + "  :: Prices: -->"+ js.get("courses["+i+"].price").toString() );
		}
		
		
		System.out.println("Print no of copies sold by Cypress Course");
		for(int i=0; i<courseCount; i++) {
			
			String title = js.get("courses["+i+"].title").toString();
			if(title.equalsIgnoreCase("Cypress")) {
				int copies = js.get("courses["+i+"].copies");
				System.out.println("Copies : "+copies);
				break;
				
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
