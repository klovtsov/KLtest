import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.sikuli.script.*
import java.util.concurrent.TimeUnit

def Session (driver, TPAM, RequestID, Image1, Image2, Url) {

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
	
    Thread.sleep(10000)
	
	//Switch to new window opened
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	
	driver.manage().window().maximize()
	
/* 	Thread.sleep(1000)
	
	//Switch to new window opened
	for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
	}
	
	driver.manage().window().maximize() */

//	libCom.SClick(Image)

	SWaitMultipleV2Param = "60," + Image1 
	libCom.SWaitMultipleV2(SWaitMultipleV2Param)
	
	if (libCom.SClickMultiple(Image1)) {
		println("TPANLogo image 1 OK")
	}
	libCom.SType(Key.F6)
	libCom.SType(Key.F6)
	Thread.sleep(5000)
	libCom.SType(Url)
	Thread.sleep(5000)
	libCom.SType(Key.ENTER)
	
	SWaitMultipleV2Param2 = "60," + Image2 
	libCom.SWaitMultipleV2(SWaitMultipleV2Param2)

	if (libCom.SClickMultiple(Image2)) {
		println("Allow Navigate Image2 OK")
	}
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
		
	} else if (paramName.equals("RequestID")) {
		RequestID = paramValue		

	} else if (paramName.equals("Image")) {
		Image0 = paramValue
	
	} else if (paramName.equals("Url")) {
		Url = paramValue
		
	} else if (paramName.equals("Image1")) {
		Image1 = paramValue
		
	} else if (paramName.equals("Image2")) {
		Image2 = paramValue
}
}
	
try {

	System.setProperty("webdriver.firefox.profile", "TPAM")
	driver = new FirefoxDriver()
	
	libCom = new libCommon()
	libP = new libPPM()
	
    libCom.login(driver, TPAMaddress, "/tpam/", UserName, UserPassword)
	
	Session(driver, TPAMaddress, RequestID, Image1, Image2, Url)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");
