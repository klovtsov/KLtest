import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.interactions.Actions
import java.util.*
					 

def addPasswordRequest(driver, TPAM, SystemName, AccountName) {

		driver.findElement(By.linkText("Add Password Request")).click()

		Thread.sleep(5000)

		driver.findElement(By.id("SystemNm")).sendKeys(SystemName)

		driver.findElement(By.id("AccountNm")).sendKeys(AccountName)

		driver.findElement(By.id("Accounts")).click()

		Thread.sleep(2000)

		driver.findElement(By.className("ResetCheckbox")).click()
                                            
		Thread.sleep(1000)

		driver.findElement(By.id("Details")).click()

		Thread.sleep(1000)

		driver.findElement(By.id("RequestReason")).sendKeys("reason")

		Thread.sleep(1000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(2000)

		if (driver.getPageSource().contains("has been submitted")) {
			println("Password for " + AccountName + " requested.")
			}
		println(driver.findElement(By.id("ResultsDiv")).getText())



}



def cancelPasswordRequest(driver, TPAM, SystemName, AccountName) {

		Thread.sleep(5000)

		driver.findElement(By.id("CurrentRequests")).click()

		Thread.sleep(7000)

		driver.findElement(By.id("SystemNm")).sendKeys(SystemName)

		driver.findElement(By.id("AccountNm")).sendKeys(AccountName)

		Thread.sleep(1000)

		driver.findElement(By.id("Listing")).click()

		Thread.sleep(2000)

		driver.findElement(By.xpath("//td[contains(text(),'$AccountName')]")).click()

		driver.findElement(By.id("Details")).click()

		Thread.sleep(4000)

		driver.findElement(By.id("ResponseComment")).sendKeys("cancel")

		Thread.sleep(1000)

     		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(5000)

		if (driver.getPageSource().contains("was submitted successfully")) {
			println("Request for " + AccountName + " canceled.");
		}
		println(driver.findElement(By.id("ResultsDiv")).getText())


}



def addSystem (driver, TPAM, SystemContentPath, SystemName) {

	libPpm = new libPPM()
	def SystemContent = libPpm.GetContent(SystemContentPath)
	def Platform = SystemContent.SystemPlatform
	def NetworkAddress = SystemContent.SystemAddress
	
	println("Add System")
	TPAMaddr = "https://" + TPAM + "/tpam/ManageSystems.asp" 
	driver.get(TPAMaddr)
	Thread.sleep(2000)
	driver.findElement(By.id("bt_NewSystem")).click()
	driver.findElement(By.id("SystemName")).sendKeys(SystemName)
	driver.findElement(By.id("NetworkAddress")).sendKeys(NetworkAddress)
	new Select(driver.findElement(By.id("PlatformID"))).selectByVisibleText(Platform)

	// Setting a password rule for UnixWare system
	if (SystemContent.UnixWareRuleName) {

		//To Do: the string below doesn't work, since $prefix from FrontPage is not handled properly. 
		//new Select(driver.findElement(By.id("PwdRuleID"))).selectByVisibleText(SystemContent.UnixWareRuleName)

		Select select = new Select(driver.findElement(By.id("PwdRuleID")))
		List<WebElement> list = select.getOptions();
        for (WebElement option : list) {
            String fullText = option.getText();
            if (fullText.contains("UnixWare")) {
                select.selectByVisibleText(fullText);
            }
        }
		
	}	

	SetConnectionTab (driver, SystemContentPath)
	driver.findElement(By.id("bt_Save")).click()
	libCmn = new libCommon()	
	libCmn.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	
	//Set FunctAcctDescription for LDAP systems
	if (SystemContent.FunctAcctDescription) {
		ChooseAccount(driver, TPAM, SystemName, SystemContent.FunctAcct)
		driver.findElement(By.id("Details")).click()
		if (SystemContent.SystemPlatform.contains("LDAP")) {
			driver.findElement(By.id("AcctDesc")).sendKeys(SystemContent.FunctAcctDescription)
		} else {
			FunctAcctDesc = "cn="+SystemContent.FunctAcct+","+SystemContent.Context
			driver.findElement(By.id("AcctDesc")).sendKeys(FunctAcctDesc)
		}
		driver.findElement(By.id("SubmitChangesButton")).click()
	}

}

def SetConnectionTab (driver, SystemContentPath) {

	libPpm = new libPPM()
	def SystemContent = libPpm.GetContent(SystemContentPath)
	def FunctAcct = SystemContent.FunctAcct
	def FunctAcctPwd = SystemContent.FunctAcctPwd
	def Platform = SystemContent.SystemPlatform

	println("Set Connection Tab")
	driver.findElement(By.id("Connection")).click()
	if (SystemContent.SystemTimeout) {
		def SystemTimeout = SystemContent.SystemTimeout
		Field = driver.findElement(By.id("Timeout"))
		Field.clear()
		Field.sendKeys(SystemTimeout)
	} //else {
		//Field = driver.findElement(By.id("Timeout"))
		//Field.clear()
		//Field.sendKeys("40")
	//}
	if (SystemContent.SID) {
		def SID = SystemContent.SID
		Field = driver.findElement(By.id("SIDSN"))
		Field.clear()
		Field.sendKeys(SID)
	}
	
	if (SystemContent.ParFunctAcct) {
		Field = driver.findElement(By.id("PARFuncAcct"))
		Field.clear()
		Field.sendKeys(SystemContent.ParFunctAcct)
	} else { 	
		Field = driver.findElement(By.id("PARFuncAcct"))
		Field.clear()
		Field.sendKeys(FunctAcct)
	}
	if (driver.findElement(By.id("pword")).isDisplayed()) {
		driver.findElement(By.id("pword")).sendKeys(FunctAcctPwd)
		driver.findElement(By.id("ConfPword")).sendKeys(FunctAcctPwd)
	}
	println("debug FunctAcctPwd $FunctAcctPwd")
	
	if (SystemContent.DomainName) {
		driver.findElement(By.id("DomainName")).sendKeys(SystemContent.DomainName)
	}
	if (SystemContent.NETBIOSDomainName) {
		driver.findElement(By.id("NetBiosName")).sendKeys(SystemContent.NETBIOSDomainName)
	}
	
/* 	switch ( Platform )	{
		case "Windows":
		//	Field = driver.findElement(By.id("PARFuncAcct"))
		//	Field.clear()
		//	Field.sendKeys(FunctAcct)
		//	driver.findElement(By.id("pword")).sendKeys(FunctAcctPwd)
		//	driver.findElement(By.id("ConfPword")).sendKeys(FunctAcctPwd)
			break
		case "Windows Active Dir ":
			def DomainName = SystemContent.DomainName
			def NETBIOSDomainName  = SystemContent.NETBIOSDomainName
			driver.findElement(By.id("DomainName")).sendKeys(DomainName)
			driver.findElement(By.id("NetBiosName")).sendKeys(NETBIOSDomainName)
			break
	} */
}


def chooseSystem (driver, TPAM, sName) {
	TPAMaddr = "https://" + TPAM + "/tpam/ManageSystems.asp"
	driver.get(TPAMaddr)
	WebElement systemNameField = (new WebDriverWait(driver, 10))
	    .until(ExpectedConditions.presenceOfElementLocated(By.id("SystemNm")));
	systemNameField.sendKeys(sName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td[contains(text(),'$sName')]]")).click();
	WebElement btnDel = new WebDriverWait(driver, 10)
	   .until(ExpectedConditions.elementToBeClickable(By.id("bt_Del")));
}

def deleteSystem (driver, TPAM, SystemName) {
	CLEAR_HOST_ENTRY_TABLE_HEADER = "The results from the clearing"
	chooseSystem(driver, TPAM, SystemName)
	//Remove host entry
	driver.findElement(By.id("bt_Clear")).click()
	WebDriverWait wait = (new WebDriverWait(driver, 10))
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultDetailDiv"), CLEAR_HOST_ENTRY_TABLE_HEADER));
    //delete system
	driver.findElement(By.id("bt_Del")).click()
	alert = driver.switchTo().alert()
	alert.accept()
	libCmn = new libCommon()
	libCmn.waitForResult("${SystemName} has been successfully deleted", driver)
	println("System deleted.")
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

def hardDeleteSystem (driver, TPAM, SystemName) {
	libCmn = new libCommon()
	TPAMaddr = "https://" + TPAM + "/tpam/ManageDeletedSystems.asp"
	driver.get(TPAMaddr)

	driver.findElement(By.id("SystemNm")).sendKeys(SystemName)
	driver.findElement(By.id("Listing")).click()
	
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.id('ListingTab'), 'Displaying'));
	
	List<WebElement> systemsToDelete = driver.findElements(By.xpath("//table[@id='SystemsTable']/*/tr"))
	if(systemsToDelete.size() == 1){
		//Delete single entry  
		systemsToDelete[0].click()
		driver.findElement(By.id("HardDeleteButton")).click()
		alert = driver.switchTo().alert()
		alert.accept()
		
		libCmn.waitForTextPresent(driver,'has been successfully deleted',5)
		println("System hard-deleted.")
	}//end of single entry deletion
	else if (systemsToDelete.size() > 1){
		//Several entries are present - hard-delete all
		driver.findElement(By.id('btnHardDeleteAll')).click()
		libCmn.waitForElementWithId(driver,'btnYes').click()
		libCmn.waitForTextPresent(driver,'has been successfully deleted',5)
		println("System hard-deleted.")
	}else{
	    println("Something went wrong: there is not any deleted system with name $SystemName")
	}
	
	/*
	driver.findElement(By.xpath("//td[contains(text(),'$SystemName')]")).click()
	driver.findElement(By.id("HardDeleteButton")).click()
	
	alert = driver.switchTo().alert()
	alert.accept()
	
	libCmn = new libCommon()
	libCmn.waitForResult("${SystemName} has been successfully deleted", driver)
	println("System hard-deleted.")
	println(driver.findElement(By.id("ResultsDiv")).getText())
	*/
}


def addAccount (driver, TPAM, SystemName, AccountName, Password, SystemContent) {
	TPAMaddr = "https://" + TPAM + "/tpam/ManageAccounts.asp?StartPage=NewAccount"
	driver.get(TPAMaddr)
	driver.findElement(By.id("SystemNm")).click() // to cope with pop-up menu
	driver.findElement(By.id("SystemNm")).sendKeys(SystemName)
	driver.findElement(By.id("System")).click()
	println("Click on System tab")
	driver.findElement(By.xpath("//tr[td[contains(text(),'$SystemName')]]")).click()
	println("SelectSystem")

	// A kind of a hack to cope with pop-up menu
	Actions action = new Actions(driver)
    action.moveToElement(driver.findElement(By.id("PSMDetails"))).click().build().perform();
	//driver.findElement(By.id("PSMDetails")).click() 
	
	driver.findElement(By.id("Details")).click()
	println("Click on Details tab")
	driver.findElement(By.id("AccountName")).sendKeys(AccountName)
	println("sendKeys to AccountName")
	driver.findElement(By.id("sPswd")).sendKeys(Password)
	driver.findElement(By.id("sConfPswd")).sendKeys(Password)

	//set description for ldap systems
	if (SystemContent.FunctAcctDescription) {
		if (SystemContent.SystemPlatform.contains("LDAP")) {
			ManagedAccountDescription = "uid="+AccountName+","+SystemContent.Context
		} else {
			ManagedAccountDescription = "cn="+AccountName+","+SystemContent.Context
		}
		println(ManagedAccountDescription)
		driver.findElement(By.id("AcctDesc")).sendKeys(ManagedAccountDescription)
	}	
	
	driver.findElement(By.id("SubmitChangesButton")).click()
	libCmn = new libCommon()
	libCmn.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

def TestSystem(driver, TPAM, SystemName, TestResult) {
	Header = "from the test of system"
	println("Test System for $SystemName")  
	chooseSystem(driver,TPAM,SystemName)
	driver.findElement(By.id("bt_Test")).click()
	libCmn = new libCommon()
	libCmn.waitForTextPresent(driver, Header, 90)
	ResultfromTPAM = libCmn.TableViewer(driver, 'testTable"')
	TestResultValues = TestResult.split(';;')
	TestResultValues.each() {if (ResultfromTPAM.contains(it)) 
		{println "Expected result($it)."}
	}
}	

def ChooseAccount(driver, TPAM, SystemName, AccountName) {
	TPAMaddr = "https://" + TPAM + "/tpam/ManageAccounts.asp"
	driver.get(TPAMaddr)
	libCmn = new libCommon()
	libCmn.waitForElementWithId(driver,"SystemNm")
	driver.findElement(By.id("SystemNm")).sendKeys(SystemName)
	driver.findElement(By.id("AccountNm")).sendKeys(AccountName)
	driver.findElement(By.id("Listing")).click()
//	driver.findElement(By.xpath("//tr[td[contains(text(),'$AccountName')]]")).click()
	driver.findElement(By.xpath("//table[@id='AccountListing']//td[text()='$AccountName']")).click()
	libCmn.waitForElementWithId(driver,"CheckPassButton")
}

def ChooseFile(driver, TPAM, SystemName, FileName) {
	TPAMaddr = "https://" + TPAM + "/tpam/FileFilter.asp"
	driver.get(TPAMaddr)
	libCmn = new libCommon()
	libCmn.waitForElementWithId(driver,"SystemNm")
	driver.findElement(By.id("SystemNm")).sendKeys(SystemName)
	driver.findElement(By.id("FileNm")).sendKeys(FileName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//table[@id='fileTable']//td[text()='$FileName']")).click()
	WebElement btnDel = new WebDriverWait(driver, 10)
	   .until(ExpectedConditions.elementToBeClickable(By.id("Delete")));
}



def SetPassword(driver, TPAM, SystemName, Account, Password) {
	println("Set password for $Account")
	ChooseAccount(driver, TPAM, SystemName, Account)
	driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("sPswd")).sendKeys(Password)
	driver.findElement(By.id("sConfPswd")).sendKeys(Password)
	driver.findElement(By.id("SubmitChangesButton")).click()
	libCmn = new libCommon()
	libCmn.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	println("AccountName=$Account Password=$Password")	
}

def CheckResetPassword(driver, TPAM, SystemName, Account, CheckReset, TestResult) {
	Header = "Results from the"
	println("$CheckReset Password for $Account on $SystemName")
	ChooseAccount(driver, TPAM, SystemName, Account)
	if (CheckReset == "Check") {
		driver.findElement(By.id("CheckPassButton")).click()
		resultTable = 'testTable'
	} else if (CheckReset == "Reset") {
		driver.findElement(By.id("ResetPassButton")).click()
		resultTable = 'testTable"'
	}
	libCmn = new libCommon()
	libCmn.waitForTextPresent(driver, Header, 90)
	ResultfromTPAM = libCmn.TableViewer(driver, resultTable)
	TestResultValues = TestResult.split(';;')
	TestResultValues.each() {if (ResultfromTPAM.contains(it))
		{println "Expected result($it)."}
	}
}

def GetContent(FilePath) {
	String fileContent = new File(FilePath).text
	s1 = fileContent.replaceAll(/(?m)^[ \t]*\r?\n/, "").replaceAll("!define ", "").replaceAll(" \\{", "=").replaceAll(/(?m)(})$/,"").replaceAll(/(?m)^[\!|\#|\*\|].*\r?\n/, "").replaceAll("\\!include.*", "")

	Map<String, String> myMap = new HashMap<String, String>()
	String[] pairs = s1.split("\\r?\\n")
	for (int i=0;i<pairs.length;i++) {
		String pair = pairs[i]
		String[] keyValue = pair.split("=", 2)
		myMap.put(keyValue[0], keyValue[1])
	}
//	println(myMap)
	return myMap
}

def ScheduleResetPassword (driver, TPAM, SystemName, AccountName) {
	println("Schedule Reset Password for $AccountName on $SystemName")
	ChooseAccount(driver, TPAM, SystemName, AccountName)
	driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("Management")).click()
	
	TimeZone utc = TimeZone.getTimeZone("UTC");
	
	def nowCal = Calendar.getInstance(utc)
	year = nowCal.get(Calendar.YEAR)
	month = nowCal.get(Calendar.MONTH) + 1
	day = nowCal.get(Calendar.DATE)
	hour = nowCal.get(Calendar.HOUR_OF_DAY)
	min = nowCal.get(Calendar.MINUTE) + 3

	if (min > 59) {
		min = min - 60
		hour = hour + 1
	}
	
	StringBuffer timestamp = new StringBuffer()
	if (min > 10) {
        timestamp.append(min)  
    } else { 
        timestamp.append("0" + min) 
	}
	minute = timestamp
	//debug
	println("year $year, month $month, day $day, hour $hour, min $minute")
	
	driver.findElement(By.id("Hr")).clear()
	driver.findElement(By.id("Hr")).sendKeys("$hour")
	driver.findElement(By.id("Min")).clear()
	driver.findElement(By.id("Min")).sendKeys("$minute")
	driver.findElement(By.id("Mon")).clear()
	driver.findElement(By.id("Mon")).sendKeys("$month")
	driver.findElement(By.id("DayNumber")).clear()
	driver.findElement(By.id("DayNumber")).sendKeys("$day")
	driver.findElement(By.id("YearNumber")).clear()
	driver.findElement(By.id("YearNumber")).sendKeys("$year")
	driver.findElement(By.id("SubmitChangesButton")).click()
	libCmn = new libCommon()
	libCmn.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	Thread.sleep(600000)
	CheckStatus (driver, TPAM, SystemName, AccountName)
}

def CheckStatus (driver, TPAM, SystemName, AccountName) {
	ChooseAccount(driver, TPAM, SystemName, AccountName)
	driver.findElement(By.id("Logs")).click()
	driver.findElement(By.id("rb_LogsDtAll")).click()
	driver.findElement(By.id("ChangeLog")).click()
	libCmn = new libCommon()
	tableName = "changeLogTable"
	result = libCmn.TableViewer(driver, tableName)
	out = result.split("   ")
	println(out[0])
}
	
def ChooseUser(driver, TPAM, UserName) {
	TPAMaddr = "https://" + TPAM + "/tpam/UserFilter.asp"
	driver.get(TPAMaddr)
	libCmn = new libCommon()
	libCmn.waitForElementWithId(driver,"UsrNm")
	driver.findElement(By.id("UsrNm")).sendKeys(UserName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td[contains(text(),'$UserName')]]")).click()
	libCmn.waitForElementWithId(driver,"GotoGroups")
}

def SetPermissions (driver, TPAM, UserName, AccountName, AccessPolicy) {
	ChooseUser(driver, TPAM, UserName)
	driver.findElement(By.id("Permissions")).click()
	driver.findElement(By.id("apaFilterAccountName")).sendKeys("$AccountName")
	driver.findElement(By.id("Results")).click()
	driver.findElement(By.xpath("//td[contains(text(),'Not Assigned')]")).click()
	new Select(driver.findElement(By.id("AccessPolicyID"))).selectByVisibleText(AccessPolicy)
	driver.findElement(By.id("divBtnSaveRow")).click()
	driver.findElement(By.id("SubmitChanges")).click()
	libCom = new libCommon()
	libCom.waitForResult("successful", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}
	
def ApprovePasswordRequest(driver, TPAM, SystemName, AccountName) {
	TPAMaddr = "https://" + TPAM + "/tpam/PwdRequestApproverFilter.asp"
	driver.get(TPAMaddr)
	driver.findElement(By.id("SystemNm")).sendKeys("$SystemName")
	driver.findElement(By.id("AccountNm")).sendKeys("$AccountName")
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td[contains(text(),'$AccountName')]]")).click()
	driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("ResponseComment")).sendKeys("Approve")	
	driver.findElement(By.id("ApproveReq")).click()	
	libCmn = new libCommon()	
	libCmn.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

def GetLogin(driver, TPAM, SystemName, AccountName) {
	TPAMaddr = "https://" + TPAM + "/tpam/PwdRequestFilter.asp"
	driver.get(TPAMaddr)
	driver.findElement(By.id("SystemNm")).sendKeys("$SystemName")
	driver.findElement(By.id("AccountNm")).sendKeys("$AccountName")
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//tr[td[contains(text(),'$AccountName')]]")).click()
	driver.findElement(By.id("Password")).click()
	def PasswordXPath = driver.findElement(By.xpath("//div[@class='displayPassword']")).getText()
	String[] PasswordParts = PasswordXPath.split("\\r?\\n")
	return PasswordParts[1].replace("]", "").replace("[", "").trim()
}

	
	
	
	
