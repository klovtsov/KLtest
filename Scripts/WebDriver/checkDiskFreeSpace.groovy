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
def thresholdValue = args[3].toInteger()

def urlSystemSettings = "https://$TPAMaddress/admin/SystemStatus.asp"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/admin/", adminName, adminPwd)
	driver.get(urlSystemSettings)
	libComn.waitForElementWithId(driver, 'statusTab')
	freeSpaceValue = driver.findElement(By.xpath("//fieldset[legend='Disk (MB)']/div[div='Available:']/div[@class = 'ctrlSet']")).getText().toInteger()
	def answer = freeSpaceValue>thresholdValue ? 'Ok': "Low Disk Space: $freeSpaceValue"
	println(answer)
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}