import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	UserName = args[1]
	Password = args[2]
	AccountName = args[3]
	CommentText = args[4]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/tpam/", UserName, Password)
	r = new libReview()
	r.completeIsaReview(driver, TPAMaddress, AccountName, CommentText)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


