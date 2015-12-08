import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.sikuli.script.*

def UserName = args[0]
def Password = args[1]
def TPAMaddress = args[2]
def Passphrase = args[3]

try {

	File currentDirectory = new File(new File("../").getAbsolutePath())
	scriptsdir = currentDirectory.getCanonicalPath()
	downloadDir = scriptsdir + "\\Keys"
	// Set FireFox profile settings
	FirefoxProfile ffp = new FirefoxProfile();
	ffp.setPreference("browser.download.folderList",2);
	ffp.setPreference("browser.download.manager.showWhenStarting",false);
	ffp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
	ffp.setPreference("browser.download.dir",downloadDir);

	System.setProperty("webdriver.firefox.profile", "TPAM")
	driver = new FirefoxDriver()
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	
	libCom = new libCommon()
	libCom.login(driver, TPAMaddress, "/tpam/", UserName, Password)
	
	TPAMaddr = "https://" + TPAMaddress + "/tpam/MyInfo.asp"
	driver.get(TPAMaddr)
	//Setting  Passphrase and download key.
	driver.findElement(By.id("Passphrase")).sendKeys(Passphrase)
	Thread.sleep(1000)
	driver.findElement(By.id("ResetCliKey")).click()
	Thread.sleep(30000)
	driver.findElement(By.id("GetCliKey")).click()
	Thread.sleep(10000)
	
	putty = scriptsdir + "\\puttygen63.exe"
	iddsa = downloadDir + "\\id_dsa"
	Passphraseppk = downloadDir + "\\$UserName"+".ppk"
	//Save private key
	def command = "$putty $iddsa"
	def proc = command.execute()
	Thread.sleep(5000)
	
	Image2 = "PuTTYgenimg.png"
	SWaitMultipleV2Param = "10," + Image2 
	libCom.SWaitMultipleV2(SWaitMultipleV2Param)
	libCom.SClickMultiple(Image2)
	
	Image0 = "PuTTYgenPassphrase.png"
	SWaitMultipleV2Param = "10," + Image0 
	libCom.SWaitMultipleV2(SWaitMultipleV2Param)
	libCom.SClickMultiple(Image0)	
	
	libCom.SType(Passphrase)
	Thread.sleep(5000)
	libCom.SType(Key.ENTER)
	Thread.sleep(5000)
	libCom.SType(Key.ENTER)
	Thread.sleep(5000)
	libCom.SType2("S",KeyModifier.ALT)
	Thread.sleep(5000)
	libCom.SType(Passphraseppk)
	Thread.sleep(5000)
	libCom.SType(Key.ENTER)
	Thread.sleep(5000)
	libCom.SType2(Key.F4,KeyModifier.ALT)
	Thread.sleep(5000)
	libCom.check_and_delete_file(downloadDir,"id_dsa")
	
	File f = new File(Passphraseppk)
	if(f.exists()) {
		println("$Passphraseppk created successfully")
	}
}

catch (e) {
	println("Catched: something went wrong: " + e)
}
driver.quit()
println("Finished.")
