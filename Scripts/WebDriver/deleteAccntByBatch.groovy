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
def sysName = args[3]
def accntName = args[4]
def batchFile = args[5]

def urlBatchAccntUpdate = "https://$TPAMaddress/tpam/BatchImportUpdate.asp?BatchType=UpdateAccounts"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def createBatchFile(filename,system,account){
	def lines = ['System,Account,Action',"$system,$account,D"]
	deleteBatchFile(filename);
	new File(filename).withPrintWriter { prntWriter ->
	    lines.each { line -> prntWriter.println(line)}
	}
}

def deleteBatchFile(filename){
	def outFile = new File(filename)
	if (outFile.exists()){
		outFile.delete()
	}
}

try {
	//Create batch update file
	createBatchFile(batchFile,sysName,accntName)
	
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	//go to users tab
	driver.get(urlBatchAccntUpdate)
	libComn.uploadFileToTpam(driver,batchFile)
	driver.findElement(By.id('Comment2')).sendKeys('Try to delete account');
	driver.findElement(By.id('btnProcessFile')).click();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//div[@id="detailDiv"]/div/span'), 'rows retrieved'));
	rslt = driver.findElement(By.id('divBatchResults')).text
	println(rslt)
	deleteBatchFile(batchFile)
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "DeleteBatchAccntError")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
