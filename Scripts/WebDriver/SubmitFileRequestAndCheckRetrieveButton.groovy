import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

def checkFileRetrieveButton (driver, TPAM, FileName, PolicyName) {

	TPAMaddr = "https://" + TPAM + "/tpam/main.asp" 
	driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	driver.findElement(By.linkText("Add File Request")).click()
	driver.findElement(By.id("FileNm")).sendKeys(FileName)
        driver.findElement(By.id("Files")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$PolicyName')]")).click()
        driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("RequestReason")).sendKeys("Test Request")
	driver.findElement(By.id("SubmitChanges")).click()

	h.waitForTextPresent (driver, "has been submitted", 10)

       	try {


		if (driver.findElement(By.id("RetrieveButton")).isEnabled()) {

			println("Retrieve Button Enabled")

		} else {


			println("Retrieve Button Disabled")

		}

	}

	catch (NoSuchElementException Ex) {

		println("Retrieve Button is not present")

	}

	driver.findElement(By.id("ResponseComment")).sendKeys("Cancel Request")
	driver.findElement(By.id("SubmitChanges")).click()
	h.waitForTextPresent (driver, "was submitted successfully", 10)
	
}
		
try {

	TPAMaddress = args[0]
        UserName = args[1]
	UserPassword = args[2]
	FileName = args[3]
	PolicyName = args[4]

	h = new libCommon()
                
	h.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	
	checkFileRetrieveButton(driver, TPAMaddress, FileName, PolicyName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


