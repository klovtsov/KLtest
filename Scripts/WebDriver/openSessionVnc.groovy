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
def vncPwd = args[4]
def expectedImgs = args[5]

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
	Thread.sleep(20000)
	for(String winHandle : driver.getWindowHandles()){
	    driver.switchTo().window(winHandle)
	}
	driver.manage().window().maximize()
		//try to login
	libComn.SWait('vncServerNonStandard.png')
	libComn.SClickMultiple('ok_vnc.png')
    libComn.SWait('vncPwdForm.png')
	Thread.sleep(500)
	libComn.SType(vncPwd)
	libComn.SClickMultiple('ok_vnc.png')
	libComn.SWait(expectedImgs)
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