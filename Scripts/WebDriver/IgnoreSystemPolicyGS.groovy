import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

import java.sql.Driver;
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
/*
Common login scenario from libCommon now is used.
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
def checkSetting(eid){
	//println("check method started")
	rbYesId = "R"+eid+"_1"
	rbNoId = "R"+eid+"_2"
	rb_yes=driver.findElement(By.id(rbYesId))
	rb_no=driver.findElement(By.id(rbNoId))
	if (rb_yes.selected){
		println("GS is ON");
		turnOff(eid)
	}
	if (rb_no.selected){
		println("GS is OFF");
	}
}

def turnOn(eid){
	rbYesId = "R"+eid+"_1"
	rb_yes=driver.findElement(By.id(rbYesId))
	if (!(rb_yes.selected)){
		rb_yes.click()
		driver.findElement(By.id("SubmitChanges")).click()
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultsDiv"), "Global Settings changes saved successfully"));
		println("GS was changed to ON");	
	}
}

def turnOff(eid){
	rbNoId = "R"+eid+"_2"
	rb_no=driver.findElement(By.id(rbNoId))
	if (!(rb_no.selected)){
		rb_no.click()
		driver.findElement(By.id("SubmitChanges")).click()
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultsDiv"), "Global Settings changes saved successfully"));
		println("GS was changed to OFF");
	}
}

try {
	//TPAMaddress = "10.30.44.206"
	TPAMaddress = args[0]
	action = args[1]
	eid = "105"		
	GSaddress = "https://" + TPAMaddress + "/admin/GlobalSettings.asp"
	libr = new libCommon()
	libr.login(driver, TPAMaddress, "/admin/", "Parmaster", "Master4PAR")
	driver.get(GSaddress)
	WebElement ignorePolicyElement = (new WebDriverWait(driver, 10))
	.until(ExpectedConditions.presenceOfElementLocated(By.id(eid)));
	
    if(action == "check"){
		checkSetting(eid)	
	} else if (action == "turnOn"){
	    turnOn(eid)
	}else if (action == "turnOff"){
	    turnOff(eid)
	}else{
	println("Action " + action + " is unknown. Possible values for action are: check, turnOn or turnOff.");
	}
}
catch (e){
println("Catched: something went wrong: " + e);
}
driver.quit()

println("Finished.");
