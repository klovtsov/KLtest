import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.openqa.selenium.support.ui.Select

def browser = args[0]
def FrontPageContentPath = args[1]
def UserName = args[2]
def Password = args[3]
def UserType = args[4]
def FirstName = args[5]
def LastName = args[6]

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
	
	TPAMaddr = "https://" + TPAMaddress + "/tpam/UserFilter.asp?StartPage=NewUser"
	driver.get(TPAMaddr)
	driver.findElement(By.id("UserName")).sendKeys(UserName)
	driver.findElement(By.id("FirstName")).sendKeys(FirstName)
    driver.findElement(By.id("LastName")).sendKeys(LastName)
	new Select(driver.findElement(By.id("UType"))).selectByVisibleText(UserType)
	if (UserType == "Cache User") {
		driver.findElement(By.id("rbCertPar")).click()
		driver.findElement(By.id("certPassword")).sendKeys(Password)
		driver.findElement(By.id("certPasswordConfirm")).sendKeys(Password)
	} else {
		driver.findElement(By.id("sPassword")).sendKeys(Password)
		driver.findElement(By.id("Confirm")).sendKeys(Password)
	}
	driver.findElement(By.id("SubmitChanges")).click()
	libCmn = new libCommon()	
	libCmn.waitForResult("User added successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")