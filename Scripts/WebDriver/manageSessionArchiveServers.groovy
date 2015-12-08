/*
 * This script allows to manage (create,test,remove or change properties) archive servers.
 */
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
def serverName = args[4]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

def urlArchiveServers = "https://$TPAMaddress/tpam/EGPArchiveServers.asp"
def keyFileName = 'id_dsa.pub'

def selectSrv(srvname){
	if(!driver.findElements(By.xpath("//tr[td = '$srvname']")).isEmpty()){
		driver.findElement(By.xpath("//tr[td = '$srvname']")).click();
	}else{
	    println("something went wrong: Server $srvname does not exist.")
    }
}

def createArchServer(name,addr,usrName,folder){
	def successResult = 'Archive server saved successfully'
	//If server already exists - log an error
	if(!driver.findElements(By.xpath("//tr[td = '$name']") ).isEmpty()){
	    println("something went wrong: Server $name already exists.")
	}else{
	     
		 driver.findElement(By.id('AddServerButton')).click();
		 libComn.waitForElementWithId(driver,'ArchSrvName').sendKeys(name);
		 driver.findElement(By.id('NetworkAddress')).sendKeys(addr);
		 driver.findElement(By.id('AccountName')).sendKeys(usrName);
		 //clear default value
		 txtFolder = driver.findElement(By.id('ArchSrvPath'))
		 txtFolder.clear();
		  txtFolder.sendKeys(folder);
		 driver.findElement(By.id('Comments')).sendKeys('Test Server created by AutoTest');
		 driver.findElement(By.id('SubmitChanges')).click();
		 
		 Thread.sleep(4000);
		 libComn.waitForResult(successResult, driver,30)
	}
}

def removeArchServer(name){
	def successResult = 'Archive server deleted successfully'
	selectSrv(name)
	driver.findElement(By.id('DeleteButton')).click();
	//Warning should appears
	libComn.waitForElementWithId(driver,'DeleteButton2').click();
	//Accept alert
	Alert alert =driver.switchTo().alert();
	alert.accept();
	//wait for result.
	libComn.waitForResult(successResult, driver,30)
}

def testArchServer(server){
	selectSrv(server)
	driver.findElement(By.id('TestButton')).click();
	//Get text result
	WebDriverWait wait = new WebDriverWait(driver, 40);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//table[@class = 'report']/thead"), 'Results from the archive server test'));
	println(driver.findElement(By.id('testTable')).text);
}

def makeDefault(srvName){
	def successResult = 'Archive server saved successfully'
	selectSrv(srvName)
	driver.findElement(By.id('Details')).click();
		
	cbxDefault = libComn.waitForElementWithId(driver,'cb_Active');
	if(!cbxDefault.isSelected()){
	    cbxDefault.click();
    }
	driver.findElement(By.id('SubmitChanges')).click();
	libComn.waitForResult(successResult, driver,30)
}

def makeNonDefault(srvName){
	def successResult = 'Archive server saved successfully'
	anotherSrvName = driver.findElements(By.xpath('//td[@class = "ArchSrvName"]')).find{ it.text != srvName}.text
	//println anotherSrvName

	//Make another server default one
	makeDefault(anotherSrvName);
}

def getKey2048(server){
	def successRegen = "The key was successfully reset for $server"
	selectSrv(server)
	driver.findElement(By.id('Details')).click();
	rbSpecific = libComn.waitForElementWithId(driver,'rb_Specific')
	rbSpecific.click();
	driver.findElement(By.id('btGeneratePair0')).click();
	libComn.waitForResult(successRegen, driver, 30);
	driver.findElement(By.id('btDnldSpcOpenSSH')).click();
	//File should be download to .\Scripts\key
	Thread.sleep(1000);
}

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", userName, userPwd)
	driver.get(urlArchiveServers)
	if(action == "create"){
		
		def serverAddress = args[5]
		def archUserName = args[6]
		def archiveFolder = args[7]
		createArchServer(serverName,serverAddress,archUserName,archiveFolder);
		
	} else if (action == "remove"){
	
		removeArchServer(serverName);
	}else if (action == "test"){
	
		testArchServer(serverName);
	}
	else if (action == "makeDefault"){
		makeDefault(serverName);
	}
	else if (action == "makeNonDefault"){
		makeNonDefault(serverName);
	}
	else if (action == "get2048bitkey"){
		downloadDir = args[5];
		keyFileName = 'id_dsa.pub'
		libComn.check_and_delete_file(downloadDir,keyFileName)
		getKey2048(serverName);
	}
	 else{
		println("Action " + action + " is unknown. Possible values for action are: create or delete.");
	}
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}