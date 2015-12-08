import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def FrontPageContentPath = args[1]
def SystemName = args[2]
def Result = args[3]
def SystemContent = args[4]

try {
	libComn = new libCommon()
	driver = libComn.Browser(browser)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	libPpm = new libPPM()
// Get FrontPage & SystemPage content	
	def FrontPage = libPpm.GetContent(FrontPageContentPath)
	def SystemCont = libPpm.GetContent(SystemContent)
	def TPAMaddress = FrontPage.TPAMHost
	def TPAMuserName = FrontPage.TPAMAdmin
	def TPAMuserPwd = FrontPage.DefaultPwd
	def successfulTestSystem = FrontPage.TestSystemSuccessful
	def failedTestSystem = FrontPage.TestSystemFailed
	if (Result == "Success") {
		expectedResult = successfulTestSystem
	} else { expectedResult = failedTestSystem
			if (SystemCont.TestSystemFailed) {
				expectedResult = SystemCont.TestSystemFailed
			}
	}
	
 	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	libPpm.TestSystem(driver, TPAMaddress, SystemName, expectedResult)	
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")