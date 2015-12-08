import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import java.nio.file.*
import java.nio.charset.StandardCharsets

SystemName = args[0]
SystemCount = args[1]
DownloadPath = args[2]
TPAM = args[3]
sysbatchFile = args[4]
accbatchFile = args[5]

urlPasswordManagement = "https://$TPAM/tpam/ProfileEditor.asp"
urlBatchSystemUpdate = "https://$TPAM/tpam/BatchImportUpdate.asp?BatchType=UpdateSystems"
urlBatchSystemImport = "https://$TPAM/tpam/BatchImportUpdate.asp?BatchType=ImportSystems"
urlBatchAccountImport = "https://$TPAM/tpam/BatchImportUpdate.asp?BatchType=ImportAccounts"
urlDeletedSystems =	"https://$TPAM/tpam/ManageDeletedSystems.asp"

sysbatchFilePath = DownloadPath + "\\" + sysbatchFile
accbatchFilePath = DownloadPath + "\\" + accbatchFile

try {
 	libComn = new libCommon()

 	// Set FireFox profile settings
	FirefoxProfile ffp = new FirefoxProfile();
	ffp.setPreference("browser.download.folderList",2);
	ffp.setPreference("browser.download.manager.showWhenStarting",false);
	ffp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
	ffp.setPreference("browser.download.dir",DownloadPath);

	driver = new FirefoxDriver(ffp)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

	
	//Login
	driver.get(urlPasswordManagement)
	Thread.sleep(5000)
	driver.findElement(By.id("loginUserName")).sendKeys("ParAdmin")
	driver.findElement(By.id("loginPassword")).sendKeys("Admin4PAR")
	driver.findElement(By.id("btnLogin")).click()
	Thread.sleep(5000)
 	
	//Create Password Check Profile
	new Select(driver.findElement(By.id("ProfileType"))).selectByVisibleText("Password Check");
	driver.findElement(By.id("NewButton")).click()
	driver.findElement(By.id("ProfileName")).sendKeys("NotifyTest")
	driver.findElement(By.id("cbxAllowNotify")).click()
	//driver.findElement(By.id("ConsecutiveFailCount")).sendKeys("1")
	//driver.findElement(By.id("cbxFailNotify")).click()
	driver.findElement(By.id("SaveButton")).click()
	libComn.waitForResult("The profile saved successfully", driver)
	
	//Create Password Change Profile
	new Select(driver.findElement(By.id("ProfileType"))).selectByVisibleText("Password Change");
	driver.findElement(By.id("NewButton")).click()
	driver.findElement(By.id("ProfileName")).sendKeys("NotifyTest")
	driver.findElement(By.id("cbxAllowNotify")).click()
	//driver.findElement(By.id("ConsecutiveFailCount")).sendKeys("1")
	//driver.findElement(By.id("cbxFailNotify")).click()
	driver.findElement(By.id("SaveButton")).click()
	libComn.waitForResult("The profile saved successfully", driver)
	
 	//Create Systems
	driver.get(urlBatchSystemImport)
	libComn.uploadFileToTpam(driver,sysbatchFilePath)
	driver.findElement(By.id('btnProcessFile')).click();
	WebDriverWait wait = new WebDriverWait(driver, 30);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//div[@id="detailDiv"]/div/span'), 'rows retrieved'));
	rslt = driver.findElement(By.id('divBatchResults')).text
	println(rslt)
	Thread.sleep(5000)
	
	
	//Create Accounts
	driver.get(urlBatchAccountImport)
	libComn.uploadFileToTpam(driver,accbatchFilePath)
	driver.findElement(By.id('btnProcessFile')).click();
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//div[@id="detailDiv"]/div/span'), 'rows retrieved'));
	rslt = driver.findElement(By.id('divBatchResults')).text
	println(rslt)
	Thread.sleep(5000)
	
	
	//GetSystemAndDownloadCertificate 
	ManageSystemsLink = "https://" + TPAM + "/tpam/ManageSystems.asp"
	driver.get(ManageSystemsLink)
		
	for (int i = 1; i <= Integer.parseInt(SystemCount); i++) {
		
		SysName = SystemName + i
		driver.findElement(By.id("SystemNm")).sendKeys(SysName)
		driver.findElement(By.id("Listing")).click()
		driver.findElement(By.xpath("//tr[td[contains(text(),'$SysName')]]")).click()
		driver.findElement(By.id("Details")).click()
		driver.findElement(By.id("Management")).click()
		driver.findElement(By.id("btnDownloadUserCert")).click()
		driver.get(ManageSystemsLink)
	}

	Thread.sleep(900000)
	
	command = "cmd /c start $DownloadPath\\run.bat $TPAM 1 $SystemCount $DownloadPath $SystemName"

	println(command)
	Runtime.getRuntime().exec(command) 
	
	Thread.sleep(900000)
	
	//Get Test and Change L0gs
	ManageAccountsLink = "https://" + TPAM + "/tpam/ManageAccounts.asp"
	driver.get(ManageAccountsLink)
		
	for (int i = 1; i <= Integer.parseInt(SystemCount); i++) {
		
		SysName = SystemName + i
		driver.findElement(By.id("SystemNm")).sendKeys(SysName)
		driver.findElement(By.id("Listing")).click()
		driver.findElement(By.xpath("//tr[td[contains(text(),'NSaccount')]]")).click()
		driver.findElement(By.id("Logs")).click()
		driver.findElement(By.id("TestLog")).click()
		results = libComn.TableViewer(driver,"testLogTable")
		println(" ***   $SysName\\NSaccount Test Log   ***")
		println(results)
		driver.findElement(By.id("ChangeLog")).click()
		results = libComn.TableViewer(driver,"changeLogTable")
		println(" ***   $SysName\\NSaccount Change Log   ***")
		println(results)
		println("")
		driver.get(ManageAccountsLink)
	} 
	
 	//Read log 
 	for (int i = 1; i <= Integer.parseInt(SystemCount); i++) {
		logfile = DownloadPath + "\\result" + i + ".log"
		List<String> lines = Files.readAllLines(Paths.get(logfile), StandardCharsets.UTF_8)
		println(lines)
	}  
	
    	//Delete Systems
	Thread.sleep(5000)
	driver.get(urlBatchSystemUpdate)
	driver.findElement(By.id('Radio1')).click()
	libComn.uploadFileToTpam(driver,sysbatchFilePath)
	driver.findElement(By.id('btnProcessFile')).click();
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath('//div[@id="detailDiv"]/div/span'), 'rows retrieved'));
	rslt = driver.findElement(By.id('divBatchResults')).text
	println(rslt)
	Thread.sleep(5000)
	
	driver.get(urlDeletedSystems)
	allsys = SystemName + "*"
	driver.findElement(By.id("SystemNm")).sendKeys(allsys)
	driver.findElement(By.id('Listing')).click()
	driver.findElement(By.id('btnHardDeleteAll')).click()
	driver.findElement(By.id('btnYes')).click()
	Thread.sleep(15000)   
	
	//Delete certs and log files
	
	File folder = new File(DownloadPath);
	
	for(File f: folder.listFiles()) {
		if(f.getName().startsWith("result") || f.getName().startsWith(SystemName))
			f.delete();
	}
	
  	//Delete Profiles
	driver.get(urlPasswordManagement)
	new Select(driver.findElement(By.id("ProfileType"))).selectByVisibleText("Password Check");
	new Select(driver.findElement(By.id("ProfileID"))).selectByVisibleText("NotifyTest");
	driver.findElement(By.id("DeleteButton")).click()
	Alert alert =driver.switchTo().alert();
	alert.accept();
	libComn.waitForResult("The profile was deleted successfully", driver)
	
	new Select(driver.findElement(By.id("ProfileType"))).selectByVisibleText("Password Change");
	new Select(driver.findElement(By.id("ProfileID"))).selectByVisibleText("NotifyTest");
	driver.findElement(By.id("DeleteButton")).click()
	Alert alert2 =driver.switchTo().alert();
	alert2.accept();
	libComn.waitForResult("The profile was deleted successfully", driver)   
	 
}

catch (e) {
		println("Catched: something went wrong: " + e)
}

driver.quit()
println("Finished.")