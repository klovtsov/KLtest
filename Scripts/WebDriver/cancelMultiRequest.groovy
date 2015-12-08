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
def cancelReason = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	driver.findElement(By.id('CurrentRequests')).click()
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	//select request by ID
	driver.findElement(By.linkText("${reqId}")).click()
	
	//supply cancel Reason
	txtArea = libComn.waitForElementWithId(driver,"ResponseComment");
	Thread.sleep(500)
	txtArea.sendKeys(cancelReason);
	//Select all or specific request should be approved
	if (args.size()==5){
	    //Select accounts for approval (all)
	    driver.findElement(By.id("cb_SelectAll")).click()
	}else{
	    sysToCancel = args[5]
	    accntToCancel = args[6]
	    driver.findElement(By.xpath("//td[div = '$sysToCancel' and div = '$accntToCancel']/../td/input")).click();
	}
	//send response
	driver.findElement(By.id("SubmitChanges")).click()
	libComn.waitForResult("was submitted successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "cancel_multi_request")
	println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}