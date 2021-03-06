import org.openqa.selenium.WebElement;
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def sysName = args[3]

def urlDeletedSystems = "https://$TPAMaddress/tpam/ManageDeletedSystems.asp"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	driver.get(urlDeletedSystems)
	//Set filtering options
	txtSystemName = libComn.waitForElementWithId(driver, 'SystemNm')
	txtSystemName.sendKeys(sysName)
	//Select system to restore
	driver.findElement(By.id("Listing")).click()
	WebDriverWait wait = new WebDriverWait(driver, 10);
	   wait.until(ExpectedConditions.textToBePresentInElement(By.id('ListingTab'), 'Displaying 1 of 1 rows meeting filter criteria'));
	driver.findElement(By.xpath("//tr[td = '$sysName']")).click();
	//Restore account and get result
	driver.findElement(By.id('UnDeleteButton')).click()
	println(driver.findElement(By.xpath("//div[@id='ResultTab']//div")).text)
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "UndoAccount")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
