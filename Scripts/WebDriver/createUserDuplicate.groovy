import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def initName = args[3]
def newName = args[4]
def initPwd = args[5]

def mWebElements = ['UserFilter','UsrNm','UserName','User added successfully']

//Duplicate button Id
def btnDuplId='Duplicate'

//Save changes button ID
def btnSaveId = 'SubmitChanges'


try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	//open corresponding page
	TPAMaddr = "https://$TPAMaddress/tpam/${mWebElements[0]}.asp"
	driver.get(TPAMaddr)
	//Set filter value
	objNameField =libComn.waitForElementWithId(driver,mWebElements[1]);
	objNameField.sendKeys(initName)
	//Go to Listing tab
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td[contains(text(),'$initName')]]")).click();
	//Duplicate
	WebElement btnDupl = new WebDriverWait(driver, 10)
	   .until(ExpectedConditions.elementToBeClickable(By.id(btnDuplId)));
	Thread.sleep(500)
	btnDupl.click();
	//Set duplicate values
	newNameField = libComn.waitForElementWithId(driver,mWebElements[2]);
	newNameField.clear();
	newNameField.sendKeys(newName);
	//For user it is necessary to specify First and Last Name
    driver.findElement(By.id("LastName")).sendKeys('Smith');
	driver.findElement(By.id("FirstName")).sendKeys('John');
	//Set user pwd
	driver.findElement(By.id('sPassword')).sendKeys(initPwd);
	driver.findElement(By.id('Confirm')).sendKeys(initPwd);
	//Save and wait results
	driver.findElement(By.id(btnSaveId)).click()
	libComn.waitForResult(mWebElements[3], driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
}