import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def SystemContentPath = args[1]
def FrontPageContentPath = args[2]
def SystemName = args[3]
def AccountName = args[4]

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
	println(SystemContent.ManagedAccountPwd)
	println(FrontPage.ManagedAccountPwd)
	if (SystemContent.ManagedAccountPwd) {
		ManagedAccountPwd = SystemContent.ManagedAccountPwd
	} else {
		ManagedAccountPwd = FrontPage.ManagedAccountPwd
	}
	
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	libPpm.addAccount (driver, TPAMaddress, SystemName, AccountName, ManagedAccountPwd, SystemContent) 
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")