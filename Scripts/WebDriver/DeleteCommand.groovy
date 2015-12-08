import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	CommandName = args[1]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/tpam/", "ParAdmin", "Admin4PAR")
	deleteCommand(driver, TPAMaddress, CommandName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


def deleteCommand(driver, TPAM, Command)  {

		TPAMaddr = "https://" + TPAM + "/tpam/CommandEditor.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)


		WebElement myTable = driver.findElement(By.id("CommandListing"));

		List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
		for (WebElement elRow:allRows){
						
	
			if (elRow.getText().contains(Command)) {

			elRow.click()	
			
			Thread.sleep(2000)			

			driver.findElement(By.id("DeleteButton")).click()

			Thread.sleep(5000)

			alert = driver.switchTo().alert()
			alert.accept()
		
			Thread.sleep(15000)

			if (driver.getPageSource().contains("Command successfully deleted")) {
				println("Command deleted.");
			return 0
			
			}
		}
	}
}
