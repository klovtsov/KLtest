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
def fileName = args[3]

def noFilesForReqst = '(Displaying 0 of 0 rows meeting  filter criteria)'

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//request file
	driver.findElement(By.id("CurrentRequests")).click()
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	//select request by file name
	driver.findElement(By.linkText("File(${fileName})")).click()
	driver.findElement(By.xpath("//tr[td = '$fileName']")).click();
	driver.findElement(By.id("Details")).click()
	//set cancel reason
	txtCancelReason = libComn.waitForElementWithId(driver,"ResponseComment");
	txtCancelReason.sendKeys('sufficient proof')
	driver.findElement(By.id("SubmitChanges")).click()
	libComn.waitForResult("was submitted successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
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