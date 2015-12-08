import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def fileName = args[3]

manageFilePage = "https://" + TPAMaddress + "/tpam/FileFilter.asp"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	driver.get(manageFilePage)
	
	fileNameField =libComn.waitForElementWithId(driver,'FileNm')
	fileNameField.sendKeys(fileName)
	driver.findElement(By.id("Listing")).click();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), 'Displaying'));
	driver.findElement(By.xpath("//table[@id='fileTable']//td[text()='$fileName']")).click();
	driver.findElement(By.id("TicketSystem")).click();
	Thread.sleep(1000)
	//Get Ticket System settings
	cbxRelReq =libComn.waitForElementWithId(driver,'RequireTixForRequest')
	println("reqTicketForRelease is "+ cbxRelReq.isSelected())
	println("reqTicketForISA is "+ driver.findElement(By.id("RequireTixForISA")).isSelected())
	selTicketSystem = new Select(driver.findElement(By.id('TSID')))
	println("Ticket System Name is "+ selTicketSystem.getFirstSelectedOption().getText())
	println("Notify Email is " + driver.findElement(By.id("TixEmailNotify")).getAttribute('value'))
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "getFileTicketSettings")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}