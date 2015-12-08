import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
ffdriver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	UserName = args[1]
	Password = args[2]
	Account = args[3]
	Comment = args[4]


	h = new libCommon()
	h.login(ffdriver, TPAMaddress, "/tpam/", UserName, Password)
	libRev = new libReview()
	libRev.submitIsaComment(ffdriver, TPAMaddress, Account, Comment)

	
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

ffdriver.quit()

println("Finished.");


