import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.interactions.Actions
import java.util.concurrent.TimeUnit
import org.apache.commons.io.FileUtils
import java.text.SimpleDateFormat 

TPAMaddress = args[0]
UserName = args[1]
UserPassword = args[2]
NewUserPassword = args[3]

libCom = new libCommon()

SuccessMessage = "Password changed successfully."
ErrorMessage = "Error changing password. "


Result = loginAndChangePassword(TPAMaddress, UserName, UserPassword, NewUserPassword)

if (Result != SuccessMessage) {

  	Thread.sleep(10000)		// OK, wait a little before trying to change the password again

	Result = Result + loginAndChangePassword(TPAMaddress, UserName, UserPassword, NewUserPassword)

}


println(Result)


// =============================== Routines ==============================================


def loginAndChangePassword (TPAM, User, Password, NewPassword) {
	
	try	{
	

		System.setProperty("webdriver.firefox.profile", "TPAM")

		driver = new FirefoxDriver()

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)

		TPAMaddr = "https://" + TPAM + "/tpam" 

		driver.get(TPAMaddr)

		Thread.sleep(7000)	// Well, I observed the issue when implicitlyWait didn't help and loginUserName stayed empty, so let it be a pause here. 


		driver.findElement(By.id("loginUserName")).sendKeys(User)

		driver.findElement(By.id("loginPassword")).sendKeys(Password)

		driver.findElement(By.id("btnLogin")).click()


		driver.findElement(By.id("sOldPwd")).sendKeys(Password)

		driver.findElement(By.id("sPassword")).sendKeys(NewPassword)

		driver.findElement(By.id("sConfirm")).sendKeys(NewPassword)

		driver.findElement(By.id("save")).click()

		PasswordChanged = libCom.waitForTextPresent(driver, "successfully", 10)

		driver.quit()

		return PasswordChanged? SuccessMessage : ErrorMessage


	} catch (e) {
		
		println("Catched: something went wrong: " + e)

		libCom.takeScreenshot(driver, "loginAndChangePassword")
		
		driver.quit()

		return ErrorMessage

	}
		
}


