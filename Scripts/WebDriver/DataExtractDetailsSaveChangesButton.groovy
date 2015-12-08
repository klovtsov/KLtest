import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

def checkSubmitChangesButton (driver, TPAM, ScheduleName) {

	TPAMaddr = "https://" + TPAM + "/tpam/DataExtractSchedules.asp" 
	driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	driver.findElement(By.xpath("//td[contains(text(),'$ScheduleName')]")).click()
        driver.findElement(By.id("Details")).click()


	h.waitForElementWithId(driver,"SubmitChanges")


       	try {


		if (driver.findElement(By.id("SubmitChanges")).isEnabled()) {

			println("Submit Changes Button Enabled")

		} else {


			println("Submit Changes Button Disabled")

		}

	}

	catch (NoSuchElementException Ex) {

		println("Retrieve Button is not present")

	}

		
}
		
try {

	TPAMaddress = args[0]
        UserName = args[1]
	UserPassword = args[2]
	ScheduleName = args[3]

	h = new libCommon()
                
	h.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	
	checkSubmitChangesButton(driver, TPAMaddress, ScheduleName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


