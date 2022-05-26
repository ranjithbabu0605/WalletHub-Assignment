package wallethub;

import org.openqa.selenium.By;
import org.testng.Reporter;
import static wallethub.PreRequirements.driver;
import static wallethub.PreRequirements.softAssert;

public class ProfilePage {
	public static String cmpanyPath = "//a[contains(@href,'"+Inputs.getString("CompanyUrl")+"')]";
	/*
	Method Name:verifyCompanyReview(String profileUrl)
	Method Description:To verify whether the review is available in the user's profile
	Input Parameters:The profile Url received on successful login
	Return Parameters:It return the Id value of the review if available else it returns a null value
	*/
	public String verifyCompanyReview(String profileUrl) {
		String reviewId=null;
		driver.get(profileUrl);
		if	(driver.findElements(By.xpath(cmpanyPath)).isEmpty()) {
			softAssert.fail();
			Reporter.log("Your review feed is not updated in your profile");
			reviewId=null;
		}
		else {
			Reporter.log("Your review feed is updated in your profile");
			reviewId = driver.findElement(By.xpath(cmpanyPath)).getAttribute("href").split("=")[1];
		}
		return reviewId;
	}
}