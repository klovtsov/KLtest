import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def FrontPageContentPath = args[1]
def SystemName = args[2]
def Account = args[3]
def CheckReset = args[4]
def Result = args[5]
def SystemContent = args[6]

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
	
	def successfulCheckPassword = FrontPage.CheckPasswordSuccessful
	def failedCheckPassword = FrontPage.CheckPasswordFailed
	
	def successfulResetPassword = FrontPage.ResetPasswordSuccessful
	def failedResetPassword = FrontPage.ResetPasswordFailed
	
	def failedNotMatchPassword = FrontPage.CheckPasswordNotMatch 
	
	def SystemCont = libPpm.GetContent(SystemContent)
	
	if (Result == "Success") {
		if (CheckReset == "Check") {
			expectedResult = successfulCheckPassword
		} else if (CheckReset == "Reset") {
			expectedResult = successfulResetPassword
			if (SystemCont.ResetPasswordSuccessful) {
				expectedResult = SystemCont.ResetPasswordSuccessful
			}
		}
	} else if (Result == "Fail") {
		if (CheckReset == "Check") {
			expectedResult = failedCheckPassword
		} else if (CheckReset == "Reset") {
			expectedResult = failedResetPassword
			if (SystemCont.ResetPasswordFailed) {
				expectedResult = SystemCont.ResetPasswordFailed
			}
		}
	} else if (Result == "FailNotMatch") {
			expectedResult = failedNotMatchPassword
	}
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	libPpm.CheckResetPassword(driver, TPAMaddress, SystemName, Account, CheckReset, expectedResult) 
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")