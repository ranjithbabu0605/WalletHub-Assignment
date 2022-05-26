package wallethub;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ProjectReviewCompany extends PreRequirements{
	
	
	@Test
	public static void login() throws InterruptedException {
		
		LoginPage loginPage = new LoginPage();
		String profileUrl= loginPage.login();
		//To proceed only if login is successful
		Reporter.log("The User's profile Url is "+profileUrl);
		if(profileUrl!=null) {
			CompanyPage companyPage = new CompanyPage();
			companyPage.starRating();
			companyPage.selectdropdown();
			companyPage.writeReview();
			ProfilePage profilepage = new ProfilePage();
			String reviewId = profilepage.verifyCompanyReview(profileUrl);
			//To proceed only if a valid reviewId is retrieved from profile page
			if(reviewId!=null)
			{
				companyPage.verifyReview(reviewId);
			}
			
		}
		softAssert.assertAll();
	}

}
