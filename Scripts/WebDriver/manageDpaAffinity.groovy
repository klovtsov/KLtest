import org.openqa.selenium.*
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//open Password Management page
def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]
def SysName = args[3]
def affinityValue = args[4]

try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	libppm = new libPPM()
	libppm.chooseSystem(driver, TPAMaddress, SysName)
	driver.findElement(By.id("Affinity")).click()
	rbLocal =libComn.waitForElementWithId(driver,"rbPARAffinityAny")
	if(affinityValue == 'Local'){
		rbLocal.click();
	}else if (affinityValue == 'DPA'){
	    driver.findElement(By.id("rbPARAffinitySelected")).click();
		driver.findElement(By.xpath("//table[@id='tblPARDPAAffinity']//input")).sendKeys('1');
	}
	else{
		println("Affinity value should be Local or DPA.");
	}
	driver.findElement(By.id("bt_Save")).click();
	libComn.waitForResult("System changes for $SysName saved successfully",driver)
	println("Successfully")
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
    driver.quit()
    println("Script finished.");
}