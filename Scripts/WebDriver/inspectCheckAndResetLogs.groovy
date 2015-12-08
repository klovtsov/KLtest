import org.junit.After;
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def systemName = args[3]
def accntName = args[4]
def expectedChangeResult = args[5]  //Possible values: Success or Failed

int RetryInterval = 60

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
df = new SimpleDateFormat('MM/dd/yyyy K:mm:ss a')

def reportSuccess(){
	println("Scheduled Check and Change were as expected.");
}

def reportFailure(descr){
	println("Something went wrong in Scheduled Check and Change.");
	println(descr)
}

def getTestLog(){
	driver.findElement(By.id('TestLog')).click()
	List<WebElement> checkRows = driver.findElements(By.xpath("//table[@id='testLogTable']/*/tr"))
	testEntries=[]
	checkRows.each {row->
		CheckDate = df.parse(row.findElement(By.xpath(".//td[1]")).getText())
		CheckResult = row.findElement(By.xpath(".//td[2]")).getText()
		testEntries +=[date:CheckDate,result:CheckResult]
	}
	testEntries
}

def getChangeLog(){
	driver.findElement(By.id('ChangeLog')).click()
	List<WebElement> sheduledRows = driver.findElements(By.xpath("//table[@id='changeLogTable']/*/tr"))
	changeEntries=[]
	sheduledRows.each {row->
		ChangeDate = df.parse(row.findElement(By.xpath(".//td[1]")).getText())
		Reason = row.findElement(By.xpath(".//td[2]")).getText()
		Result = row.findElement(By.xpath(".//td[3]")).getText()
		Comment = row.findElement(By.xpath(".//td[4]")).getText()
		changeEntries +=[date:ChangeDate,reason:Reason,result:Result,comment:Comment]
	}
	lastEntry = changeEntries[-1]
	if(lastEntry.reason=='New Account'){
		changeEntries.remove(lastEntry)
	}
	changeEntries
}

def printlog(logList){
	logList.each {mapitem->
		line = ''
		mapitem.each{
			line+=it.value.toString()+' '
		}
		println line
	}
}

try {
	libComn = new libCommon()
	libPpm = new libPPM()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	libPpm.ChooseAccount(driver, TPAMaddress, systemName, accntName)
	//go to Logs tab
	driver.findElement(By.id('Logs')).click()
	rbAllDates = libComn.waitForElementWithId(driver, 'rb_LogsDtAll')
	rbAllDates.click();
	//store Tables
	testLog = getTestLog()
	changeLog = getChangeLog()
	//Some output for control
		
	//Try to get change log after retry interval
	if (changeLog.size() == 0){
		Thread.sleep(RetryInterval*1000)
		libPpm.ChooseAccount(driver, TPAMaddress, systemName, accntName)
		//go to Logs tab
		driver.findElement(By.id('Logs')).click()
		changeLog = getChangeLog()
		testLog = getTestLog()
	}
	
	j=0
	while (testLog.size() == 0 && j < 5){
		//Try to get test log after retry interval
		Thread.sleep(RetryInterval*1000)
		libPpm.ChooseAccount(driver, TPAMaddress, systemName, accntName)
	    //go to Logs tab
	    driver.findElement(By.id('Logs')).click()
		j++
	    testLog = getTestLog()
	}
	println '--- Test log ---'
	printlog(testLog)
	println '--- Change log ---'
	printlog(changeLog)
	
	assert testLog.size() > 0 : "Something went wrong: Test log is empty even after ${j*RetryInterval} seconds."
	assert changeLog.size() > 0 : 'Something went wrong: Change log is empty even after retry interval.'
	
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "inspectCheckAndResetLogs")
	println("Catched: Something went wrong: " + toutex);
}
catch (e){
	println("Catched: Something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
	
//Try to define What came first, the check or change 
firstReset = changeLog[-1]
firstTest = testLog.pop()
	
//Change has come before test
if (firstTest.date.after(firstReset.date)){
	
	//The most likely scenario
	if(firstReset.reason == 'Scheduled Change'){
			
		//Expected change result
		if(firstReset.result == expectedChangeResult){
			if(expectedChangeResult=='Success'){
				checkSuccessfulTest(firstTest)
			}else{
			    checkFailedTest(firstTest)
			}
		}
		
		//Scheduled reset can be canceled by Mismatch
		else if(firstReset.result == 'Canceled'){
			if(firstReset.comment.contains('Pre-empted by Mismatch Reset')){
				checkMismatchEntries(expectedChangeResult)
			}else{
				reportFailure("Scheduled reset was canceled by the following reason: ${firstReset.comment}")
			}
		}//end of canceled check
			
		//Another Scheduled change result
		else{
		    reportFailure("We expect that change result will be $expectedChangeResult or Canceled")
		}
	
	//Change reason is not Scheduled change
	}else{
	   reportFailure("We expected a scheduled passord change. But real reason is ${firstReset.reason}")
	}
}	

//Test has come before change
else{
    switch (firstTest.result) {
		
		//Managed account password matches to the password on system.
		//Correct functional account password is specified or managed account can check his own password and passwords are matching.
		//It suffices to check the Scheduled change entries
		case 'Successful':
		    if(isSchedChange(expectedChangeResult)){
				reportSuccess()
			}else{
			    reportFailure('Change log does not contain any scheduled change entry with expected result')
			}
			break
		
		//Failed check. It is good if we have a wrong functional account password specified.
		case 'Failed - Host Unreachable':
		    if(isSchedChange(expectedChangeResult) && expectedChangeResult=='Failed'){
				reportSuccess()
			}else{
			    reportFailure('Unexpected Check failure.')
			}
			break
		
		//"Delayed - Change in progress" that happens when a password check starts but sees an in-process change for the same account.
		//When it happens the check will be pushed out and tried again in 60 seconds.
		case 'Delayed - Change in progress':
			if(isSchedChange(expectedChangeResult)){
				nextTestRow = testLog.pop()
				if(expectedChangeResult == 'Success'){
					checkSuccessfulTest(nextTestRow)
				}else{
				    checkFailedTest(nextTestRow)
				}
			}else{
				reportFailure('Change log does not contain any scheduled change entry with expected result')
			}
		    break
		
		//Mismatch. Look for mismatch entries in the Change log.
		case 'Failed - Password Mismatch':
		    isMismatchPresent = changeLog.any{it.reason == 'Password Mismatch' && (it.result == expectedChangeResult || it.result == 'Canceled')}
			if (isMismatchPresent && isSchedChange(expectedChangeResult)){
				reportSuccess()
			}else{
			    reportFailure('Password mismatch, but Change log does not contain scheduled or mismatch entry.')
			}		
		    break
		default:
		    reportFailure("Unexpected Check result: ${firstTest.result}")
		
	}//end of switch
}
	
//check functions

//We expect Successful change or Delayed - Change in progress with successful check after
def checkSuccessfulTest(testRow){
	if(testRow.result == 'Successful'){
		reportSuccess()
	}else if(testRow.result == 'Delayed - Change in progress'){
	    nextRow = testLog.pop()
	    checkSuccessfulTest(nextRow)
	}else{
		reportFailure('Unexpected test result after successfull change: '+ testRow.result)
	}
}

//Expected test results for wrong func account pwd: Mismatch,Host unreachable or Success
def checkFailedTest(testRow){
	//Managed account can check password and passwords are matching
	if(testRow.result == 'Successful'){
		reportSuccess()
	//Managed account can't check password
	}else if(testRow.result =='Failed - Host Unreachable'){
		reportSuccess()
	}
	//Managed account can check its own password, but they are not matching
	else if(testRow.result =='Failed - Password Mismatch'){
		//Does change log contain any mismatch entry? 
		checkMismatchEntries('Failed')
	}else if(testRow.result =='Delayed - Change in progress'){
	    nextRow = testLog.pop()
		checkFailedTest(nextRow)
	}
	else{
		reportFailure('Unexpected test result after failed change: '+ testRow.result)
	}
}

//Try to find entries with mismatch 
def checkMismatchEntries(expectedRes){
	if(changeLog.any{it.reason == 'Password Mismatch' && (it.result == expectedRes || it.result == 'Canceled')}){
		reportSuccess()
	}else{
	    reportFailure("There are no Mismatch entries with $expectedRes result")
	}
}

//Returns if there are any Scheduled change entry with specified result
def boolean isSchedChange(result){
	changeLog.any{
		it.reason == 'Scheduled Change' && it.result == result
		}
}