import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM")
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
managePwdPage = "/tpam/PasswordManagement.asp"

//open Password Management page
def TPAMaddress = args[0]
def admin = args[1]
def adminPwd = args[2]


managePwdPageFull = "https://" + TPAMaddress + managePwdPage

try {
	libr = new libCommon()
	libr.login(driver, TPAMaddress, "/tpam/", admin, adminPwd)
	driver.get(managePwdPageFull)
	//Listing tab should be opened
	WebDriverWait wait = (new WebDriverWait(driver, 10))
	    wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//span[@class='fieldHint']"), "Displaying"));
	//Calculate passwords
	def manual=0, auto=0, none=0
	def Expression = '(?m)^Account (Auto|Manually|Not) Managed.*$'
	driver.findElements(By.xpath("//table[@id='AccountsTable']/*/tr/td[6]")).each {
		if((matcher = it.getText()  =~ Expression)){
			switch (matcher[0][1]){
				case 'Manually':
				    manual +=1
				    break
				case 'Auto':
				    auto +=1
				    break
				case 'Not':
				    none +=1
					break
			}//end of switch
	    }//end if
	}//closure end
	println("Auto:$auto Manual:$manual Not Managed:$none")
}//end of try block
catch (e){
	println("Catched: something went wrong: " + e);
}
driver.quit()

println("Script finished.");