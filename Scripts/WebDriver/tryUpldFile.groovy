import org.openqa.selenium.WebElement;

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
def batchOperation = args[3]
def batchFile = args[4]

def urlBatchPage = "https://$TPAMaddress/tpam/BatchImportUpdate.asp?BatchType=$batchOperation"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	//go to users tab
	driver.get(urlBatchPage)
	//Try to upload file
	if(!libComn.uploadFileToTpam(driver,batchFile)){
		println("File hasn't been uploaded")
	}
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "TryToBatchFileUploaded")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
