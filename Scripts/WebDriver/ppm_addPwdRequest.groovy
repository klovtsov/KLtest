import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def browser = args[0]
def UserName = args[1]
def UserPwd = args[2]
def SystemName = args[3]
def AccountName = args[4]
def TPAMaddress = args[5]

try {
	libComn = new libCommon()
	driver = libComn.Browser(browser)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	libPpm = new libPPM()
	libComn.login(driver, TPAMaddress, "/tpam/", UserName, UserPwd)
	libPpm.addPasswordRequest(driver, TPAMaddress, SystemName, AccountName)
}
catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")