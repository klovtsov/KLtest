import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.openqa.selenium.support.ui.Select

def FrontPageContentPath = args[0]
def UserName = args[1]
def Password = args[2]
def UserType = args[3]
def FirstName = args[4]
def LastName = args[5]

try {
	System.setProperty("webdriver.firefox.profile", "TPAM")
	driver = new FirefoxDriver()
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	libCmn = new libCommon()
	libPpm = new libPPM()
// Get FrontPage content	
	def FrontPage = libPpm.GetContent(FrontPageContentPath)
	def TPAMaddress = FrontPage.TPAMHost
	def TPAMuserName = FrontPage.TPAMAdmin
	def TPAMuserPwd = FrontPage.DefaultPwd
	
	libCmn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	
	TPAMaddr = "https://" + TPAMaddress + "/tpam/UserFilter.asp?StartPage=NewUser"
	driver.get(TPAMaddr)
	driver.findElement(By.id("UserName")).sendKeys(UserName)
	driver.findElement(By.id("FirstName")).sendKeys(FirstName)
    driver.findElement(By.id("LastName")).sendKeys(LastName)
	new Select(driver.findElement(By.id("UType"))).selectByVisibleText(UserType)
    driver.findElement(By.id("sPassword")).sendKeys(Password)
	driver.findElement(By.id("Confirm")).sendKeys(Password)
	
	driver.findElement(By.id("KeyBased")).click()
	driver.findElement(By.id("cb_CliAccess")).click()
	
	driver.findElement(By.id("SubmitChanges")).click()
		
	libCmn.waitForResult("User added successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")