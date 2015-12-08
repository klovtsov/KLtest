import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.ArrayList

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def CheckAlerts(driver, TPAM, ResultFile, Receiver) {

	File f = new File(ResultFile)
	if(f.exists() && !f.isDirectory()) {  f.delete() }

	PrintStream out = new PrintStream(new FileOutputStream(ResultFile))
	PrintStream original = new PrintStream(System.out)
	System.setOut(out)
	
    TPAMaddr = "https://" + TPAM + "/admin/ManageAlertReceivers.asp" 
    driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS)
	
	driver.manage().window().maximize()
	
	driver.findElement(By.id("AddAlertReceiver")).click()
	driver.findElement(By.id("ReceiverName")).sendKeys(Receiver)
	driver.findElement(By.id("EmailAddress")).sendKeys("Email@Address.com")
	Thread.sleep(2000)
	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForResult("Receiver added successfully", driver)
	if (driver.getPageSource().contains("Receiver added successfully")) {
			original.println("Receiver added successfully");
	}
	if (!driver.findElement(By.id("SubmitChanges")).isEnabled()) {
			original.println("SubmitChanges button is disabled!")
	}
	driver.findElement(By.id("Alerts")).click()
	Thread.sleep(2000)
	WebElement table = driver.findElement(By.id("subscriptionsTable"))
	
	List<WebElement> allRows = table.findElements(By.tagName("tr"));
	for (WebElement row : allRows) {
	    i = 1
		List<WebElement> cells = row.findElements(By.xpath(".//*[local-name(.)='tr' or local-name(.)='td']"));
		for (WebElement cell : cells) {
		print(cell.getText()+" ")
		i = i + 1
		if (i == 4) {println("")}
 }
}

	driver.findElement(By.id("DeleteAlertReceiver")).click()
	alert = driver.switchTo().alert()
	alert.accept()
	libCom.waitForResult("Alert Receiver successfully deleted", driver)
	if (driver.getPageSource().contains("Alert Receiver successfully deleted")) {
			original.println("Alert Receiver successfully deleted");
	}

}



println("Ready.GO.")
	
try {
    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]
	ResultFile = args[3]
	Receiver = args[4]
	
    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/admin/", UserName, UserPwd)
	
    CheckAlerts(driver, TPAMaddress, ResultFile, Receiver)
	
}
catch (e) {
    println("Catched: something went wrong: " + e)
}

driver.quit()

//println("Finished.")

