import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

def setMysqlConnectionTab (driver, TPAM, SystemName, FunctionalAccount, FunctionalAccountPassword, ConnectionPort, SshAccount) {

	TPAMaddr = "https://" + TPAM + "/tpam/ManageSystems.asp" 
	driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	driver.findElement(By.id("SystemNm")).sendKeys(SystemName)
        driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$SystemName')]")).click()
        driver.findElement(By.id("Details")).click()
        driver.findElement(By.id("Connection")).click()
	driver.findElement(By.id("PARFuncAcct")).clear()
	driver.findElement(By.id("PARFuncAcct")).sendKeys(FunctionalAccount)
	driver.findElement(By.id("pword")).sendKeys(FunctionalAccountPassword)
	driver.findElement(By.id("ConfPword")).sendKeys(FunctionalAccountPassword)
	driver.findElement(By.id("Port")).sendKeys(ConnectionPort)

	if (driver.findElement(By.id("btGeneratePair20")).isEnabled()) {

			println("First Test Failed")

		} else {


			println("First Test OK - Generate Key Disabled")

		}
                                                         
	driver.findElement(By.id("bt_Save")).click()
	h.waitForTextPresent (driver, "saved successfully", 10)
        driver.findElement(By.id("Connection")).click()


	if (driver.findElement(By.id("btGeneratePair20")).isEnabled()) {

			println("Second Test Failed")

		} else {


			println("Second Test OK - Generate Key Disabled")

		}

	driver.findElement(By.id("cb_UseSshFl")).click()
	driver.findElement(By.id("SSHAccount")).sendKeys(SshAccount)
	driver.findElement(By.id("rb_SshSpecific")).click()

      	if (driver.findElement(By.id("btGeneratePair20")).isEnabled()) {

			println("Third Test Failed")

		} else {


			println("Third Test OK - Generate Key Disabled")

		}

	driver.findElement(By.id("bt_Save")).click()
	h.waitForTextPresent (driver, "saved successfully", 30)
        driver.findElement(By.id("Connection")).click()


      	if (driver.findElement(By.id("btGeneratePair20")).isEnabled()) {

		driver.findElement(By.id("btGeneratePair20")).click()
		h.waitForTextPresent (driver, "Successfully re-generated key pair", 20)	
		driver.findElement(By.id("bt_Save")).click()
		h.waitForTextPresent (driver, "saved successfully", 10)
	        driver.findElement(By.id("Connection")).click()
		Thread.sleep(5000)
		driver.findElement(By.id("btDnldSpcOpenSSH2")).click()
		Thread.sleep(5000)
		println("Downloading SSH Key")
		
		} else {


			println("Forth Test Failed - Generate Key Disabled")

		}



}
		
try {

	TPAMaddress = args[0]
        UserName = args[1]
	UserPassword = args[2]
	SystemName = args[3]
	FunctionalAccount = args[4]
	FunctionalAccountPassword = args[5]
	ConnectionPort = args[6]
	SshAccount = args[7]

	h = new libCommon()
                
	h.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	
	setMysqlConnectionTab(driver, TPAMaddress, SystemName, FunctionalAccount, FunctionalAccountPassword, ConnectionPort, SshAccount)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


