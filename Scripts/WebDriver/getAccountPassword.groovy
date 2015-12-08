import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def accntName = args[3]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	driver.findElement(By.id('CurrentRequests')).click()
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//span[@class="fieldHint"]'), 'Displaying'));
	driver.findElement(By.linkText("Pwd(${accntName})")).click()
	//wait
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("reportTab"), 'rows meeting filter criteria'));
	driver.findElement(By.xpath("//td[contains(text(),'$accntName')]")).click();
	driver.findElement(By.id('Password')).click()
	PasswordXPath = driver.findElement(By.xpath('//div[@class="displayPassword"]')).getText()
	String[] PasswordParts = PasswordXPath.split("\\r?\\n");
	password = PasswordParts[1].replace("]", "").replace("[", "").trim();
	println(password)

}catch (TimeoutException toutex){
    libComn.takeScreenshot(driver, "get_account_password")
    println("Catched: trouble to find element: " + toutex);
}
catch (e){
    println("Catched: something went wrong: " + e);
}
finally{
    driver.quit()
}