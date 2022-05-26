package wallethub;

import org.openqa.selenium.By;
import static wallethub.PreRequirements.softAssert;
import static wallethub.PreRequirements.driver;
import org.testng.Reporter;

public class ReviewConfirmationPage {
	
	/*
	Method Name:reviewConfirmation()
	Method Description:To verify whether the user receives a confirmation on successful submission of review
	Input Parameters:Nil
	Return Parameters:Nil
	*/	
	public void reviewConfirmation() {
		
		String confirmationText1 = driver.findElement(By.xpath("//h2")).getText();
		String confirmationText2 = driver.findElement(By.xpath("//h4")).getText();
		if (confirmationText1.equals("Awesome!") && confirmationText2.equals("Your review has been posted."))
		{
			Reporter.log("The below message is displayed \n"+ confirmationText2+"\n"+confirmationText2);
			driver.findElement(By.className("btn rvc-continue-btn")).click();
		}
		else {
			softAssert.fail("Confirmation screen was not displayed");
		}
			
		
	}
}

