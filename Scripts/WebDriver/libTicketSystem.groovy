import org.openqa.selenium.*

import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.interactions.Actions

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;






def addManagedTicketSystem(driver, TPAM, TicketSystemName, ManagedTicketSystemSystemName, ManagedTicketSystemSystemFunctAcct, TicketSQLStatement, ValidTicketNumber1, NewRuleName) {


		TPAMaddr = "https://" + TPAM + "/admin/ManageTS.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.id("AddTicketSystem")).click()

		Thread.sleep(2000)

		driver.findElement(By.id("TSName")).sendKeys(TicketSystemName)

		driver.findElement(By.id("TSDescription")).sendKeys(TicketSystemName)

		driver.findElement(By.id("EnableFL")).click()

		driver.findElement(By.id("AllowDisabledValidationFL")).click()

		driver.findElement(By.id("rbPar")).click()

		Thread.sleep(2000)

		driver.findElement(By.id("PARSystemName")).sendKeys(ManagedTicketSystemSystemName)

		driver.findElement(By.id("PARAccountName")).sendKeys(ManagedTicketSystemSystemFunctAcct)

                Thread.sleep(2000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)

		driver.findElement(By.id("Data")).click()

		libCom = new libCommon()
		libCom.waitForTextPresent(driver, "SQL Command", 15);

		driver.findElement(By.id("SQLStatement")).sendKeys(TicketSQLStatement)

		driver.findElement(By.id("subTicketNumber")).clear()

		driver.findElement(By.id("subTicketNumber")).sendKeys(ValidTicketNumber1)

		driver.findElement(By.id("btnGenerateList")).click()

		Thread.sleep(20000)

//		alert = driver.switchTo().alert()
//		alert.accept()

		if (libCom.waitForTextPresent(driver, "Ticket System tested successfully", 30)) {
			println("System tested.");
		}

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(20000)

		if (driver.getPageSource().contains("Ticket System saved successfully")) {
			println("System saved.");
		}

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.xpath("//td[contains(text(),'$TicketSystemName')]")).click()

		driver.findElement(By.id("Rules")).click()

		Thread.sleep(15000)

		driver.findElement(By.id("AddRule")).click()

		Thread.sleep(5000)

		driver.findElement(By.id("RuleName")).clear()

		driver.findElement(By.id("RuleName")).sendKeys(NewRuleName)

		Thread.sleep(3000)

		driver.findElement(By.id("RuleEnableFL")).click()

		Thread.sleep(3000)

		new Select(driver.findElement(By.name("LHV"))).selectByVisibleText(":SystemName:")

		Thread.sleep(3000)

		new Select(driver.findElement(By.name("RHV1"))).selectByVisibleText("[Computer]")

		Thread.sleep(3000)

		driver.findElement(By.id("btnValidate")).click()

		Thread.sleep(3000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)

		if (driver.getPageSource().contains("Ticket System Rules saved successfully")) {
			println("Rule saved.");
		}                                                                                                                                     
                                                                           	

}


def addNonManagedTicketSystem(driver, TPAM, TicketSystemName, NonManagedTicketSystemAddress, NonManagedTicketSystemPort, TicketsDB, NonManagedTicketSystemUser, NonManagedTicketSystemPassword, TicketSQLStatement, ValidTicketNumber1, NewRuleName) {


		TPAMaddr = "https://" + TPAM + "/admin/ManageTS.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.id("AddTicketSystem")).click()

		Thread.sleep(2000)

		driver.findElement(By.id("TSName")).sendKeys(TicketSystemName)

		driver.findElement(By.id("TSDescription")).sendKeys(TicketSystemName)

		driver.findElement(By.id("EnableFL")).click()

		driver.findElement(By.id("AllowDisabledValidationFL")).click()

		new Select(driver.findElement(By.id("ODBCDriver"))).selectByVisibleText("MySQL")

		Thread.sleep(2000)

		driver.findElement(By.id("NetworkAddress")).sendKeys(NonManagedTicketSystemAddress)

		driver.findElement(By.id("PortNumber")).sendKeys(NonManagedTicketSystemPort)

		driver.findElement(By.id("Timeout")).sendKeys("60")

		driver.findElement(By.id("DatabaseName")).sendKeys(TicketsDB)

		driver.findElement(By.id("UserID")).sendKeys(NonManagedTicketSystemUser)

		driver.findElement(By.id("Password")).sendKeys(NonManagedTicketSystemPassword)

                Thread.sleep(2000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)

		driver.findElement(By.id("Data")).click()

		libCom = new libCommon()
		libCom.waitForTextPresent(driver, "SQL Command", 15);

		driver.findElement(By.id("SQLStatement")).sendKeys(TicketSQLStatement)

		driver.findElement(By.id("subTicketNumber")).clear()

		driver.findElement(By.id("subTicketNumber")).sendKeys(ValidTicketNumber1)

		driver.findElement(By.id("btnGenerateList")).click()

		Thread.sleep(20000)

//		alert = driver.switchTo().alert()
//		alert.accept()

		if (libCom.waitForTextPresent(driver, "Ticket System tested successfully", 30)) {
			println("System tested.");
		}

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(20000)

		if (driver.getPageSource().contains("Ticket System saved successfully")) {
			println("System saved.");
		}

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.xpath("//td[contains(text(),'$TicketSystemName')]")).click()

		driver.findElement(By.id("Rules")).click()

		Thread.sleep(15000)

		driver.findElement(By.id("AddRule")).click()

		Thread.sleep(5000)

		driver.findElement(By.id("RuleName")).clear()

		driver.findElement(By.id("RuleName")).sendKeys(NewRuleName)

		Thread.sleep(3000)

		driver.findElement(By.id("RuleEnableFL")).click()

		Thread.sleep(3000)

		new Select(driver.findElement(By.name("LHV"))).selectByVisibleText(":SystemName:")

		Thread.sleep(3000)

		new Select(driver.findElement(By.name("RHV1"))).selectByVisibleText("[Computer]")

		Thread.sleep(3000)

		driver.findElement(By.id("btnValidate")).click()

		Thread.sleep(3000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)

		if (driver.getPageSource().contains("Ticket System Rules saved successfully")) {
			println("Rule saved.");
		}                                                                                                                                     
                                                                           	


}



def addWebServiceTicketSystem(driver, TPAM, TicketSystemName, TicketSystemSystemAddress, XMLPath, XMLErrorPath, TicketSystemSystemName, TicketSystemSystemFunctAcct, TicketParameterName, ValidTicketNumber1, NewRuleName) {


		TPAMaddr = "https://" + TPAM + "/admin/ManageTS.asp" 

		WebServiceURI = "http://" + TicketSystemSystemAddress + "/RetrieveTicket.asp"

		XMLPath = "/" + XMLPath + "/*"

		XMLErrorPath = "/" + XMLErrorPath + "/*"

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.id("AddTicketSystem")).click()

		Thread.sleep(2000)

		driver.findElement(By.id("TSName")).sendKeys(TicketSystemName)

		driver.findElement(By.id("TSDescription")).sendKeys(TicketSystemName)

		driver.findElement(By.id("EnableFL")).click()

		driver.findElement(By.id("AllowDisabledValidationFL")).click()

		driver.findElement(By.id("rbWeb")).click()

		Thread.sleep(2000)

		driver.findElement(By.id("WebServiceURI")).sendKeys(WebServiceURI)

		new Select(driver.findElement(By.id("WebServiceType"))).selectByVisibleText("HTTP POST")

		new Select(driver.findElement(By.id("WebServiceXMLEncoding"))).selectByVisibleText("ISO-8859-1")

		driver.findElement(By.id("WebServiceXPath")).sendKeys(XMLPath)

		driver.findElement(By.id("WebServiceErrorXPath")).sendKeys(XMLErrorPath)

		driver.findElement(By.id("rbWebServiceSystem")).click()

                Thread.sleep(2000)

		driver.findElement(By.id("WebServiceSystemName")).sendKeys(TicketSystemSystemName)

		driver.findElement(By.id("WebServiceAccountName")).sendKeys(TicketSystemSystemFunctAcct)

                Thread.sleep(2000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)                                                     

		driver.findElement(By.id("Data")).click()

		libCom = new libCommon()
		libCom.waitForTextPresent(driver, "Web Service Parameters", 15);

		driver.findElement(By.cssSelector("input.parmname")).sendKeys(TicketParameterName)

		new Select(driver.findElement(By.cssSelector("select.parmval"))).selectByVisibleText(":TicketNumber:")

		driver.findElement(By.id("subTicketNumber")).clear()

		driver.findElement(By.id("subTicketNumber")).sendKeys(ValidTicketNumber1)

		driver.findElement(By.id("btnGenerateList")).click()

		Thread.sleep(20000)

//		alert = driver.switchTo().alert()
//		alert.accept()

		if (libCom.waitForTextPresent(driver, "Ticket System tested successfully", 30)) {
//			println("System tested.");
		}

      		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(20000)

		if (driver.getPageSource().contains("Ticket System saved successfully")) {
			println("System saved.");
		}

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.xpath("//td[contains(text(),'$TicketSystemName')]")).click()

		driver.findElement(By.id("Rules")).click()

		Thread.sleep(15000)

		driver.findElement(By.id("AddRule")).click()

		Thread.sleep(5000)

		driver.findElement(By.id("RuleName")).clear()

		driver.findElement(By.id("RuleName")).sendKeys(NewRuleName)

		Thread.sleep(3000)

		driver.findElement(By.id("RuleEnableFL")).click()

		Thread.sleep(3000)

		new Select(driver.findElement(By.name("LHV"))).selectByVisibleText(":Platform:")

		Thread.sleep(3000)

		new Select(driver.findElement(By.name("RHV1"))).selectByVisibleText("[ProductName]")

		Thread.sleep(3000)

		driver.findElement(By.id("btnValidate")).click()

		Thread.sleep(3000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)

		if (driver.getPageSource().contains("Ticket System Rules saved successfully")) {
			println("Rule saved.");
		}                                                                                                                                     
                                                                           	


}



def addManualTicketSystem(driver, TPAM, TicketSystemName) {

		TPAMaddr = "https://" + TPAM + "/admin/ManageTS.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.id("AddTicketSystem")).click()

		Thread.sleep(2000)

		driver.findElement(By.id("TSName")).sendKeys(TicketSystemName)

		driver.findElement(By.id("TSDescription")).sendKeys(TicketSystemName)

		driver.findElement(By.id("EnableFL")).click()

		driver.findElement(By.id("ManualFL")).click()

		Thread.sleep(2000)

		new Select(driver.findElement(By.id("valQuickSel"))).selectByVisibleText("Custom Expression (enter below)")

		driver.findElement(By.id("ValidationExpr")).clear()

		driver.findElement(By.id("ValidationExpr")).sendKeys("[0-8]{5}")

                Thread.sleep(2000)

		driver.findElement(By.id("SubmitChanges")).click()

		Thread.sleep(10000)

		driver.get(TPAMaddr)
		Thread.sleep(10000)

		if (driver.getPageSource().contains(TicketSystemName)) {
			println("System created.");
		}


}








def deleteTicketSystem(driver, TPAM, TicketSystemName)  {

		TPAMaddr = "https://" + TPAM + "/admin/ManageTS.asp" 

		driver.get(TPAMaddr)
		
		Thread.sleep(10000)

		driver.findElement(By.xpath("//td[contains(text(),'$TicketSystemName')]")).click()

		driver.findElement(By.id("Details")).click()

		Thread.sleep(5000)

		driver.findElement(By.id("DeleteTicketSystem")).click()

		Thread.sleep(30000)

		alert = driver.switchTo().alert()
		alert.accept()
		
		Thread.sleep(15000)

		if (driver.getPageSource().contains("Ticket System successfully deleted")) {
			println("System deleted.");
		}                 

}



                                                         
		


