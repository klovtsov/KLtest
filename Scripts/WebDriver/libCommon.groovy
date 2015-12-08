import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.interactions.Actions
import org.apache.commons.io.FileUtils

import java.text.SimpleDateFormat 
import java.util.concurrent.TimeUnit
import java.io.File
import javax.imageio.ImageIO;

import org.openqa.selenium.ie.InternetExplorerDriver
import org.sikuli.script.*

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.*
import org.openqa.selenium.remote.DesiredCapabilities


// Sikuli functions

def class Sikuli {

	private static Screen s;
	private static boolean IS_INSTANCE_AVAILABLE = false;

	String cwd = new File( "." ).getCanonicalPath();
	public String imageDir = new File(cwd).getParent() + "\\Images\\";

	public static Screen getScreen() {
		if (IS_INSTANCE_AVAILABLE == true) {
			return s;
		} else {
			s = new Screen();
			IS_INSTANCE_AVAILABLE = true;
			return s;
		}
	}
}


def boolean SClickMultiple (String Args) throws Exception {

		String[] Argz = Args.split(",");

		Sikuli Sikuli = new Sikuli();
		
		Boolean Result = false;
		
		println("SClickMultiple in action...");
				
		for (int i = 0; i < Argz.length; i++) {
		
			Result = true;
			
			String Path = Sikuli.imageDir + Argz[i];
			
			println("Sikuli click: " + Path);
			
			Screen S = Sikuli.getScreen();
			
			try {
				S.click(Path, 0);
			} catch (FindFailed e) {
				System.out.println(e.toString());
				Result = false;
			}
			
			if (Result) {
				return Result;    // true
			}
		}
		
		return Result;            // false
}


def boolean SWaitMultipleV2(String Args) throws Exception {   // V2. Waiting each image in cycle (short iterations) within WaitTotal

		String[] Argz = Args.split(",");   // the first parameter is WaitTotal; all the others are images

		int WaitTotal = Argz[0].toInteger();  // indicates how many seconds we are going to wait these images in total. 

		int NumberOfImages = Argz.length - 1;

		int WaitImage = 5;        // how many seconds we are going to wait each image in every iteration. 
		
		int HowMuchLoop = (int) WaitTotal/(NumberOfImages*WaitImage);

		Sikuli Sikuli = new Sikuli();

		Boolean Result = false;
			
		println("SWaitMultipleV2 in action. Parameters: " + Args + ". WaitTotal: no more than " + WaitTotal + " sec.");
		println("Wait each image in cycle " + WaitImage + " sec for " + HowMuchLoop + " times. Number of images: " + NumberOfImages);
			
		for (int j = 0; j < HowMuchLoop; j++) {
		
			for (int i = 1; i <= NumberOfImages; i++) {  // starting from the second element of Argz
			
				Result = true;
				
				String Path = Sikuli.imageDir + Argz[i];
				println("Sikuli wait: " + Path);
				
				Screen S = Sikuli.getScreen();
							
				try {
					S.wait(Path, WaitImage);
				} catch (FindFailed e) {
					System.out.println(e.toString());
					Result = false;
				}
				
				if (Result) {
					return Result;    // true
				}
			}
		}

		return Result;            // false
}



def boolean SWait (String Arg) throws Exception {

		Sikuli Sikuli = new Sikuli();

		Boolean Result = true;
		
		System.out.println("SWait in action...");
				
		String Path = Sikuli.imageDir + Arg;
			
		System.out.println("Sikuli wait: " + Path);
			
		Screen S = Sikuli.getScreen();
			
		try {
				S.wait(Path, 60);
			} catch (FindFailed e) {
				System.out.println(e.toString());
				Result = false;
			}
			
			if (Result) {
				return Result;    // true
			}
		
		return Result;            // false
}



public boolean SType(String Arg) throws Exception {

		Boolean Result = true;
		System.out.println("Sikuli type: " + Arg);
		
		Screen S = Sikuli.getScreen();
		try {
			S.type(Arg, 0);
		} catch (FindFailed e) {
			System.out.println(e.toString());
			Result = false;
		}
		return Result;
}                                          


public boolean SType2(String Arg1, Arg2) throws Exception {
		//Arg2 - KeyModifier.ALT, KeyModifier.CTRL, KeyModifier.SHIFT, KeyModifier.WIN
		Boolean Result = true;
		System.out.println("Sikuli type: " + Arg1 + Arg2);
		Screen S = Sikuli.getScreen();
		try {
			S.type(Arg1,Arg2)
		} catch (FindFailed e) {
			System.out.println(e.toString());
			Result = false;
		}
		return Result;
}


public boolean SMouseDown() throws Exception {

		Boolean Result = true;

		System.out.println("Sikuli mouse down " );

		Screen S = Sikuli.getScreen();

		try {
			S.mouseDown(Button.LEFT);

		} catch (FindFailed e) {

			System.out.println(e.toString());

			Result = false;
		}
		return Result;
}                                     


public boolean SMouseUp() throws Exception {

		Boolean Result = true;

		System.out.println("Sikuli mouse up " );

		Screen S = Sikuli.getScreen();

		try {
			S.mouseUp();

		} catch (FindFailed e) {

			System.out.println(e.toString());

			Result = false;
		}
		return Result;
}                                          



def boolean SMouseMove (String Arg) throws Exception {

		Sikuli Sikuli = new Sikuli();

		Boolean Result = true;
		
		System.out.println("SMouseMove in action...");
				
		String Path = Sikuli.imageDir + Arg;
			
		System.out.println("Sikuli mouse move: " + Path);
			
		Screen S = Sikuli.getScreen();
			
		try {
				S.mouseMove(Path);
			} catch (FindFailed e) {
				System.out.println(e.toString());
				Result = false;
			}
			
			if (Result) {
				return Result;    // true
			}
		
		return Result;            // false
}

def STakeScreenShot(screenshotName) throws Exception {
	//define screenshot name and path
	screenshotFolderName = 'C:\\TPAM\\Screenshots'
	extension = 'png'
	screenshotFolderHandler = new File (screenshotFolderName)
    //If a screenshot folder does not exist it will be created.
	if ( !screenshotFolderHandler.exists() ) {
		screenshotFolderHandler.mkdirs()
	}
    //Construct a screenshot file name
	timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime())
	fileName = screenshotName + "_" + timeStamp + "." + extension

	println("Screenshot Name: $fileName")
		
	Sikuli Sikuli = new Sikuli();
	Screen S = Sikuli.getScreen();
	try{
		File file = new File(screenshotFolderName + "\\" + fileName)
	    bufImg = S.capture().getImage();
		ImageIO.write(bufImg, extension, file)
	}catch(IOException e) {
            println("Write error for " + file.getPath() + ": " + e.getMessage());
        }
}


// Login to TPAM 

def login (driver, TPAM, Path, User, Password) {
	
	TPAMaddr = "https://" + TPAM + Path
	driver.get(TPAMaddr)
	Thread.sleep(5000)		// It's no good, but sometimes loginUserName was available, but keys were not sent properly. 

	// hack for ie	
    if(driver instanceof InternetExplorerDriver) { 
		driver.navigate().to("javascript:document.getElementById('overridelink').click()")
    }
	
	driver.findElement(By.id("loginUserName")).sendKeys(User)
	driver.findElement(By.id("loginPassword")).sendKeys(Password)
	driver.findElement(By.id("btnLogin")).click()

	if (!Path.contains("8443")) {
	WebElement recentActivElement = (new WebDriverWait(driver, 20))
	.until(ExpectedConditions.presenceOfElementLocated(By.id("RecentActivity")));
}
}


def logout (driver, TPAM) {

		TPAMaddr = "https://" + TPAM + "/logout.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(2000)
                
      		driver.findElement(By.id("B1")).click()

		Thread.sleep(2000)

}


def loginAndChangePassword (driver, TPAM, Path, User, Password, NewPassword) {

	TPAMaddr = "https://" + TPAM + Path 

	driver.get(TPAMaddr)

	Thread.sleep(2000)

	driver.findElement(By.id("loginUserName")).sendKeys(User)

	driver.findElement(By.id("loginPassword")).sendKeys(Password)

	driver.findElement(By.id("btnLogin")).click()

	Thread.sleep(10000)

	driver.findElement(By.id("sOldPwd")).sendKeys(Password)

	driver.findElement(By.id("sPassword")).sendKeys(NewPassword)

	driver.findElement(By.id("sConfirm")).sendKeys(NewPassword)

	driver.findElement(By.id("save")).click()

	Thread.sleep(7000)

	if (!driver.getPageSource().contains("successfully")) {
		println("Error changing password for User: " + User);
	}

		
}


//Login using Defender Desktop Token as Secondary authentication method


def loginWithPasswordThenWithToken(driver, TPAM, Path, User, Password, PathToToken, Passphrase, Token) {

	TPAMaddr = "https://" + TPAM + Path 

	driver.get(TPAMaddr)
	Thread.sleep(2000)

	driver.findElement(By.id("loginUserName")).sendKeys(User)

	driver.findElement(By.id("loginPassword")).sendKeys(Password)

	driver.findElement(By.id("btnLogin")).click()

	Thread.sleep(5000)

	if (driver.getPageSource().contains("Enter Passcode")) {
			println("First authentication succeeded.");
		}

	driver.findElement(By.name("B1")).click()

	Thread.sleep(2000)

	if (driver.getPageSource().contains("Challenge")) {

		element = driver.findElement(By.className("fieldHint"))

		challenge = element.getText().replaceAll(/[^\d]*(\d{8})[^\d]*/, '$1')

		println(challenge.toString())

	//Minimize any another windows.
	String cm = "cmd.exe /c " + "\"" + System.getenv("APPDATA") + "\\Microsoft\\Internet Explorer\\Quick Launch\\Shows Desktop.lnk" + "\""
	Runtime.getRuntime().exec(cm)


		command = "..\\AutoIt3.exe ..\\token.au3 " + PathToToken + " " + Token + " " + Passphrase + " " + challenge

		}
	else {

		command = "..\\AutoIt3.exe ..\\token.au3 " + PathToToken + " " + Token + " " + Passphrase

		}


	def proc = command.execute()

	proc.waitFor()
        
	println "response: ${ proc.exitValue()}"

	def response = proc.exitValue().toString()

	Thread.sleep(1000)

	driver.findElement(By.id("sPasscode")).sendKeys(response)

//        new Actions(driver).contextClick(element).sendKeys("p").perform();

	Thread.sleep(1000)

	driver.findElement(By.name("B1")).click()

	Thread.sleep(10000)
        
	if (driver.getPageSource().contains("The Privileged Appliance and Modules")) {
			println("Token authentication succeeded.");
	}                                                                               

		
}



// Wait for a given test in the TPAM Result Field

def waitForResult(String result, driver) {
	
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultsDiv"), result));
}

//Similar as previous function, but time out can be set by arguments
def waitForResult(String result, driver,int timeout) {
	WebDriverWait wait = new WebDriverWait(driver, timeout);
	wait.until(ExpectedConditions.textToBePresentInElement(By.id("ResultsDiv"), result));
}

// Wait for the text on the page

def	boolean waitForTextPresent (driver, textRequired, tryNumber) {
		
		for ( i in 1..tryNumber ) {

			Thread.sleep(1000)

			if (driver.getPageSource().contains(textRequired)) {
				
				return true;

			} 
		}

		return false;
	
}


// Take a screenshot

def takeScreenshot (driver, screenshotName) {

		screenshotFolder = "C:\\TPAM\\Screenshots"
		screenshotFolderHandler = new File (screenshotFolder)

		if ( !screenshotFolderHandler.exists() ) {
			screenshotFolderHandler.mkdirs()
		}


		timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime())

		screenshotName = screenshotName + "_" + timeStamp + ".png"

		println("Screenshot Name: " + screenshotName)

		screenshotPath = screenshotFolder + "\\" + screenshotName

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenshotPath));
}


def	boolean waitForElementEnabled(driver, elementId, tryNumber) {
		
		for ( i in 1..tryNumber ) {

			Thread.sleep(1000)

			if (driver.findElement(By.id(elementId)).isEnabled()) {
				
				return true;

			} 
		}

		return false;
	
}

//Check alert is present and it has specified text.
public boolean isAlertPresent(driver,expectedText)
{
	try
	{
		Alert alert =driver.switchTo().alert();
		receivedText = alert.getText();
		if(receivedText == expectedText){
			alert.accept();
			return true;
		}else{
			println("Alert text doesn't correspond to expected one.")
			alert.accept();
			return false;
		}
	}   // try
	catch (NoAlertPresentException Ex)
	{
		println("Alert hasn't appeared.")
		return false;
	}   // catch
}   // isAlertPresent()

//Waits while element with specified Id doesn't appears
def WebElement waitForElementWithId(driver,Id){
	WebElement awaitedElem = (new WebDriverWait(driver, 10))
	.until(ExpectedConditions.presenceOfElementLocated(By.id(Id)));
	return awaitedElem
}

def TableViewer(driver,TableName) {
	allcells=""
	WebElement table = driver.findElement(By.id(TableName))
	List<WebElement> allRows = table.findElements(By.tagName("tr"));
	for (WebElement row : allRows) {
		List<WebElement> cells = row.findElements(By.tagName("td"))
		for (WebElement cell : cells) {
//		print(cell.getText()+" ")
		allcells = allcells + cell.getText()+" "
	}}
	return allcells
	}

boolean uploadFileToTpam(driver,file){
	def Result = false
	//Check if file exists
	def upldFile = new File(file)
	if (upldFile.exists()){
		driver.findElement(By.id('PAMUploadBtnSelect')).click();
		
		String winHandleMain = driver.getWindowHandle();
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle)
		}
		//click on browse button
		SWait('browse.png')
		SClickMultiple('browse.png')
		Thread.sleep(1500)
		//Enter path to file
		SType(file)
		SWait('open.png')
		SClickMultiple('open.png')
		//Click upload
		driver.findElement(By.id('UploadButton')).click();
		Thread.sleep(500)
		//How many windows remain?
		if(driver.getWindowHandles().size() > 1){
			//Dialog window still visible.Get an error.
			println(driver.findElements(By.xpath('//span[@class="uploadError"]')).text)
			driver.findElement(By.id('btnClose')).click();
			driver.switchTo().window(winHandleMain);
		}else{
		    println('File has been uploaded.')
			driver.switchTo().window(winHandleMain);
			Result = true
		}
	}//end of if file exists 
	else{
		println("Something went wrong: File $file does not exist.")
	}
	return Result
}

def Browser(browser) { 
	
	switch ( browser ) {

		case "chrome":
			System.setProperty("webdriver.chrome.driver", "ChromeDriver\\chromedriver.exe")
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions options = new ChromeOptions()
			options.addArguments("always-authorize-plugins=true", "start-maximized")
			driver = new ChromeDriver(options) 
			break
	
		case "firefox":
			System.setProperty("webdriver.firefox.profile", "TPAM")
			driver = new FirefoxDriver()
			break
	}
	return driver
}

def check_and_delete_file(fileDirectory,fileNameExpr){
	def directory = new File(fileDirectory)
	if (!directory.isDirectory())
	{
	   println "The directory with name ${fileDirectory} doesn't exist or it is NOT a directory."
	}else{
	   directory.eachFileMatch(~fileNameExpr) {f-> println("File $f has been deleted.")
		   f.delete()
	   }
	}
}



