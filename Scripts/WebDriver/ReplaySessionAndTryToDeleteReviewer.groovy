import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");

driver = new FirefoxDriver()
driver2 = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
                                                                  
	TPAMaddress = args[0]
        UserName = args[1]
	UserPassword = args[2]
	AdminName = args[3]
	AdminPassword = args[4]
	RequestId = args[5]

		
try {


	h = new libCommon()
	lpp = new libPPM()



	h.login(driver2, TPAMaddress, "/tpam/", AdminName, AdminPassword)                
	h.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)


/////////Open session for replay



       	TPAMaddr = "https://" + TPAMaddress + "/tpam/main.asp"	
	driver.get(TPAMaddr)
	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)
	driver.findElement(By.id("PendingReviews")).click()
	if(h.waitForTextPresent(driver, RequestId, 60)) {
		driver.findElement(By.linkText(RequestId)).click()                   
		h.waitForTextPresent(driver, "PSM Sessions for Review", 30)
                Thread.sleep(5000)
                driver.findElement(By.id("SessionLogs")).click()	
                h.waitForTextPresent(driver, "Start Date", 30)
                WebElement myTable = driver.findElement(By.id("SessionLogsTable"))
                List<WebElement> allRows = myTable.findElements(By.tagName("tr"))
                allRows[0].click()
//              String winHandleBefore = driver.getWindowHandle()
		driver.findElement(By.id("ReplayButton")).click()
		Thread.sleep(15000)
//        	for(String winHandle : driver.getWindowHandles()){
//			driver.switchTo().window(winHandle)
//		}
	} else {
			println("Request ID unavailable");
	}


//////// Try to delete reviewer

	TPAMaddr = "https://" + TPAMaddress + "/tpam/UserFilter.asp"
	driver2.get(TPAMaddr)
	h.waitForElementWithId(driver2,"UsrNm")
	driver2.findElement(By.id("UsrNm")).sendKeys(UserName)
	driver2.findElement(By.id("Listing")).click()
	driver2.findElement(By.xpath("//tr[td[contains(text(),'$UserName')]]")).click()
	h.waitForElementWithId(driver2,"GotoGroups")
        driver2.findElement(By.id("Delete")).click()
	alert = driver2.switchTo().alert()
	alert.accept()

	result = "You can not delete this user since PSM Sessions are active for this user"

	if (h.waitForResult(result, driver2)) {

		println("Reviewer prevented from deletion")

		}

	Thread.sleep(60000)

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()
driver2.quit()

println("Finished.");
