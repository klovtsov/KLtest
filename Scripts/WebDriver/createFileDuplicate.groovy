import org.openqa.selenium.WebElement;

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
def sysName = args[3]
def fileName = args[4]
def duplName = args[5]
def duplFileName = args[6]

urlFileManagement = "https://$TPAMaddress/tpam/FileFilter.asp"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	//open corresponding page
	driver.get(urlFileManagement)
	//Set filter value
	systemField =libComn.waitForElementWithId(driver,'SystemNm');
	systemField.sendKeys(sysName);
	driver.findElement(By.id('FileNm')).sendKeys(fileName);
	//Go to Listing tab
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td = '$sysName' and td = '$fileName']")).click();
	//Duplicate
	WebElement btnDupl = new WebDriverWait(driver, 10)
	   .until(ExpectedConditions.elementToBeClickable(By.id('Duplicate')));
	Thread.sleep(500)
	btnDupl.click();
	//Set duplicate values
	WebDriverWait wait = new WebDriverWait(driver, 60);
	txtFileName = driver.findElement(By.id('FileName'))
	wait.until(ExpectedConditions.visibilityOf(txtFileName))
	//Webdriver can send a few symbols before File Name input field will be visible
	Thread.sleep(1000)
	txtFileName.sendKeys(duplName);
	
	//Try to upload file
	libComn.uploadFileToTpam(driver,duplFileName)
	//Save and wait results
	driver.findElement(By.id('SubmitChanges')).click()
	Thread.sleep(5000)
	//libComn.waitForResult('File saved successfully', driver)
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}