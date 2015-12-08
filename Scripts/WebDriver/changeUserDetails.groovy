/*
 * Script fills TPAM user properties. Properties and it values should be specifies as 
 * comma delimited arrays. Example of usage:
 * 
 * groovy -cp <selenium-server.jar> changeUserDetails.groovy <TPAMadress> <userName> ...
 *     <password> prop1,prop2,prop3 value1,value2,value3
 *     
 * Supported properties are defined in mapFields map.
 */
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.WebElement

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def specifiedProperties = args[3].split(',')
def specifiedValues =args[4].split(',')

def interfaceName = "/tpam/"
def userInfoPage = "https://" + TPAMaddress + interfaceName + "MyInfo.asp"
//Map for user properties
def mapFields = [userID:"sUserID",lastname:"sLastName",firstname:"sFirstName",phone:"sPhone",mobile:"sPager",email:"sEmail",description:"sComment"]

def setPropertyValue(value,field){
	
}

try{
	libr = new libCommon()
	libr.login(driver, TPAMaddress,interfaceName , userName, userPwd)
	driver.get(userInfoPage)
	//Set user properties
	if(specifiedProperties.size() == specifiedValues.size()){
		specified=[specifiedProperties,specifiedValues].transpose().collectEntries { it }
		specifiedProperties.each {
			if(mapFields.containsKey(it)){
				WebElement propfield = driver.findElement(By.id(mapFields[it]))
				try{
				    propfield.clear();
				    propfield.sendKeys(specified[it]);
				}
				catch(InvalidElementStateException iee)
				{
				    println("Property ${it} can't be changed.")
				}
			}else{
				println("The property ${it} is unknown. Known properties are: " + mapFields.keySet())
			}
		}
	}else{
		println('Number of specified properties do not correspond to number of specified values')
		System.exit(0)
	}
	driver.findElement(By.id("save")).click()
	//Wait for result
	msgLastName = driver.findElement(By.id(mapFields['lastname'])).getAttribute('value')
	msgFirstName = driver.findElement(By.id(mapFields['firstname'])).getAttribute('value')
	message = "Your user details for user ${msgLastName}, ${msgFirstName} were successfully saved."
	//println(msgLastName)
	//println(message)
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("feedbackDiv"), message));
	
	println("Successfully");
}
catch(e) {
    println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
