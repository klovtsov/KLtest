import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	TSName = args[1]
	ManagedSystemName = args[2]
	ManagedSystemFunctAcct = args[3]
	SQLStatement = args[4]
	TicketNumber = args[5]
	RuleName = args[6]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	libTS = new libTicketSystem()
	libTS.addManagedTicketSystem(driver, TPAMaddress, TSName, ManagedSystemName, ManagedSystemFunctAcct, SQLStatement, TicketNumber, RuleName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


