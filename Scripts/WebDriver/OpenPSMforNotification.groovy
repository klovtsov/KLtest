import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.List

def TPAM = args[0]
def UserName = args[1]
def UserPassword = args[2]
def RequestID = args[3]
def SecToOpen = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

TPAMaddr = "https://" + TPAM + "/tpam" 
driver.get(TPAMaddr)
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

def MsecToOpen = SecToOpen.toInteger() * 1000
	
try {                                                                      
	
	libCom = new libCommon()
	libSession = new libPSM()
	libCom.login(driver, TPAM, "/tpam/", UserName, UserPassword)	
	libSession.openSession(driver, RequestID)
	Thread.sleep(MsecToOpen)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");
