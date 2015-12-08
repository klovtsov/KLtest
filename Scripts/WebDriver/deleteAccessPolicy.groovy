import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def policyName = args[3]

successDeletionResult = 'Access Policy successfully deleted.'

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def selectUser(driver,manageUsersPage,policyToSelect){
	driver.get(manageUsersPage)
	policyNameField =libComn.waitForElementWithId(driver,'filtPolicyName')
	policyNameField.sendKeys(policyToSelect)
	driver.findElement(By.id("Listing")).click();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	//select policy
	driver.findElement(By.xpath("//tr[td = '$policyToSelect']")).click();
	driver.findElement(By.id("Details")).click();
}

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	//go to users tab
	managePolicyPage = "https://" + TPAMaddress + "/tpam/ManageAccessPolicies.asp"
	selectUser(driver,managePolicyPage,policyName)
	btnDeletePolicy = libComn.waitForElementWithId(driver,'DeleteButton')
	//make policy inactive
	driver.findElement(By.id('cbxActiveFl')).click();
	driver.findElement(By.id('SubmitChangesButton')).click()
	libComn.waitForResult('Access Policy saved successfully', driver)
	
	if(btnDeletePolicy.enabled){
	    btnDeletePolicy.click();
		alert = driver.switchTo().alert()
		alert.accept()
		libComn.waitForResult(successDeletionResult, driver)
	}else{
	    println("Something went wrong: Delete Policy is disabled.")
	}
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "DeleteUserError")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
