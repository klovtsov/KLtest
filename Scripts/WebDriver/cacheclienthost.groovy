import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.interactions.Actions
import java.util.concurrent.TimeUnit

TPAM = args[0]
Operation = args[1]
ClientHost = args[2]

libCom = new libCommon()


println("TPAM:" + TPAM)
println("Operation:" + Operation)
println("ClientHost:" + ClientHost)

TPAMaddr = "https://" + TPAM + "/tpam/ManageCSClientHost.asp"
println("TPAM address:" + TPAMaddr);

System.setProperty("webdriver.firefox.profile", "TPAM")

driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

driver.get(TPAMaddr)

try {

	MyField = driver.findElement(By.id("loginUserName"))
	MyField.click()
	MyField.sendKeys("Paradmin")

	MyField = driver.findElement(By.id("loginPassword"))
	MyField.click()
	MyField.sendKeys("Admin4PAR" + Keys.RETURN)


	if (Operation == "add") {
		
		MyField = driver.findElement(By.id("NewHostButton"))
		MyField.click()


		MyField = driver.findElement(By.id("NetworkAddress"))
		MyField.clear()
		MyField.click()
		MyField.sendKeys(ClientHost)

	} else {  // delete client host


		MyField = driver.findElement(By.xpath("//select/option[contains(text(), '$ClientHost')]"))
		MyField.click()


		MyField = driver.findElement(By.id("DeleteHostButton"))
		MyField.click()


		alert = driver.switchTo().alert()
		alert.accept()

	}



	MyField = driver.findElement(By.id("SubmitChangesButton"))
	MyField.click()

    if (libCom.waitForTextPresent(driver, "Success", 10)) {
		println("Client Host: finished");
	}

}
catch (e) {
    println("Catched: something went wrong." + e);
}

driver.quit()


