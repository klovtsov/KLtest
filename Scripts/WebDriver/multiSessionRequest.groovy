import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def action = args[3]
def reqId = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//request session
	libPsm = new libPSM()
	if(action == "cancel"){
		def notes = args[5]
		if (args.size()==6){
			//Cancel request for all accounts
			libPsm.cancelMultiSessionRequestForAll(driver,reqId,notes);
		}else{
		    //Cancel request for specific account
		    def cancelSystem = args[6]
		    def cancelAccnt = args[7]
			libPsm.cancelMultiSessionRequest(driver,reqId,notes,cancelSystem,cancelAccnt);
		}//end of cancel block
	}else if (action == "approve"){
	    def approveComment = args[5]
		libPsm.approveMultiSessionRequestForAll(driver,reqId,approveComment);
	}//end of if == approve block
	else if (action == "review"){
		def reviewActn = args[5]
		def reviewComment = args[6]
		//println("Comment: $reviewComment, reviewaction: $reviewActn")
		libPsm.reviewMultiSessionRequestForAll(driver,reqId,reviewComment,reviewActn);
	}//end of if == review block
	else if (action == "login"){
		//open session and do nothing
		libPsm.openSession(driver, reqId);
		Thread.sleep(50000);
		libPsm.terminateSession(driver);
	}//end of if==login block
	else{
	    println("Action " + action + " is unknown. Possible values for action are: cancel, approve or review.");
	}//end of action choosing block
}//end of try block
catch (TimeoutException toutex){
	txt = driver.findElement(By.id('ResultsDiv')).getText();
	if(txt != termSessionMessage1){
		println("something went wrong: " + toutex)
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}