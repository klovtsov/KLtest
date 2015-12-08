import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxDriver

def StartMailAgent (driver, TPAMaddress) {
	TPAMaddr = "https://" + TPAMaddress + "/admin/MailAgent.asp"
	driver.get(TPAMaddr)
	Thread.sleep(3000)
	driver.findElement(By.id("StartButton")).click()
	libCom.waitForTextPresent(driver, "The Mail Agent has started", 20)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}
	
try {

	TPAMaddress = args[0]
	UserName = args[1]
	UserPassword = args[2]

	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()

	libCom = new libCommon()
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

	libCom.login(driver, TPAMaddress, "/admin/", UserName, UserPassword)
	
	StartMailAgent(driver, TPAMaddress)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


