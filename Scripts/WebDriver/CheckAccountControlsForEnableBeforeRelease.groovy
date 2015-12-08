import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

def CheckControls (driver, TPAM, AccountName) {

    TPAMaddr = "https://" + TPAM + "/tpam/ManageAccounts.asp" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    driver.findElement(By.id("Filter")).click()
    driver.findElement(By.id("AccountNm")).sendKeys(AccountName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$AccountName')]")).click()
	driver.findElement(By.id("Details")).click()
//	driver.findElement(By.id("cb_EnableBeforeReleaseFl")).click()
//	driver.findElement(By.id("SubmitChangesButton")).click()
	Thread.sleep(2000)
	
	Success = true

	if (!driver.findElement(By.id("cb_EnableBeforeReleaseFl")).isSelected()) {
			println("Enable Account before Release checkbox is not checked!")
			Success = false
	}	


	if (driver.findElement(By.id("cb_ChgSvc")).isEnabled()) {
			println("Change Services checkbox is enabled!")
			Success = false
	}	

//	if (!driver.findElement(By.id("CheckPassButton")).isEnabled()) {
//			println("Check Password button is disabled!")
//			Success = false
//	}	
///////////Uncomment when BFER 6716 is fixed !!!!
//	if (driver.findElement(By.id("SubmitChangesButton")).isEnabled()) {
//			println("Submit Changes button is enabled!")
//			Success = false
//	}	


	if (driver.findElement(By.id("cb_RstSvc")).isEnabled()) {
			println("Restart Services checkbox is enabled!")
			Success = false
	}	


	if (driver.findElement(By.id("cb_ChgTask")).isEnabled()) {
			println("Change Scheduled Task password checkbox is enabled!")
			Success = false
	}	


	if (driver.findElement(By.id("cb_UseSelf")).isEnabled()) {
			println("Use Self checkbox is enabled!")
			Success = false
	}	


//	if (!driver.findElement(By.id("rb_ChkFl0")).isSelected()) {
//			println("Check Password radiobutton is not checked!")
//			Success = false
//	}	


//	if (driver.findElement(By.id("rb_ChkFl1")).isSelected()) {
//			println("Don't Check Password radiobutton is checked!")
//			Success = false
//	}	


//	if (driver.findElement(By.id("cb_ResetFl")).isEnabled()) {
//			println("Reset on Mismatch checkbox is enabled!")
//			Success = false
//	}	


//	if (!driver.findElement(By.id("cb_ReleaseChgFl")).isSelected()) {
//			println("Change after Release checkbox is not checked!")
//			Success = false
//	}	

	if (Success) {

	println("All controls are fine")

	}

}
		
try {

    TPAMaddress = args[0]
	AccountName = args[1]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	
	CheckControls(driver, TPAMaddress, AccountName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


