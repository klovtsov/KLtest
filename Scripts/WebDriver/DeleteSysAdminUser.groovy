import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxDriver

def DeleteSysAdminUser (driver, TPAMaddress, AdminUserName) {
	TPAMaddr = "https://" + TPAMaddress + "/admin/AdminUserFilter.asp"
	driver.get(TPAMaddr)
	driver.findElement(By.id("UsrNm")).sendKeys("$AdminUserName")
	driver.findElement(By.id("Listing")).click()
	libCom.waitForTextPresent(driver, "$AdminUserName", 10)
	driver.findElement(By.xpath("//td[contains(text(),'$AdminUserName')]")).click()
	driver.findElement(By.id("Delete")).click()
	Thread.sleep(2000)
	alert = driver.switchTo().alert()
	alert.accept()
	libCom.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}
	
try {

	TPAMaddress = args[0]
	UserName = args[1]
	UserPassword = args[2]
	AdminUserName = args[3]
	
	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()

	libCom = new libCommon()
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

	libCom.login(driver, TPAMaddress, "/admin/", UserName, UserPassword)
	
	DeleteSysAdminUser(driver, TPAMaddress, AdminUserName)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


