import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit
import java.util.List

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def reqId = args[3]
def systemName = args[4]
def accntName = args[5]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//find request where new account should be added
	driver.findElement(By.id('CurrentRequests')).click()
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	driver.findElement(By.linkText(reqId)).click()
	//add new account
	btnNewAccnt =libComn.waitForElementWithId(driver,"AddAccountsButton2")
	btnNewAccnt.click();
	//set filter for accounts
	sysField =libComn.waitForElementWithId(driver,"SystemNm")
	sysField.sendKeys(systemName)
	//set filter for accounts
	accntField =libComn.waitForElementWithId(driver,"AccountNm")
	accntField.sendKeys(accntName)
	driver.findElement(By.id("Accounts")).click();
	//open Accounts tab
	accntsTable = libComn.waitForElementWithId(driver,"accountsTable");
	//only one account expected, so check first checkbox
	driver.findElement(By.xpath('//table[@id="accountsTable"]//input[@class="ResetCheckbox"]')).click();
	//go to Details tab
	driver.findElement(By.id("Details")).click()
	//Save 
	btnSaveChanges = libComn.waitForElementWithId(driver,"SubmitChanges");
	btnSaveChanges.click();
	libComn.waitForResult("has been submitted", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
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