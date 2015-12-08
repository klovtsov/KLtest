import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit
import java.util.Calendar
import java.text.SimpleDateFormat
import java.io.*

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXParseException

import javax.mail.*;
import javax.mail.search.*;

File currentDirectory = new File(new File("../").getAbsolutePath());
downloadDir = currentDirectory.getCanonicalPath() + "\\Ethalons\\StoredReports\\"

FirefoxProfile ffp = new FirefoxProfile();
ffp.setPreference("browser.download.folderList",2);
ffp.setPreference("browser.download.manager.showWhenStarting",false);
ffp.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,text/xml,text/html");
ffp.setPreference("browser.download.dir",downloadDir);




try {

	String TPAMaddress = args[0];
	String ReportName = args[1];
	String ReportType = args[2];


	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
	calendar.add(calendar.DAY_OF_MONTH, -1);
	if ((calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && (ReportName ==~ /^Wkly.*/)) {
		System.out.println "It's not a day for weekly reports!";
		return "It's not a day for weekly reports!";
	}
	ReportDate = sdf.format(calendar.getTime());
	System.out.println ReportDate;


	if (ReportType == "stored"){

		String fileName1 = ReportName + "_" + ReportDate + ".csv";
		String fileName2 = ReportName + "_" + ReportDate + ".htm";
		String fileName3 = ReportName + "_" + ReportDate + ".xml";
		String TPAMaddr = "https://" + TPAMaddress + "/tpam/BrowseReports.asp";
                
		driver = new FirefoxDriver(ffp);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)	
		h = new libCommon();
		h.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR");
		driver.get(TPAMaddr);
		Thread.sleep(5000);
		driver.findElement(By.linkText(ReportDate)).click();
		Thread.sleep(5000);

		for (fileName in [
			fileName1,
			fileName2,
			fileName3
		]) {

			File myFile = new File(downloadDir + fileName);
			if (myFile.exists()) {
				myFile.delete();
			}
			if (driver.findElement(By.linkText(fileName)).isDisplayed()) {
				driver.findElement(By.linkText(fileName)).click()
			}
			else {
				System.out.println fileName + " report not found!";
			}
			Thread.sleep(5000);
		}
		driver.quit();
	}
	if (ReportType == "archived"){

		driver = new FirefoxDriver(ffp);
		h = new libCommon();
		h.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR");
		Thread.sleep(5000);
		driver.quit();
	}

	if (ReportType == "emailed"){

		driver = new FirefoxDriver(ffp);
		h = new libCommon();
		h.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR");
		Thread.sleep(5000);

		mbox = "INBOX";
		protocol = "pop3";

		switch (ReportName){
			case "CollectionMembership":
				reportFullName = "Collection Membership"
				break
			case "DlyActivityLog":
				reportFullName = "Daily Activity Report"
				break
			case "DlyApproverActivity":
				reportFullName = "Daily Approver Activity"
				break
			case "DlyFailedLogins":
				reportFullName = "Daily Failed Logins Report"
				break
			case "DlyFileAging":
				reportFullName = "File Inventory and Aging Report"
				break
			case "DlyFileRelActivity":
				reportFullName = "Daily File Release Activity"
				break
			case "DlyFirewallLog":
				reportFullName = "Daily Firewall Log Report"
				break
			case "DlyGNSystemMappings":
				reportFullName = "Daily Generic System Auto Discovery Mappings"
				break
			case "DlyGNUserMappings":
				reportFullName = "Daily Generic User Auto Discovery Mappings"
				break
			case "DlyLDAPSystemMappings":
				reportFullName = "Daily LDAP/AD System Auto Discovery Mappings"
				break
			case "DlyLDAPUserMappings":
				reportFullName = "Daily LDAP/AD User Auto Discovery Mappings"
				break
			case "DlyPasswordAging":
				reportFullName = "Password Inventory and Aging Report"
				break
			case "DlyPwdTestResults":
				reportFullName = "Daily Password Test Results"
				break
			case "DlyPwdTestSummary":
				reportFullName = "Daily Password Test Summary Report"
				break
			case "DlyPwdUpdateResults":
				reportFullName = "Daily Password Change Results"
				break
			case "DlyPwdUpdateSummary":
				reportFullName = "Daily Password Change Summary Report"
				break
			case "DlyReleaseActivity":
				reportFullName = "Daily Password Release Activity"
				break
			case "DlyReviewerActivity":
				reportFullName = "Daily Reviewer Activity"
				break
			case "DlySecurityLog":
				reportFullName = "Daily Security Log Report"
				break
			case "DlySessionActivity":
				reportFullName = "Daily Session Activity"
				break
			case "DlySessionActivityDtls":
				reportFullName = "Daily Session Activity Detailed"
				break
			case "DlySysAdminActivity":
				reportFullName = "Daily Sys-Admin Activity Report"
				break
			case "GroupMembership":
				reportFullName = "Group Membership"
				break
			case "MgmtSummaryReport":
				reportFullName = "Management Summary Report"
				break
			case "PendingReleaseReviews":
				reportFullName = "Pending Password Release Reviews"
				break
			case "PendingSessionReviews":
				reportFullName = "Pending PSM Session Reviews"
				break
			case "SystemDPAAffinity":
				reportFullName = "System DPA Affinity"
				break
			case "SystemsNoAccount":
				reportFullName = "Systems with no Accounts"
				break
			case "SystemsNoCollection":
				reportFullName = "Systems not in a Collection"
				break
			case "UserEGPEntitlement":
				reportFullName = "PSM Entitlement Report"
				break
			case "UserEntitlement":
				reportFullName = "User Entitlement Report"
				break
			case "UserFileEntitlement":
				reportFullName = "User File Entitlement Report"
				break
			case "UsersNoGroup":
				reportFullName = "Users not in a Group"
				break
			case "WklyAppUsageReport":
				reportFullName = "Appliance Usage Report"
				break
			case "WklyFileRelActivity":
				reportFullName = "Weekly File Release Activity"
				break
			case "WklyReleaseActivity":
				reportFullName = "Weekly Password Release Activity"
				break
			case "WklySessionActivity":
				reportFullName = "Weekly Session Activity"
				break
			case "WklySessionActivityDtls":
				reportFullName = "Weekly Session Activity Detailed"
				break
		}

		host = args[3];
		user = args[4];
		password = args[5];
		subject = "TPAM Reports -- " + reportFullName;

		try {


			Properties props = System.getProperties();
			javax.mail.Session session = javax.mail.Session.getInstance(props, null);
			Store store = null;
			if (protocol != null){
				store = session.getStore(protocol);
			} else {
				store = session.getStore();
			}
			if (host != null || user != null || password != null) {
				store.connect(host, user, password);
			} else {
				store.connect();
			}
			Folder folder = store.getDefaultFolder();
			if (folder == null) {
				System.out.println "Cant find default namespace";
			}
			folder = folder.getFolder(mbox);
			if (folder == null) {
				System.out.println "Invalid folder";
			}

			folder.open(Folder.READ_WRITE);
			SearchTerm term = null;
			SearchTerm bodyTerm = null;

			if (subject != null)
				term = new SubjectTerm(subject);

			Message[] msgs = folder.search(term);
			System.out.println("FOUND " + msgs.length + " MESSAGES");
			if (msgs.length == 0) {
				// no match
				System.out.println "no messages";
			} else if (msgs.length >= 2) {
				System.out.println "too many messages";
			} else {
				Message m = msgs[0];

				o = m.getContent();

				if (o instanceof Multipart) {
					System.out.println("This is a Multipart");
					for (int i = 0; i < o.getCount(); i++) {
						part = o.getBodyPart(i);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
							if (part.getSize() != -1){
							part.saveFile(downloadDir + part.getFileName());
							} else {
							System.out.println "Attachment is empty";
							}
						}
					}
				} else  {
					System.out.println "This is NOT a Multipart";
				}
			}
			if (msgs.length != 0) {
				folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);
			}
			folder.close(true);
			store.close();
		} catch (Exception ex) {
			System.out.println("Oops, got exception! " + ex.getMessage());
			ex.printStackTrace();
		}

		driver.quit();
	}

	checkReport(ReportName, ReportDate, downloadDir);
}
catch (e) {
	println("Catched: something went wrong: " + e);
	driver.quit();
}




println("Finished.");


def checkReport(ReportName, ReportDate, DownloadDir) {

	String fileName1 = ReportName + "_" + ReportDate + ".csv";
	String fileName2 = ReportName + "_" + ReportDate + ".htm";
	String fileName3 = ReportName + "_" + ReportDate + ".xml";

	File myFile1 = new File(DownloadDir + fileName1);
	File myFile2 = new File(DownloadDir + fileName2);
	File myFile3 = new File(DownloadDir + fileName3);

	switch (ReportName){
		case "CollectionMembership":
			headerString = "CollectionName,CollectionDsc,MemberName"
			break
		case "DlyActivityLog":
			headerString = "LogTime,UserName,RoleUsed,ObjectType,Operation,Failed,Target,Details"
			break
		case "DlyApproverActivity":
			headerString = "RequestType,RequestID,SystemName,AccountName,FileName,TicketSystem,TicketNumber,TicketValidatedDt,RequestorName,ApproverName,SubmittedDt,ReqReleaseDt,ExpiresDt,CloseDt,CanceledDt,ReleaseDuration,Status,ResponseID,ResponseCd,ResponseDt,ResponseComment"
		break
		case "DlyFailedLogins":
			headerString = "LogTime,UserName,IPAddress,Status"
			break
		case "DlyFileAging":
			headerString = "Age,System,Filename,LastChange"
			break
		case "DlyFileRelActivity":
			headerString = "RetrievedDt,Filename,SystemName,RequestID,Requestor,Approver,Reason,ApprComment,ApprTime,TSName,TicketNbr,ReasonCodeText,PaddedRequestID"
			break
		case "DlyFirewallLog":
			headerString = "MsgDateTime,Source,MsgText"
			break
		case "DlyGNSystemMappings":
			headerString = "MappingID,SystemName,SQLStatement,AutoUpdFreqDHM,NotifyEmail,NotificationType,ComputernameExistsNoMapping,ComputernameExistsMappingExists,ComputernameRemovedFromSource,PARCollectionName,IgnoreUpdateColumns,LastCheckDt,TemplateSystemName,PlatformName,PwdRuleName,MaxReleaseDurationDHM,PrimaryEmail,SystemAutoFl,EGPOnlyFl,FuncAcct,FuncAcctAuthType,Timeout,AllowFuncReqFl,PasswordCheckProfile,PasswordCheckSchedule,PasswordChangeProfile,PasswordChangeSchedule,PostReqResetDHM,MappedColumns"
			break
		case "DlyGNUserMappings":
			headerString = "MappingID,SystemName,SQLStatement,AutoUpdFreqDHM,NotifyEmail,NotificationType,ConflictStrategy,UsernameExistsNoMapping,UsernameExistsMappingExists,UsernameRemovedFromSource,PARGroupName,IgnoreUpdateColumns,LastCheckDt,TemplateUserName,UserType,SysAdminFl,TimeBasedAccess,PrimaryAuthentication,PrimAuthSource,PrimAuthID,ExternalAuthentication,ExtAuthSource,MappedColumns"
			break
		case "DlyLDAPSystemMappings":
			headerString = "MappingID,SystemName,DistinguishedName,FilterString,AutoUpdFreqDHM,NotifyEmail,NotificationType,ComputernameExistsNoMapping,ComputernameExistsMappingExists,ComputernameRemovedFromSource,PARCollectionName,IgnoreUpdateColumns,LastCheckDt,TemplateSystemName,PlatformName,PwdRuleName,MaxReleaseDurationDHM,PrimaryEmail,SystemAutoFl,EGPOnlyFl,FuncAcct,FuncAcctAuthType,Timeout,AllowFuncReqFl,PasswordCheckProfile,PasswordCheckSchedule,PasswordChangeProfile,PasswordChangeSchedule,PostReqResetDHM"
			break
		case "DlyLDAPUserMappings":
			headerString = "MappingID,SystemName,DistinguishedName,FilterString,AutoUpdFreqDHM,NotifyEmail,NotificationType,ConflictStrategy,UsernameExistsNoMapping,UsernameExistsMappingExists,UsernameRemovedFromSource,PARGroupName,IgnoreUpdateColumns,LastCheckDt,TemplateUserName,UserType,SysAdminFl,TimeBasedAccess,PrimaryAuthentication,PrimAuthSource,PrimAuthID,ExternalAuthentication,ExtAuthSource"
			break
		case "DlyPasswordAging":
			headerString = "Age,System,Account,LastChange,AcctAutoFl,PrimaryEmail"
			break
		case "DlyPwdTestResults":
			headerString = "System,Account,TestDt,Results"
			break
		case "DlyPwdTestSummary":
			headerString = "System,Account,TestDt,Results,TotalTestsPerformed,TotalFailedTests,TotalSuccessTests"
			break
		case "DlyPwdUpdateResults":
			headerString = "System,Account,ChangeDt,Reason,Results,Comment"
			break
		case "DlyPwdUpdateSummary":
			headerString = "System,Account,ChangeDt,Reason,Results,TotalUpdatesAttempted,TotalFailedUpdates,TotalSuccessUpdates"
			break
		case "DlyReleaseActivity":
			headerString = "RetrievedDt,AccountName,SystemName,RequestID,Requestor,Approver,Reason,ApprComment,ApprTime,TSName,TicketNbr,EarlyExpireReason,ChangeDt,ChangeStatus,ProxiedFor,EarlyExpireDt,ReasonCodeText,RequestorUserName,ApproverUserName,PlatformName,ApplianceIdentity,paddedRequestID,ISAReleaseID"
			break
		case "DlyReviewerActivity":
			headerString = "ReviewType,RequestID,SystemName,AccountName,TicketSystem,TicketNumber,TicketValidatedDt,RequestorName,ReviewerName,SubmittedDt,ReleaseDt,ExpiresDt,CloseDt,CanceledDt,ReleaseDuration,Status,SessionLogID,CompleteFl,ReviewDt,SessionLogStartDt,SessionLogEndDt,ReviewComment"
			break
		case "DlySecurityLog":
			headerString = "LogDate,LogTime,Type,Cat,ID,UserName,Details"
			break
		case "DlySessionActivity":
			headerString = "StartDt,AccountName,SystemName,RequestID,Requestor,Approver,Reason,ApprComment,ApprTime,TSName,TicketNbr,EarlyExpireReason,EarlyExpireDt,FileSize,EndDt,FileTransfers,PCMCommand,PasswordReleasedFl,ReasonCodeText,PaddedRequestID"
			break
		case "DlySessionActivityDtls":
			headerString = "RequestID,SessionLogID,RowType,AccountName,PlatformName,Requestor,Reason,TSName,TicketNbr,EarlyExpireReason,EarlyExpireDt,PasswordReleasedFL,ReasonCodeText,ApprovedDt,SubmittedDt,ReqReleaseDt,ReleaseDuration,ExpiresDt,FirstRetrieveDt,AccessPolicyName,PwdApproversReqd,SessApproversReqd,PwdRequireMGApprFl,SessRequireMGApprFl,PaddedRequestID,ApplianceIdentity,Approver,ApprComment,ApprTime,SessionLogStartDt,SessionLogEndDt,LogFileSize,FileTransfers,PCMCommand,WindowSize,EventType,EventDetail,OffsetTime,OffsetDateTime,FileName,TransferTransferFileSize,DestPath,Status,Results,TransferStartDt,TransferEndDt,Direction"
			break
		case "DlySysAdminActivity":
			headerString = "LogTime,AdminName,Operation,ObjectType,ObjectName"
			break
		case "GroupMembership":
			headerString = "GroupName,GroupDsc,UserName,LastName,FirstName"
			break
		case "MgmtSummaryReport":
			headerString = "Message,Value,Units"
			break
		case "PendingReleaseReviews":
			headerString = "RequestID,ISAReleaseID,UserName,UserFullName,SystemName,AccountName,Reason,ReqReleaseDt,ExpiresDt,Duration,MinRetrievedDt,MaxRetrievedDt,Releases,ReviewCnt,ReviewerDesc,TicketSystem,TicketNbr,ReasonCodeText"
			break
		case "PendingSessionReviews":
			headerString = "RequestID,UserName,UserFullName,SystemName,AccountName,Reason,ReqReleaseDt,ExpiresDt,Duration,MinStartDt,MaxEndDt,Sessions,ReviewCnt,ReviewerDesc,TicketSystem,TicketNbr,ReasonCodeText"
			break
		case "SystemDPAAffinity":
			headerString = "SystemName,NetworkAddress,CollectionName,SSName,Priority,AffinityType"
			break
		case "SystemsNoAccount":
			headerString = "SystemName,PlatformName,NetworkAddress,PrimaryEmail"
			break
		case "SystemsNoCollection":
			headerString = "SystemName,PlatformName,NetworkAddress,PrimaryEmail"
			break
		case "UserEGPEntitlement":
			headerString = "UserName,FullName,SecAuthReqd,LastAccessDt,GroupName,SystemName,AccountName,FileName,CollectionName,PolicyName,PolicySource,CmdName"
			break
		case "UserEntitlement":
			headerString = "UserName,FullName,SecAuthReqd,LastAccessDt,GroupName,SystemName,AccountName,FileName,CollectionName,PolicyName,PolicySource,CmdName"
			break
		case "UserFileEntitlement":
			headerString = "UserName,FullName,SecAuthReqd,LastAccessDt,GroupName,SystemName,AccountName,FileName,CollectionName,PolicyName,PolicySource,CmdName"
			break
		case "UsersNoGroup":
			headerString = "UserName,LastName,FirstName"
			break
		case "WklyAppUsageReport":
			headerString = "Message,Value,Units"
			break
		case "WklyFileRelActivity":
			headerString = "RetrievedDt,Filename,SystemName,RequestID,Requestor,Approver,Reason,ApprComment,ApprTime,TSName,TicketNbr,ReasonCodeText,PaddedRequestID"
			break
		case "WklyReleaseActivity":
			headerString = "RetrievedDt,AccountName,SystemName,RequestID,Requestor,Approver,Reason,ApprComment,ApprTime,TSName,TicketNbr,EarlyExpireReason,ChangeDt,ChangeStatus,ProxiedFor,EarlyExpireDt,ReasonCodeText,RequestorUserName,ApproverUserName,PlatformName,ApplianceIdentity,PaddedRequestID"
			break
		case "WklySessionActivity":
			headerString = "StartDt,AccountName,SystemName,RequestID,Requestor,Approver,Reason,ApprComment,ApprTime,TSName,TicketNbr,EarlyExpireReason,EarlyExpireDt,FileSize,EndDt,FileTransfers,PCMCommand,PasswordReleasedFl,ReasonCodeText,PaddedRequestID"
			break
		case "WklySessionActivityDtls":
			headerString = "RequestID,SessionLogID,RowType,AccountName,PlatformName,Requestor,Reason,TSName,TicketNbr,EarlyExpireReason,EarlyExpireDt,PasswordReleasedFL,ReasonCodeText,ApprovedDt,SubmittedDt,ReqReleaseDt,ReleaseDuration,ExpiresDt,FirstRetrieveDt,AccessPolicyName,PwdApproversReqd,SessApproversReqd,PwdRequireMGApprFl,SessRequireMGApprFl,PaddedRequestID,ApplianceIdentity,Approver,ApprComment,ApprTime,SessionLogStartDt,SessionLogEndDt,LogFileSize,FileTransfers,PCMCommand,WindowSize,EventType,EventDetail,OffsetTime,OffsetDateTime,FileName,TransferTransferFileSize,DestPath,Status,Results,TransferStartDt,TransferEndDt,Direction"
			break
	}

	if (myFile1.exists()) {
		input = new DataInputStream(new FileInputStream(myFile1));
		reader = new BufferedReader(new InputStreamReader(input))
		String firstLine = reader.readLine();
		//		System.out.println firstLine;
		if (firstLine == headerString) {
			println "CSV looks valid"
		}
		reader.close();
		myFile1.delete();
	}

	if (myFile2.exists()) {
		input = new DataInputStream(new FileInputStream(myFile2));
		reader = new BufferedReader(new InputStreamReader(input))
		String firstLine = reader.readLine();
		System.out.println firstLine;
		if (firstLine == '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">') {
			println "HTM looks valid"
		}
		reader.close();
		myFile2.delete();
	}

	if (myFile3.exists()) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.parse(myFile3);
			println "XML looks valid";
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myFile3.delete();
	}
}
