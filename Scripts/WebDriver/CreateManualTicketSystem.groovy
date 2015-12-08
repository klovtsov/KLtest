import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	TSName = args[1]
	
	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	libTS = new libTicketSystem()
	libTS.addManualTicketSystem(driver, TPAMaddress, TSName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


