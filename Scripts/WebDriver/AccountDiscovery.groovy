import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.openqa.selenium.support.ui.Select

def TPAMaddress = args[0]
def TPAMuserName = args[1]
def TPAMuserPwd = args[2]
def ProfileName = args[3]

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
acceptNextAlert = true

def closeAlertAndGetItsText() {
   
   try {
      Alert alert = driver.switchTo().alert()
      String alertText = alert.getText()
      if (acceptNextAlert) {
        alert.accept()
      } else {
        alert.dismiss()
      }
      return alertText
    } finally {
      acceptNextAlert = true
    }
}

def ProfileOperation (driver, TPAMaddress, ProfileName) {

	TPAMaddr = "https://" + TPAMaddress + "/tpam/ProfileEditor.asp" 
	driver.get(TPAMaddr)

	new Select(driver.findElement(By.id("ProfileType"))).selectByVisibleText("Account Discovery")
	driver.findElement(By.id("NewButton")).click()
    driver.findElement(By.id("ProfileName")).sendKeys(ProfileName)
    driver.findElement(By.id("btnAddRow")).click()
	if (driver.findElements(By.cssSelector("select.autoNewAction.autoDiscCtrl")).size() != 0) {
	println("Detail added")
	}
	driver.findElement(By.id("btnDeleteRow")).click()
	if (driver.findElements(By.cssSelector("select.autoNewAction.autoDiscCtrl")).size() == 0) {
	println("Detail deleted.")
	}
	driver.findElement(By.id("SaveButton")).click()
	alert = closeAlertAndGetItsText()
	println(alert)
	driver.findElement(By.id("btnAddRow")).click()
	if (driver.findElements(By.cssSelector("select.autoNewAction.autoDiscCtrl")).size() != 0) {
	println("Detail added.")
	}
	driver.findElement(By.id("btnDeleteAll")).click()
	alert = closeAlertAndGetItsText()
	println(alert)
	if (driver.findElements(By.cssSelector("select.autoNewAction.autoDiscCtrl")).size() == 0) {
	println("Detail deleted.")
	}
}
try {
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", TPAMuserName, TPAMuserPwd)
	ProfileOperation (driver, TPAMaddress, ProfileName)
}
catch (e) {
	println("Catched: something went wrong: " + e)
}

driver.quit()
println("Finished.")

