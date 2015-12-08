import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def FrontPageContentPath = args[1]
def UserName = args[2]
def AccountName = args[3]
def AccessPolicy = args[4]

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
	libPpm.SetPermissions(driver, TPAMaddress, UserName, AccountName, AccessPolicy)
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")