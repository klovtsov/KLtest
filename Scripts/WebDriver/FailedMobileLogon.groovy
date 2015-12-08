import org.openqa.selenium.*
import java.util.concurrent.TimeUnit
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.*
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

TPAM = args[0]
User = args[1]
Password = args[2]
NOT_ALLOWED_TEXT = "Your User ID is not allowed access using a mobile device"

def FailedLoginMobile (driver, TPAM, User, Password) {
	TPAMaddr = "https://$TPAM/tpam"
	driver.get(TPAMaddr)
	Thread.sleep(5000)		// It's no good, but sometimes loginUserName was available, but keys were not sent properly. 
	driver.findElement(By.id("loginUserName")).sendKeys(User)
	driver.findElement(By.id("loginPassword")).sendKeys(Password)
	driver.findElement(By.id("btnLogin")).click()
//	WebElement recentActivElement = (new WebDriverWait(driver, 10))
//	.until(ExpectedConditions.presenceOfElementLocated(By.id("buttonDiv")))
	libCom.waitForTextPresent(driver, NOT_ALLOWED_TEXT, 10)
	println(driver.findElement(By.xpath("//tr")).getText())
}

try {
	libCom = new libCommon() 
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--user-agent=Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53")
	System.setProperty("webdriver.chrome.driver", "ChromeDriver\\chromedriver.exe")
	options.addArguments("--always-authorize-plugins=true")
	DesiredCapabilities capabilities = DesiredCapabilities.chrome()
	capabilities.setCapability(ChromeOptions.CAPABILITY, options)
	driver = new ChromeDriver(capabilities)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	FailedLoginMobile (driver, TPAM, User, Password)
}

catch (e){
    println("Catched: something went wrong: " + e);
}
finally{
    driver.quit()
    println("Script finished.");
}