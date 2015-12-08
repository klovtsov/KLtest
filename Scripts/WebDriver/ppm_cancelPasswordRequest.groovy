import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

try {

	def browser = args[0]
	def TPAMaddress = args[1]

	def UserName = args[2]
	def UserPassword = args[3]
	def SystemName = args[4]
	def AccountName = args[5]

	libComn = new libCommon()
	driver = libComn.Browser(browser)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	libComn.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	
	driver.findElement(By.id("CurrentRequests")).click()
	
	driver.findElement(By.linkText("Pwd(${AccountName})")).click();

	driver.findElement(By.xpath("//tr[td = '$AccountName']")).click();

	driver.findElement(By.id("Details")).click()

	driver.findElement(By.id("ResponseComment")).sendKeys("cancel")

	Thread.sleep(1000)

	driver.findElement(By.id("SubmitChanges")).click()

	Thread.sleep(5000)

	if (driver.getPageSource().contains("was submitted successfully")) {
		println("Request for " + AccountName + " canceled.");
	}
	println(driver.findElement(By.id("ResultsDiv")).getText())


}
catch (e) {
    println("Catched: something went wrong: " + e);
}


driver.quit()



println("Finished.");


