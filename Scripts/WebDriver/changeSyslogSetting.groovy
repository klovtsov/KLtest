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
def syslogIP = args[3]
def syslogPort = args[4]

def urlSyslogSettings = "https://$TPAMaddress/admin/SysLogConfig.asp"
def succesChange = 'SysLog Configuration Settings saved successfully'

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	
	libCom = new libCommon()
	libCom.login(driver, TPAMaddress, "/admin/", adminName, adminPwd)
	driver.get(urlSyslogSettings)
	libCom.waitForElementWithId(driver, 'SysServer')

	driver.findElement(By.id("SysServer")).clear()
	driver.findElement(By.id("SysServer")).sendKeys(syslogIP)
	driver.findElement(By.id("SysPort")).clear()
	driver.findElement(By.id("SysPort")).sendKeys(syslogPort)

	if (!driver.findElement(By.id("cb_AdminLog")).isSelected()) {
		driver.findElement(By.id("cb_AdminLog")).click()
	}	
	
	if (!driver.findElement(By.id("cb_UserLog")).isSelected()) {
		driver.findElement(By.id("cb_UserLog")).click()
	}	
	
	if (!driver.findElement(By.id("cb_FailedLogins")).isSelected()) {
		driver.findElement(By.id("cb_FailedLogins")).click()
	}	

	driver.findElement(By.id('SubmitChanges')).click();
	libCom.waitForResult(succesChange, driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())	

}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}

