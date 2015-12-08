import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	TSName = args[1]
	SystemAddress = args[2]
	SystemPort = args[3]
	DBName = args[4]
	UserName = args[5]
	UserPassword = args[6]
	SQLStatement = args[7]
	TicketNumber = args[8]
	RuleName = args[9]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	libTS = new libTicketSystem()
	libTS.addNonManagedTicketSystem(driver, TPAMaddress, TSName, SystemAddress, SystemPort, DBName, UserName, UserPassword, SQLStatement, TicketNumber, RuleName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


