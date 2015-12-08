import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import org.openqa.selenium.firefox.FirefoxDriver

def NetTool (driver, TPAMaddress, Path, Command, Host) {
	TPAMaddr = "https://" + TPAMaddress + Path + Command + ".asp" 
	driver.get(TPAMaddr)
	if (Command == "ShowRoutes"){
		for ( i in 1..18 ) {
			println(driver.findElement(By.xpath("//div[5]/table/tbody/tr["+i+"]/td")).getText())
		}
		for ( i in 19..35 ) {
			println(driver.findElement(By.xpath("//tr["+i+"]/td")).getText())
		}
	} else {
		if (Command == "TelnetTest"){
			driver.findElement(By.id("NetAdr")).sendKeys("$Host")
		} else {
			driver.findElement(By.id("ParmString")).sendKeys("$Host")
		}
	driver.findElement(By.id("SubmitChanges")).click() 
	Thread.sleep(5000)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	}
}
	
try {

	TPAMaddress = args[0]
	Path = args[1]
	UserName = args[2]
	UserPassword = args[3]
	Command = args[4]
	Host = args[5]
	
	System.setProperty("webdriver.firefox.profile", "TPAM") 
	driver = new FirefoxDriver()

	libCom = new libCommon()
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

	libCom.login(driver, TPAMaddress, Path, UserName, UserPassword)
	
	Thread.sleep(10000)
	
	NetTool(driver, TPAMaddress, Path, Command, Host)

}

catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


