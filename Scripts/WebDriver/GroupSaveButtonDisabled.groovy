import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def DisabledSaveButton (driver, TPAM, GroupName) {

    TPAMaddr = "https://" + TPAM + "/tpam/GroupFilter.asp?StartPage=NewGroup" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

    driver.findElement(By.id("GroupName")).sendKeys(GroupName)

	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForElementEnabled(driver, "SubmitChanges", 10)
	libCom.waitForResult("The group was created successfully", driver, 10)
	if (!driver.findElement(By.id("SubmitChanges")).isEnabled()) {
			println("SubmitChanges button is disabled!")
	}	
	
	driver.findElement(By.id("GroupDsc")).sendKeys("Description")

	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForElementEnabled(driver, "SubmitChanges", 10)
	libCom.waitForResult("The group was saved successfully", driver, 10)

	if (!driver.findElement(By.id("SubmitChanges")).isEnabled()) {
			println("SubmitChanges button is disabled!")
	}
	
	driver.findElement(By.id("Delete")).click()
	Alert alert =driver.switchTo().alert()
	alert.accept()
	
}
		
try {

    TPAMaddress = args[0]
	GroupName = args[1]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	
	DisabledSaveButton(driver, TPAMaddress, GroupName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


