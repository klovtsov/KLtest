import org.openqa.selenium.WebElement;

import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

//open Password Management page
def TPAMaddress = args[0]
def adminName = args[1]
def adminPwd = args[2]
def sysName = args[3]

def testFileName = args.length > 5 ? args[5] : 'TestFile'
//File name can be specified at script call

def urlAddFilePage = "https://$TPAMaddress/tpam/FileFilter.asp?StartPage=NewFile"

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {
	//Minimize any another windows.
	String cm = "cmd.exe /c " + "\"" + System.getenv("APPDATA") + "\\Microsoft\\Internet Explorer\\Quick Launch\\Shows Desktop.lnk" + "\""
	Runtime.getRuntime().exec(cm)
	Thread.sleep(1000)
	
	libComn = new libCommon()
	libComn.login(driver, TPAMaddress, "/tpam/", adminName, adminPwd)
	
	//go to add file tab
	driver.manage().window().maximize() 
	driver.get(urlAddFilePage)

	//filter by system name
	txtSysName =libComn.waitForElementWithId(driver,'SystemNm')
	txtSysName.sendKeys(sysName)
	driver.findElement(By.id('System')).click();

	//Select system
	WebDriverWait wait = new WebDriverWait(driver, 60);
	wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//div[@id='systemTab']/span"), 'Displaying'));
	driver.findElement(By.xpath("//tr[td='$sysName']")).click();

	//go to Details tab
	driver.findElement(By.id('Details')).click();
	txtFileName = driver.findElement(By.id('FileName'))
	wait.until(ExpectedConditions.visibilityOf(txtFileName))
	//Webdriver can send a few symbols before File Name input field will be visible
	Thread.sleep(1000)
	txtFileName.sendKeys(testFileName);
	
	//Try to upload file
	if(!libComn.uploadFileToTpam(driver,args[4])){
		//File uploading failed
		println("File hasn't been uploaded")
		driver.findElement(By.id('CancelButton')).click();
		libComn.waitForElementWithId(driver,'SystemNm')
	}else{
	    //File uploading is successful
	    if(args.length == 7){
			//Specify notification e-mail
			driver.findElement(By.id('RelNotifyEmail')).sendKeys(args[6])
		}    
	    driver.findElement(By.id('SubmitChanges')).click();
		libComn.waitForResult('File saved successfully', driver)
		println('File created successfully')
	}
}
catch (TimeoutException toutex){
	libComn.STakeScreenShot("CreateFileError")
	println("Catched: something went wrong: " + toutex);
}
catch (e){
	println("Catched: something went wrong: " + e);
}
finally{
	driver.quit()
	println("Script finished.");
}
