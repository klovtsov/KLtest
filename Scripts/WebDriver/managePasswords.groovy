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
managePwdPage = "/tpam/PasswordManagement.asp"

//open Password Management page
def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def action = args[3]
def system = args[4]
def account = args[5]

managePwdPageFull = "https://" + TPAMaddress + managePwdPage

def selectPwdByRequestor(req,sys,accnt){
	//Filter passwords by systemName and by account
	driver.findElement(By.id("ReleasedTo")).sendKeys(req)
	driver.findElement(By.id("Listing")).click()
	WebDriverWait wait = (new WebDriverWait(driver, 10))
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
	try{
	    sysresult = driver.findElement(By.linkText(sys))
	    accresult = driver.findElement(By.linkText(accnt))
		println("Successfully")
	}
	catch(NoSuchElementException ignored)
	{
		println("Failed to find password for ${sys}\\${accnt}. Screenshot was saved.")
		libr.takeScreenshot(driver, "LastReleasedPwd")
	}
}

def checkChangeFrequency(expectedResult){
	result = driver.findElement(By.xpath("//td[@name='ChangeFreq']")).getText()
	if (result.contains(expectedResult)){
		println("Successfully.")
	}else{
	    println("Failed. Real value: ${result}.Expected value: ${expectedResult}.")
	}
}

def getLastChangeDate(){
	result = driver.findElement(By.xpath("//table[@id='AccountsTable']/tbody/tr/td[3]")).getText()
	println(result)
}

def resetPwd(expectedResult){
	driver.findElement(By.xpath("//table[@id='AccountsTable']/tbody/tr/td[3]")).click()
	driver.findElement(By.id("ResetPassButton")).click()
	//If an account is non-managed, an alert will appear.
	if(libr.isAlertPresent(driver,'Are you sure you want to change this non-managed password?')){
		println('Alert has appeared')
	}  
	WebDriverWait wait = (new WebDriverWait(driver, 30))
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@id='ResultTab']/table/tbody"), expectedResult));
	println("Successfully.")
}

def scheduleResetPwd(){
	driver.findElement(By.id("cb_ResetAll")).click()
	driver.findElement(By.id("ScheduleResetButton")).click()
	WebDriverWait wait = (new WebDriverWait(driver, 30))
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//table[@id='testTable\"']"), "scheduled for Automatic Forced Reset"));
	println("Successfully.")
}

def selectPwdBySystemAndAccount(system,account){
	//Filter passwords by systemName and by account
	driver.findElement(By.id("SystemNm")).sendKeys(system)
	driver.findElement(By.id("AccountNm")).sendKeys(account)
	driver.findElement(By.id("Listing")).click()
	WebDriverWait wait = (new WebDriverWait(driver, 10))
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
}

def viewHistory(system,account){
	driver.findElement(By.xpath("//table[@id='AccountsTable']/tbody/tr/td[3]")).click()
	driver.findElement(By.id("Logs")).click()
	WebElement dateField = (new WebDriverWait(driver, 10))
	   .until(ExpectedConditions.presenceOfElementLocated(By.id("SingleDt")));
	driver.findElement(By.id("ChangeLog")).click()
	result = driver.findElement(By.xpath("//table[@id='changeLogTable']/tbody/tr[1]/td[3]")).getText()
	println(result)
}

def viewDependentLog(system,depsystem,account){
	libCmn = new libCommon()
	driver.findElement(By.xpath("//table[@id='AccountsTable']/tbody/tr/td[3]")).click()
	driver.findElement(By.id("Logs")).click()
	dateField = libCmn.waitForElementWithId(driver,"SingleDt");
	driver.findElement(By.id("ChangeLog")).click()
	result = driver.findElement(By.xpath("//tr[td = 'Forced Reset' and td = 'Success']")).click();
	driver.findElement(By.id("DependentChangeLog")).click();
	dateField = libCmn.waitForElementWithId(driver,"daChangeList");
	result = driver.findElement(By.xpath("//tr[td='$depsystem' and td = '$account']/td[5]")).getText()
	println(result)	
}

try {
	libr = new libCommon()
	libr.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	driver.get(managePwdPageFull)
	WebElement sysName = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.presenceOfElementLocated(By.id("SystemNm")));	
	//Action
	if (action == "checkFreq"){
		selectPwdBySystemAndAccount(system,account)
		checkChangeFrequency(args[6])
	} else if (action == "getLastChangeDate"){
	    selectPwdBySystemAndAccount(system,account)
	    getLastChangeDate()
	} else if (action == "reset"){
	    selectPwdBySystemAndAccount(system,account)
	    resetPwd(args[6])
	} else if (action == "ScheduleReset"){
	    accountFilter = account + '*'
	    selectPwdBySystemAndAccount(system,accountFilter)
	    scheduleResetPwd()
	}else if (action == "getLastReleasedPwd"){
	    selectPwdByRequestor(args[6],system,account)
	}else if (action == "viewHistory"){
	    selectPwdBySystemAndAccount(system,account)
	    viewHistory(system,account)
	}
	else if (action == "DependentChangeLog"){
		def depntSystem = args[6]
		selectPwdBySystemAndAccount(system,account)
		viewDependentLog(system,depntSystem,account)
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