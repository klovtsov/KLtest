import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.Calendar
import java.text.SimpleDateFormat


//FirefoxProfile ffprofile = new FirefoxProfile(new File("C:\\TPAM\\ff4s_profile"));
System.setProperty("webdriver.firefox.profile", "TPAM");
driver = new FirefoxDriver()
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)		

try {

	TPAMaddress = args[0]
	ReportName = args[1]
	UserName = args[2]
	ObjectName = args[3]
	
	
	h = new libCommon()
	h.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	readReport(driver, TPAMaddress, ReportName, UserName, ObjectName)
	
}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


def readReport(driver, TPAM, ReportName, UserName, ObjectName) {

	String outFileName = "reportout.tmp";
	String outFilePath = "C:\\TPAM"+ "\\" + outFileName;
	File outFile = new File(outFilePath);
		if (outFile.exists()) {
			outFile.delete();
		}
		
	FileWriter fw = new FileWriter(outFile);
	PrintWriter pw = new PrintWriter(fw);
	System.out.println("Writing command output to " + outFilePath);



	if (ReportName == "Activity Report") {

	TPAMaddr = "https://" + TPAM + "/tpam/ActivityRpt.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()
			
	driver.findElement(By.id("UserNm")).sendKeys(UserName);
			
	new Select(driver.findElement(By.id("logObj"))).selectByVisibleText(ObjectName);
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("activityReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 4 rows of the report!
	Integer i = 6;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[5].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[9].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\ActivityReport.csv -spat -ls!LogTime -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col7\\t\$col8\\t\$col9\\t\$col11\\t\$col12\" -write -yes"

	def proc = command.execute()
	proc.waitFor()



	}


	if (ReportName == "User Password Changes") {

	TPAMaddr = "https://" + TPAM + "/tpam/ActivityRpt.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

	driver.findElement(By.id("UserNm")).sendKeys(UserName);

	driver.findElement(By.id("TargetTxt")).sendKeys(ObjectName);
			
	new Select(driver.findElement(By.id("logObj"))).selectByVisibleText("User Password");
	new Select(driver.findElement(By.id("logOp"))).selectByVisibleText("Change");
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("activityReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
				
		List<WebElement> cells = allRows[1].findElements(By.tagName("td"));


			if ((cells[1].getText() == UserName) && (cells[5].getText() == "Change") && (cells[8].getText() == "Success")) {
		
				System.out.println "Password Change Logged";

			} else {
			
				System.out.println "Test Failed";

			}

                                                      
	}



	if (ReportName == "Approver User Activity") {

	TPAMaddr = "https://" + TPAM + "/tpam/ActivityRpt.asp?Role=APR"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("UserNm")).sendKeys(UserName);
			
	new Select(driver.findElement(By.id("logObj"))).selectByVisibleText(ObjectName);
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("activityReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 1 row of the report!
	Integer i = 3;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[5].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[9].getText();
		
		System.out.println str;
		pw.println(str);
			
		}


	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\ActivityReport.csv -spat -ls!LogTime -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col7\\t\$col8\\t\$col9\\t\$col12\\t\$col13\" -write -yes"
	System.out.println(command);
	def proc = command.execute()
	proc.waitFor()

	}



	if (ReportName == "Requestor User Activity") {

	TPAMaddr = "https://" + TPAM + "/tpam/ActivityRpt.asp?Role=REQ"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()
			
	driver.findElement(By.id("UserNm")).sendKeys(UserName);
			
	new Select(driver.findElement(By.id("logObj"))).selectByVisibleText(ObjectName);
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("activityReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	Integer i = allRows.size();

	if (i > 3) {i = 4};
	//We are reading only the first 2 rows of the report!

	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[5].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[9].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\ActivityReport.csv -spat -ls!LogTime -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col7\\t\$col8\\t\$col9\" -write -yes"
	System.out.println(command);
	def proc = command.execute()
	proc.waitFor()



	}



	if (ReportName == "ISA User Activity") {

	TPAMaddr = "https://" + TPAM + "/tpam/ActivityRpt.asp?Role=ISA"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("UserNm")).sendKeys(UserName);
			
	new Select(driver.findElement(By.id("logObj"))).selectByVisibleText(ObjectName);
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("activityReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 1 row of the report!
	Integer i = 3;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[5].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[9].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\ActivityReport.csv -spat -ls!LogTime -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col7\\t\$col8\\t\$col9\\t\$col13\\t\$col14\" -write -yes"
	System.out.println(command);
	def proc = command.execute()
	proc.waitFor()


	}



	if (ReportName == "Passwords Currently In Use") {

		System.out.println "Reading Passwords Currently In Use Report!";

		TPAMaddr = "https://" + TPAM + "/tpam/PwdInUseRpt.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)

		driver.findElement(By.id("AccountNm")).sendKeys(ObjectName);
		
		driver.findElement(By.id("InUseBy")).sendKeys(UserName);
		
		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(5000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("pwdUpdateReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		Integer i = allRows.size();
		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				 
		 str+=cells[0].getText() + "\t";
		 str+=cells[1].getText() + "\t";
		 str+=cells[2].getText() + "\t";
		 str+=cells[3].getText() + "\t";
		 str+=cells[4].getText() + "\t";
		 str+=cells[6].getText() + "\t";
		 str+=cells[9].getText() + "\t";
		 str+=cells[10].getText() + "\t";
		 str+=cells[11].getText() + "\t";
		 str+=cells[12].getText() + "\t";
		 str+=cells[13].getText();
	
		System.out.println str;
		pw.println(str);
		
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PwdInUse.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col6\\t\$col8\\t\$col11\\t\$col12\\t\$col13\\t\$col14\\t\$col15\\t\$col16\\t\$col17\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	
        }



	if (ReportName == "PSM Enabled Accounts Inventory") {

		System.out.println "Reading PSM Enabled Accounts Inventory Report!";

		TPAMaddr = "https://" + TPAM + "/tpam/EgpInvRpt.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)
		
		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
		
		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(5000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("AccountInventory"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		Integer i = allRows.size();
		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				 
		 str+=cells[0].getText() + "\t";
		 str+=cells[1].getText();
	
		System.out.println str;
		pw.println(str);
		
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PSMAccountsInv.csv -spat -ls!System -sep \",\" -format \"\$col1\\t\$col2\" -write -yes"

	def proc = command.execute()
	proc.waitFor()

	
        }



	if (ReportName == "Password Aging Inventory") {

		TPAMaddr = "https://" + TPAM + "/tpam/PwdInvRpt.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)
		
		driver.findElement(By.id("rb_Dt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);

		driver.findElement(By.id("ReportLayout")).click()

		driver.findElement(By.id("sortLastChangeDt")).click()

		Thread.sleep(1000)                                    				

		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(10000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("passwordInventory"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		Integer i = allRows.size();
		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				 
		 str+=cells[0].getText() + "\t";
		 str+=cells[1].getText();
	
		System.out.println str;
		pw.println(str);
		
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PasswordAgingInv.csv -spat -ls!System -sep \",\" -format \"\$col1\\t\$col2\\t\$col4\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	
        }



	if (ReportName == "File Aging Inventory") {

		TPAMaddr = "https://" + TPAM + "/tpam/FileInvRpt.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)

		driver.findElement(By.id("rb_Dt0")).click()
		
		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
		
		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(10000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("fileInventory"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		Integer i = allRows.size();
		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				 
		 str+=cells[0].getText() + "\t";
		 str+=cells[1].getText();
	
		System.out.println str;
		pw.println(str);
		
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\FileAgingInv.csv -spat -ls!System -sep \",\" -format \"\$col1\\t\$col2\\t\$col4\" -write -yes"

	def proc = command.execute()
	proc.waitFor()

	
        }



	if (ReportName == "Release-Reset Reconcile") {

		TPAMaddr = "https://" + TPAM + "/tpam/RptReleaseReset.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()
		
		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
		
		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(10000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("pwdReleaseResetReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		//Integer i = allRows.size();
		Integer i = 5

		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				 
		 str+=cells[2].getText() + "\t";
		 str+=cells[3].getText() + "\t";
		 str+=cells[4].getText() + "\t";
		 str+=cells[5].getText() + "\t";
		 str+=cells[6].getText() + "\t";
		 str+=cells[7].getText() + "\t";
		 str+=cells[8].getText() + "\t";
		 str+=cells[11].getText();
	
		System.out.println str;
		pw.println(str);
		
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PWReleaseReset.csv -spat -ls!RequestID -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col7\\t\$col8\\t\$col12\\t\$col13\\t\$col14\\t\$col16\" -write -yes"

	def proc = command.execute()
	proc.waitFor()

	
        }



	if (ReportName == "User Entitlement Report") {

		TPAMaddr = "https://" + TPAM + "/tpam/EntitlementRpt.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
		
		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(5000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("userPwdEntitlementReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		//Integer i = allRows.size();
		Integer i = 7

		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				 
		 str+=cells[0].getText() + "\t";
		 str+=cells[1].getText() + "\t";
		 str+=cells[2].getText() + "\t";
		 str+=cells[4].getText() + "\t";
		 str+=cells[5].getText() + "\t";
		 str+=cells[6].getText() + "\t";
		 str+=cells[7].getText() + "\t";
		 str+=cells[8].getText() + "\t";
		 str+=cells[9].getText() + "\t";
		 str+=cells[10].getText() + "\t";
		 str+=cells[11].getText();
	
		System.out.println str;
		pw.println(str);
		

		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\UserEntitlement.csv -spat -ls!UserName -!Global -sep \",\" -format \"\$col1\\t\$col2\$col3\\t\$col4\\t\$col6\\t\$col7\\t\$col8\\t\$col9\\t\$col10\\t\$col11\\t\$col12\\t\$col13\\t\$col14\\t\$col15\\t\$col16\\t\$col17\" -write -yes"

	def proc = command.execute()
	proc.waitFor()
	

        }



	if (ReportName == "Failed Logins Report") {

		TPAMaddr = "https://" + TPAM + "/tpam/FailedLoginRpt.asp"
		
		driver.get(TPAMaddr)
				
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()
		
		driver.findElement(By.id("Report")).click()
		
		Thread.sleep(10000)                                    		
		
		WebElement reportTable = driver.findElement(By.id("failedLoginsReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
		
		Integer i = allRows.size();

		System.out.println i.toString();
		
		for (Integer j=1; j<=i-2; j++){
		String str = "";
				
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
		
		if (cells[1].getText() == UserName) {
		 
		 str+=cells[1].getText() + "\t";
		 str+=cells[2].getText() + "\t";
		 str+=cells[3].getText();
	         j = i-2;
		 }

		System.out.println str;
		pw.println(str);
		
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\FailedLogin.csv -spat -ls!LogTime -cut- BADPASS to * -sep \",\" -format \"\$col2\\t\$col3\\t\$col4\" -write -yes"

	def proc = command.execute()
	proc.waitFor()

	
        }


	if (ReportName == "Password Update Activity") {

	TPAMaddr = "https://" + TPAM + "/tpam/PwdUpdRpt.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
	driver.findElement(By.id("rb_Rea1")).click()

	new Select(driver.findElement(By.id("ChgRea"))).selectByVisibleText(UserName);
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("pwdUpdateReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 3 row of the report!
	Integer i = 5;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			str+=cells[3].getText() + "\t";
			str+=cells[4].getText() + "\t";
			 str+=cells[5].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PasswordUpdate.csv -spat -ls!System -sep \",\" -format \"\$col1\\t\$col2\\t\$col4\\t\$col5\\t\$col6\\t\$col7\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}




	if (ReportName == "Password Update Schedule") {

	TPAMaddr = "https://" + TPAM + "/tpam/PwdSchdRpt.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
	driver.findElement(By.id("rb_Rea1")).click()

	new Select(driver.findElement(By.id("ChgRea"))).selectByVisibleText(UserName);
			
	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("pwdScheduledUpdateReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 3 row of the report!
	Integer i = 5;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			str+=cells[3].getText() + "\t";
			 str+=cells[7].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PasswordSchedule.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col4\\t\$col6\\t\$col7\\t\$col8\\t\$col9\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}


	if (ReportName == "Password Test Activity") {

	TPAMaddr = "https://" + TPAM + "/tpam/PwdTstRpt.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
	Thread.sleep(2000)

	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("pwdTestReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 2 row of the report!
	Integer i = 4;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[3].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PasswordTest.csv -spat -ls!System -sep \",\" -format \"\$col1\\t\$col2\\t\$col4\\t\$col5\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}



	if (ReportName == "Expired Passwords") {

	TPAMaddr = "https://" + TPAM + "/tpam/PwdExpiredRpt.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

	driver.findElement(By.id("rb_filterDt1")).click()
        
	SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
	Calendar calendar = Calendar.getInstance();
	calendar.add(calendar.DAY_OF_MONTH, 1);
	String mydate = sdf.format(calendar.getTime())
	System.out.println mydate

	driver.findElement(By.id("SingleDt")).clear()

	driver.findElement(By.id("SingleDt")).sendKeys(mydate)

	driver.findElement(By.id("SystemNm")).sendKeys(ObjectName)
			
	Thread.sleep(2000)

	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("pwdUpdateReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	Integer k = allRows.size();
	//We are reading only the first 3 rows of the report!
	Integer i = 5;
	System.out.println k.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[9].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PasswordExpired.csv -spat -ls!System -sep \",\" -format \"\$col1\\t\$col2\\t\$col4\\t\$col5\\t\$col6\\t\$col7\\t\$col8\\t\$col9\\t\$col10\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}




	if (ReportName == "Password Release Requests Listing") {

	TPAMaddr = "https://" + TPAM + "/tpam/RptPwdReleaseRequests.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
	Thread.sleep(2000)

	driver.findElement(By.id("Listing")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("ReleaseRequestsReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 1 row of the report!
	Integer i = 3;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[10].getText() + "\t";
			 str+=cells[11].getText() + "\t";
			 str+=cells[12].getText() + "\t";
			 str+=cells[13].getText() + "\t";
			 str+=cells[14].getText() + "\t";
			 str+=cells[15].getText() + "\t";
			 str+=cells[16].getText() + "\t";
			 str+=cells[17].getText() + "\t";
			 str+=cells[19].getText();
		
		System.out.println str;
		pw.println(str);
			

		}

	driver.findElement(By.id("ExportCSVButton")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\ExportReleaseRequests.csv -spat -ls!Request -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col9\\t\$col12\\t\$col13\\t\$col14\\t\$col15\\t\$col16\\t\$col17\\t\$col18\\t\$col19\\t\$col20\\t\$col21\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}



	if (ReportName == "Auto Approved Password Releases") {

		TPAMaddr = "https://" + TPAM + "/tpam/RptAutoApprRelease.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
		Thread.sleep(2000)

		driver.findElement(By.id("ReportLayout")).click()

		Thread.sleep(1000)

		driver.findElement(By.id("sortRetrieveDt")).click()

                driver.findElement(By.id("Report")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("pwdAutoApprovedReleaseReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		//Integer i = allRows.size();
		//We are reading only the first 2 rows of the report!
		Integer i = 4;
		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
			String str = "";
					
			List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[8].getText();
		
			System.out.println str;
			pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\AutoApprRelease.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col9\\t\$col10\\t\$col11\\t\$col12\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}


	if (ReportName == "Auto Approved File Releases") {

	TPAMaddr = "https://" + TPAM + "/tpam/RptAutoApprFileRelease.asp"
			
	driver.get(TPAMaddr)
					
	Thread.sleep(9000)

//	driver.findElement(By.id("rb_filterDt0")).click()

	driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
	Thread.sleep(2000)

	driver.findElement(By.id("Report")).click()
			
	Thread.sleep(10000)
			
	WebElement reportTable = driver.findElement(By.id("pwdAutoApprovedReleaseReport"));
	List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
	//Integer i = allRows.size();
	//We are reading only the first 1 row of the report!
	Integer i = 3;
	System.out.println i.toString();
			
	for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\AutoApprFileRelease.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col8\\t\$col9\\t\$col10\\t\$col11\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}


	if (ReportName == "Password Release Activity") {

		TPAMaddr = "https://" + TPAM + "/tpam/PasswordReleaseActivity.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);

		new Select(driver.findElement(By.id("ReasonCodeID"))).selectByVisibleText(UserName);
			
		Thread.sleep(2000)

		driver.findElement(By.id("ReportLayout")).click()

		Thread.sleep(1000)

		driver.findElement(By.id("sortRetrieveDt")).click()

		driver.findElement(By.id("Report")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("pwdReleaseActivityReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		//Integer i = allRows.size();
		//We are reading only the first 3 rows of the report!
		Integer i = 5;
		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
		String str = "";
					
		List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[8].getText() + "\t";
			 str+=cells[9].getText() + "\t";
			 str+=cells[13].getText();
		
		System.out.println str;
		pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PasswordReleaseAct.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col9\\t\$col10\\t\$col11\\t\$col12\\t\$col13\\t\$col14\\t\$col15\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}

	if (ReportName == "File Release Activity") {

		TPAMaddr = "https://" + TPAM + "/tpam/FileReleaseActivity.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);

		new Select(driver.findElement(By.id("ReasonCodeID"))).selectByVisibleText(UserName);
			
		Thread.sleep(2000)

		driver.findElement(By.id("ReportLayout")).click()

		Thread.sleep(1000)

		driver.findElement(By.id("sortRetrieveDt")).click()

		driver.findElement(By.id("Report")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("fileReleaseActivityReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		//Integer i = allRows.size();
		//We are reading only the first 1 row of the report!
		Integer i = 3;
		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
			String str = "";
					
			List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[8].getText() + "\t";
			 str+=cells[9].getText() + "\t";
			 str+=cells[11].getText();
		
			System.out.println str;
			pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\FileReleaseAct.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col9\\t\$col10\\t\$col11\\t\$col12\\t\$col13\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}




	if (ReportName == "Auto Approved PSM Sessions") {

		TPAMaddr = "https://" + TPAM + "/tpam/RptAutoApprSession.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);
			
		Thread.sleep(2000)

		driver.findElement(By.id("ReportLayout")).click()

		Thread.sleep(1000)

		driver.findElement(By.id("sortStartDt")).click()

		driver.findElement(By.id("Report")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("pwdAutoApprovedReleaseReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		//Integer i = allRows.size();
		//We are reading only the first 1 row of the report!
		Integer i = 3;
		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
			String str = "";
					
			List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[6].getText() + "\t";
			 str+=cells[7].getText();
		
			System.out.println str;
			pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\AutoApprSession.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col8\\t\$col9\\t\$col10\\t\$col11\\t\$col12\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}




	if (ReportName == "PSM Session Activity") {

		TPAMaddr = "https://" + TPAM + "/tpam/EGPSessionActivity.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);

		new Select(driver.findElement(By.id("ReasonCodeID"))).selectByVisibleText(UserName);
			
		Thread.sleep(2000)

		driver.findElement(By.id("ReportLayout")).click()

		Thread.sleep(1000)

		driver.findElement(By.id("sortStartDt")).click()

		driver.findElement(By.id("Report")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("pwdReleaseActivityReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		//Integer i = allRows.size();
		//We are reading only the first 1 row of the report!
		Integer i = 3;
		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
			String str = "";
					
			List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[8].getText() + "\t";
			 str+=cells[9].getText() + "\t";
			 str+=cells[10].getText() + "\t";
			 str+=cells[13].getText();
		
			System.out.println str;
			pw.println(str);
			
		}

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\PSMSessionActivity.csv -spat -ls!SystemName -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\$col5\\t\$col9\\t\$col10\\t\$col11\\t\$col12\\t\$col13\\t\$col14\\t\$col15\" -write -yes"
	System.out.println(command);
	def proc = command.execute()
	proc.waitFor()


	}


	if (ReportName == "Session Requests Listing") {

		TPAMaddr = "https://" + TPAM + "/tpam/RptSessionRequests.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

//		driver.findElement(By.id("rb_filterDt0")).click()

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);

		new Select(driver.findElement(By.id("ReasonCodeID"))).selectByVisibleText(UserName);
			
		Thread.sleep(2000)

               	driver.findElement(By.id("Listing")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("SessionRequestsReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		//Integer i = allRows.size();
		//We are reading only the first 1 row of the report!
		Integer i = 3;
		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
			String str = "";
					
			List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[4].getText() + "\t";
			 str+=cells[7].getText() + "\t";
			 str+=cells[10].getText() + "\t";
			 str+=cells[11].getText() + "\t";
			 str+=cells[12].getText() + "\t";
			 str+=cells[13].getText() + "\t";
			 str+=cells[14].getText() + "\t";
			 str+=cells[15].getText() + "\t";
			 str+=cells[16].getText() + "\t";
			 str+=cells[17].getText() + "\t";
			 str+=cells[19].getText();
		
			System.out.println str;
			pw.println(str);
			
		}

	driver.findElement(By.id("ExportCSVButton")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\ExportSessionRequests.csv -spat -ls!RequestID -sep \",\" -format \"\$col2\\t\$col3\$col4\\t\$col5\\t\$col6\\t\$col9\\t\$col12\\t\$col13\\t\$col14\\t\$col15\\t\$col16\\t\$col17\\t\$col18\\t\$col19\\t\$col20\\t\$col21\" -write -yes"

	def proc = command.execute()
	proc.waitFor()


	}


	if (ReportName == "Windows Domain Account Dependencies") {

		TPAMaddr = "https://" + TPAM + "/tpam/DADependenciesFilt.asp"
			
		driver.get(TPAMaddr)
					
		Thread.sleep(9000)

		driver.findElement(By.id("SystemNm")).sendKeys(ObjectName);

		Thread.sleep(2000)

               	driver.findElement(By.id("Report")).click()
			
		Thread.sleep(10000)
			
		WebElement reportTable = driver.findElement(By.id("domainAccountsReport"));
		List<WebElement> allRows = reportTable.findElements(By.tagName("tr"));
			
		Integer i = allRows.size();

		System.out.println i.toString();
			
		for (Integer j=1; j<=i-2; j++){
			String str = "";
					
			List<WebElement> cells = allRows[j].findElements(By.tagName("td"));
				
			 str+=cells[0].getText() + "\t";
			 str+=cells[1].getText() + "\t";
			 str+=cells[2].getText() + "\t";
			 str+=cells[3].getText() + "\t";
			 str+=cells[5].getText();
		
			System.out.println str;
			pw.println(str);
			
		}

/*		allRows[1].click()

               	driver.findElement(By.id("DependentSystems")).click()
			
		Thread.sleep(2000)
		
		WebElement reportTable2 = driver.findElement(By.id("daDependentsTable"));
		List<WebElement> allRows2 = reportTable2.findElements(By.tagName("tr"));


		for (Integer j=0; j<=1; j++){
			String str2 = "";
					
			List<WebElement> cells2 = allRows2[j].findElements(By.tagName("td"));
				
			 str2+=cells2[0].getText() + "\t";
			 str2+=cells2[1].getText() + "\t";
			 str2+=cells2[2].getText() + "\t";
			 str2+=cells2[3].getText() + "\t";
			 str2+=cells2[5].getText();
		
			System.out.println str2;
			pw.println(str2);
			
		}
*/
	

	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	command = "..\\sfk filter C:\\TPAM\\Scripts\\keys\\WinDomActDep.csv -spat -ls!DomainController -sep \",\" -format \"\$col1\\t\$col2\\t\$col3\\t\$col4\\t\$col5\\t\$col6\\t\$col8\" -write -yes"

	def proc = command.execute()
	proc.waitFor()



	}

/*	if (ReportName == "Session Requests Listing")  {

	driver.findElement(By.id("ExportCSVButton")).click()
	Thread.sleep(10000)
	
	} else if (ReportName == "Password Release Requests Listing") {

	driver.findElement(By.id("ExportCSVButton")).click()
	Thread.sleep(10000)
	} else {
	           
	driver.findElement(By.id("exportCSV")).click()
	Thread.sleep(10000)

	}
*/
  
	fw.close();
}
