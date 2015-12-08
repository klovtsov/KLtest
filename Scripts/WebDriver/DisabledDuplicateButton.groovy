import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def DisabledDuplicateButton (driver, TPAM, AccountName) {

    TPAMaddr = "https://" + TPAM + "/tpam/ManageAccounts.asp" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    driver.findElement(By.id("Filter")).click()
    driver.findElement(By.id("AccountNm")).sendKeys(AccountName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$AccountName')]")).click()
	Thread.sleep(2000)
	if (!driver.findElement(By.id("DuplicateButton")).isEnabled()) {
			println("Duplicate button is disabled!")
	}	
			
}
		
try {

    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]
	AccounName = args[3]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", UserName, UserPwd)

	
	DisabledDuplicateButton(driver, TPAMaddress, AccounName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


