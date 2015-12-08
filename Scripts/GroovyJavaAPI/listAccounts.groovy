import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.Iterator
import java.util.List



import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.Account
import com.edmz.api.bo.ListResult
import com.edmz.api.filters.AccountFilter
import com.sshtools.j2ssh.session.SessionChannelClient


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

SystemName = ""
CollectionName = ""
DualControlRequiredFlag = "All"
NetworkAddress = ""
AcctAutoFl = ""
Platform = ""
SysAutoFl = ""
AccountName = ""

AccountCustom1 = ""
AccountCustom2 = ""
AccountCustom3 = ""
AccountCustom4 = ""
AccountCustom5 = ""
AccountCustom6 = ""

SystemCustom1 = ""
SystemCustom2 = ""
SystemCustom3 = ""
SystemCustom4 = ""
SystemCustom5 = ""
SystemCustom6 = ""

PasswordChangeProfile = ""
PasswordCheckProfile = ""

DisabledSchedules = ""

println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
 keydir = new File(cwd).getParent() + "\\keys"      
for (String s: args) {
	
	String[] parameter = s.split("=")
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
//	System.out.println(paramName + "=" + paramValue)
	
	if (paramName.equals("authFile")){
		authFile = keydir + "\\" + paramValue
		
	} else if (paramName.equals("apiUser")) {
		apiUser = paramValue
		
	} else if (paramName.equals("TPAM")) {
		TPAM = paramValue
		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue
		
	} else if (paramName.equals("CollectionName")) {
		CollectionName = paramValue	

	} else if (paramName.equals("DualControlRequiredFlag")) {
		DualControlRequiredFlag = paramValue		
		
	} else if (paramName.equals("NetworkAddress")) {
		NetworkAddress = paramValue	
		
	} else if (paramName.equals("AcctAutoFl")) {
		AcctAutoFl = paramValue	
		
	} else if (paramName.equals("Platform")) {
		Platform = paramValue	
		
	} else if (paramName.equals("SysAutoFl")) {
		SysAutoFl = paramValue	
		
	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue	
		
	} else if (paramName.equals("SystemCustom1")) {
		SystemCustom1 = paramValue	
				
	} else if (paramName.equals("SystemCustom2")) {
		SystemCustom2 = paramValue	
				
	} else if (paramName.equals("SystemCustom3")) {
		SystemCustom3 = paramValue	
				
	} else if (paramName.equals("SystemCustom4")) {
		SystemCustom4 = paramValue	
				
	} else if (paramName.equals("SystemCustom5")) {
		SystemCustom5 = paramValue	
				
	} else if (paramName.equals("SystemCustom6")) {
		SystemCustom6 = paramValue	
		
	} else if (paramName.equals("AccountCustom1")) {
		AccountCustom1 = paramValue	
				
	} else if (paramName.equals("AccountCustom2")) {
		AccountCustom2 = paramValue	
				
	} else if (paramName.equals("AccountCustom3")) {
		AccountCustom3 = paramValue	
				
	} else if (paramName.equals("AccountCustom4")) {
		AccountCustom4 = paramValue	
				
	} else if (paramName.equals("AccountCustom5")) {
		AccountCustom5 = paramValue	
				
	} else if (paramName.equals("AccountCustom6")) {
		AccountCustom6 = paramValue	
	
	} else if (paramName.equals("DisabledSchedules")) {
		DisabledSchedules = paramValue	
	
	} else {
		println("Unknown option: " + paramName + "=" + paramValue)
	}
}

APIClient ac = new APIClient()
        
try {
	ac.connect(TPAM)
	ac.authenticate(authFile, apiUser)
}

catch (e) {
	println("Something wrong with connection to TPAM: " + e)
} 

try {

	SessionChannelClient scc = ac.createSessionChannel()
	APICommandLib acl = new APICommandLib(scc)
 
	if (!SystemName.equals("")) {
		println("SystemName used!")
	}
	
	if (!CollectionName.equals("")) {
		println("CollectionName used!")
	}

    AccountFilter filter = new AccountFilter()
    filter.setSystemName(SystemName)
	filter.setCollectionName(CollectionName)
	filter.setDualControlRequiredFlag(DualControlRequiredFlag)
	filter.setNetworkAddress(NetworkAddress)
	filter.setAcctAutoFl(AcctAutoFl)
	
	filter.setAccountName(AccountName)
	filter.setSysAutoFl(SysAutoFl)
	filter.setPlatform(Platform)
	
	filter.setAccountCustom1(AccountCustom1)
	filter.setAccountCustom2(AccountCustom2)
	filter.setAccountCustom3(AccountCustom3)
	filter.setAccountCustom4(AccountCustom4)
	filter.setAccountCustom5(AccountCustom5)
	filter.setAccountCustom6(AccountCustom6)
	
	filter.setSystemCustom1(SystemCustom1)
	filter.setSystemCustom2(SystemCustom2)
	filter.setSystemCustom3(SystemCustom3)
	filter.setSystemCustom4(SystemCustom4)
	filter.setSystemCustom5(SystemCustom5)
	filter.setSystemCustom6(SystemCustom6)
	
	filter.setDisabledSchedules(DisabledSchedules)
		
    ListResult res = new ListResult()
    List accounts = new ArrayList()
    acl.listAccounts(filter, res, accounts)
	
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
	
    Iterator iter = accounts.iterator();
            while (iter.hasNext()) {
                Account element = (Account) iter.next();
	System.out.println(" System Name: " + element.getSystem())
	System.out.println(" Account Name: " + element.getAccount())
	System.out.println(" AccountAutoFl: " + element.getAccountAutoFl())
	System.out.println(" AllowISADurationFlag: " + element.getAllowISADurationFlag())
	System.out.println(" BlockAutoChangeFlag: " + element.getBlockAutoChangeFlag())
	System.out.println(" ChangeFrequency: " + element.getChangeFreq())
	System.out.println(" ChangeServiceFlag: " + element.getChgSvcFl())
	System.out.println(" ChangeTaskFlag: " + element.getChangeTaskFlag())
	System.out.println(" ChangeTime: " + element.getChangeTimeAsString())
	System.out.println(" CheckFl: " + element.getCheckFl())
	System.out.println(" ChgSvcFl: " + element.getChgSvcFl())
	System.out.println(" Custom1: " + element.getCustom1())
	System.out.println(" Description: " + element.getDescription())
	System.out.println(" EnableBeforeReleaseFlag: " + element.getEnableBeforeReleaseFlag())
	System.out.println(" EscalationEmail: " + element.getEscalationEmail())
	System.out.println(" EscalationTime: " + element.getEscalationTime())
	System.out.println(" IgnoreSystemPolicies: " + element.getIgnoreSystemPolicies())
	System.out.println(" LockFlag: " + element.getLockFlag())	
	System.out.println(" MaxReleaseDuration: " + element.getMaxReleaseDuration())
	System.out.println(" MinApprovers: " + element.getMinApprovers())
	System.out.println(" NextChangeDtAsString: " + element.getNextChangeDtAsString())
	System.out.println(" PasswordCheckProfile: " + element.getPasswordCheckProfile())
	System.out.println(" PasswordChangeProfile: " + element.getPasswordChangeProfile())
	System.out.println(" PasswordRule: " + element.getPasswordRule())
	System.out.println(" ReleaseChgFl: " + element.getReleaseChgFl())
	System.out.println(" ReleaseDuration: " + element.getReleaseDuration())
	System.out.println(" RelNotifyEmail: " + element.getRelNotifyEmail())
	System.out.println(" RequireTicketForAPI: " + element.getRequireTicketForAPI())
	System.out.println(" RequireTicketForCLI: " + element.getRequireTicketForCLI())
	System.out.println(" RequireTicketForISA: " + element.getRequireTicketForISA())
	System.out.println(" RequireTicketForRequest: " + element.getRequireTicketForRequest())
	System.out.println(" ResetFl: " + element.getResetFl())
	System.out.println(" RestartServiceFlag: " + element.getRestartServiceFlag())
	System.out.println(" ReviewCount: " + element.getReviewCount())
	System.out.println(" ReviewerName: " + element.getReviewerName())
	System.out.println(" ReviewerType: " + element.getReviewerType())
	System.out.println(" SimulPrivAccReleases: " + element.getSimulPrivAccReleases())
	System.out.println(" TicketEmailNotify: " + element.getTicketEmailNotify())
	System.out.println(" TicketSystemName: " + element.getTicketSystemName())
	System.out.println(" UseSelfFl: " + element.getUseSelfFl())
	
	System.out.println(" AccountCustom1: " + element.getCustom1())
	System.out.println(" AccountCustom2: " + element.getCustom2())
	System.out.println(" AccountCustom3: " + element.getCustom3())
	System.out.println(" AccountCustom4: " + element.getCustom4())
	System.out.println(" AccountCustom5: " + element.getCustom5())
	System.out.println(" AccountCustom6: " + element.getCustom6())
	
	System.out.println(" RequireTicketForPSM: " + element.getRequireTicketForPSM())
	
	System.out.println(" AccountDN: " + element.getAccountDn())
	
	System.out.println(" DisableChangeScheduleFl: " + element.getDisableChangeScheduleFlag())
	System.out.println(" DisableCheckScheduleFl: " + element.getDisableCheckScheduleFlag())

	
}

} catch (IOException e) {

	println(e)

} catch (InvalidStateException e) {

	println(e)

} catch (InterruptedException e) {

	println(e)

} finally {
	ac.disconnect()

}
  

