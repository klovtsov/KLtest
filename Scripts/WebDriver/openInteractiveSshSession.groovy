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
def reqId = args[3]
def loginPwd = args[4]
def pause = args[5].toInteger();
def expectedImgs = args[6];

def termSessionMessage1 = 'Active session can be forcibly removed in 30 minutes'
def termSessionMessage = "Terminated ActiveSession"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//request session
	libPsm = new libPSM()
		
	String winHandleMain = driver.getWindowHandle();
	libPsm.openSession(driver,reqId);
	//Switch to new window opened
	Thread.sleep(30000)
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	driver.manage().window().maximize()
	//try to login
	if (!libComn.SClickMultiple("psmEnterPwd.png")) {
		println("Password prompt image wasn't found.")
		libComn.STakeScreenShot('RRP_Interactive_login');
	}
		Thread.sleep(500)
		libComn.SType(loginPwd)
		libComn.SType("\n")
		Thread.sleep(pause)
		if (!libComn.SClickMultiple(expectedImgs)){
			Thread.sleep(pause)
			if (!libComn.SClickMultiple(expectedImgs)){
				println("No one from expected window was found.")
				//libComn.takeScreenshot(driver, "OpenRdpSessionFailure")
				libComn.STakeScreenShot('OpenRdpSessionFailure')
			}
		}
		//Terminate session
		driver.switchTo().window(winHandleMain);
		driver.findElement(By.id("TerminateSessionButton")).click();
		libComn.SMouseMove('UserHome.png')
		libComn.waitForResult(termSessionMessage, driver,30);
}
catch (TimeoutException toutex){
	txt = driver.findElement(By.id('ResultsDiv')).getText();
	if(txt != termSessionMessage1){
		println("something went wrong: " + toutex)
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}