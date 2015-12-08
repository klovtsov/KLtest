import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def DeleteCustomColumns (driver, TPAM) {

    TPAMaddr = "https://" + TPAM + "/admin/GlobalSettings.asp" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	new Select(driver.findElement(By.id("categoryFilter"))).selectByVisibleText("Custom Column Names");
//  Delete Custom Columns
    for ( i in 23..40 ) {
		driver.findElement(By.id("T"+i)).clear()
	}
	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())			
}
		
try {

    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/admin/", UserName, UserPwd)
	
	DeleteCustomColumns(driver, TPAMaddress)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


