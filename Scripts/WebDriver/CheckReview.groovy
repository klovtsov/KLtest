import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.Calendar
import java.text.SimpleDateFormat

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()
		
try {

	TPAMaddress = args[0]
	UserName = args[1]
	Password = args[2]  
	TableLink = args[3]
	TableName = args[4]
	RowContains = args[5]
	
	
	h = new libCommon()
	h.login(driver, TPAMaddress, "/tpam/", UserName, Password)
	r = new libReview()
	r.tableLoginAndReadRow(driver, TPAMaddress, TableLink, TableName, RowContains)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");
