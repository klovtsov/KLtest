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
def approveNote = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//go to requests pending approval
	driver.findElement(By.linkText("Password Requests Pending Approval")).click()
	//wait
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("reportTab"), 'rows meeting filter criteria'));
	
	//select request by ID
	driver.findElement(By.xpath("//td[contains(text(),'$reqId')]")).click()
	//Specify details
	driver.findElement(By.id("Details")).click()
	//supply approve Response
	txtArea = libComn.waitForElementWithId(driver,"ResponseComment");
	txtArea.sendKeys(approveNote);
	//Select all or specific request should be approved
	if (args.size()==5){
	    //Select accounts for approval (all)
	    driver.findElement(By.id("cb_SelectAll")).click()
	}else{
	     sysToApprove = args[5]
	     accntToApprove = args[6]
		 driver.findElement(By.xpath("//tr[td = '$accntToApprove' and td = '$sysToApprove']/*/input")).click();
	}
	//send response
	driver.findElement(By.id("ApproveReq")).click()
	libComn.waitForResult("was submitted successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "approve_multi_request")
	println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}