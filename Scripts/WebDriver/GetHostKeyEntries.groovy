import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def GetHostKeyEntries (TPAMaddress) {

    TPAMaddr = "https://" + TPAMaddress + "/admin/ManageHostKeys.asp" 
    driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS)
	libComn = new libCommon()
	libComn.waitForTextPresent (driver, "Network Address", 10)
	result = libComn.TableViewer(driver,"keysTable")
	println(result)
}
	
try {
	FrontPageContentPath = args[0]
	libPpm = new libPPM()
	def FrontPage = libPpm.GetContent(FrontPageContentPath)
	def TPAMaddress = FrontPage.TPAMHost
	def TPAMuserName = FrontPage.TPAMMaster
	def TPAMuserPwd = FrontPage.MasterPwd

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/admin/", TPAMuserName, TPAMuserPwd)
	
	GetHostKeyEntries(TPAMaddress)
	
}
catch (e) {
    println("Catched: something went wrong: " + e)
}

driver.quit()

println("Finished.")


