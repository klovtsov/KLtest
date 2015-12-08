import org.openqa.selenium.TimeoutException
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def systemName = args[3]
def accntName = args[4]
def keyLength = args[5]

def is1024 = (keyLength == '1024') ? true : false

//Load libraries
libComn = new libCommon()
libPpm = new libPPM()

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	libPpm.ChooseAccount(driver, TPAMaddress, systemName, accntName)
	//Move to PSM details tab and set common settings
	driver.findElement(By.id("PSMDetails")).click();
	cbEnablePsm =libComn.waitForElementWithId(driver,"cb_EnableFl")
	//Specify authentication options
	driver.findElement(By.id("SessionAuthentication")).click();
	//Select a key to be generated
	if(is1024){
		driver.findElement(By.id('btGeneratePair1')).click();
	}else{
		driver.findElement(By.id('btGeneratePair0')).click();
	}
	libComn.waitForResult('Successfully re-generated key pair!', driver)
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "SetPsmKeyForAccount")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}