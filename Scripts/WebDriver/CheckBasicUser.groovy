import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def CheckBasicUserVsCollection (TPAMaddress) {

    TPAMaddr = "https://" + TPAMaddress + "/tpam/ListCollectionsFilter.asp" 
    driver.get(TPAMaddr)
	libComn = new libCommon()
	libComn.waitForTextPresent(driver, "You do not have access to this page", 10)
	println(driver.findElement(By.cssSelector("div.accessDenied")).getText())

}
	
try {
    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", UserName, UserPwd)
	
	CheckBasicUserVsCollection(TPAMaddress)
	
}
catch (e) {
    println("Catched: something went wrong: " + e)
}

driver.quit()

println("Finished.")


