import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

Quantity = args[4].toInteger()

for (def i=1; i<=Quantity; i++) {

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

try {


	TPAMaddress = args[0]

	UserName = args[1] + i.toString()
	UserPassword = args[2]
	NewUserPassword = args[3]

	
	h = new libCommon()
	h.loginAndChangePassword(driver, TPAMaddress, "/tpam/", UserName, UserPassword, NewUserPassword)


}
catch (e) {
    println("Catched: something went wrong: " + e);
}


driver.quit()

}



println("Finished.");


