import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.ArrayList

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def GlobalSetting (driver, TPAM) {

	File f = new File(ResultFile)
	if(f.exists() && !f.isDirectory()) { f.delete() }

	PrintStream out = new PrintStream(new FileOutputStream(ResultFile));
	System.setOut(out)
	
    TPAMaddr = "https://" + TPAM + "/admin/GlobalSettings.asp" 
    driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS)
	
	WebElement table = driver.findElement(By.id("GlobalSettingsTable"))
	List<WebElement> allRows = table.findElements(By.tagName("tr"));
	for (WebElement row : allRows) {
		println("")
	 	//println("-----------------------------------------")
		i = 1
		List<WebElement> cells = row.findElements(By.xpath(".//*[local-name(.)='tr' or local-name(.)='td']"));
		for (WebElement cell : cells) {
			if (i==2) {
			str = cell.getText() + " = "
			print(str)
			}
			else {print(cell.getText()+ " ")}
			i = i+1
			//println(i)
 }
}
}

println("Ready.GO.")
	
try {
    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]
	ResultFile = args[3]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/admin/", UserName, UserPwd)
	
	GlobalSetting(driver, TPAMaddress)
	
}
catch (e) {
    println("Catched: something went wrong: " + e)
}

driver.quit()

//println("Finished.")

