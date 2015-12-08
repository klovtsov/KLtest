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
managePwdPage = "/tpam/PasswordManagement.asp"

//open Password Management page
def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def action = args[3]

managePwdPageFull = "https://" + TPAMaddress + managePwdPage

try {
	libr = new libCommon()
	libr.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	driver.get(managePwdPageFull)
	//Action
	if (action == 'save'){
		//check if filter already exists
		inputSysName = libr.waitForElementWithId(driver, 'SystemNm');
		if(!inputSysName.isDisplayed()){ //Default filter is already set.
			WebDriverWait waitF = (new WebDriverWait(driver, 10))
			waitF.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
			//Go to Filter tab
			driver.findElement(By.id('Filter')).click()
		}
		def map = [All:'rb_acctAutoBoth',Disabled:'rb_acctAutoNotChecked', Automatic:'rb_acctAutoChecked',Manual:'rb_acctAutoManualChecked']
		inputSysName.clear()
		inputSysName.sendKeys(args[4])
		inputAccntName = driver.findElement(By.id('AccountNm'))
		inputAccntName.clear()
		inputAccntName.sendKeys(args[5])
		//select automatic password management
		driver.findElement(By.id(map[args[6]])).click()
		//select Default Filter settings
		new Select(driver.findElement(By.id('FilterSettings'))).selectByVisibleText('Save')
		//go to listing tab (to save filter)
		driver.findElement(By.id('Listing')).click()
		WebDriverWait wait = (new WebDriverWait(driver, 10))
	        wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
	
	} else if (action == 'clear'){
	    WebDriverWait waitF = (new WebDriverWait(driver, 10))
	        waitF.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
	    driver.findElement(By.id('Filter')).click()
		inputSysName = libr.waitForElementWithId(driver, 'SystemNm');
		inputSysName.clear()
		driver.findElement(By.id('AccountNm')).clear()
		new Select(driver.findElement(By.id('FilterSettings'))).selectByVisibleText('Clear')
		//go to listing tab (to save filter)
		driver.findElement(By.id('Listing')).click()
		WebDriverWait wait = (new WebDriverWait(driver, 10))
			wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
	} 
	else{
		println("Action ${action} is unknown.");
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
driver.quit()

println("Script finished.");