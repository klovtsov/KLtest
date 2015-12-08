/*
 * If priorities are not specified for server and DPA,
 *  'Allow PSM sessions to be run on any defined DPA' will be chosen.
 */
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def systemName = args[3]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	libPpm = new libPPM()
	libPpm.chooseSystem (driver, TPAMaddress, systemName)
	driver.findElement(By.id("Affinity")).click();
	rbPsmAffinity =libComn.waitForElementWithId(driver,"rbEGPAffinitySelected");
	if(args.length == 4){
		//'Allow PSM sessions to be run on any defined DPA' should be set.
		driver.findElement(By.id('rbEGPAffinityAny')).click();
	}else{
	    //Set priorities for LocalServer and DPA    
	    rbPsmAffinity.click();
	    editLocSrvPriority=driver.findElementByXPath("//tr[td='LocalServer']/*/input")
		editLocSrvPriority.clear();
		editLocSrvPriority.sendKeys(args[4]);
		editDpaPriority=driver.findElementByXPath("//table[@id = 'tblEGPDPAAffinity']/*/tr[not (td = 'LocalServer')]/*/input")
		editDpaPriority.clear();
		editDpaPriority.sendKeys(args[5]);
	}
	WebDriverWait wait = new WebDriverWait(driver, 10);
	WebElement btnSave = wait.until(ExpectedConditions.elementToBeClickable(By.id("bt_Save")));
	btnSave.click();
	libComn.waitForResult("System changes for $systemName saved successfully", driver)
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