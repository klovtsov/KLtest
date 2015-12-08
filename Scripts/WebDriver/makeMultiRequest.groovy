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
def requestFilter = args[3]
def requestNotes = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//click on add request link
	driver.findElement(By.linkText("Add Password Request")).click()
	//set filter for accounts
	accntField =libComn.waitForElementWithId(driver,"AccountNm")
	accntField.sendKeys(requestFilter)
	driver.findElement(By.id("Accounts")).click();
	accntsTable = libComn.waitForElementWithId(driver,"accountsTable");
	//Select all accounts for request
	List<WebElement> chkboxes = driver.findElements(By.xpath('//table[@id="accountsTable"]//input[@class="ResetCheckbox"]'))
	chkboxes.each {it.click()};
	//Specify details
	driver.findElement(By.id("Details")).click()
	//set request reason
	txtArea = libComn.waitForElementWithId(driver,"RequestReason");
	txtArea.sendKeys(requestNotes);
	//send request
	driver.findElement(By.id("SubmitChanges")).click()
	libComn.waitForResult("has been submitted", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}catch (TimeoutException toutex){
    libComn.takeScreenshot(driver, "make_multi_request")
    println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}