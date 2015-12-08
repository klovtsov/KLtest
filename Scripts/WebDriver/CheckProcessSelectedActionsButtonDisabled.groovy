import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def FrontPageContentPath = args[1]
def SystemName = args[2]

try {
	
	libComn = new libCommon()
	driver = libComn.Browser(browser)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	libPpm = new libPPM()

	// Get FrontPage content	
	def FrontPage = libPpm.GetContent(FrontPageContentPath)
	def TPAMaddress = FrontPage.TPAMHost
	def TPAMuserName = FrontPage.TPAMAdmin
	def TPAMuserPwd = FrontPage.DefaultPwd
	
 	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	libPpm.chooseSystem(driver, TPAMaddress, SystemName)	
	driver.findElement(By.id("AccountDiscovery")).click()
    driver.findElement(By.id("DiscoveredAccounts")).click()
	libComn.waitForElementWithId(driver, "btnRefresh")
    driver.findElement(By.cssSelector("input.ActionAdd")).click()
    driver.findElement(By.cssSelector("input.ActionDelete")).click()
	libComn.waitForElementWithId(driver, "btnProcessDiscoveryActions")
	driver.findElement(By.id("btnProcessDiscoveryActions")).click()
	alert = driver.switchTo().alert()
	alert.accept()
	
	if (libComn.waitForTextPresent(driver, "Account processed successfully", 5)) {
		println("Account processed successfully")
	}
	if (libComn.waitForTextPresent(driver, "Auto Management turned OFF", 1)) {
		println("Auto Management turned OFF")
	}
	if (!driver.findElement(By.id("btnProcessDiscoveryActions")).isEnabled()) {
		println("btnProcessDiscoveryActions button is disabled!")
	}
		
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")
