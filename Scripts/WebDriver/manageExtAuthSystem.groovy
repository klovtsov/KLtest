import org.openqa.selenium.*
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select

import java.util.concurrent.TimeUnit

AUTH_SYSTEM_CREATED = "New WinAD added successfully."
AUTH_SYSTEM_DELETED = "External Authentication configuration deleted successfully."
btnNewId = "NewSystemButton"

//Check if system is already exists
def boolean isSystemExists(sysName){
	xpath = "//select[@id='ExtAuthCfgID']/option[.= '" +sysName+ "']"
	Boolean isPresent = driver.findElements(By.xpath(xpath)).size() > 0
	return isPresent
}

//Create system function
def createADauthSystem(AuthSysName,AuthSysAddress){
	if(!isSystemExists(AuthSysName)){
		driver.findElement(By.id(btnNewId)).click()
		driver.findElement(By.id("SystemName")).sendKeys(AuthSysName)
		driver.findElement(By.id("ServerAddress")).sendKeys(AuthSysAddress)
		driver.findElement(By.id("SubmitChangesButton")).click()
		libr.waitForResult(AUTH_SYSTEM_CREATED,driver)
		println("Successful");
	}else{
		println("System already exists.");
	}
}

def deleteADauthSystem(sysName){
	if(isSystemExists(sysName)){
		rcpath = "//select[@id='ExtAuthCfgID']/option[.= '" +sysName+ "']"
		driver.findElement(By.xpath(rcpath)).click()
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("DeleteSystemButton")));
		element.click();
		alert = driver.switchTo().alert()
		alert.accept()
		libr.waitForResult(AUTH_SYSTEM_DELETED,driver)
		println("Successful");
	}else{
	    println("System does not exists.");
	}
}

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


try {
	//TPAMaddress = "10.30.44.206"
	TPAMaddress = args[0]
	action = args[1]
	extrnlAuthSysName = args[2]
	AsysConfigAddress = "https://" + TPAMaddress + "/admin/ADConfig.asp"
	//login to admin interface
	libr = new libCommon()
	libr.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	driver.get(AsysConfigAddress)
	WebElement newRCbutton = (new WebDriverWait(driver, 10))
	.until(ExpectedConditions.presenceOfElementLocated(By.id(btnNewId)));
	
	if(action == "create"){
		extrnlAuthSysAddress = args[3]
		createADauthSystem(extrnlAuthSysName,extrnlAuthSysAddress)
	} else if (action == "delete"){
		deleteADauthSystem(extrnlAuthSysName)
	}else{
		println("Action " + action + " is unknown. Possible values for action are: create or delete.");
	}
}
catch (e){
    println("Catched: something went wrong: " + e);
}
driver.quit()

println("Script finished.");
