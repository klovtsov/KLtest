import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

try {

	TPAMaddress = args[0]
	SystemName = args[1]

	h = new libCommon()
	p = new libPPM()
	
	h.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	p.hardDeleteSystem(driver, TPAMaddress, SystemName)
	

}
catch (e) {
    println("Catched: Something went wrong: " + e);
}

driver.quit()

println("Finished.");



