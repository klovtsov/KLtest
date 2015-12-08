import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def SystemContentPath = args[1]
def FrontPageContentPath = args[2]
def SystemName = args[3]

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
	// Get System content
	def SystemContent = libPpm.GetContent(SystemContentPath)
	def FunctAcct = SystemContent.FunctAcct
	def FunctAcctPwd = SystemContent.FunctAcctPwd
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	libPpm.SetPassword(driver, TPAMaddress, SystemName, FunctAcct, FunctAcctPwd)
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")