/*
 * This script is designed for checking of session archiving logs.
 * These logs can be found under Management > 'Session mgmt' menu in TPAM GUI.
 * logName parameter can accept the following values:
 *     session - Session Logs
 *     server - Archive Servers
 *     archive - Archive Log
 * Script checks logs for the current date (it is assumed that it is called by autotest).
 * If you have any questions about this script, do not hesitate to contact Alexander Buyanov.
 */
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
libComn = new libCommon()

def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def logName = args[3]

def pages = ['session':'EGPListSessionLogFilter',
			 'server':'EGPArchiveServers',
			 'DpaLog':'EGPSessionServers']

def TPAMaddr = "https://$TPAMaddress/tpam/${pages[logName]}.asp"

def setCurrntDateFilter(){
	/*
	 * This function sets a following filter: Single date and this date is current 
	 */
	libComn.waitForElementWithId(driver,'rb_filterDt1').click();
	//get current date
	def today = new Date()
	txtSingleDate = driver.findElement(By.id('SingleDt'))
	txtSingleDate.clear();
	txtSingleDate.sendKeys(today.format('MM/dd/yy'));
}

def setLayoutVisibleColumn(Object... ColumnNames){
	/*
	 * This function sets the columns listed in the arguments, will be displayed.
	 * Example: setLayoutVisibleColumn('Archive Server','Archive Server Path')
	 */
	def tableReadyMessage = 'Select the report setup options for the desired report Layout';
	driver.findElement(By.id('Layout')).click();
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//table[@id='formTable2']//td"), tableReadyMessage));
	ColumnNames.each {
		cbx = driver.findElement(By.xpath("//table[@id = 'ResultColumnsTable']/tr[td='$it']/*/input"));
		if (!cbx.selected)
		    cbx.click()
	}
}

def String getTableValueByColumnName(WebElement table,columnHeader,rowIdentifier){
    /*
     * Function gets a value of cell. A column is defined by header. A row is defined by any unique value
     * (Request Id as example).   	
     */
	// Get a column position
	def ClmnIndex=0
	List<WebElement> th=table.findElements(By.tagName("th"));
	for(int i=0;i<th.size();i++){
		if(th.get(i).getText() == columnHeader){
			ClmnIndex=i+1;
			break;
		}
	}
    //Find a row and get a cell text.
	if(ClmnIndex > 0){
	     celltext = table.findElement(By.xpath("//tr[td='$rowIdentifier']/td[$ClmnIndex]")).text
		 if(celltext != ''){
			 celltext
		 }else{
		     'Empty'
		 }
	}else{
	    println("something went wrong: Can't find a column with name '$columnHeader'")
	}
}

def String getFileSize(sessionOutput){
	/*
	 *Since BFER 6567 the script tries to find an entry by a file size. 
	 *The file size value should be returned by a session log inspection.
	 */
	def matcher = sessionOutput =~ '(?m)File Size: ([0-9]{1,})'
	if(matcher){
		fileSize = matcher[0][1]
	}else{
		println("something went wrong: Can't find a file size in: $sessionOutput")
	}
	return fileSize
}

try {
	
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	//open corresponding page
	driver.get(TPAMaddr)
	
	if(logName=='session'){
		def reqId = args[4]
		setCurrntDateFilter()
		setLayoutVisibleColumn('Archive Server','Archive Server Path');
		//Get log values
		driver.findElement(By.id('Listing')).click();
		//Wait table loading
		tblSessionReport = libComn.waitForElementWithId(driver,"SessionLogsReport");
		srvName = getTableValueByColumnName(tblSessionReport,'Archive Server',reqId)
		fldrName = getTableValueByColumnName(tblSessionReport,'Archive Server Path',reqId)
		duration = getTableValueByColumnName(tblSessionReport,'Duration',reqId)
		fileSize = getTableValueByColumnName(tblSessionReport,'File Size',reqId)
		//print output
		println("Archive Server: $srvName")
		println("Archive Folder: $fldrName")
		println("Duration: $duration")
		println("File Size: $fileSize")
	}//End of session choose
	else if(logName=='server'){
		//Check Archive Server log
	     def archServerName = args[4]
		 //Since BFER 6567 the script tries to find an entry by a file size. 
		 //The file size value should be returned by a session log inspection.
		 def fSize = getFileSize(args[5])
		 driver.findElement(By.xpath("//table[@id='serversTable']/*/tr[td='$archServerName']")).click();
		 driver.findElement(By.id('ArchivedFiles')).click();
		 setCurrntDateFilter();
		 driver.findElement(By.id('SessionLogs')).click();
		 //wait
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//div[@id="changeList"]/span'), 'Displaying'));
		 //get result (file name)
		 logEntries = driver.findElements(By.xpath("//table[@id='changeLogTable']/*/tr[td='$fSize']"))
		 if(logEntries.empty){
			 println("There is no any entry with a file size equal to $fSize.")
		 }else{
		     println(logEntries[0].findElement(By.xpath('td[3]')).getText().split('/')[-1])
		 }
		 
	}else if (logName=='DpaLog'){
	     def DpaOrLocal = args[4]
		 libComn.waitForElementWithId(driver,'serversTable')
		 if(DpaOrLocal == 'local'){
			 driver.findElementByXPath("//table[@id = 'serversTable']/*/tr[td = 'localhost']").click();
		 }else{
		     driver.findElementByXPath("//table[@id = 'serversTable']/*/tr[not (td = 'localhost')]").click();
		 }
		 // go to Details tab
		 driver.findElement(By.id('Log')).click();
	     libComn.waitForElementWithId(driver,'rb_filterDt1').click();
	     //get current date
	     def today = new Date()
	     txtSingleDate = driver.findElement(By.id('logDt'))
	     txtSingleDate.clear();
	     txtSingleDate.sendKeys(today.format('MM/dd/yy'));
		 //Get log values
		 driver.findElement(By.xpath("//div[@id='LogTabs']//a[@id='Log']")).click();
		 //wait
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@id='LogDiv']/span"), 'Displaying'));
		 //get log.
		 LogText = driver.findElement(By.xpath("//div[@id='LogDiv']//table[@class='report'][tbody]")).getText();
		 println(LogText)
	}else{
	    println("Incorrect logName argument '$logName'. Choose one of them: session, server or archive, please.")
	}
}

catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	//println("Script finished.");
}

