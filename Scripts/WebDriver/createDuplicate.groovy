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
def objType = args[3]
def objName = args[4]
def newName = args[5]

def mWebElements = ['system':['ManageSystems','SystemNm','SystemName',"System changes for $newName saved successfully"],
	                'user':['UserFilter','UsrNm','UserName','User added successfully'],
					'collection':['CollectionFilter','CollectionNm','CollectionName','The new collection was created successfully'],
					'group':['GroupFilter','GroupNm','GroupName','The group was created successfully'],
					'account':['ManageAccounts','AccountNm','AccountName',"Changes saved successfully for $newName"],
					'policy':['ManageAccessPolicies','filtPolicyName','PolicyName','Access Policy saved successfully']]

//Duplicate button Id
//Save changes button ID
def btnDuplId='Duplicate'
def btnSaveId = 'SubmitChanges'

if(objType == 'system'){
	btnDuplId='bt_Duplicate'
	btnSaveId='bt_Save'
}
if (['account','policy'].contains(objType)){
    btnDuplId = 'DuplicateButton'
	btnSaveId = 'SubmitChangesButton'
}

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
    //open corresponding page
	TPAMaddr = "https://$TPAMaddress/tpam/${mWebElements[objType][0]}.asp"
	driver.get(TPAMaddr)
	//Set filter value
	objNameField =libComn.waitForElementWithId(driver,mWebElements[objType][1]);
    objNameField.sendKeys(objName)
	//Go to Listing tab
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td[contains(text(),'$objName')]]")).click();
	//Duplicate
	WebElement btnDupl = new WebDriverWait(driver, 10)
	   .until(ExpectedConditions.elementToBeClickable(By.id(btnDuplId)));
	Thread.sleep(500)
	btnDupl.click();
	//Set duplicate values
	newNameField = libComn.waitForElementWithId(driver,mWebElements[objType][2]);
	newNameField.clear();
	newNameField.sendKeys(newName);
	//For user it is necessary to specify First and Last Name
	if(objType == 'user'){
		driver.findElement(By.id("LastName")).sendKeys('Smith');
		driver.findElement(By.id("FirstName")).sendKeys('John');
	}
	//Mark policy as active.
	if(objType == 'policy'){
		
		driver.findElement(By.id('cbxActiveFl')).click();
	}
	//Save and wait results
	driver.findElement(By.id(btnSaveId)).click()
	libComn.waitForResult(mWebElements[objType][3], driver)
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}