import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.chrome.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import java.util.List

qasPassword=""
blackImage=""

def OpenSession (driver, TPAM, AccountName, RequestID, OSType, Image, qasPassword, blackImage) {

    TPAMaddr = "https://" + TPAM + "/tpam" 
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
	
	driver.findElement(By.id("CurrentRequests")).click()
	
	if (!driver.findElement(By.id("CurrentRequests"))) {
			println("CurrentRequests failed.")
	}
	
	driver.findElement(By.linkText("$RequestID")).click()
	
	String winHandleBefore = driver.getWindowHandle()
	
	driver.findElement(By.id("StartSessionButton")).click()
	
    Thread.sleep(1000)
	
	//Switch to new window opened
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	
	driver.manage().window().maximize()
	
	Thread.sleep(1000)
	
	//Switch to new window opened
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	
	driver.manage().window().maximize()
	
	if (Browser == "InternetExplorer") { 
		Thread.sleep(10000)
		driver.navigate().to("javascript:document.getElementById('overridelink').click()")
		Thread.sleep(4000)
    }
	
//	System.out.println(OSType)
//	System.out.println(Image)
	
	if (!OSType.contains("Win")) {

		SWaitMultipleV2Param = "60," + Image; 

		if (!libCom.SWaitMultipleV2(SWaitMultipleV2Param)) {
			println("SWaitMultipleV2 failed.")
		}	

		if (!libCom.SClickMultiple(Image)) {
			println("SClickMultiple failed.")
		}

    }
//	System.out.println(qasPassword)
	if (qasPassword != "") {
		
		Thread.sleep(1000)
		if (!libCom.SClickMultiple(blackImage)) {
			println("SClickMultiple failed.")
		}
		libCom.SType(qasPassword)
		libCom.SType("\n")
		Thread.sleep(30000)
		libCom.SType(qasPassword)
		libCom.SType("\n")
		}
	
	
	Thread.sleep(60000)
//	libCom.takeScreenshot(driver, "QASsession")
}

for (String s: args) {
	
	String[] parameter = s.split("=")
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
	if (paramName.equals("TPAMaddress")) {
		TPAMaddress = paramValue
		
	} else if (paramName.equals("UserName")) {
		UserName = paramValue
		
	} else if (paramName.equals("UserPassword")) {
		UserPassword = paramValue
		
	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue		
		
	} else if (paramName.equals("RequestID")) {
		RequestID = paramValue		

	} else if (paramName.equals("Browser")) {
		Browser = paramValue	
	
	} else if (paramName.equals("OSType")) {
		OSType = paramValue	

	} else if (paramName.equals("Image")) {
		Image = paramValue
	
	} else if (paramName.equals("CheckFlag")) {
		CheckFlag = paramValue
		
	} else if (paramName.equals("qasPassword")) {
		qasPassword = paramValue
		
	} else if (paramName.equals("blackImage")) {
		blackImage = paramValue
}
}
	
try {

	if (OSType == "QAS") {

	String cm = "cmd.exe /c " + "\"" + System.getenv("APPDATA") + "\\Microsoft\\Internet Explorer\\Quick Launch\\Shows Desktop.lnk" + "\""
	Runtime.getRuntime().exec(cm)
	Thread.sleep(10000)
	}
	
	libCom = new libCommon()
	libP = new libPPM()
	
	switch (Browser) {

		case "FireFox":
			System.setProperty("webdriver.firefox.profile", "TPAM");
			driver = new FirefoxDriver()
		break
	 
 
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", "ChromeDriver\\chromedriver.exe")
			
			ChromeOptions options = new ChromeOptions()
			options.addArguments("--always-authorize-plugins=true")
			options.addArguments("--enable-npapi=true")
			DesiredCapabilities capabilities = DesiredCapabilities.chrome()
			capabilities.setCapability(ChromeOptions.CAPABILITY, options)
			
			driver = new ChromeDriver(capabilities)
		break
	 
 
		case "InternetExplorer":
			System.setProperty("webdriver.ie.driver","IEDriver\\IEDriverServer.exe")
			driver = new InternetExplorerDriver()
		break
	}
	


    libCom.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	
	if (OSType == "QAS") {
	
	String winHandleBefore = driver.getWindowHandle()
	driver.switchTo().window(winHandleBefore)
	driver.manage().window().setPosition(new Point(0,0))
	driver.manage().window().maximize()
	}
	
	OpenSession(driver, TPAMaddress, AccountName, RequestID, OSType, Image, qasPassword, blackImage)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");
