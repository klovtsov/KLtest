import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def reqId = args[3]

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
	libPsm.selectRequest(driver,reqId);
    btnStartSession = libComn.waitForElementWithId(driver,"StartSessionButton");
	if(btnStartSession.isEnabled()){
		println("Enabled")
	}else{
	    println("Disabled")
	}
}//try block end
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}