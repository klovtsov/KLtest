import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.firefox.internal.ProfilesIni
import org.openqa.selenium.interactions.Actions

SystemName=''
NetworkAddr=''
CollectionName=''
AccountName=''
PSM=''
UserName=''
GroupName=''
FileName=''

for (String s: args) {

	
	
	String[] parameter = s.split("=")
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
	if (paramName.equals("TPAMaddress")) {
		TPAMaddress = paramValue
	} else if (paramName.equals("TPAMuserName")) {
		TPAMuserName = paramValue
	} else if (paramName.equals("TPAMuserPwd")) {
		TPAMuserPwd = paramValue
	} else if (paramName.equals("Operation")) {
		Operation = paramValue		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue		
	} else if (paramName.equals("NetworkAddr")) {
		NetworkAddr = paramValue		
	} else if (paramName.equals("CollectionName")) {
		CollectionName = paramValue		
	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue		
	} else if (paramName.equals("PSM")) {
		PSM = paramValue		
	} else if (paramName.equals("UserName")) {
		UserName = paramValue		
	} else if (paramName.equals("GroupName")) {
		GroupName = paramValue		
	} else if (paramName.equals("FileName")) {
		FileName = paramValue		
	}
}
//System.setProperty("webdriver.firefox.profile", "TPAM")
FirefoxProfile profile = new FirefoxProfile();
//ProfilesIni allProfiles = new ProfilesIni();
//FirefoxProfile profile = allProfiles.getProfile("TPAM");
profile.setPreference("browser.download.folderList",2);
profile.setPreference("browser.download.manager.showWhenStarting",false);
File currentDirectory = new File(new File("../").getAbsolutePath());
downdir = currentDirectory.getCanonicalPath() + "\\Ethalons\\ListOperations"
profile.setPreference("browser.download.dir",downdir);
profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/octet-stream,application/csv,text/csv"); 
driver = new FirefoxDriver(profile)
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//def libCom.TableViewer (TableName) {
//	WebElement table = driver.findElement(By.id(TableName))
//	List<WebElement> allRows = table.findElements(By.tagName("tr"));
//	for (WebElement row : allRows) {
//		List<WebElement> cells = row.findElements(By.xpath(".//*[local-name(.)='tr' or local-name(.)='td']"));
//		List<WebElement> cells = row.findElements(By.tagName("td"))
//		for (WebElement cell : cells) {
//		print(cell.getText()+" ")
//	}}}


def Main (driver, TPAMaddress) {

//libCom = new libCommon()

if (Operation == "System") {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/ListSysFilt.asp"
}
if (Operation == "Account") {
	if (PSM == "Yes") {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/EGPListAcctFilt.asp"
	} else {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/ListAcctFilt.asp"
		}
}
if (Operation == "Collection") {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/ListCollectionsFilter.asp"
}
if (Operation == "User") {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/ListUserFilt.asp"
}
if (Operation == "Group") {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/ListGroupsFilter.asp"
}
if (Operation == "File") {
		TPAMaddr = "https://" + TPAMaddress + "/tpam/ListFilesFilt.asp"
}
    driver.get(TPAMaddr)
	 if (Operation == "File" | Operation == "Account" | Operation == "System" | Operation == "Collection" & AccountName == "" & FileName == "" ) {
	 driver.findElement(By.id("SystemNm")).sendKeys("$SystemName*")
	 }
	 if (Operation != "Collection" & Operation != "User" & Operation != "Group" ) {
	 driver.findElement(By.id("NetworkAddr")).sendKeys("$NetworkAddr*")
	 }
	 if (Operation != "User" & Operation != "Group" ) {
	 driver.findElement(By.id("CollectionNm")).sendKeys("$CollectionName*")
	 }
	 if (Operation == "Account" | Operation == "Collection" & AccountName != "" ) {
	 driver.findElement(By.id("AccountNm")).sendKeys("$AccountName*")
	 }
	 if (Operation == "User") {
	 driver.findElement(By.id("GroupNm")).sendKeys("$GroupName*")
	 driver.findElement(By.id("UsrNm")).sendKeys("$UserName*")
	 driver.findElement(By.id("Email")).sendKeys("Email@mail.com")
	 }
	 if (Operation == "Group") {
	 driver.findElement(By.id("GroupNm")).sendKeys("$GroupName*")
	 driver.findElement(By.id("UserNm")).sendKeys("$UserName*")
	 }
	 if (Operation == "File") {
	 driver.findElement(By.id("FileNm")).sendKeys("")
	 }
	 if (Operation == "Collection" & FileName != '') {
	 driver.findElement(By.id("FileNm")).sendKeys("$FileName*")
	 }
	 driver.findElement(By.id("Layout")).click()
	 driver.findElement(By.id("showRowNum")).click()
	 if (Operation == "Account" & PSM != "Yes"){
	 driver.findElement(By.id("showNextChangeDt")).click()
	 }
	 if (Operation != "Collection" & Operation != "Group"){
	 driver.findElement(By.id("rb_SortType0")).click()
	 }
	 driver.findElement(By.id("Listing")).click()
//Listing	 
	println("Listing")
	Thread.sleep(1000)
	
	if (Operation == "Account") {
	TableName = "accountListingReport"
	} else if (Operation == "System") {
	TableName = "systemListingReport"
	} else if (Operation == "Collection") {
	TableName = "CollectionListingReport"
	} else if (Operation == "User") {
	TableName = "UserListingReport"
	} else if (Operation == "Group") {
	TableName = "GroupListingReport"
	} else if (Operation == "File") {
	TableName = "fileListingReport"
	}
	
	result = libCom.TableViewer(driver,TableName)
	println(result)

	if (Operation == "Account") {
	driver.findElement(By.xpath("//td[contains(text(),'$AccountName')]")).click()
	} else if (Operation == "System") {
	driver.findElement(By.xpath("//td[contains(text(),'$SystemName')]")).click()
	} else if (Operation == "Collection") {
	driver.findElement(By.xpath("//td[contains(text(),'$CollectionName')]")).click()
	} else if (Operation == "User") {
	driver.findElement(By.xpath("//td[contains(text(),'$UserName')]")).click()
	} else if (Operation == "Group") {
	driver.findElement(By.xpath("//td[contains(text(),'$GroupName')]")).click()
	} else if (Operation == "File") {
	driver.findElement(By.xpath("//td[contains(text(),'$FileName')]")).click()
	} 
	//Collections
if (PSM != "Yes" ) {
	if (Operation != "Collection" & Operation != "User" & Operation != "Group") {
	driver.findElement(By.id("Collections")).click()
	println("Collections")
	} else if (Operation != "User") {
	driver.findElement(By.id("Members")).click()
	println("Members")
	}
	TableName = "collectionsTable"
	if (Operation == "Account") {
	Actions action = new Actions(driver)	
	action.moveToElement(driver.findElement(By.name("B3"))).click().build().perform();
	}
	if (Operation == "User") {
	driver.findElement(By.id("Groups")).click()
	println("Groups")
	TableName = "GroupsTable"
	}
	if (Operation == "Group") {
	TableName = "GroupsTable"
	}
	result = libCom.TableViewer(driver,TableName)
	println(result)
//Permissions
	driver.findElement(By.id("Permissions")).click()
	println("Permissions")
	Thread.sleep(1000)
	
	TableName = "permissionsTable"
	
	result = libCom.TableViewer(driver,TableName)
	println(result)

}	 
	 //driver.findElement(By.name("B3")).click()
	 driver.findElement(By.xpath("//*[@value='Export to Excel']")).click()
	 Thread.sleep(2000)
	 driver.findElement(By.xpath("//*[@value='Export to CSV']")).click()
	 Thread.sleep(2000)
	 if (Operation == "Collection" | Operation == "Group") {
	 driver.findElement(By.xpath("//*[@value='Export Members to Excel']")).click()
	 Thread.sleep(2000)
	 driver.findElement(By.xpath("//*[@value='Export Members to CSV']")).click()
	 Thread.sleep(2000)
	 }
}
try {
	
	libCom = new libCommon()
	
	libCom.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	
	Main (driver, TPAMaddress)
}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");
