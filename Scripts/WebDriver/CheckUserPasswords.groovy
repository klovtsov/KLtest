import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.interactions.Actions

Quantity = args[3].toInteger()

for (def i=1; i<=Quantity; i++) {

	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()

	try {


		TPAMaddress = args[0]

		UserName = args[1] + i.toString()
		UserPassword = args[2]


		TPAMaddr = "https://" + TPAMaddress + "/tpam/" 

		driver.get(TPAMaddr)

		Thread.sleep(5000)

		driver.findElement(By.id("loginUserName")).sendKeys(UserName)

		driver.findElement(By.id("loginPassword")).sendKeys(UserPassword + Keys.RETURN)

		Thread.sleep(10000)

		// Actions action = new Actions(driver)	// A hack for hardware appliance (Message of the Day)
		// action.sendKeys(Keys.RETURN)

		alert = driver.switchTo().alert() // A hack for hardware appliance (Message of the Day)
		alert.accept()

		Thread.sleep(5000)


		if (!driver.getPageSource().contains("Recent Activity")) {
			println("Error changing password for User: " + User);
		}



	}
	catch (e) {
		println("Catched: something went wrong: " + e + "User: " + UserName);
	}


	driver.quit()

}



println("Finished.");


