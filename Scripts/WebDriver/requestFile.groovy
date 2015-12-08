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
def systemName = args[3]
def fileName = args[4]

def noFilesForReqst = '(Displaying 0 of 0 rows meeting filter criteria)'

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//request file
	driver.findElement(By.linkText("Add File Request")).click()
	//set filter for file
	sysNameField =libComn.waitForElementWithId(driver,"SystemNm")
	sysNameField.sendKeys(systemName)
	driver.findElement(By.id("FileNm")).sendKeys(fileName);
	//go to files tab
	driver.findElement(By.id('Files')).click();
	WebDriverWait wait = new WebDriverWait(driver, 60);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@id='filesTab']/span"), 'rows meeting filter criteria'));
    //Check if there are files for request
    hintText = driver.findElement(By.xpath("//div[@id='filesTab']/span")).getText();
	if (hintText == noFilesForReqst){
		println('There are no files for request')
	}else{
	     //select file    
	    driver.findElement(By.xpath("//tr[td = '$systemName' and td = '$fileName']")).click();
		driver.findElement(By.id("Details")).click()
		//set request reason
		WebElement txtArea = (new WebDriverWait(driver, 30))
	    .until(ExpectedConditions.presenceOfElementLocated(By.id("RequestReason")));
		txtArea.sendKeys('test request');
		driver.findElement(By.id("SubmitChanges")).click()
		libComn.waitForResult("has been submitted", driver)
		println(driver.findElement(By.id("ResultsDiv")).getText())
	}//end of else block
}//end of try block
catch (TimeoutException toutex){
    libComn.takeScreenshot(driver, "requestFile")
    println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}