import org.openqa.selenium.WebElement;

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
def batchFile = args[3]
def resultMessage = args[4]

def urlBatchCachePermissionsUpdate = "https://$TPAMaddress/tpam/BatchImportUpdate.asp?BatchType=UpdateCSPermissions"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


try {

	//Minimize any another windows
	String cm = "cmd.exe /c " + "\"" + System.getenv("APPDATA") + "\\Microsoft\\Internet Explorer\\Quick Launch\\Shows Desktop.lnk" + "\""
	Runtime.getRuntime().exec(cm)

	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	
	driver.get(urlBatchCachePermissionsUpdate)
	
	libComn.uploadFileToTpam(driver,batchFile)
	
	driver.findElement(By.id('Comment2')).sendKeys('Cache Comment');
	driver.findElement(By.id('btnProcessFile')).click();
	
	WebDriverWait wait = new WebDriverWait(driver, 30);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//div[@id="divBatchResults"]/table/tbody'), resultMessage));
	
	rslt = driver.findElement(By.id('divBatchResults')).text
	println(rslt)

}

catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "CacheServerPermissions")
	println("Catched: something went wrong: " + toutex);
}

catch (e){
	println("Catched: something went wrong: " + e);
}

finally{
	driver.quit()
	println("Script finished.");
}

