import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.concurrent.TimeUnit
import org.apache.commons.io.FileUtils
import java.io.File
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait

def AddCliBasicUser (driver, TPAM, CLIUserName) {
	TPAMaddr = "https://" + TPAM + "/tpam/UserFilter.asp?StartPage=NewUser" 
	driver.get(TPAMaddr)
	driver.findElement(By.id("UserName")).sendKeys("$CLIUserName")
	driver.findElement(By.id("LastName")).sendKeys("lname")
	driver.findElement(By.id("FirstName")).sendKeys("fname")
	driver.findElement(By.id("cb_WebAccess")).click()
	driver.findElement(By.id("KeyBased")).click()
	driver.findElement(By.id("cb_CliAccess")).click()
	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForResult("successfully", driver, 60)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	driver.findElement(By.id("GetCliKey")).click()
	Thread.sleep(5000)
}

def DeleteCLIUser (driver, TPAM, CLIUserName) {
	TPAMaddr = "https://" + TPAM + "/tpam/UserFilter.asp" 
	driver.get(TPAMaddr)
	driver.findElement(By.id("UsrNm")).sendKeys("$CLIUserName")
	driver.findElement(By.id("Listing")).click()
	libCom.waitForTextPresent(driver, "$CLIUserName", 30)
	driver.findElement(By.xpath("//td[contains(text(),'$CLIUserName')]")).click()
	driver.findElement(By.id("Delete")).click()
	Thread.sleep(2000)
	alert = driver.switchTo().alert()
	alert.accept()
	libCom.waitForResult("successfully", driver, 30)
	println(driver.findElement(By.id("ResultsDiv")).getText())

}	

def AddTpamCLIIDUser (driver, TPAM2, TPAM1, TpamCLIIDUserName, TpamNameForCli, CLIUserName, PathToKey) {
	TPAMaddr = "https://" + TPAM2 + "/tpam/ParCliIdFilter.asp?StartPage=NewCliID" 
	driver.get(TPAMaddr)
	driver.findElement(By.id("CLIUserName")).sendKeys("$CLIUserName")
	driver.findElement(By.id("PARName")).sendKeys("$TpamNameForCli")
	driver.findElement(By.id("PARAddress")).sendKeys("$TPAM1")
	File file = new File(PathToKey)
	String KeyFileContent = FileUtils.readFileToString(new File(PathToKey))
	driver.findElement(By.id("DSSKey")).sendKeys(KeyFileContent)
	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForResult("successfully", driver, 60)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	if (!driver.findElement(By.id("SubmitChanges")).isEnabled()) {
		println("SaveChanges button is disabled!")
	}
}

def SetPermissions (driver, TPAM, CLIUserName, AccountName) {
	TPAMaddr = "https://" + TPAM + "/tpam/UserFilter.asp"
	driver.get(TPAMaddr)
	driver.findElement(By.id("UsrNm")).sendKeys("$CLIUserName")
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$CLIUserName')]")).click()
	driver.findElement(By.id("Permissions")).click()
	driver.findElement(By.id("apaFilterAccountName")).sendKeys("$AccountName")
	driver.findElement(By.id("Results")).click()
	driver.findElement(By.xpath("//td[contains(text(),'Not Assigned')]")).click()
	new Select(driver.findElement(By.id("AccessPolicyID"))).selectByVisibleText("ISA")
	driver.findElement(By.id("divBtnSaveRow")).click()
	driver.findElement(By.id("SubmitChanges")).click()
	libCom.waitForResult("successful", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	Thread.sleep(5000)
}

def DeleteTpamCLIIDUser (driver, TPAM, CLIUserName) {
	TPAMaddr = "https://" + TPAM + "/tpam/ParCliIdFilter.asp" 
	driver.get(TPAMaddr)
	driver.findElement(By.id("UsrNm")).sendKeys("$CLIUserName")
	driver.findElement(By.id("Listing")).click()
	libCom.waitForTextPresent(driver, "$CLIUserName", 30)
	driver.findElement(By.xpath("//td[contains(text(),'$CLIUserName')]")).click()
	driver.findElement(By.id("Delete")).click()
	Thread.sleep(2000)
	alert = driver.switchTo().alert()
	alert.accept()
	libCom.waitForResult("successfully", driver, 60)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	File file = new File(PathToKey)
	if(file.delete()){
   			System.out.println(file.getName() + " is deleted!")
    }else{
    		System.out.println("Delete operation is failed.")
    }
}	

try {

	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)

	action = args[0]
	userName = args[1]
	userPwd = args[2]
	PathToKey = args[3]
	TpamCLIIDUserName = args[4]
	CLIUserName = args[5]
	TPAMHost = args[6]
	String[] TPAMaddresses = args[7].split(",")
	systemName = args[8]
	
//	String[] TPAMaddresses = args[7].split(";")
	if (TPAMaddresses[0] == TPAMHost) {
                TPAMaddress1 = TPAMaddresses[1]
                TPAMaddress2 = TPAMaddresses[0]
	} else {
                TPAMaddress1 = TPAMaddresses[0]
                TPAMaddress2 = TPAMaddresses[1]
	}
	
	TpamNameForCli = "TpamNameForCli"
	
	libCom = new libCommon()
	libPsm = new libPSM()	
	libPpm = new libPPM()
	
	PathToKey = PathToKey + "id_dsa"
	PathToKey = PathToKey.replace("\\", "\\\\")
	File file = new File(PathToKey)
	
	if(action == "createenv"){

		SystemContentPath = args[9]
		accountName = args[10]
		Password = args[11]
		
// Add CliUser, System, Account and set permissions on TPAM1
		
	if(file.exists() && !file.isDirectory()) {
		file.delete()
	}
	
	libCom.login(driver, TPAMaddress1, "/tpam/", userName, userPwd)
	AddCliBasicUser (driver, TPAMaddress1, CLIUserName)
	libPpm.addSystem (driver, TPAMaddress1, SystemContentPath, systemName) 
	SystemContent = libPpm.GetContent(SystemContentPath)
	libPpm.addAccount(driver, TPAMaddress1, systemName, accountName, Password, SystemContent)
	SetPermissions (driver, TPAMaddress1, CLIUserName, accountName)
	
//Add TPAMCliID on TPAM2
	
	libCom.login(driver, TPAMaddress2, "/tpam/", userName, userPwd)
	AddTpamCLIIDUser (driver, TPAMaddress2, TPAMaddress1, TpamCLIIDUserName, TpamNameForCli, CLIUserName, PathToKey)
	 
	} else if (action == "deleteenv"){
		libCom.login(driver, TPAMaddress1, "/tpam/", userName, userPwd)
		libPpm.deleteSystem (driver, TPAMaddress1, systemName)
		DeleteCLIUser(driver, TPAMaddress1, CLIUserName)
		libCom.login(driver, TPAMaddress2, "/tpam/", userName, userPwd)
		DeleteTpamCLIIDUser (driver, TPAMaddress2, CLIUserName)
	} 
}

catch (e) {
	libCom.takeScreenshot(driver, "check_TPAM_CLI_ID")
    println("Catched: something went wrong: " + e)
}

driver.quit()

println("Finished.")
