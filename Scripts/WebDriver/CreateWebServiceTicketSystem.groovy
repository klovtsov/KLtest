import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	TSName = args[1]
	TicketSystemAddress = args[2]
	TicketPath = args[3]
	ErrorPath = args[4]
	ManagedSystemName = args[5]
	ManagedSystemAccount = args[6]
	TicketParameter = args[7]
	TicketNumber = args[8]
	RuleName = args[9]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	libTS = new libTicketSystem()
	libTS.addWebServiceTicketSystem(driver, TPAMaddress, TSName, TicketSystemAddress, TicketPath, ErrorPath, ManagedSystemName, ManagedSystemAccount, TicketParameter, TicketNumber, RuleName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


