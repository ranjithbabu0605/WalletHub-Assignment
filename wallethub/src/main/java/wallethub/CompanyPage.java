package wallethub;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import static wallethub.PreRequirements.driver;
import static wallethub.PreRequirements.softAssert;
import org.testng.Reporter;

public class CompanyPage {
	
	
	static String CompanyUrl = Inputs.getString("CompanyUrl"); //$NON-NLS-1$
	static int starRating = Integer.parseInt(Inputs.getString("StarRating")); //$NON-NLS-1$
	static String dropdownOption = Inputs.getString("Policy"); //$NON-NLS-1$
	static String review = Inputs.getString("Review");  //$NON-NLS-1$
	 
	/*
	Method Name:verifyReview(String reviewId)
	Method Description:To verify the review entered in the company profile page
	Input Parameters:The review id received from profile page
	Return Parameters:Nil
	*/
	public void verifyReview(String reviewId) {
		
		String reviewSection ="//article[@data-rvid='"+reviewId+"']"; //$NON-NLS-1$ //$NON-NLS-2$
		driver.get(Inputs.getString("CompanyUrl")); //$NON-NLS-1$
		System.out.println(reviewSection);
		
		//To verify that the review is available in the company profile
		if (driver.findElement(By.xpath(reviewSection+"//meta[@itemprop='name']")).getAttribute("content").equals("Your Review")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		{
			Reporter.log("Your review in available"); //$NON-NLS-1$
			//To verify correct star rating is available
			if (Integer.valueOf(driver.findElement(By.xpath(reviewSection+"//meta[@itemprop='ratingValue']")).getAttribute("content"))==starRating) //$NON-NLS-1$ //$NON-NLS-2$
				Reporter.log("Correct star rating is selected"); //$NON-NLS-1$
			else
				softAssert.fail("Correct no of starts is not available"); //$NON-NLS-1$
			//To verify that the correct review is displayed
			if (driver.findElement(By.xpath(reviewSection+"//div[@itemprop='description']")).getText().equals(review)) //$NON-NLS-1$
				Reporter.log("The correct review is posted"); //$NON-NLS-1$
			else
				softAssert.fail("The correct review is not posted"); //$NON-NLS-1$
			//To verify that the correct product is selected
			if (driver.findElement(By.xpath(reviewSection+"//div[@class='rvtab-ci-category']/span")).getText().endsWith(dropdownOption)) //$NON-NLS-1$
				Reporter.log("The correct category is selected"); //$NON-NLS-1$
			else
				softAssert.fail("The correct category is not selected"); //$NON-NLS-1$
		}
		else {
			Reporter.log("Your review in not available"); //$NON-NLS-1$
			softAssert.fail();
		}
		
	}

	/*
	Method Name:starRating()
	Method Description:To select the correct star rating in the company page
	Input Parameters:Nil
	Return Parameters:Nil
	*/
	public void starRating(){
		
		if (starRating <=5)
		{
			String stars;
			driver.get(Inputs.getString("CompanyUrl")); //$NON-NLS-1$
			stars = "//review-star[@class='rvs-svg']//*[local-name()='svg' and @aria-label='"+starRating+" star rating']"; //$NON-NLS-1$ //$NON-NLS-2$
			Actions mouseAction = new Actions(driver);
			mouseAction.moveToElement(driver.findElement(By.xpath(stars))).perform();
			boolean highlight = driver.findElement(By.xpath("//review-star[@class='rvs-svg']//*[local-name()='svg' and @aria-label='"+starRating+" star rating']")).getAttribute("aria-checked").contains("true");// and ='true']")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			if (highlight)
			{
				Reporter.log("The star in "+starRating+" position is highlighted"); //$NON-NLS-1$ //$NON-NLS-2$
				mouseAction.click().perform();
			}
			else
			{
				softAssert.fail("Star rating is not highlghted"); //$NON-NLS-1$
				Reporter.log("The star in "+starRating+" position is not highlighted"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			mouseAction.click().perform();
		}
		else{
			softAssert.fail("Invalid star rating"); //$NON-NLS-1$
			Reporter.log("Please enter a star rating from 1-5"); //$NON-NLS-1$
		}
	}
	
	/*
	Method Name:selectdropdown()
	Method Description:To select the policy from the policy dropdown
	Input Parameters:Nil
	Return Parameters:Nil
	*/
	public void selectdropdown() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.findElement(By.className("wrev-drp")).click(); //$NON-NLS-1$
		WebElement dropdown = driver.findElement(By.xpath("//ng-dropdown[@class='wrev-drp']//ul"));  //$NON-NLS-1$
		Thread.sleep(2000);
		if (dropdown.isDisplayed()==true)
		{
			Reporter.log("Dropdown is available"); //$NON-NLS-1$
			List<WebElement> dropdownList= driver.findElements(By.xpath("//ng-dropdown[@class='wrev-drp']//ul/li"));
			for(int i=0;i<dropdownList.size();i++)
			{
				
				if(dropdownList.get(i).getText().equals(dropdownOption))
				{
					Reporter.log(dropdownList.get(i).getText());
					dropdownList.get(i).click();
					break;
				}
				else if(i==dropdownList.size()-1 &&  (!dropdownList.get(i).getText().equals(dropdownOption)))
				{
					Reporter.log(dropdownList.get(i).getText());
					softAssert.fail("Dropdown failure");
					Reporter.log("The \""+dropdownOption+"\" option is not available in the above dropdownlist"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				else 
				{
					Reporter.log(dropdownList.get(i).getText());
				}
			}
		}
		else 
		{
			softAssert.fail("Dropdown failure"); //$NON-NLS-1$
			Reporter.log("Dropdown not available"); //$NON-NLS-1$
		}
	}
	/*
	Method Name:writeReview()
	Method Description:To write the users review in the review section and click submit button
	Input Parameters:Nil
	Return Parameters:Nil
	*/
	public void writeReview() throws InterruptedException {
		
		driver.findElement(By.xpath("//textarea[@placeholder = 'Write your review...']")).sendKeys(review); //$NON-NLS-1$
		String charCounter = driver.findElement(By.className("wrev-user-input-count")).getText(); //$NON-NLS-1$
		//I have used inpage validation to check the minimum characters allowed instead of 200
		int minCount = Integer.parseInt(charCounter.split(" ")[0]); //$NON-NLS-1$
		int enteredChars = Integer.parseInt(driver.findElement(By.xpath("//div[@class='wrev-user-input-count']/span")).getText()); //$NON-NLS-1$
		Reporter.log("The minimum no of character required is"+minCount); //$NON-NLS-1$
		
		if(minCount > enteredChars)
		{
			driver.findElement(By.xpath("//div[text()=' Submit ']")).click(); //$NON-NLS-1$
			//to verify if error message is displayed
			WebElement error = driver.findElement(By.xpath("//div[text()='Please add at least "+minCount+" characters']")); //$NON-NLS-1$ //$NON-NLS-2$
			if (error.isDisplayed()==true)
			{
				Reporter.log("Error message\""+error.getText()+"\" is displayed"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		else if(minCount <= enteredChars)
		{
			driver.findElement(By.xpath("//div[text()=' Submit ']")).click(); //$NON-NLS-1$
			Thread.sleep(3000);
			if(driver.getTitle().equals("WalletHub - Review Confirmation")) //$NON-NLS-1$
			{
				Reporter.log("Confirmation Screen is opened"); //$NON-NLS-1$
			}
			else 
			{
				softAssert.fail("Confirmation Screen is not opened"); //$NON-NLS-1$
				Reporter.log("You did not receive a confirmation for your review"); //$NON-NLS-1$
			}
		}
	}
	
}

