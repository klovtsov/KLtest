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
def userName = args[3]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	//go to users tab
	manageUserPage = "https://" + TPAMaddress + "/tpam/UserFilter.asp"
	driver.get(manageUserPage)
	usrNameField =libComn.waitForElementWithId(driver,"UsrNm")
	usrNameField.sendKeys(userName)
	driver.findElement(By.id("Listing")).click();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	//select user
	driver.findElement(By.xpath("//tr[td = '$userName']")).click();
	driver.findElement(By.id("Details")).click();
	txtFieldCertThumb =libComn.waitForElementWithId(driver,"CertThumbprint")
	Thread.sleep(1000)
	println(txtFieldCertThumb.getAttribute("value"))
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "CreatePromptAccountError")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}