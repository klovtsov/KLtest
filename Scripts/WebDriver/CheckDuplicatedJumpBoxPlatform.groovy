import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

def CheckControls (driver, TPAM, PlatformName, JumpBoxName) {

    TPAMaddr = "https://" + TPAM + "/tpam/ManageCustomPLatforms.asp" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    driver.findElement(By.id("fltrPlatformName")).sendKeys(PlatformName)
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$PlatformName')]")).click()
	driver.findElement(By.id("Details")).click()
	driver.findElement(By.id("btnDuplicatePlatform")).click()
	driver.findElement(By.id("CustomPlatformName")).sendKeys("Duplicate")
	driver.findElement(By.id("btnSavePlatform")).click()
	Thread.sleep(2000)

	if (driver.getPageSource().contains("The Custom Platform was saved successfully")) {
		println("Platform duplicated.");
	}

	
	Success = true

	if (driver.findElement(By.id("ActiveFl")).isSelected()) {
			println("Active checkbox is checked!")
			Success = false
	}	

	if (!driver.findElement(By.id("ActiveFl")).isEnabled()) {
			println("Active checkbox is disabled!")
			Success = false
	}	


	if (driver.findElement(By.id("AutomationActiveFl")).isEnabled()) {
			println("Automation Active checkbox is enabled!")
			Success = false
	}	

	myselect = new Select(driver.findElement(By.id("PlatformTypeID")))

	if (myselect.getFirstSelectedOption().getText() != "Jump Box") {
			println("Wrong Custom Platform Type!" + myselect.getFirstSelectedOption().getText())
			Success = false
	}	

	myselect0 = new Select(driver.findElement(By.id("JumpBoxID")))

	if (myselect0.getFirstSelectedOption().getText() != JumpBoxName) {
			println("Wrong Jump Box Name!" + myselect0.getFirstSelectedOption().getText())
			Success = false
	}	



//	mytext = driver.findElement(By.id("Port")).getText()
//	println(mytext)
 
//	if (mytext != Port.toString()) {
//			println("Wrong port!" + mytext)
//			Success = false
//	}	

	if (!driver.findElement(By.id("SSHFl")).isSelected()) {
			println("DSS Key checkbox is not checked!")
			Success = false
	}	

	if (driver.findElement(By.id("PasswordFl")).isSelected()) {
			println("Password checkbox is checked!")
			Success = false
	}	

//	mytext = driver.findElement(By.id("PlatSpecificLabel")).getText()
//	println(mytext)
 

//	if (driver.findElement(By.id("PlatSpecificLabel")).getText() != Label) {
//			println("Wrong platform specific label!" + mytext)
//			Success = false
//	}	

	if (!driver.findElement(By.id("EnablePwdFl")).isSelected()) {
			println("Enable Password checkbox is not checked!")
			Success = false
	}	

	if (!driver.findElement(By.id("NetBiosNameFl")).isSelected()) {
			println("NETBIOS name checkbox is not checked!")
			Success = false
	}	

	if (!driver.findElement(By.id("DomainNameFl")).isSelected()) {
			println("Domain name checkbox is not checked!")
			Success = false
	}	

	if (!driver.findElement(By.id("PSMEnabledFl")).isSelected()) {
			println("Enable PSM checkbox is not checked!")
			Success = false
	}	

                                                                           
	myselect = new Select(driver.findElement(By.id("ProxyTypes")))
	List<WebElement> SelectOptions1 = myselect.getOptions()

	for (WebElement temp : SelectOptions1) {
	
	if (temp.getText() == "SSH - Automatic Login Using DSS Key") {

		if (temp.isSelected()) {

		        println(temp.getText() + " proxy is selected!")
			Success = false

			}		

		}

	if (temp.getText() == "SSH - Automatic Login Using Password") {

		if (!temp.isSelected()) {

		        println(temp.getText() + " proxy is not selected!")
			Success = false

			}		

		}

	if (temp.getText() == "SSH - Interactive Login") {

		if (!temp.isSelected()) {

		        println(temp.getText() + " proxy is not selected!")
			Success = false

			}		

		}

	if (temp.getText() == "x3270 - Interactive Login Using Password") {

		if (!temp.isSelected()) {

		        println(temp.getText() + " proxy is not selected!")
			Success = false

			}		

		}

	if (temp.getText() == "x5250 - Interactive Login") {

		if (temp.isSelected()) {

		        println(temp.getText() + " proxy is selected!")
			Success = false

			}		

		}


	}


	myselect = new Select(driver.findElement(By.id("TransferTypes")))
	List<WebElement> SelectOptions2 = myselect.getOptions()

	for (WebElement temp : SelectOptions2) {
	
	if (temp.getText() == "Windows File Copy") {

		if (temp.isSelected()) {

		        println(temp.getText() + " transfer type is selected!")
			Success = false

			}		

		}

	if (temp.getText() == "Secure Copy (SCP)") {

		if (!temp.isSelected()) {

		        println(temp.getText() + " transfer type is not selected!")
			Success = false

			}		

		}

	}

	Thread.sleep(6000)

	if (Success) {

	println("All controls are fine")

	}

	driver.findElement(By.id("btnDeletePlatform")).click()
	Thread.sleep(2000)

	alert = driver.switchTo().alert()
	alert.accept()

	if (driver.getPageSource().contains("The Custom Platform was deleted successfully")) {
		println("Platform deleted.");
	}



}
		
try {

	TPAMaddress = args[0]
	PlatformName = args[1]
	JumpBoxName = args[2]

	libCom = new libCommon()
                
	libCom.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	
	CheckControls(driver, TPAMaddress, PlatformName, JumpBoxName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


