import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)	

try {

	TPAMaddress = args[0]

	TSName = args[1]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	libTS = new libTicketSystem()
	libTS.deleteTicketSystem(driver, TPAMaddress, TSName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


