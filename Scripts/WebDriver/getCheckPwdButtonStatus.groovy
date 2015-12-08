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
def systemName = args[3]
def accntName = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	libPpm.ChooseAccount(driver, TPAMaddress, systemName, accntName)
	if(driver.findElement(By.id("CheckPassButton")).enabled){
		println("Check Password Button is Enabled")
	}else{
	    println("Check Password Button is Disabled")
	}
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "Get_check_password_button_status")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}