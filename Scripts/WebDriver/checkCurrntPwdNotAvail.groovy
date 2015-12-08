import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//open Password Management page
def TPAMaddress = args[0]
def user = args[1]
def userPwd = args[2]
def systemName = args[3]
def accntName = args[4]

def PwdShownPeriod = 20*1000

manageAcctPageFull = "https://" + TPAMaddress + "/tpam/ManageAccounts.asp"

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", user, userPwd)
	driver.get(manageAcctPageFull)
	//Set filter options
	systemField =libComn.waitForElementWithId(driver,"SystemNm")
	systemField.sendKeys(systemName);
	driver.findElement(By.id('AccountNm')).sendKeys(accntName);
	driver.findElement(By.id("Listing")).click();
	//choose account
	accTable =libComn.waitForElementWithId(driver,"AccountListing")
	driver.findElement(By.xpath("//table[@id='AccountListing']/tbody/tr[1]")).click()
	driver.findElement(By.id("Passwords")).click()
	//wait page load
	rbAllDates =libComn.waitForElementWithId(driver,'rb_PasswordDtAll')
	//Check Current Password tab
	if(!driver.findElements(By.xpath("//li[@class='tabberactive']/a[@id='CurrentPassword']")).isEmpty()){
		println('Error: Current password tab is enabled!')
	}
	rbAllDates.click();
	//Pull Past passwords
	driver.findElement(By.id('PastPasswords')).click()
	//wait page load
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@id='passwordList']/span"), 'Displaying'));
	driver.findElement(By.xpath("//table[@id='pastPasswordTable']/tbody/tr[1]")).click();
	driver.findElement(By.id('Password')).click();
	//Ensure that password page is opened and wait
	libComn.waitForTextPresent (driver, 'Surrounding "[" and "]" are not part of the password.', 20)
	Thread.sleep(PwdShownPeriod)
	pwdTable =libComn.waitForElementWithId(driver,'pastPasswordTable');
	//Check Current Password tab
	if(!driver.findElements(By.xpath("//li[@class='tabberactive']/a[@id='CurrentPassword']")).empty){
		println('Error: Current password tab is enabled!')
	}else{
	    println('Ok: Current password tab is disbled.')
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}