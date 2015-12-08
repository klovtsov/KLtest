import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.List

def TPAM = args[0]
def UserName = args[1]
def UserPassword = args[2]

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

TPAMaddr = "https://" + TPAM + "/tpam" 
driver.get(TPAMaddr)
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

try {                                                                      
	
	libCom = new libCommon()
	libCom.login(driver, TPAM, "/tpam/", UserName, UserPassword)	
	appTime = getAppTime(driver, UserName)
	println(appTime)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()


def getAppTime(driver, UserName) {

	driver.findElement(By.linkText(UserName)).click();
	driver.findElement(By.linkText("About TPAM")).click();
		
		String winHandleMain = driver.getWindowHandle();
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle)
		}

		myTime = driver.findElement(By.id('footerServerTime')).getText();

		driver.switchTo().window(winHandleMain);
		return myTime;

}
