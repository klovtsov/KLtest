import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//open Password Management page
def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def systemName = args[3]
def placeHldr = args[4]
def proxyConnType = args[5]
def wrongProxyConnType = args[6]

//alert messages
notManagedAccntAlert = "This account cannot be managed!"

def tryCreateSimpleAccount(driver, TPAM, SystemName,AccountName){
	String cm = "cmd.exe /c " + "\"" + System.getenv("APPDATA") + "\\Microsoft\\Internet Explorer\\Quick Launch\\Shows Desktop.lnk" + "\""
	Runtime.getRuntime().exec(cm)
	libPpm = new libPPM()
	libPpm.chooseSystem (driver,TPAM, SystemName)
	driver.findElement(By.id("bt_Accounts")).click()
	
	btnAddAccnt =libComn.waitForElementWithId(driver,"AddAccountButton")
	btnAddAccnt.click()
	
	Actions builder = new Actions(driver);
	builder.moveByOffset(100, 100)
	
	systemField =libComn.waitForElementWithId(driver,"SystemNm")
	driver.findElement(By.id("System")).click()
	builder.moveByOffset(100, 100)
	sysTable =libComn.waitForElementWithId(driver,"systemTable")
	driver.findElement(By.xpath("//table[@id='systemTable']/tbody/tr[1]")).click()
	driver.findElement(By.id("Details")).click()
	builder.moveByOffset(100, 100)
	accntNameField =libComn.waitForElementWithId(driver,"AccountName")
		
	accntNameField.sendKeys(AccountName)
	driver.findElement(By.id("SubmitChangesButton")).click()
}

def setAccountPsmSettings(proxyConnType,wrongProxyConnType){
	driver.findElement(By.id("PSMDetails")).click();
	cbEnablePsm =libComn.waitForElementWithId(driver,"cb_EnableFl")
	cbEnablePsm.click();
	//driver.findElement(By.id("EgpMinApprovers")).sendKeys("0")
	new Select(driver.findElement(By.id("ProxyType"))).selectByVisibleText(wrongProxyConnType)
	driver.findElement(By.id("SubmitChangesButton")).click();
	libComn.waitForResult("Interactive authentication is required when configuring this account", driver)
	new Select(driver.findElement(By.id("ProxyType"))).selectByVisibleText(proxyConnType)
	driver.findElement(By.id("SubmitChangesButton")).click();
	libComn.waitForResult("PSM Details saved successfully", driver)	
}

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	tryCreateSimpleAccount(driver, TPAMaddress, systemName,placeHldr)
	//Check alert appears
	if(libComn.isAlertPresent(driver,notManagedAccntAlert)){
		println("Nonmanaged account alert was catched.")
	}
	//Make account nonmanaged
	driver.findElement(By.id("pwdMgmtNone")).click();
	alert = driver.switchTo().alert()
	alert.accept()
	driver.findElement(By.id("SubmitChangesButton")).click();
	libComn.waitForResult("Changes saved successfully for $placeHldr", driver)
	//PSM settings
	setAccountPsmSettings(proxyConnType,wrongProxyConnType)
	println("PSM Details saved successfully")
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