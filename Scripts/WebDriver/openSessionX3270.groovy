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

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)

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
	libComn.SWaitMultipleV2('60,mainframe_x3270_window.png,mainframe_x3270_window2.png')
	libComn.SMouseMove('mainframe_connect.png')
	libComn.SMouseDown()
	libComn.SWait('mainframe_ip.png')
	libComn.SMouseMove('mainframe_ip.png')
	libComn.SMouseUp()
	Thread.sleep(500)
	if(libComn.SWait('mainframe_logon_succeeded.png')) {
		println("x3270 terminal logon succeeded")		
	}


}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}