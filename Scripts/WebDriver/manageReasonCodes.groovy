import org.openqa.selenium.*
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select

import java.util.concurrent.TimeUnit

REASON_CODE_CANT_BE_DELETED = "Reason Code cannot be deleted because it has been used."
REASON_CODE_ADDED = "Reason Code successfully added"
REASON_CODE_DELETED = "Reason Code successfully deleted"
/*
def login (driver, TPAM, Path, User, Password) {
	TPAMaddr = "https://" + TPAM + Path
	driver.get(TPAMaddr)
	driver.findElement(By.id("loginUserName")).sendKeys(User)
	driver.findElement(By.id("loginPassword")).sendKeys(Password + Keys.RETURN)
	driver.findElement(By.id("btnLogin")).click()
	WebElement recentActivElement = (new WebDriverWait(driver, 10))
	.until(ExpectedConditions.presenceOfElementLocated(By.id("RecentActivity")));
}
*/
def createRCode(codeName){
	rcpath = "//select/option[contains(text(),'"+codeName+"')]"
	Boolean isPresent = driver.findElements(By.xpath(rcpath)).size() > 0
	if(!isPresent){
		driver.findElement(By.id("NewButton")).click()
		driver.findElement(By.id("ReasonCodeName")).sendKeys(codeName)
		driver.findElement(By.id("Description")).sendKeys("Reason Code for test purposes")
		driver.findElement(By.id("SaveButton")).click()
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultsDiv"), REASON_CODE_ADDED));
		println("Reason Code " + codeName + " was created.");
	}else{
	    println("Reason Code " + codeName + " already exists.");
	}
}

def deleteRCode(codeName){
	rcpath = "//select/option[contains(text(),'"+codeName+"')]"
	Boolean isPresent = driver.findElements(By.xpath(rcpath)).size() > 0
	if(isPresent){
		driver.findElement(By.xpath(rcpath)).click()
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("DeleteButton")));
		element.click();
		alert = driver.switchTo().alert()
		alert.accept()
		try{
			wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultsDiv"), REASON_CODE_DELETED));
			println("Reason Code " + codeName + " was deleted.");
		}
		catch (TimeoutException e){
			if (driver.getPageSource().contains(this.REASON_CODE_CANT_BE_DELETED)) {
				println("Reason Code " + codeName + " cannot be deleted.");
			}
		}//end catch block
	}//end if block
	else{
	    println("Reason Code " + codeName + " does not exists.");
	}
}

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	//TPAMaddress = "10.30.44.206"
	TPAMaddress = args[0]
	action = args[1]
	codeName = args[2]
	manRCaddress = "https://" + TPAMaddress + "/admin/ManageReasonCodes.asp"
	libr = new libCommon()
	libr.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	driver.get(manRCaddress)
	WebElement newRCbutton = (new WebDriverWait(driver, 10))
	.until(ExpectedConditions.presenceOfElementLocated(By.id("NewButton")));
	
	if(action == "create"){
		createRCode(codeName)
	} else if (action == "delete"){
		deleteRCode(codeName)
	}else{
	    println("Action " + action + " is unknown. Possible values for action are: create or delete.");
	}
}
catch (e){
println("Catched: something went wrong: " + e);
}
driver.quit()

println("Script finished.");