import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit

def TPAMaddress = args[0]
def TPAMuserName = args[1]
def TPAMuserPwd = args[2]
def UserName = args[3]
def TemplateName = args[4]
def NewUserName = args[5]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	libComn = new libCommon()
	//login to TPAM
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	driver.get("https://" + TPAMaddress + "/tpam/UserFilter.asp");
	//add template
	libComn.waitForElementWithId(driver,"UsrNm");
    driver.findElement(By.id("UsrNm")).sendKeys("$UserName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$UserName')]")).click()
	Thread.sleep(1000)
	driver.findElement(By.id("Template")).click();
	Thread.sleep(1000)
    driver.findElement(By.id("cbxTemplateFl")).click();
	Thread.sleep(1000)
    driver.findElement(By.id("Details")).click();
	Thread.sleep(1000)
	alert = driver.switchTo().alert()
	alert.accept()
	libComn.waitForElementWithId(driver,"UserName");
	driver.findElement(By.id("UserName")).clear();
    driver.findElement(By.id("UserName")).sendKeys("$TemplateName");
    driver.findElement(By.id("SubmitChanges")).click();
	libComn.waitForResult("User added successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	//add user from template
	driver.findElement(By.id("bt_UseTemplate")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$TemplateName')]")).click()
    driver.findElement(By.id("Details")).click();
    driver.findElement(By.id("UserName")).sendKeys("$NewUserName");
    driver.findElement(By.id("LastName")).sendKeys("a");
    driver.findElement(By.id("FirstName")).sendKeys("b");
    driver.findElement(By.id("SubmitChanges")).click();
	libComn.waitForResult("User added successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	//check btnDisassociate exist
	driver.get("https://" + TPAMaddress + "/tpam/UserFilter.asp");
	libComn.waitForElementWithId(driver,"UsrNm");
    driver.findElement(By.id("UsrNm")).sendKeys("$NewUserName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$NewUserName')]")).click()
	driver.findElement(By.id("Details")).click();
	Thread.sleep(1000)
	if (driver.findElement( By.id("btnDisassociate") ).isDisplayed()){
		println("btnDisassociate exists")
	}
	driver.findElement(By.id("btnDisassociate")).click();
	Thread.sleep(1000)
	alert = driver.switchTo().alert()
	alert.accept()
	driver.findElement(By.id("SubmitChanges")).click();
	//check btnDisassociate does not exist
	driver.get("https://" + TPAMaddress + "/tpam/UserFilter.asp");
	libComn.waitForElementWithId(driver,"UsrNm");
    driver.findElement(By.id("UsrNm")).sendKeys("$NewUserName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$NewUserName')]")).click()
	driver.findElement(By.id("Details")).click();
	Thread.sleep(1000)
	if (!driver.findElement( By.id("btnDisassociate") ).isDisplayed()){
		println("btnDisassociate does not exist")
	}
	//delete template
	driver.get("https://" + TPAMaddress + "/tpam/UserFilter.asp");
	libComn.waitForElementWithId(driver,"UsrNm");
    driver.findElement(By.id("UsrNm")).sendKeys("$TemplateName");
    driver.findElement(By.id("Listing")).click();
	driver.findElement(By.xpath("//td[contains(text(),'$TemplateName')]")).click()
	driver.findElement(By.id("Delete")).click();
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