import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

Quantity = args[5].toInteger()

for (def i=1; i<=Quantity; i++) {

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

try {


	TPAMaddress = args[0]

	UserName = args[1] + i.toString()
	UserPassword = args[2]
	SystemName = args[3]
	AccountName = args[4] + i.toString()

	h = new libCommon()
	h.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	l = new libPPM()
	l.addPasswordRequest(driver, TPAMaddress, SystemName, AccountName)


}
catch (e) {
    println("Catched: something went wrong: " + e);
}


driver.quit()

}



println("Finished.");


