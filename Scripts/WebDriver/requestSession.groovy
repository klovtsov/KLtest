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
def action = args[3]

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
	if(action == "make"){
		def systemName = args[4]
		def accntName = args[5]
		def sessionNotes = args[6]
	    libPsm.makeSessionRequest(driver,systemName,accntName,sessionNotes);
	} else if (action == "cancel"){
	    def reqId = args[4]
		def notes = args[5]
		libPsm.cancelSessionRequest(driver,reqId,notes);
	} else if (action == "login"){
	    def reqId = args[4]
		def login = args[5]
		def loginPwd = args[6]
		def pause = args[7].toInteger();
		def expectedImgs = args[8];
		String winHandleMain = driver.getWindowHandle();
		libPsm.openSession(driver,reqId);
		//Switch to new window opened
		Thread.sleep(30000)
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle)
		}
		driver.manage().window().maximize()
		//try to login
		if (!libComn.SClickMultiple("psmAccountPrompt.png,psmAccountPromptA.png")) {
			println("Account prompt image wasn't found.")
			libComn.takeScreenshot(driver, 'Check Prpmpt Placeholder');
		}
		Thread.sleep(500)
		libComn.SType(login)
		libComn.SType("\n")
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
	}else{
	    println("Action " + action + " is unknown. Possible values for action are: make or cancel.");
	}
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