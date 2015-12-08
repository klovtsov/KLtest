import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def TPAMuserName = args[1]
def TPAMuserPwd = args[2]
def sName = args[3]

try {
	System.setProperty("webdriver.firefox.profile", "TPAM")
	driver = new FirefoxDriver()
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	libComn = new libCommon()
	libPpm = new libPPM()
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	libPpm.chooseSystem(driver,TPAMaddress,sName)
	driver.findElement(By.id("AccountDiscovery")).click()
	driver.findElement(By.id("btnTestDiscovery")).click()
	libComn.waitForTextPresent (driver, "Show lines with Action", 100)
//	libComn.waitForElementWithId(driver,"cbxIgnored")
	result = libComn.TableViewer(driver,"DiscoveryListing")
	println(result)
	
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")