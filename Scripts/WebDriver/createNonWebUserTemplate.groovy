/*
 * This script creates an CLI- or API- user template.
 * It used in PropagateOptionsFromTemplate.UserPropagation tests.
 */
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit
import java.util.List

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def templateName = args[3]
def userInterface = args[4] //Api or CLI
def userType = args.length >= 6 ? args[5] : 'Basic';
def restrictedHosts = args.length == 7 ? args[6] : '';

 //New user template
TPAMaddr = "https://$TPAMaddress/tpam/UserFilter.asp?StartPage=NewUserTemplate"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def successResult = 'User added successfully'

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	driver.get(TPAMaddr)
    //Set template name
	driver.findElement(By.id('UserName')).sendKeys(templateName);
	driver.findElement(By.id('LastName')).sendKeys('Smith');
	driver.findElement(By.id('FirstName')).sendKeys('John');
	//Select user type
	new Select(driver.findElement(By.id("UType"))).selectByVisibleText(userType)
	//Select user interface
	//Uncheck Allow WEB Access
	cbxAllowWeb=driver.findElement(By.id('cb_WebAccess'))
	if(cbxAllowWeb.isSelected()){
		cbxAllowWeb.click();
	}
	//go to Key Based tab
	driver.findElement(By.id('KeyBased')).click();
	switch (userInterface){
		case 'CLI':
		    driver.findElement(By.id('cb_CliAccess')).click();
			break;
		case 'API':
		    driver.findElement(By.id('cb_ApiAccess')).click();
		    break;
		default:
		    println('Wrong User Type was specified. CLI user will be creatred.')
			driver.findElement(By.id('cb_CliAccess')).click();
	}
	//Set Restricted hosts
	if (restrictedHosts != ''){
	    driver.findElement(By.id('RestrictedIP')).sendKeys(restrictedHosts);
	}
		//Save template
	driver.findElement(By.id('SubmitChanges')).click();
	libComn.waitForResult(successResult, driver)
}catch (TimeoutException toutex){
	//If there is a problem to create new user
	useradderror = "Timed out after 10 seconds waiting for text ('$successResult') to be present";
	if(toutex.message.contains(useradderror)){
		user_add_result = driver.findElement(By.id("ResultsDiv")).getText();
		println("Can't add user due to: $user_add_result")
	}else{
		libComn.takeScreenshot(driver, "CreateUserTemplate")
		println("Catched: trouble to find element: " + toutex);
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}