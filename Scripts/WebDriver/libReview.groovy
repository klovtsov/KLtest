import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.Calendar
import java.text.SimpleDateFormat



def submitComment(driver, TPAM, RequestId, CommentText) {

	h = new libCommon()

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	if(h.waitForTextPresent(driver, RequestId, 60)) {

		driver.findElement(By.linkText(RequestId)).click()                   

		h.waitForTextPresent(driver, "Review Comment", 30)

		driver.findElement(By.id("NewReviewComment")).sendKeys(CommentText)

		h.waitForElementEnabled(driver, "NewReviewCommentButton", 30) 

		driver.findElement(By.id("NewReviewCommentButton")).click()

		if (h.waitForTextPresent(driver, RequestId + " Saved Successfully", 30)) {

			println("Comment submitted.");
		}

	        driver.findElement(By.id("Reviews")).click()
	
		h.waitForTextPresent(driver, "Complete Flag", 30)

		reviewResult = tableReadRow(driver, "ReviewsTable", CommentText)

		println(reviewResult);

	} else {

		println("Request ID unavailable");

	}

}


def submitIsaComment(driver, TPAM, AccountName, CommentText) {

	h = new libCommon()

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	myString = "Pwd(" + AccountName + ")";

	if(h.waitForTextPresent(driver, myString, 60)) {

	WebElement myTable = driver.findElement(By.id("requestsTable"));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
	String RequestId = "Request Not Found";

	for (WebElement elRow:allRows){
					
		List<WebElement> cells = elRow.findElements(By.tagName("td"));
			
		for (WebElement elCell:cells){
	
			if (elCell.getText() == myString) {

			RequestId = cells[1].getText();

//			return RequestId;

				}
		
			}

	                                                
		}

		System.out.println RequestId;
                                   
		driver.findElement(By.linkText(RequestId)).click()                   

		h.waitForTextPresent(driver, "Review Comment", 30)

		driver.findElement(By.id("NewReviewComment")).sendKeys(CommentText)

		h.waitForElementEnabled(driver, "NewReviewCommentButton", 30) 

		driver.findElement(By.id("NewReviewCommentButton")).click()

		if (h.waitForTextPresent(driver, RequestId + " Saved Successfully", 30)) {

			println("Comment submitted.");
		}

	        driver.findElement(By.id("Reviews")).click()
	
		h.waitForTextPresent(driver, "Complete Flag", 30)

		reviewResult = tableReadRow(driver, "ReviewsTable", CommentText)

		println(reviewResult);

	} else {

		println("Request ID unavailable");

	}

}



def completeReview(driver, TPAM, RequestId, CommentText) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	if(h.waitForTextPresent(driver, RequestId, 60)) {
                                           
		driver.findElement(By.linkText(RequestId)).click()                   

		h.waitForTextPresent(driver, "Review Comment", 30)

		driver.findElement(By.id("NewReviewComment")).sendKeys(CommentText)

		if (h.waitForElementEnabled(driver, "CompleteReviewButton", 30)) {

			driver.findElement(By.id("CompleteReviewButton")).click()

			if (h.waitForTextPresent(driver, RequestId + " Saved Successfully", 30)) {

				println("Review completed.")
	
			}

        		driver.findElement(By.id("Reviews")).click()
	
			h.waitForTextPresent(driver, "Complete Flag", 30)

			reviewResult = tableReadRow(driver, "ReviewsTable", CommentText)

			println(reviewResult);

		} else {

			println("Complete Review Button Disabled.");			

		}

	} else {

		println("Request ID unavailable");

	}

}



def completeIsaReview(driver, TPAM, AccountName, CommentText) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	myString = "Pwd(" + AccountName + ")";

System.out.println(myString);

	if(h.waitForTextPresent(driver, myString, 60)) {
                                           
	String RequestId = "Request Not Found";

	WebElement myTable = driver.findElement(By.id("requestsTable"));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
	for (WebElement elRow:allRows){
					
		List<WebElement> cells = elRow.findElements(By.tagName("td"));
			
		for (WebElement elCell:cells){
	
			if (elCell.getText() == myString) {

			RequestId = cells[1].getText();

//			return RequestId;
			
				}
		
			}

                                                
		}


		System.out.println(RequestId);


		driver.findElement(By.linkText(RequestId)).click()                   

		h.waitForTextPresent(driver, "Review Comment", 30)

		driver.findElement(By.id("NewReviewComment")).sendKeys(CommentText)

		if (h.waitForElementEnabled(driver, "CompleteReviewButton", 30)) {

			driver.findElement(By.id("CompleteReviewButton")).click()

			if (h.waitForTextPresent(driver, RequestId + " Saved Successfully", 30)) {

				println("Review completed.")
	
			}

        		driver.findElement(By.id("Reviews")).click()
	
			h.waitForTextPresent(driver, "Complete Flag", 30)

			reviewResult = tableReadRow(driver, "ReviewsTable", CommentText)

			println(reviewResult);

		} else {

			println("Complete Review Button Disabled.");			

		}

	} else {

		println("Request ID unavailable");

	}

}




def checkPasswordReviewStatus(driver, TPAM, RequestId, Status) {

       	TPAMaddr = "https://" + TPAM + "/tpam/PwdReleaseReviewFilter.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "Review Status", 30)

//	new Select(driver.findElement(By.id("Status"))).selectByVisibleText(Status)

	driver.findElement(By.id("FilterRequestID")).sendKeys(RequestId)

	Thread.sleep(1000)

	driver.findElement(By.id("Listing")).click()

	h.waitForTextPresent(driver, "Request ID", 30)

	if(h.waitForTextPresent(driver, RequestId, 30)) {
                                                         
	reviewStatus = tableReadRow(driver, "requestsTable", RequestId)

		println(reviewStatus);       

	} else {

		println("Request ID unavailable");

	}

}



def checkSessionReviewStatus(driver, TPAM, RequestId, Status) {

       	TPAMaddr = "https://" + TPAM + "/tpam/SessionReviewFilter.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "Review Status", 30)

//	new Select(driver.findElement(By.id("Status"))).selectByVisibleText(Status)

	driver.findElement(By.id("FilterRequestID")).sendKeys(RequestId)

	Thread.sleep(1000)

	driver.findElement(By.id("Listing")).click()

	h.waitForTextPresent(driver, "Request ID", 30)

	if(h.waitForTextPresent(driver, RequestId, 30)) {
                                                         
	reviewStatus = tableReadRow(driver, "requestsTable", RequestId)

		println(reviewStatus);       

	} else {

		println("Request ID unavailable");

	}

}





def replayLogAndCompleteReview(driver, TPAM, RequestId, CommentText) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	if(h.waitForTextPresent(driver, RequestId, 60)) {
                                                         
	driver.findElement(By.linkText(RequestId)).click()                   

	h.waitForTextPresent(driver, "PSM Sessions for Review", 30)

	driver.findElement(By.id("NewReviewComment")).sendKeys(CommentText)

	h.waitForElementEnabled(driver, "CompleteReviewButton", 30) 

	driver.findElement(By.id("CompleteReviewButton")).click()

	if (h.waitForTextPresent(driver, RequestId + " Saved Successfully", 30)) {

		println("Log replay is not required.")
	
		println("Review completed.")

	} else if (h.waitForTextPresent(driver, "You must replay a session log for Session Request " + RequestId + " before you can complete your review", 30)){

		println("Need to replay session log.")

		driver.findElement(By.id("SessionLogs")).click()	

		h.waitForTextPresent(driver, "Start Date", 30)

		WebElement myTable = driver.findElement(By.id("SessionLogsTable"))

		List<WebElement> allRows = myTable.findElements(By.tagName("tr"))

		allRows[0].click()

		driver.findElement(By.id("ReplayButton")).click()

		Thread.sleep(80000)
		
		driver.findElement(By.id("Details")).click()
	
		h.waitForTextPresent(driver, "PSM Sessions for Review", 30)

		driver.findElement(By.id("NewReviewComment")).sendKeys(CommentText)

		h.waitForElementEnabled(driver, "CompleteReviewButton", 30) 

		driver.findElement(By.id("CompleteReviewButton")).click()

		if (h.waitForTextPresent(driver, RequestId + " Saved Successfully", 30)) {

		println("Review completed.")

			
		}

        }

        driver.findElement(By.id("Reviews")).click()
	
	h.waitForTextPresent(driver, "Complete Flag", 30)

	reviewResult = tableReadRow(driver, "ReviewsTable", CommentText)

	println(reviewResult);

//	for(String handle : driver.getWindowHandles()){  
//         	  driver.switchTo().window(handle); 
//          	 System.out.println(driver.getTitle());
//           	if (driver.getTitle().contains("Quest Software PSM Replay")) {
//           	driver.close();
//          	 System.out.println("Killing...");
//	           } 
//	        }		


	} else {

			println("Request ID unavailable");

	}



}



def replayLogAndAddBookmark(driver, TPAM, RequestId, Bookmark) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	if(h.waitForTextPresent(driver, RequestId, 60)) {
                                                         
	driver.findElement(By.linkText(RequestId)).click()                   

	h.waitForTextPresent(driver, "PSM Sessions for Review", 30)

		Thread.sleep(5000)

               	driver.findElement(By.id("SessionLogs")).click()	

		h.waitForTextPresent(driver, "Start Date", 30)

		WebElement myTable = driver.findElement(By.id("SessionLogsTable"))

		List<WebElement> allRows = myTable.findElements(By.tagName("tr"))

		allRows[0].click()

		String winHandleBefore = driver.getWindowHandle()

		driver.findElement(By.id("ReplayButton")).click()

		Thread.sleep(10000)

		//Switch to new window opened
	
		for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
		}
	
		driver.manage().window().maximize()
	
		Thread.sleep(10000)

		h.SWait("all_servers.png")

		h.SClickMultiple("pause.png")

		h.SMouseMove("controls_menu.png")

		h.SMouseDown()

		Thread.sleep(1000)

		h.SMouseMove("metadata.png")

		Thread.sleep(1000)

                h.SMouseMove("add_bookmark.png")

		h.SMouseUp()

		Thread.sleep(1000)

		h.SType(Bookmark)

		Thread.sleep(1000)

		if (h.SClickMultiple("ok_bookmark.png")) {

			println("Adding bookmark.")				

			Thread.sleep(1000)

		}
                                                            

	} else {

			println("Request ID unavailable");

	}

}


def replayLogAndManageBookmark(driver, TPAM, RequestId, Action) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	if(h.waitForTextPresent(driver, RequestId, 60)) {
                                                         
	driver.findElement(By.linkText(RequestId)).click()                   

	h.waitForTextPresent(driver, "PSM Sessions for Review", 30)

		Thread.sleep(5000)

               	driver.findElement(By.id("SessionLogs")).click()	

		h.waitForTextPresent(driver, "Start Date", 30)

		WebElement myTable = driver.findElement(By.id("SessionLogsTable"))

		List<WebElement> allRows = myTable.findElements(By.tagName("tr"))

		allRows[0].click()

		String winHandleBefore = driver.getWindowHandle()

		driver.findElement(By.id("ReplayButton")).click()

		Thread.sleep(15000)

		//Switch to new window opened
	
		for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle)
		}
	
		driver.manage().window().maximize()
	
		Thread.sleep(120000)

		h.SClickMultiple("pause.png")

		h.SMouseMove("controls_menu.png")

		h.SMouseDown()

		Thread.sleep(1000)

		h.SMouseMove("metadata.png")

		Thread.sleep(500)

              h.SMouseMove("add_bookmark.png")

		Thread.sleep(1000)

		h.SMouseMove("open_dialog.png")

		Thread.sleep(1000)

		h.SMouseUp()

		Thread.sleep(5000)

       		h.SClickMultiple("select_bookmark.png")

		Thread.sleep(1000)

		if (!h.SWait("KLBOOKMARK777.png")) {

			println("Cannot find the bookmark.")

		}

		h.SClickMultiple("KLBOOKMARK777.png")

		Thread.sleep(1000)

		if (Action == "Jump") {

			h.SClickMultiple("jump_to_bookmark.png")

			h.SClickMultiple("pause.png")

			if (h.SWait("all_servers.png")) {

				println("Jumped to bookmark.")				

			}
                                       
			Thread.sleep(5000)
                     
		} else if (Action == "Delete") {

			h.SClickMultiple("delete_bookmark.png")

			Thread.sleep(1000)						

			h.SClickMultiple("delete_bookmark_ok.png")

			Thread.sleep(5000)

			if (h.SWait("no_bookmark.png")) {

				println("Bookmark deleted.")

			}				

		} else { println("Wrong action.") }

		
	} else {

			println("Request ID unavailable");

	}

}











def tableReadRow(driver, TableName, RowContains) {

	WebElement myTable = driver.findElement(By.id(TableName));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
	Integer i = allRows.size();
	
	System.out.println i.toString();

	Integer rowFound = 0;
			
	for (WebElement elRow:allRows){
		String str = "";
					
		List<WebElement> cells = elRow.findElements(By.tagName("td"));
			
		for (WebElement elCell:cells){
	
			 str+=elCell.getText() + ",";
		
			}

//		System.out.println str;
			
			if (str.contains(RowContains)) {

			//	System.out.println str;
				return str;
				rowFound = 1;
			}
	


		}

		if (rowFound == 0){

		        return "Row not found";

				}
                   
}



                                                        
def checkReviewsCount(driver, TPAM, RequestId, Count) {

	h = new libCommon()

       	TPAMaddr = "https://" + TPAM + "/tpam/PwdReleaseReviewFilter.asp"
			
	driver.get(TPAMaddr)
					
	h.waitForTextPresent(driver, "Review Status", 30)

//	new Select(driver.findElement(By.id("Status"))).selectByVisibleText("All Requests")

	driver.findElement(By.id("FilterRequestID")).sendKeys(RequestId)

	driver.findElement(By.id("Listing")).click()

	if(h.waitForTextPresent(driver, "Request ID", 30)) {

	WebElement myTable = driver.findElement(By.id("requestsTable"));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));

	allRows[0].click()

	driver.findElement(By.id("Reviews")).click()	

	h.waitForTextPresent(driver, "Complete Flag", 30)

	rowsCount = tableRowsCount(driver, "ReviewsTable");

	println(rowsCount);
	println(Count);
                                             
		if (Count.toString() == rowsCount.toString()) {

		println("Rows count is right");

		} else {
		
		println("Rows count is wrong");

		}

		

	} else {

		println("Table unavailable");
		return 0;

	}

}



def checkIsaReviewsCount(driver, TPAM, AccountName, Count) {

	h = new libCommon()

	ReleaseId = getIsaReleaseId(driver, TPAM, AccountName)

System.out.println(ReleaseId);

       	TPAMaddr = "https://" + TPAM + "/tpam/PwdReleaseReviewFilter.asp"
			
	driver.get(TPAMaddr)
					
	h.waitForTextPresent(driver, "Review Status", 30)

//	new Select(driver.findElement(By.id("Status"))).selectByVisibleText("All Requests")

	driver.findElement(By.id("FilterISAReleaseID")).sendKeys(ReleaseId)

	driver.findElement(By.id("Listing")).click()

	if(h.waitForTextPresent(driver, "Request ID", 30)) {

	WebElement myTable = driver.findElement(By.id("requestsTable"));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));

	allRows[0].click()

	driver.findElement(By.id("Reviews")).click()	

	h.waitForTextPresent(driver, "Complete Flag", 30)

	rowsCount = tableRowsCount(driver, "ReviewsTable");

	println(rowsCount);
	println(Count);
                                             
		if (Count.toString() == rowsCount.toString()) {

		println("Rows count is right");

		} else {
		
		println("Rows count is wrong");

		}


		

	} else {

		println("Table unavailable");
		return 0;

	}




}



def tableRowsCount(driver, TableName) {

	WebElement myTable = driver.findElement(By.id(TableName));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
	Integer i = allRows.size();
	
	return i;
                   
}



def tableLoginAndReadRow(driver, TPAM, TableLink, TableName, RowContains) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 20)

	driver.findElement(By.id(TableLink)).click()

//	h.waitForTextPresent(driver, "Request ID", 10)

	Thread.sleep(5000)

	WebElement myTable = driver.findElement(By.id(TableName));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
	Integer i = allRows.size();
	
	System.out.println i.toString();

	Integer rowFound = 0;
			
	for (WebElement elRow:allRows){
		String str = "";
					
		List<WebElement> cells = elRow.findElements(By.tagName("td"));
			
		for (WebElement elCell:cells){
	
			 str+=elCell.getText() + ",";
		
			}

//		System.out.println str;
			
			if (str.contains(RowContains)) {

				System.out.println str;
				return str;
				rowFound = 1;
			}
	


		}

		if (rowFound == 0){

		        return "Row not found";

				}
                   
}




def getIsaReleaseId(driver, TPAM, AccountName) {

       	TPAMaddr = "https://" + TPAM + "/tpam/main.asp"
			
	driver.get(TPAMaddr)
					
	h = new libCommon()

	h.waitForTextPresent(driver, "The Privileged Appliance and Modules", 30)

	driver.findElement(By.id("PendingReviews")).click()

	myString = "Pwd(" + AccountName + ")";

	if(h.waitForTextPresent(driver, myString, 60)) {
                                           
	

	WebElement myTable = driver.findElement(By.id("requestsTable"));

	List<WebElement> allRows = myTable.findElements(By.tagName("tr"));
			
	for (WebElement elRow:allRows){
					
		List<WebElement> cells = elRow.findElements(By.tagName("td"));
			
		for (WebElement elCell:cells){
	
			if (elCell.getText() == myString) {

			RequestId = cells[1].getText();

			return RequestId;
			
				}
		
			}

		System.out.println RequestId;
			
	                                                
		}

	}
}