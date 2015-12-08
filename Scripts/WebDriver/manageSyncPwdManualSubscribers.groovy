import org.openqa.selenium.*
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
managePwdPage = "/tpam/ManageSyncPass.asp"

libr = new libCommon()

//open Password Management page
def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def action = args[3]
def syncPwdName = args[4]
def system = args[5]
def account = args[6]

managePwdPageFull = "https://" + TPAMaddress + managePwdPage

def selectSyncPwdByName(pwdName){
	driver.findElement(By.id('SyncPassNm')).sendKeys(pwdName);
	driver.findElement(By.id('Listing')).click();
	WebDriverWait wait = (new WebDriverWait(driver, 10))
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
	driver.findElement(By.xpath("//tr[td = '$pwdName']")).click();
}

def warnIfEnabled(element){
	if(element.isEnabled()){
		println("Priority level field is enabled")
	}
}

def Subscribe(sysName,accName){
	driver.findElement(By.id('AddSubsButton')).click();
	sysNameFld = libr.waitForElementWithId(driver,'SystemNm');
	sysNameFld.sendKeys(sysName);
	driver.findElement(By.id('AccountNm')).sendKeys(accName);
	driver.findElement(By.id('Candidates')).click();
	sysNameFld = libr.waitForElementWithId(driver,'subscribersTab');
	cbxSelect=driver.findElement(By.xpath("//table[@id='SubscribersTable']//input[@type='checkbox']"))
	inptPriority = driver.findElement(By.xpath("//table[@id='SubscribersTable']//tr/td[6]/input"))
	warnIfEnabled(inptPriority)
	cbxSelect.click();
	warnIfEnabled(inptPriority);
	driver.findElement(By.id('SaveButton')).click();
	libr.waitForResult('New subscribers added successfully', driver)
}

def Unsubscribe(sysName,accName){
	driver.findElement(By.id('SubscriberStatus')).click();
	WebDriverWait wait = (new WebDriverWait(driver, 10))
	    wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@id='StatusReportDiv']//span[@class='fieldHint']"), "Displaying"));
	if(!driver.findElements(By.xpath("//tr[td[a = '$sysName' and a = '$accName']]/td[5]/span")).empty){
		println('Error: Priority field is found!')
	}
	driver.findElement(By.xpath("//tr[td[a = '$sysName' and a = '$accName']]/td[5]/input[@type = 'checkbox']")).click()
	driver.findElement(By.id('SaveButton')).click();
	libr.waitForResult('Subscribers removed successfully', driver)
}

try {
	libr.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	driver.get(managePwdPageFull)
	libr.waitForElementWithId(driver,'SyncPassNm')
	//Action
	selectSyncPwdByName(syncPwdName);
	
	if (action == "add"){
		Subscribe(system,account)
	} else if (action == "drop"){
		Unsubscribe(system,account)
	} 
	else{
		println("Action ${action} is unknown.");
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
driver.quit()

println("Script finished.");