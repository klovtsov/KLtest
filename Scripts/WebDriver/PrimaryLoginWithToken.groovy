import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select

System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()

try {

	TPAMaddress = args[0]
        TPAMUser = args[1]
        TokenPath = args[2]
        TokenPassphrase = args[3]
	TokenName = args[4]


	loginWithToken(driver, TPAMaddress, "/tpam/", TPAMUser, TokenPath, TokenPassphrase, TokenName)
	

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


def loginWithToken(driver, TPAM, Path, User, PathToToken, Passphrase, Token) {

	TPAMaddr = "https://" + TPAM + Path  
	driver.get(TPAMaddr)
	Thread.sleep(2000)                   
	driver.findElement(By.id("loginUserName")).sendKeys(User)
//////////////////////////////////////////////////////////////////////////////////////////////////
///////The next command is just a crutch - arbitrary password is typed to enable the Login button 
	driver.findElement(By.id("loginPassword")).sendKeys("EnableButton")
	driver.findElement(By.id("btnLogin")).click()
        Thread.sleep(5000)
        if (driver.getPageSource().contains("Enter Response")) {
			println("Fake login step succeeded");
		}
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
		println("Challenge is missed");		
		}
	def proc = command.execute()
	proc.waitFor()
	println "response: ${ proc.exitValue()}"
	def response = proc.exitValue().toString()
	Thread.sleep(1000)
	driver.findElement(By.id("sPasscode")).sendKeys(response)
	Thread.sleep(1000)
	driver.findElement(By.name("B1")).click()
	Thread.sleep(10000)
	if (driver.getPageSource().contains("The Privileged Appliance and Modules")) {
			println("Token authentication succeeded.");
	}                                                                               
}
