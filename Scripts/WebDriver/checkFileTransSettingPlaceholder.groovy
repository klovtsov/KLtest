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
def admin = args[1]
def adminPwd = args[2]
def systemName = args[3]
def placeHldr = args[4]
def platform = args[5]
def shareName = "tshare"
manageAcctPageFull = "https://" + TPAMaddress + "/tpam/ManageAccounts.asp"
ftMethod = 'Windows File Copy'
ftMethod1 = 'SCP using PSM Functional Account'
def spcwPlatforms = ['SPCW 2','SPCW (DC) 2','SPCW Pwd']

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	driver.get(manageAcctPageFull)
	//Set filter options
	systemField =libComn.waitForElementWithId(driver,"SystemNm")
	systemField.sendKeys(systemName);
	driver.findElement(By.id('AccountNm')).sendKeys(placeHldr);
	driver.findElement(By.id("Listing")).click();
	//choose account
	accTable =libComn.waitForElementWithId(driver,"AccountListing")
	driver.findElement(By.xpath("//table[@id='AccountListing']/tbody/tr[1]")).click()
	driver.findElement(By.id("PSMDetails")).click()
	//wait page load
	fileTranferTab =libComn.waitForElementWithId(driver,"FileTransfer")
	fileTranferTab.click();
	//set File transfer properies
	ftid = libComn.waitForElementWithId(driver,"FileTransTypeID")
	new Select(ftid).selectByVisibleText(ftMethod); //File Transfer Method
	//share
	driver.findElement(By.id('FileTransPath')).sendKeys(shareName);
	driver.findElement(By.id("FileTransAuthMeth0")).click() //Same as Session Authentication.
	//Try to save and check error
	driver.findElement(By.id("SubmitChangesButton")).click();
	libComn.waitForResult("File transfer credentials must be specified at file transfer time", driver)
	//Choose correct method
	driver.findElement(By.id("FileTransAuthMeth1")).click()
	driver.findElement(By.id("SubmitChangesButton")).click();
	libComn.waitForResult("PSM Details saved successfully", driver)
	//check that it is possible to use "SCP using PSM Functional Account" File transfer method
	if(spcwPlatforms.contains(platform)){
		//It is necessary to change system settings
		driver.findElement(By.id("SystemButton")).click();
		sysTable =libComn.waitForElementWithId(driver,"SystemListing")
		driver.findElement(By.xpath("//table[@id='SystemListing']/tbody/tr[1]")).click()
		driver.findElement(By.id("Details")).click()
		systemField =libComn.waitForElementWithId(driver,"SystemName")
		driver.findElement(By.id("Connection")).click()
		egpFuncAcct = libComn.waitForElementWithId(driver,"EGPFuncAcct")
		egpFuncAcct.sendKeys('fakeAccount');
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement btnSave = wait.until(ExpectedConditions.elementToBeClickable(By.id("bt_Save")));
		btnSave.click();
		//driver.findElement(By.id("bt_Save")).click();
		libComn.waitForResult("System changes for $systemName saved successfully", driver)
		//set account properties
		driver.findElement(By.id("bt_Accounts")).click();
		accTable =libComn.waitForElementWithId(driver,"AccountListing")
		driver.findElement(By.xpath("//table[@id='AccountListing']/tbody/tr[1]")).click()
		driver.findElement(By.id("PSMDetails")).click()
		fileTranferTab =libComn.waitForElementWithId(driver,"FileTransfer")
		fileTranferTab.click();
		ftid1 = libComn.waitForElementWithId(driver,"FileTransTypeID")
		new Select(ftid1).selectByVisibleText(ftMethod1);
		driver.findElement(By.id("SubmitChangesButton")).click();
	    libComn.waitForResult("PSM Details saved successfully", driver)
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}