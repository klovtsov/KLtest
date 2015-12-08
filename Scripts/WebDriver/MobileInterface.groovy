import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.*
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

objectType=''
systemName=''
accountName=''

for (String s: args) {
	
	String[] parameter = s.split("=")
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
	if (paramName.equals("TPAMaddress")) {
		tpam = paramValue
	} else if (paramName.equals("TPAMuserName")) {
		userName = paramValue
	} else if (paramName.equals("TPAMuserPwd")) {
		userPwd = paramValue
	} else if (paramName.equals("action")) {
		action = paramValue
	} else if (paramName.equals("objectType")) {
		objectType = paramValue
	} else if (paramName.equals("systemName")) {
		systemName = paramValue
	} else if (paramName.equals("accountName")) {
		accountName = paramValue
	} 
}

def processElement(locator, operation) {
    WebDriverWait wait = new WebDriverWait(driver, 20)
	WebElement myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator))
	if (operation == "click") {
		driver.findElement(locator).click()
	} else if (operation == "getText") {
		driver.findElement(locator).getText()
	}
}


def loginMobile (driver, TPAM, Path, User, Password) {
	TPAMaddr = "https://$TPAM/$Path"
	driver.get(TPAMaddr)
	Thread.sleep(5000)		// It's no good, but sometimes loginUserName was available, but keys were not sent properly. 
	driver.findElement(By.id("loginUserName")).sendKeys(User)
	driver.findElement(By.id("loginPassword")).sendKeys(Password)
	driver.findElement(By.id("btnLogin")).click()
	WebElement recentActivElement = (new WebDriverWait(driver, 20))
	.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Logout")));
}

def RetrievePassword (driver, tpam)  {
	processElement(By.id("simplifiedAnchor"), "click")
	processElement(By.linkText("My Current Requests"), "click")
	processElement(By.linkText("Retrieve Password"), "click")
	println(processElement(By.id("ResultsDiv"), "getText"))
}

def AddRequest (driver, tpam, systemName, accountName)  {
	processElement(By.id("simplifiedAnchor"), "click")
	processElement(By.linkText(objectType), "click")
	driver.findElement(By.id("sysName")).sendKeys(systemName)
	if (objectType == "File") {
//	driver.findElement(By.id("fileName")).sendKeys(fileName)
	} else { 
	driver.findElement(By.id("acctName")).sendKeys(accountName)
	}
	processElement(By.xpath("//*[@value='Go']"), "click")
	println(processElement(By.xpath("//div[@class='pageHeader']"), "getText"))
	println(processElement(By.xpath("/html/body/form/ol/li"), "getText"))
	println("")
	processElement(By.linkText("Quick Request"), "click")
	println(processElement(By.id("Form1"), "getText"))
	processElement(By.xpath("//*[@value='Submit']"), "click")
	println(processElement(By.xpath("/html/body/span"), "getText"))
	Thread.sleep(2000)
}

def CancelRequest (driver, tpam)  {
	processElement(By.id("simplifiedAnchor"), "click")
	processElement(By.linkText("My Current Requests"), "click")
	println(processElement(By.xpath("//div[@class='pageHeader']"), "getText"))
	println(processElement(By.xpath("/html/body/form"), "getText"))
	processElement(By.linkText("Quick Expire"), "click")
	println(processElement(By.id("Form1"), "getText"))
	processElement(By.xpath("//*[@value='Expire/Cancel']"), "click")
	println(processElement(By.xpath("/html/body/span"), "getText"))
	Thread.sleep(2000)
}

def ApproveRequest (driver, tpam)  {
	processElement(By.id("simplifiedAnchor"), "click")
	processElement(By.linkText("My Current Approvals"), "click")
	println(processElement(By.xpath("//div[@class='pageHeader']"), "getText"))
	println(processElement(By.xpath("/html/body/form/ol/li"), "getText"))
	println("")
	processElement(By.linkText("Quick Approve"), "click")
	println(processElement(By.id("Form1"), "getText"))
	println("")
	processElement(By.xpath("//*[@value='Proceed']"), "click")
	println(processElement(By.xpath("/html/body/span"), "getText"))
	println("")
	Thread.sleep(2000)
}

try {

	ChromeOptions options = new ChromeOptions();
	options.addArguments("--user-agent=Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53")
	System.setProperty("webdriver.chrome.driver", "ChromeDriver\\chromedriver.exe")
	options.addArguments("--always-authorize-plugins=true")
	DesiredCapabilities capabilities = DesiredCapabilities.chrome()
	capabilities.setCapability(ChromeOptions.CAPABILITY, options)
	driver = new ChromeDriver(capabilities)

	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	libComn = new libCommon()
    //login to TPAM
	loginMobile(driver, tpam, "/tpam/", userName, userPwd)
	
	if (action == "addRequest"){
	AddRequest (driver, tpam, systemName, accountName)
	}	
	if (action == "cancel"){
	CancelRequest (driver, tpam)
	}
	if (action == "approve"){
	ApproveRequest (driver, tpam)
	}
	if (action == "retrieve"){
	RetrievePassword (driver, tpam)
	}
               
}
catch (e){
               println("Catched: something went wrong: " + e);
}
finally{
               driver.quit()
               println("Script finished.");
}
