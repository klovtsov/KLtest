import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit
import org.sikuli.script.*

//open Password Management page
def TPAMaddress = args[0]
def userName = args[1]
def userPwd = args[2]
def reqId = args[3]
def action = args[4]
def AcceptImage = args[5]

final termSessionMessage1 = 'Active session can be forcibly removed in 30 minutes'
final termSessionMessage = "Terminated ActiveSession"
//final AcceptButtonImages = 'Accept4.png,Accept5.png,psmSrvrMngr.png,psmRecycleBin.png,psm_LogonMessageWin.png,shortcut.png';
final PsmHeader = 'ff_psm_header_inactive.png'

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def moveToFileTransferTab(){
	textRequired = "System:"
	driver.findElement(By.id('FileTransfer')).click();
	libComn.waitForTextPresent(driver, textRequired, 5)
}

def waitFileTransferResult(file,ftresult){
	WebElement myDynamicElement = (new WebDriverWait(driver, 40))
	.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id = 'transJobList']/*/div[span='$file']")));
	elemStatus = driver.findElement(By.xpath("//div[@id = 'transJobList']/*/div[span='$file']/span[4]"));
	//Wait for a success result in 10 seconds. If there isn't success the status will be returned.
	try{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(elemStatus, ftresult));
	}//end of try block
	catch (TimeoutException timeEx){
		actualStatus = elemStatus.getText();
		println("Something went wrong: $actualStatus")
	}
}

def specifyTransferCreds(usr,usrPwd){
	println("File Transfer creds started")
	driver.findElement(By.id('tAccount')).sendKeys(usr);
	driver.findElement(By.id('tPassword')).sendKeys(usrPwd);
}

def downloadFile(fileName){
	SuccessResult = 'Your file download has completed successfully'
	//file transfer page should be opened.
	//Specify file name and try to click Download button
	driver.findElement(By.id('FQFN')).sendKeys(fileName);
	btnDownload = driver.findElement(By.id('downloadButton'));
	if(btnDownload.getAttribute('class') == 'enabledButton'){ //if button is not active send warning to console
		btnDownload.click();
	    // and wait resultat.
		waitFileTransferResult(fileName,SuccessResult);
	}else{
	    println('Something went wrong: Download button still disabled.')
	}
}//end of downloadFile function

def downloadFileErr(fileName,expecterError){
	driver.findElement(By.id('FQFN')).sendKeys(fileName);
	btnDownload = driver.findElement(By.id('downloadButton'));
	if(btnDownload.getAttribute('class') == 'enabledButton'){ //if button is not active send warning to console
		btnDownload.click();
		// and wait result.
		waitFileTransferResult(fileName,expecterError);
	}else{
		println('Something went wrong: Download button still disabled.')
	}
}

def downloadWithCreds(file,downloader,downloaderPwd){
	specifyTransferCreds(downloader,downloaderPwd)
	downloadFile(file)
}

def select_file(filePath){
	img_select_button = 'select_file.png'
	img_dialog_open_sign = 'FileTransf_1.png'
	img_open_file_button = 'FileTranferOpen.png'
	
	libComn.SWait(img_select_button);
	libComn.SClickMultiple(img_select_button);
	if(!libComn.SWait(img_dialog_open_sign)){
		println("something went wrong: File selection dialog wasn't opened.")
		libComn.STakeScreenShot('FileTransfer')
	}else{
	    libComn.SType2('N',KeyModifier.ALT)
		libComn.SType(filePath);
	}
	libComn.SClickMultiple(img_open_file_button);

}

def uploadFiles(filesToUpload,fileStore){
	img_upload_btn = 'upload.png'
	SuccessResult = 'Your file transfer has completed successfully'
	//Separate file names
	def existingFiles = []
	String[] filez = filesToUpload.split(",");
	//Select each file for upload.
	filez.each {
		fileFullPath = fileStore + "\\" + it
		def uploadFile = new File(fileFullPath)
		if (uploadFile.exists()){
			existingFiles.add(it)
			select_file(fileFullPath)
		}else{
		   println("something went wrong: File $fileFullPath for upload doesn't exist.")
		}
     }//end of each closure
	//Upload files
	if(!libComn.SWait(img_upload_btn)){
		println("something went wrong: Upload button hasn't appeared.")
		libComn.STakeScreenShot('FileTransfer')
	}else{
	     libComn.SClickMultiple(img_upload_btn);
	}
	//wait for results
	existingFiles.each {waitFileTransferResult(it,SuccessResult)};
}

boolean isLegendPresent(legend){
	rslt = driver.findElements(By.xpath("//legend")).findResult {it.getText() == legend ? it.getText() : null}
	return rslt != null
}

def GetContent(FilePath) {
	myMap = [:]
	def Expression = '^\\!define[ \\t]+(.+)[ \\t]+\\{(.+)\\}$'
	String fileContent = new File(FilePath).eachLine{line->
		  if((matcher = line =~ Expression)){
				 myMap.put(matcher[0][1], matcher[0][2])
		  }
	}
	return myMap
}

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	//request session
	libPsm = new libPSM()
	
	String winHandleMain = driver.getWindowHandle();
	driver.manage().window().maximize()
	libPsm.openSession(driver,reqId);
	//Switch to new window opened
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	//Set focus on psm window
	 if(!libComn.SWait(PsmHeader)){
		 //It's possible that all windows are minimazed.
		 libComn.SMouseMove('ff_minimazed.png')
		 libComn.SWait('ff_psmwindow_minimazed.png')
		 libComn.SClickMultiple('ff_psmwindow_minimazed.png');
	 }else{
	     libComn.SClickMultiple(PsmHeader);
	 }
	 //A Psm session interface loading requires more than 5 sec usually.
	 Thread.sleep(5000)
	//try to login
	if(!libComn.SWaitMultipleV2('100,'+ AcceptImage)){
		println("something went wrong: Can't find Accept button images.")
		libComn.STakeScreenShot('FileTransfer');
	}
	else{
		libComn.SClickMultiple(AcceptImage);
	}
	//Move to File Transfer Tab
	moveToFileTransferTab();
	//Do file transfer operation 
	switch (action) {
		case 'download':
		    downloadFile(args[6])
		    break
		case 'downloadErr':
		    downloadFileErr(args[6],args[7])
		    break
		case 'upload':
		    uploadFiles(args[6],args[7])
		    break
		case 'specifyCreds':
		    //Password can contain non-alphanumeric symbols and groovy call can be damaged, so read it from file
			def isFunctional = args[9].toBoolean()
		    def SystemContent = GetContent(args[8])
            def AcctPwd = isFunctional ? SystemContent.FunctAcctPwd : SystemContent.ManagedAccountPwd;
		    downloadWithCreds(args[6],args[7],AcctPwd)
		    break
		case 'checkUploadAllowed':
		    if(isLegendPresent('Select Upload File Name & Destination Directory')){
				println('File Upload is allowed')
			}else{
			    println('File Upload is prohibited')
			}
		    break
		case 'checkDownloadAllowed':
		     if(isLegendPresent('Enter Download Fully Qualified File Name')){
				println('File Download is allowed')
			}else{
			    println('File Download is prohibited')
			}
			break
		default:
			println("Unknown action: $action");
	}	
	//Terminate session
	driver.switchTo().window(winHandleMain);
	driver.findElement(By.id("TerminateSessionButton")).click();
	libComn.SMouseMove('UserHome.png')
	libComn.waitForResult(termSessionMessage, driver,30);
}
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