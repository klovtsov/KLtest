import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");

driver1 = new FirefoxDriver()
driver2 = new FirefoxDriver()
driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
                                                                  
	TPAMaddress = args[0]
        UserName = args[1]
	UserPassword = args[2]
	SystemName = args[3]
	AccountName = args[4]
	FileName = args[5]
	PolicyName = args[6]

		
try {


	h = new libCommon()
	lpp = new libPPM()



	h.login(driver1, TPAMaddress, "/tpam/", UserName, UserPassword)                
	h.login(driver2, TPAMaddress, "/tpam/", UserName, UserPassword)

//////// System Permissions Tab

	lpp.chooseSystem(driver1, TPAMaddress, SystemName)

        driver1.findElement(By.id("Permissions")).click()
	driver1.findElement(By.id("apaFilterUserName")).sendKeys(UserName)
	driver1.findElement(By.xpath("(//a[contains(text(),'Results')])[2]")).click()
	new Select(driver1.findElement(By.id("AccessPolicyID"))).selectByVisibleText(PolicyName)
	              	                                         	
	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails != "Sess; REQ; n/a n/a n/a n/a n/a ") {

		println("Wrong Policy Selected")

		}

	println(policyDetails)

	updatePolicy(driver2, TPAMaddress, PolicyName)

        driver1.findElement(By.id("btnRefresh")).click()

	Thread.sleep(3000)

	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails == "Pwd;File;Sess; ISA;REQ;REV; n/a n/a n/a n/a n/a ") {

		println("System Policy Details Refreshed Successfully")

		}
                                                      
	println(policyDetails)
                                                      
	updatePolicy(driver2, TPAMaddress, PolicyName)

//////// User Permissions Tab		

	lpp.ChooseUser(driver1, TPAMaddress, UserName)

        driver1.findElement(By.id("Permissions")).click()
	driver1.findElement(By.id("apaFilterSystemName")).sendKeys(SystemName)
	driver1.findElement(By.id("Results")).click()
	new Select(driver1.findElement(By.id("AccessPolicyID"))).selectByVisibleText(PolicyName)
	              	                                         	
	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails != "Sess; REQ; n/a n/a n/a n/a n/a ") {

		println("Wrong Policy Selected")

		}

	println(policyDetails)

	updatePolicy(driver2, TPAMaddress, PolicyName)

        driver1.findElement(By.id("btnRefresh")).click()

	Thread.sleep(3000)

	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails == "Pwd;File;Sess; ISA;REQ;REV; n/a n/a n/a n/a n/a ") {

		println("User Policy Details Refreshed Successfully")

		}
                                                      
	println(policyDetails)
                                                      
	updatePolicy(driver2, TPAMaddress, PolicyName)

/////// Account Permissions Tab

	lpp.ChooseAccount(driver1, TPAMaddress, SystemName, AccountName)

        driver1.findElement(By.id("Permissions")).click()
	driver1.findElement(By.id("apaFilterUserName")).sendKeys(UserName)
	driver1.findElement(By.xpath("(//a[contains(text(),'Results')])[3]")).click()
	new Select(driver1.findElement(By.id("AccessPolicyID"))).selectByVisibleText(PolicyName)
	              	                                         	
	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails != "Sess; REQ; n/a n/a n/a n/a n/a ") {

		println("Wrong Policy Selected")

		}

	println(policyDetails)

	updatePolicy(driver2, TPAMaddress, PolicyName)

        driver1.findElement(By.id("btnRefresh")).click()

	Thread.sleep(3000)

	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails == "Pwd;File;Sess; ISA;REQ;REV; n/a n/a n/a n/a n/a ") {

		println("Account Policy Details Refreshed Successfully")

		}
                                                      
	println(policyDetails)
                                                      
	updatePolicy(driver2, TPAMaddress, PolicyName)


/////// File Permissions Tab

	lpp.ChooseFile(driver1, TPAMaddress, SystemName, FileName)

        driver1.findElement(By.id("Permissions")).click()
	driver1.findElement(By.id("apaFilterUserName")).sendKeys(UserName)
	driver1.findElement(By.id("Results")).click()
	new Select(driver1.findElement(By.id("AccessPolicyID"))).selectByVisibleText(PolicyName)
	              	                                         	
	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails != "Sess; REQ; n/a n/a n/a n/a n/a ") {

		println("Wrong Policy Selected")

		}

	println(policyDetails)

	updatePolicy(driver2, TPAMaddress, PolicyName)

        driver1.findElement(By.id("btnRefresh")).click()

	Thread.sleep(3000)

	policyDetails = TableViewer(driver1, "tblAccessPolicyDetails")

	if (policyDetails == "Pwd;File;Sess; ISA;REQ;REV; n/a n/a n/a n/a n/a ") {

		println("File Policy Details Refreshed Successfully")

		}
                                                      
	println(policyDetails)
                                                      
	updatePolicy(driver2, TPAMaddress, PolicyName)



}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver1.quit()
driver2.quit()

println("Finished.");


def updatePolicy(driver, TPAM, PolicyName) {

	TPAMaddr = "https://" + TPAM + "/tpam/ManageAccessPolicies.asp" 
	driver.get(TPAMaddr)
	driver.findElement(By.id("filtPolicyName")).sendKeys(PolicyName)
        driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$PolicyName')]")).click()
        driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("cbxAPDtlTypePassword")).click()
	driver.findElement(By.id("cbxAPDtlTypeFile")).click()
	driver.findElement(By.id("cbxISA")).click()
	driver.findElement(By.id("cbxReview")).click()
	driver.findElement(By.id("SubmitChangesButton")).click()             
	h.waitForTextPresent (driver, "Access Policy saved successfully", 10)


}


def TableViewer(driver, tableName) {

retString = ""
	WebElement table = driver.findElement(By.id(tableName))
	List<WebElement> allRows = table.findElements(By.tagName("tr"));
	for (Integer j=0; j<=(allRows.size()-1); j++) {

		List<WebElement> cells = allRows[j].findElements(By.tagName("td"))
		for (Integer i=0; i<=cells.size()-1; i++) {
			List<WebElement> divs = cells[i].findElements(By.tagName("div"))		                                                                  	
		        for (Integer k=0; k<=divs.size()-1; k++) {
//				println("Row " + j.toString() + " Cell " + i.toString() + " Div " + k.toString() + " " + divs[k].getText())
				retString += divs[k].getText()
				retString += " "
			}
		}
	}

return retString

}
