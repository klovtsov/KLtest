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
def expectedImgs = args[4]

final termSessionMessage1 = 'Active session can be forcibly removed in 30 minutes'
final termSessionMessage = "Terminated ActiveSession"
final AcceptButtonImages = 'Accept4.png,Accept5.png';
final PsmHeader = 'ff_psm_header_inactive.png'
def SwaitParams = '60,'+ AcceptButtonImages;
def SwaitPromptParams = '60,' + expectedImgs;

//Minimize any another windows.
//String cm = "cmd.exe /c " + "\"" + System.getenv("APPDATA") + "\\Microsoft\\Internet Explorer\\Quick Launch\\Shows Desktop.lnk" + "\""
//Runtime.getRuntime().exec(cm)

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
driver.manage().window().maximize()

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//request session
	libPsm = new libPSM()
    
	String winHandleMain = driver.getWindowHandle();
	libPsm.openSession(driver,reqId);
		//Switch to new window opened
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	//Set focus on psm window
	 if(libComn.SWait(PsmHeader)){
		 libComn.SClickMultiple(PsmHeader);
	 }
	 //A Psm session interface loading requires more than 20 sec usually. 
	 Thread.sleep(10000)
		//try to login
	if(!libComn.SWaitMultipleV2(SwaitParams)){
		println("something went wrong: Can't find Accept button images.")
		libComn.takeScreenshot(driver, 'tryOpenSshSession')
	}else{
	    libComn.SClickMultiple(AcceptButtonImages);
	}
	
	if(!libComn.SWaitMultipleV2(SwaitPromptParams)){
		println("something went wrong: Can't find prompt images.")
		libComn.takeScreenshot(driver, 'tryOpenSshSession')
	}else{
	    libComn.SType('exit')
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