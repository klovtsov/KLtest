import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def DisabledSaveButton (driver, TPAM, AccountName) {

    TPAMaddr = "https://" + TPAM + "/tpam/ManageAccounts.asp" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    driver.findElement(By.id("Filter")).click()
    driver.findElement(By.id("AccountNm")).sendKeys(AccountName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$AccountName')]")).click()
	driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("PSMDetails")).click()
// bfer 6130
	driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("AcctDesc")).sendKeys("Description")

	driver.findElement(By.id("SubmitChangesButton")).click()
	Thread.sleep(3000)
	if (!driver.findElement(By.id("SubmitChangesButton")).isEnabled()) {
			println("SubmitChanges button is disabled!")
	}	
}
		
try {

    TPAMaddress = args[0]
	AccounName = args[1]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	
	DisabledSaveButton(driver, TPAMaddress, AccounName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


