import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]
        TPAMUser = args[1]
        TPAMPassword = args[2]
        TokenPath = args[3]
        TokenPassphrase = args[4]
	TokenName = args[5]

	h = new libCommon()
	h.loginWithPasswordThenWithToken(driver, TPAMaddress, "/tpam/", TPAMUser, TPAMPassword, TokenPath, TokenPassphrase, TokenName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


