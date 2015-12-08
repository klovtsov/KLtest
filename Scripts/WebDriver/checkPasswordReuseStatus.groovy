import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.util.concurrent.TimeUnit


def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def pwdRuleName = args[3]
boolean forGlobal = (args[4].toLowerCase() =='y')?:false
String prefix = 'Global'

//open Password Rule
System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
libComn = new libCommon()

try {
	libComn.login(driver, TPAMaddress, "/admin/", adminName, adminPwd)
	//open Password Rule page
	driver.get("https://$TPAMaddress/admin/ManagePwdRules.asp")
	new Select(driver.findElement(By.id('PasswordRuleID'))).selectByVisibleText(pwdRuleName)
	if(forGlobal){
		selectPwdReuse = driver.findElementById('AllowRepeatGlobal')
	}else{
	    selectPwdReuse = driver.findElementById('AllowRepeatAccount')
		prefix = 'Account'
	}
	def result = selectPwdReuse.enabled ? 'enabled' : 'disabled' 
	println "$prefix Password reuse is $result"
}
catch (TimeoutException toutex){
	libComn.takeScreenshot(driver, "checkPasswordReuseStatus")
	println("Catched: Something went wrong: " + toutex);
}
catch (e){
	println("Catched: Something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}