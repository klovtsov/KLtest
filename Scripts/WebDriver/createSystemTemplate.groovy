/*
 * This script creates a system template based on a system that is already exists.
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
def systemName = args[3]
def accntToTemplate = args[4]
def templateName = args[5]

def retainCollectnMembership = args.length >= 7 ? args[6].toBoolean() : false;
def retainPermissions = args.length == 8 ? args[7].toBoolean() : false;

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def successResult = "System changes for $templateName saved successfully"

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	libPpm.chooseSystem (driver, TPAMaddress, systemName)
	//click Template tab
	driver.findElement(By.id('Template')).click();
	libComn.waitForElementWithId(driver, 'cbxTemplateFl').click();
	driver.findElement(By.id('cbxRetainAccounts')).click();
	driver.findElement(By.xpath("//table[@id = 'tblRetainedAccounts']/*/tr[td='$accntToTemplate']/*/input")).click();
	//Retain Collection Membership
	if(retainCollectnMembership){
		driver.findElement(By.id('cbxRetainCollections')).click();
	}
	//Retain Collection Membership
	if(retainPermissions){
		driver.findElement(By.id('cbxRetainPermissions')).click();
	}
	//Set template name
	driver.findElement(By.id('Details')).click();
	txtTemplName = libComn.waitForElementWithId(driver, 'SystemName')
	txtTemplName.clear();
	txtTemplName.sendKeys(templateName)
	//Save template
	driver.findElement(By.id('bt_Save')).click();
	libComn.waitForResult(successResult, driver)
}catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "add_account_to_request")
	println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}