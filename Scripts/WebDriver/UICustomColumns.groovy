import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def CustomColumns (driver, TPAM, Obj, Objname) {

	if ("$Obj" == "User") {
		TPAMaddr = "https://" + TPAM + "/tpam/UserFilter.asp"
	} else { TPAMaddr = "https://" + TPAM + "/tpam/Manage"+Obj+"s.asp"
		}
    driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
	if ("$Obj" == "User") {
		driver.findElement(By.id("UsrNm")).clear()
		driver.findElement(By.id("UsrNm")).sendKeys("$Objname")	
	} else {driver.findElement(By.id("$Obj"+"Nm")).clear()
			driver.findElement(By.id("$Obj"+"Nm")).sendKeys("$Objname")		
		}
	driver.findElement(By.id("Listing")).click()
	driver.findElement(By.xpath("//td[contains(text(),'$Objname')]")).click()
    driver.findElement(By.id("Details")).click()
    driver.findElement(By.id("CustomInformation")).click()
	for ( i in 1..6 ) {
		driver.findElement(By.id("$Obj"+"Custom"+i)).clear()
		driver.findElement(By.id("$Obj"+"Custom"+i)).sendKeys("$Obj"+"Custom"+i)
	}
	if ("$Obj" == "System") {
		driver.findElement(By.id("bt_Save")).click()
	} else if ("$Obj" == "Account") {
		driver.findElement(By.id("SubmitChangesButton")).click()
	}
	else {driver.findElement(By.id("SubmitChanges")).click()}
	libCom.waitForResult("successfully", driver)
	println(driver.findElement(By.id("ResultsDiv")).getText())
	driver.findElement(By.id("CustomInformation")).click()
	for ( i in 1..6 ) {
		if ("$Obj" == "System") {
		println(driver.findElement(By.id("lblCustom"+i)).getText())
		} else { println(driver.findElement(By.id("lbl"+"$Obj"+"Custom"+i)).getText())
			}
		println(driver.findElement(By.id("$Obj"+"Custom"+i)).getAttribute("value"))
	}
}

def ListCustomColumns (driver, TPAM, Obj) {

	switch (Obj) {
		case "System":
	Objs = "Sys"
	break
	    case "Account":
	Objs = "Acct"
	break
	    case "User":
	Objs = "User"
	break
	}

	TPAMaddr = "https://" + TPAM + "/tpam/List"+Objs+"Filt.asp"
	driver.get(TPAMaddr)
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
	for ( i in 1..6 ) {
	if ("$Objs" == "Sys") {
		driver.findElement(By.id("fltrCustom"+i)).clear()
		driver.findElement(By.id("fltrCustom"+i)).sendKeys("$Obj"+"Custom"+i)	
	} else {driver.findElement(By.id("fltr"+"$Objs"+"Custom"+i)).clear()
			driver.findElement(By.id("fltr"+"$Objs"+"Custom"+i)).sendKeys("$Obj"+"Custom"+i)		
		}
	}
	driver.findElement(By.id("Listing")).click()
	if ("$Objs" == "Acct") {
		println(driver.findElement(By.xpath("//tr[@id='highlight0']/td[2]")).getText())
	} else {println(driver.findElement(By.cssSelector("#highlight0 > td")).getText())
	}
}
	
try {

    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]
	Obj = args[3]
	Objname = args[4]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", UserName, UserPwd)
	
	CustomColumns(driver, TPAMaddress, Obj, Objname)
	
	ListCustomColumns(driver, TPAMaddress, Obj)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


