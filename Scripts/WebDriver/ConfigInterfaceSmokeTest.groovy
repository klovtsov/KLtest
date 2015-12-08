import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxDriver


def CheckConfigInterface (driver, TPAMaddress) {
	TPAMaddr = "https://" + TPAMaddress + ":8443/config/ShowNetworkSettings.asp"
	driver.get(TPAMaddr)
	libCom.waitForTextPresent(driver, "Current Running Values", 10)
	println(driver.findElement(By.xpath("//fieldset/div[2]/div[2]")).getText())
}
	
try {

	TPAMaddress = args[0]
	AdminUserName = args[1]
	AdminUserPass = args[2]
	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()

	libCom = new libCommon()
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
	
	libCom.login(driver, TPAMaddress, ":8443/config/", AdminUserName, AdminUserPass)
	
	Thread.sleep(10000)
	
	CheckConfigInterface (driver, TPAMaddress)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


