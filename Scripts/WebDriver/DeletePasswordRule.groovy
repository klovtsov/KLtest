import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)	

try {

	TPAMaddress = args[0]

	RuleName = args[1]

	h = new libCommon()
	h.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	deletePasswordRule(driver, TPAMaddress, RuleName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


def deletePasswordRule(driver, TPAM, PasswordRuleName)  {

		TPAMaddr = "https://" + TPAM + "/admin/ManagePwdRules.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		new Select(driver.findElement(By.id("PasswordRuleID"))).selectByVisibleText(PasswordRuleName)

		driver.findElement(By.id("DeleteRuleButton")).click()

		Thread.sleep(5000)

		alert = driver.switchTo().alert()
		alert.accept()
		
		Thread.sleep(15000)

		if (driver.getPageSource().contains("Password Rule was deleted successfully")) {
			println("Rule deleted.");
		}                 

}
