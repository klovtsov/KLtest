import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]

	RuleName = args[1]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	createPasswordRule(driver, TPAMaddress, RuleName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


def createPasswordRule(driver, TPAM, PasswordRuleName)  {

		TPAMaddr = "https://" + TPAM + "/admin/ManagePwdRules.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(5000)

		driver.findElement(By.id("NewRuleButton")).click()
		driver.findElement(By.id("PwdRuleName")).sendKeys(RuleName)
		driver.findElement(By.id("PwdRuleDesc")).sendKeys("Rule for UnixWare passwords")
		
		driver.findElement(By.id("MaxChars")).clear()
		driver.findElement(By.id("MaxChars")).sendKeys("8")
		driver.findElement(By.id("MinChars")).clear()
		driver.findElement(By.id("MinChars")).sendKeys("7")

		new Select(driver.findElement(By.id("ReqFirst"))).selectByVisibleText("Alpha Characters Only")
		new Select(driver.findElement(By.id("ReqUpper"))).selectByVisibleText("Require at least 2")
		new Select(driver.findElement(By.id("ReqLower"))).selectByVisibleText("Require at least 2")
		new Select(driver.findElement(By.id("ReqNum"))).selectByVisibleText("Require at least 1")
		new Select(driver.findElement(By.id("ReqNonAN"))).selectByVisibleText("Not Permitted")

		driver.findElement(By.id("SubmitChangesButton")).click()

		Thread.sleep(5000)

		if (driver.getPageSource().contains("Password Rule changes saved successfully")) {
			println("Rule created.");
		}                 

}


