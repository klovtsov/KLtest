import org.openqa.selenium.WebElement;

import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.firefox.internal.ProfilesIni
import org.openqa.selenium.interactions.Actions


import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def exportType = args[3]

def urlBatchCacheExport = "https://$TPAMaddress/tpam/ListCSPermissions.asp"


FirefoxProfile profile = new FirefoxProfile()
profile.setPreference("browser.download.folderList",2)
profile.setPreference("browser.download.manager.showWhenStarting",false)

File currentDirectory = new File(new File("../").getAbsolutePath())
downdir = currentDirectory.getCanonicalPath() + "\\keys"

profile.setPreference("browser.download.dir",downdir)
profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/octet-stream,application/csv,text/csv") 

driver = new FirefoxDriver(profile)
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)


try {

	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	
	driver.get(urlBatchCacheExport)

	if (exportType == "Excel") {

		driver.findElement(By.id('ExportButton')).click()
		println("Excel button found and pressed.")
	
	} else {

		driver.findElement(By.id('ExportCSVButton')).click()
		println("Csv button found and pressed.")
	}

	Thread.sleep(5000)  // To make sure a file with permissions was saved before closing the browser and checking the results.

}

catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "CacheServerExportPermissions")
	println("Catched: something went wrong: " + toutex);
}

catch (e){
	println("Catched: something went wrong: " + e);
}

finally{
	driver.quit()
	println("Finished.");
}

