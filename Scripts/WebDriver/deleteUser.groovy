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

successDeletionResult = "${userName} has been successfully deleted"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	//go to users tab
	libPpm.ChooseUser(driver, TPAMaddress, userName)
	btnDeleteUser = libComn.waitForElementWithId(driver,"Delete")
	btnDeleteUser.click();
	alert = driver.switchTo().alert()
	alert.accept()
	libComn.waitForResult(successDeletionResult, driver)
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "DeleteUserError")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
