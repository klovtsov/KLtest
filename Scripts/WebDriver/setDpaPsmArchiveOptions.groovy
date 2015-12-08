/*
 * This script allows to modify a PSM sessions archiving method.
 * Some examples how to call it from Fitnesse:
 *     - Set that local server should archive sessions on archserver
 *     setDpaPsmArchiveOptions;${TPAMHost};${TPAMAdmin};${DefaultPwd};local;true;@archserver;Y
 *     
 *     - Set that DPA should archive sessions on archserver
 *     setDpaPsmArchiveOptions;${TPAMHost};${TPAMAdmin};${DefaultPwd};DPA;true;@archserver;Y
 *     
 *     - Set that DPA shouldn't archive sessions
 *     setDpaPsmArchiveOptions;${TPAMHost};${TPAMAdmin};${DefaultPwd};DPA;false
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
def LocalOrDpa = args[3]
def toArchive = args[4].toBoolean()

def urlDpaPage = "https://$TPAMaddress/tpam/EGPSessionServers.asp"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//go to add file tab
	driver.get(urlDpaPage)
	//Select string which has Network address unequal to localhost
	libComn.waitForElementWithId(driver,'serversTable')
	if(LocalOrDpa == 'local'){
		driver.findElementByXPath("//table[@id = 'serversTable']/*/tr[td = 'localhost']").click();
	}else{
	    driver.findElementByXPath("//table[@id = 'serversTable']/*/tr[not (td = 'localhost')]").click();
	}
	// go to Details tab
	driver.findElement(By.id('Details')).click();
	cbAutoArch = libComn.waitForElementWithId(driver,'cb_AutoArchive')

	if(toArchive){
		def ArchServerName = args[5] == 'default'?'Default Archive Server':args[5]
		def isUsedForReplay = args[6].toBoolean()
		//set checkbox and select server
		if(!cbAutoArch.isSelected()){
			cbAutoArch.click();
		}
		new Select(driver.findElement(By.id('ArchSrvID'))).selectByVisibleText(ArchServerName);
		Thread.sleep(5000)
		//Set 'Use this DPA for replays of session logs archived here?' checkbox
		cbReplay = driver.findElement(By.id('cb_ReplayAffinity'))
		if(isUsedForReplay){
			if(!cbReplay.isSelected())
			   cbReplay.click();
		}else{
		    if(cbReplay.isSelected())
		      cbReplay.click();
		}// end of if isUsedForReplay
	}else{
	 //Clear check box
	   if(cbAutoArch.isSelected())
	       cbAutoArch.click();
	}//end of if toArchive
	//Save changes an get result
	driver.findElement(By.id('SubmitChanges')).click();
	libComn.waitForResult('was updated successfully', driver)
}//end of try block
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "setDpaArchiveServer")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
