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

def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def SysName = args[3]

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	libppm = new libPPM()
	libppm.chooseSystem(driver, TPAMaddress, SysName)
	driver.findElement(By.id("Details")).click()
	tabConnection =libComn.waitForElementWithId(driver,"Connection");
	tabConnection.click();
	txtTimeout =libComn.waitForElementWithId(driver,"Timeout");
	Thread.sleep(500)
	println(txtTimeout.getAttribute("value"))
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}