/*
 * This script creates an user template. This template is based on an existing user.
 */
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit
import java.util.List

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def exampleUser = args[3]
def templateName = args[4]

def retainGroupMembership = args.length >= 6 ? args[5].toBoolean() : false;
def retainPermissions = args.length == 7 ? args[6].toBoolean() : false;

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def successResult = 'User added successfully'
def alertText = 'You must change the Template Name when creating Template from an existing User.'

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	libPpm.ChooseUser(driver, TPAMaddress, exampleUser)
	//click Template tab
	driver.findElement(By.id('Template')).click();
	libComn.waitForElementWithId(driver, 'cbxTemplateFl').click();
	//Retain Group Membership
	if(retainGroupMembership){
		driver.findElement(By.id('cbxRetainGroups')).click();
	}
	//Retain Collection Membership
	if(retainPermissions){
		driver.findElement(By.id('cbxRetainPermissions')).click();
	}
	//Set template name
	driver.findElement(By.id('Details')).click();
	Thread.sleep(500)
	libComn.isAlertPresent(driver,alertText)
	txtTemplName = libComn.waitForElementWithId(driver, 'UserName')
	txtTemplName.clear();
	txtTemplName.sendKeys(templateName)
	//Save template
	driver.findElement(By.id('SubmitChanges')).click();
	libComn.waitForResult(successResult, driver)
}catch (TimeoutException toutex){
    //If there is a problem to create new user
    useradderror = "Timed out after 10 seconds waiting for text ('User added successfully') to be present";
    if(toutex.message.contains(useradderror)){
		user_add_result = driver.findElement(By.id("ResultsDiv")).getText();
		println("Can't add user due to: $user_add_result")	
	}else{
	    libComn.takeScreenshot(driver, "CreateUserTemplate")
	    println("Catched: trouble to find element: " + toutex);
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}