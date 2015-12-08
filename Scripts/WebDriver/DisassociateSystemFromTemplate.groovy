import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def TPAMuserName = args[1]
def TPAMuserPwd = args[2]
def SystemName = args[3]
def TemplateName = args[4]
def NewSystemName = args[5]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	//add template
	driver.get("https://" + TPAMaddress + "/tpam/ManageSystems.asp");
	libComn.waitForElementWithId(driver,"SystemNm");
    driver.findElement(By.id("SystemNm")).sendKeys("$SystemName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$SystemName')]")).click()
	Thread.sleep(1000)
	driver.findElement(By.id("Template")).click();
	Thread.sleep(1000)
    driver.findElement(By.id("cbxTemplateFl")).click();
	Thread.sleep(1000)
    driver.findElement(By.id("Details")).click();
	libComn.waitForElementWithId(driver,"SystemName");
	driver.findElement(By.id("SystemName")).clear();
    driver.findElement(By.id("SystemName")).sendKeys("$TemplateName");
    driver.findElement(By.id("bt_Save")).click();
	libComn.waitForResult("saved successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	//add system from template
	driver.findElement(By.id("bt_UseTemplate")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$TemplateName')]")).click()
    driver.findElement(By.id("Details")).click();
    driver.findElement(By.id("SystemName")).sendKeys("$NewSystemName");
    driver.findElement(By.id("bt_Save")).click();
	libComn.waitForResult("saved successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	//check btnDisassociate exist
	driver.get("https://" + TPAMaddress + "/tpam/ManageSystems.asp");
	libComn.waitForElementWithId(driver,"SystemNm");
    driver.findElement(By.id("SystemNm")).sendKeys("$NewSystemName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$NewSystemName')]")).click()
	driver.findElement(By.id("Details")).click();
	Thread.sleep(1000)
	if (driver.findElement( By.id("btnDisassociate") ).isDisplayed()){
		println("btnDisassociate exists")
	}
	driver.findElement(By.id("btnDisassociate")).click();
	Thread.sleep(1000)
	alert = driver.switchTo().alert()
	alert.accept()
	driver.findElement(By.id("bt_Save")).click();
	//check btnDisassociate does not exist
    driver.get("https://" + TPAMaddress + "/tpam/ManageSystems.asp");
	libComn.waitForElementWithId(driver,"SystemNm");
    driver.findElement(By.id("SystemNm")).sendKeys("$NewSystemName");
	driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$NewSystemName')]")).click()
    driver.findElement(By.id("Details")).click();
	Thread.sleep(1000)
	if (!driver.findElement( By.id("btnDisassociate") ).isDisplayed()){
		println("btnDisassociate does not exist")
	}
	//delete template
	driver.get("https://" + TPAMaddress + "/tpam/ManageSystems.asp");
	libComn.waitForElementWithId(driver,"SystemNm");
    driver.findElement(By.id("SystemNm")).sendKeys("$TemplateName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$TemplateName')]")).click()
	driver.findElement(By.id("bt_Del")).click();
	Thread.sleep(1000)
	alert = driver.switchTo().alert()
	alert.accept()
	libComn.waitForResult("has been successfully deleted", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}