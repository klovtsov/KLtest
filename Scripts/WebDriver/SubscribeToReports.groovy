import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0];

	UserName = args[1];
	Password = args[2];
	EmailAddress = args[3];

	h = new libCommon();
	h.login(driver, TPAMaddress, "/tpam/", UserName, Password);
	enableAllReports(driver, TPAMaddress)
	subscribeToReports(driver, TPAMaddress, EmailAddress);
}
catch (e) {
	println("Catched: something went wrong: " + e);
}

driver.quit();

println("Finished.");

def  enableAllReports(driver, TPAM)  {

	TPAMaddr = "https://" + TPAM + "/tpam/ReportSubscriptions.asp";
	
	driver.get(TPAMaddr);
	
	Thread.sleep(5000);
	
	WebElement reportTable = driver.findElement(By.id("reportsTable"));
	
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
	
		for (WebElement row : allRows) {
			
			mysel = new Select(row.findElement(By.tagName("select")))
			mysel.selectByVisibleText("HTML & CSV");
	
		}
}

def  subscribeToReports(driver, TPAM, EmailAddress)  {

	TPAMaddr = "https://" + TPAM + "/tpam/ReportSubscriptions.asp";

	driver.get(TPAMaddr);

	Thread.sleep(5000);

	WebElement reportTable = driver.findElement(By.id("reportsTable"));

	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));

	for (WebElement row : allRows) {
		
		mysel = new Select(row.findElement(By.tagName("select"))).getFirstSelectedOption()
		String selectedOption = mysel.getText();
		
		if (selectedOption != "Disabled") {

			row.click();
			Thread.sleep(1000);
			driver.findElement(By.id("AdditionalRecipients")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("EmailAddress_0")).sendKeys(EmailAddress);
			new Select(driver.findElement(By.xpath("//tr[@id='row_0']/td[@class='SubscriptionType']/select[@class='subscribedto']"))).selectByVisibleText("All");
			driver.findElement(By.xpath("//input[contains(@value, 'Add This Recipient')]")).click();
			Thread.sleep(5000);
			if (driver.getPageSource().contains("Successfully added email address")) {
				println("Subscription added.");
				
			}
			driver.findElement(By.id("MyReportSubscriptions")).click();
			Thread.sleep(1000);
		}
	}
}