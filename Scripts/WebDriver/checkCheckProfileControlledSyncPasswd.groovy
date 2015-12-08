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
def sysName = args[3]
def accntName = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	libPpm.ChooseAccount(driver, TPAMaddress, sysName, accntName)
	driver.findElement(By.id('Details')).click()
	driver.findElement(By.id('Management')).click()
	checkProfDetails = libComn.waitForElementWithId(driver,'CheckProfileName')
	
	if(driver.findElement(By.xpath("(//div[@class='SyncPassOnlyMsg fieldHint'])[1]")).isDisplayed()){
	    println("Managed by Sync password check profile.")
	}else{
		println("Managed by account check profile.")
	}

}//end of try block
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "check_Account_Check_Porofile")
	println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
}
