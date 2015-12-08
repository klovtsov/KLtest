import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def sysName = args[3]
def accntName = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	driver.findElement(By.id('CurrentRequests')).click()
	
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	
	status = driver.findElement(By.xpath("//tr[td = '$sysName' and td[a = 'Pwd($accntName)']]/td[5]")).getText();
	println(status)
	//wait
	driver.findElement(By.xpath("//tr[td = '$sysName' and td[a = 'Pwd($accntName)']]/*/a")).click();
	txtarea =libComn.waitForElementWithId(driver,"ResponseComment")
	//satisfy that password tab is disabled
    pwdStatus = driver.findElement(By.xpath("//li[a[@id='Password']]")).getAttribute('class')
	if(pwdStatus == 'tabberdisabled'){
		println('Password tab is disabled')
	}else{
	    println('Password tab is enabled')
	}
}catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "get_account_password")
	println("Catched: trouble to find element: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
}