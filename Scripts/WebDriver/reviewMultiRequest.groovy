import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def reqId = args[3]
def action = args[4]
def reviewComment = args[5]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	driver.findElement(By.id("PendingReviews")).click()
	WebDriverWait wait = new WebDriverWait(driver, 60);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class = 'fieldHint']"), 'rows meeting filter criteria'));
	
	//select request by ID
	driver.findElement(By.linkText("${reqId}")).click()
	
	//supply commit
	txtArea = libComn.waitForElementWithId(driver,"NewReviewComment");
	Thread.sleep(5000)
	txtArea.sendKeys(reviewComment);
	//Select all or specific request should be approved
	if (args.size()==6){
		//Select accounts for approval (all)
	    driver.findElement(By.id("cb_SelectAll")).click()
	}else{
	   sysToReview = args[6]
	   accntToReview = args[7]
	   driver.findElement(By.xpath("//tr[td = '$accntToReview' and td = '$sysToReview']/*/input")).click();
	}
	
	//send review comment
	if (action == 'comment'){
		driver.findElement(By.id("NewReviewCommentButton")).click()
	}else if (action == "complete"){
	    driver.findElement(By.id("CompleteReviewButton")).click()
	}
	else{
		println("Action ${action} is unknown.");
	}
	libComn.waitForResult("Review Comment for Release Request $reqId Saved Successfully", driver)
}catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "review_multi_request")
	println("Catched: trouble to find element: " + toutex);
}
catch (NoSuchElementException nosuchex){
	libComn.takeScreenshot(driver, "review_multi_request")
	println("Catched: trouble to find element: " + nosuchex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}