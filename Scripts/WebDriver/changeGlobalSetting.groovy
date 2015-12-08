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
def category = args[3]
def optionName = args[4]
def optionValue = args[5]

def urlGlobalSettings = "https://$TPAMaddress/admin/GlobalSettings.asp"
def succesChange = 'Global Settings changes saved successfully'

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/admin/", adminName, adminPwd)
	driver.get(urlGlobalSettings)
	libComn.waitForElementWithId(driver, 'GlobalSettingsTable')
	
	inputOptionElement = driver.findElement(By.xpath("//tr[td/span = '$category' and td='$optionName']/*/input"))
	inputOptionElement.clear();
	inputOptionElement.sendKeys(optionValue);
	driver.findElement(By.id('SubmitChanges')).click();
	libComn.waitForResult(succesChange, driver)
	nvalue = inputOptionElement.getAttribute('value')
	if(nvalue != optionValue){
		println("Something went wrong: new value is '$nvalue'")
		println("Expected value is '$optionValue'")
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
