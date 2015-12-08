import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {

	TPAMaddress = args[0]
	ReceiverName = args[1]
	ReceiverAddress = args[2]
	Interval = args[3]
	Threshold = args[4]
	Cycles = args[5]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")

	setReceiver(driver, TPAMaddress, ReceiverName, ReceiverAddress)
	setThreshold(driver, TPAMaddress, Interval, Threshold, Cycles)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


def setReceiver(driver, TPAM, ReceiverName, ReceiverAddress) {

		TPAMaddr = "https://" + TPAM + "/admin/ManageAlertReceivers.asp" 

  		driver.get(TPAMaddr)
		
		driver.findElement(By.id("AddAlertReceiver")).click()

		driver.findElement(By.id("ReceiverName")).sendKeys(ReceiverName)

		driver.findElement(By.id("Comment")).sendKeys("This receiver get emails about appliance CPU state")

       	driver.findElement(By.id("EmailAddress")).sendKeys(ReceiverAddress)

        driver.findElement(By.id("SubmitChanges")).click()

		if (h.waitForTextPresent(driver, "Receiver added successfully", 30)) {
			println("Receiver added.");
		}

		driver.findElement(By.id("Alerts")).click()

      	driver.findElement(By.xpath("//td[contains(text(), 'The appliance CPU has exceeded')]/..//input[@name='cbxAlert_L2_1']")).click()

//		driver.findElement(By.name("cbxAlert_L2_1")).click()
		
        driver.findElement(By.id("SubmitChanges")).click()

		if (h.waitForTextPresent(driver, "Subscriptions successfully saved", 30)) {
			println("Subscription saved.");
		}


}

def setThreshold(driver, TPAM, Interval, Threshold, Cycles) {

		TPAMaddr = "https://" + TPAM + "/admin/AlertThresholds.asp" 

       		driver.get(TPAMaddr)
		
		driver.findElement(By.id("I4")).clear()

		driver.findElement(By.id("I4")).sendKeys(Interval)

		driver.findElement(By.id("T4")).clear()

		driver.findElement(By.id("T4")).sendKeys(Threshold)

		driver.findElement(By.id("D4")).clear()

		driver.findElement(By.id("D4")).sendKeys(Cycles)

                driver.findElement(By.id("SubmitChanges")).click()

		if (h.waitForTextPresent(driver, "Successfully saved Alert Threshold settings", 30)) {
			println("CPU Threshold changed.");
		}

}