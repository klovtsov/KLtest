import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxDriver
	
def AddSysAdminUser (driver, TPAMaddress, AdminUserName, AdminUserPass) {
	TPAMaddr = "https://" + TPAMaddress + "/admin/AdminUserFilter.asp?StartPage=NewUser" 
	driver.get(TPAMaddr)
	driver.findElement(By.id("UserName")).sendKeys("$AdminUserName")
	driver.findElement(By.id("LastName")).sendKeys("lname")
	driver.findElement(By.id("FirstName")).sendKeys("fname")
	driver.findElement(By.id("sPassword")).sendKeys("$AdminUserPass")
	driver.findElement(By.id("Confirm")).sendKeys("$AdminUserPass")
	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	if (!driver.findElement(By.id("SubmitChanges")).isEnabled()) {
		println("SaveChanges button is disabled!")
	}
}	
	
try {

	TPAMaddress = args[0]
	UserName = args[1]
	UserPassword = args[2]
	AdminUserName = args[3]
	NewAdminUserPass = args[4]
	
	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()

	libCom = new libCommon()
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

	libCom.login(driver, TPAMaddress, "/admin/", UserName, UserPassword)
	
	AddSysAdminUser(driver, TPAMaddress, AdminUserName, "Password1")
	
	driver.close()
	
	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()
	
	libCom.loginAndChangePassword(driver, TPAMaddress, ":8443/config/main.asp", AdminUserName, "Password1", NewAdminUserPass)
	
}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


