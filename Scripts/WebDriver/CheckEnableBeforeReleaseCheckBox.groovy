import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

def CheckEnableBeforeReleaseFlag (driver, TPAM, SystemName, AccountName) {

	TPAMaddr = "https://" + TPAM + "/tpam/ManageAccounts.asp" 
	driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	driver.findElement(By.id("Filter")).click()
	driver.findElement(By.id("SystemNm")).sendKeys(SystemName)
	driver.findElement(By.id("AccountNm")).sendKeys(AccountName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$AccountName')]")).click()
	driver.findElement(By.id("Details")).click()

       	Success = true

	try {

	if (!driver.findElement(By.id("cb_EnableBeforeReleaseFl")).isDisplayed()) {
			println("Enable Account before Release checkbox is not dispalyed")
			Success = false
	}	


	if (Success) {

		if (driver.findElement(By.id("cb_EnableBeforeReleaseFl")).isEnabled()) {

			println("Enable Account before Release checkbox is displayed and enabled")

		} else {


			println("Enable Account before Release checkbox is displayed and disabled")

		}

	}


	}

	catch (NoSuchElementException Ex) {

		println("cb_EnableBeforeReleaseFl element is not present")

	}


	
}
		
try {

	TPAMaddress = args[0]
        SystemName = args[1]
	AccountName = args[2]

	libCom = new libCommon()
                
	libCom.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	
	CheckEnableBeforeReleaseFlag(driver, TPAMaddress, SystemName, AccountName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


