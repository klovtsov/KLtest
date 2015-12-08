import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

def makeSessionRequest(driver,systemName,accntName,sessionNotes){
	libCmn = new libCommon()
	//click on link
	driver.findElement(By.linkText("Add Session Request")).click();
	//set filter values: system and account
	inputSysNm = libCmn.waitForElementWithId(driver,"SystemNm");
	inputSysNm.sendKeys(systemName);
	driver.findElement(By.id("AccountNm")).sendKeys(accntName);
	driver.findElement(By.id("Accounts")).click();
	//select account
	accntsTable = libCmn.waitForElementWithId(driver,"accountsTable");
	//Select all accounts for request
	List<WebElement> chkboxes = driver.findElements(By.xpath('//table[@id="accountsTable"]//input[@class="ResetCheckbox"]'))
	chkboxes.each {it.click()};
	//driver.findElement(By.xpath('//table[@id="accountsTable"]//input[@class="ResetCheckbox"]')).click();
	//go to Details tab
	driver.findElement(By.id("Details")).click()
	//set request reason
	txtArea = libCmn.waitForElementWithId(driver,"RequestReason");
	txtArea.sendKeys(sessionNotes);
	//send request
	driver.findElement(By.id("SubmitChanges")).click()
	libCmn.waitForResult("has been submitted", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

def cancelSessionRequest(driver,requestId,Notes){
	libCmn = new libCommon()
	//driver.findElement(By.id("CurrentRequests")).click();
	//tblRequests = libCmn.waitForElementWithId(driver,"requestsTable");
	//driver.findElement(By.xpath("//table[@id='requestsTable']//a[.='$requestId']")).click();
	selectRequest(driver,requestId);
	txtArea = libCmn.waitForElementWithId(driver,"ResponseComment");
	txtArea.sendKeys(Notes);
	driver.findElement(By.id("SubmitChanges")).click();
	libCmn.waitForResult("was submitted successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

def openSession(driver,requestId){
	libCmn = new libCommon()
	//driver.findElement(By.id("CurrentRequests")).click();
	//tblRequests = libCmn.waitForElementWithId(driver,"requestsTable");
	//driver.findElement(By.xpath("//table[@id='requestsTable']//a[.='$requestId']")).click();
	selectRequest(driver,requestId);
	btnStartSession = libCmn.waitForElementWithId(driver,"StartSessionButton");
	btnStartSession.click();
};

def terminateSession(driver){
	libCmn = new libCommon()
	driver.findElement(By.id("TerminateSessionButton")).click();
	libCmn.waitForResult("Terminated ActiveSession", driver)
}

//Select Current requests by ID
def selectRequest(driver,requestId){
	libCmn = new libCommon()
	driver.findElement(By.id("CurrentRequests")).click();
	tblRequests = libCmn.waitForElementWithId(driver,"requestsTable");
	driver.findElement(By.xpath("//table[@id='requestsTable']//a[.='$requestId']")).click();
}

//For Cancel Requests scenarios. Clicks on Save Changes button and wait a result
def submitCancel(driver){
	libCmn = new libCommon()
	driver.findElement(By.id("SubmitChanges")).click()
	libCmn.waitForResult("was submitted successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}

def cancelMultiSessionRequestForAll(driver,requestId,cancelReason){
	libCmn = new libCommon()
	selectRequest(driver,requestId);
	txtArea = libCmn.waitForElementWithId(driver,"ResponseComment");
	txtArea.sendKeys(cancelReason);
	driver.findElement(By.id("cb_SelectAll")).click();
	submitCancel(driver)
}//end of cancelMultiSessionRequestForAll function

def cancelMultiSessionRequest(driver,requestId,cancelReason,cancelSystem,cancelAccnt){
	libCmn = new libCommon()
	selectRequest(driver,requestId);
	txtArea = libCmn.waitForElementWithId(driver,"ResponseComment");
	txtArea.sendKeys(cancelReason);
	driver.findElement(By.xpath("//td[div = '$cancelSystem' and div = '$cancelAccnt']/../td/input")).click();
	submitCancel(driver);
}//end of cancelMultiSessionRequest function

def approveMultiSessionRequestForAll(driver,requestId,Comment){
	driver.findElement(By.linkText("Session Requests Pending Approval")).click();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("reportTab"), 'rows meeting filter criteria'));
	//select request by ID
	driver.findElement(By.xpath("//td[contains(text(),'$requestId')]")).click()
	//Specify details
	driver.findElement(By.id("Details")).click()
	//supply approve Response
	libCmn = new libCommon()
	txtArea = libCmn.waitForElementWithId(driver,"ResponseComment");
	txtArea.sendKeys(Comment);
	driver.findElement(By.id("cb_SelectAll")).click()
	driver.findElement(By.id("ApproveReq")).click()
	libCmn.waitForResult("was submitted successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}//end of approveMultiSessionRequest function

def reviewMultiSessionRequestForAll(drvr,requestId,comment,todo){
	libCmn = new libCommon()
	drvr.findElement(By.id("PendingReviews")).click()
	WebDriverWait wait = new WebDriverWait(drvr, 60);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class = 'fieldHint']"), 'rows meeting filter criteria'));
	//select request by ID
	drvr.findElement(By.linkText("${requestId}")).click()
		//supply commit
	txtArea = libCmn.waitForElementWithId(drvr,"NewReviewComment");
	txtArea.sendKeys(comment);
		//Select accounts for approval (all)
	drvr.findElement(By.id("cb_SelectAll")).click()
		//send review comment
	if (todo == 'comment'){
		drvr.findElement(By.id("NewReviewCommentButton")).click()
	}else if (todo == "complete"){
		drvr.findElement(By.id("CompleteReviewButton")).click()
	}
	else{
		println("Please, choose complete or comment as a review action. The ${action} is unknown.");
	}
	libCmn.waitForResult("Reveiw Comment for Session Request $requestId Saved Successfully", drvr)
}