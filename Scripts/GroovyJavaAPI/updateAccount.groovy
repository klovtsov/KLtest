import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import com.edmz.api.bo.IDResult
import java.util.Calendar
import java.text.DateFormat
import java.text.SimpleDateFormat


import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.AccessPolicyRequest
import com.edmz.api.bo.Account

import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

AutoFlag=""                // N=None,Y=Automatic,M=Manual
AccountDn=""
BlockAutoChangeFlag=""     // Y/N
ChangeFrequency=""         //  -2 and 360
ChangeTime=""                 // 24-hour format
CheckFlag=""                  // Y/N
ChangeServiceFlag=""          // Y/N
ChangeTaskFlag=""
ChgSvcFl=""
Custom1=""
Custom2=""
Custom3=""
Custom4=""
Custom5=""
Custom6=""
Description=""
EnableBeforeReleaseFlag="" 
EscalationEmail=""
EscalationTime=""           // (zero) to disable
IgnoreSystemPolicies=""
LockFlag=""                 // Y/N
MaxReleaseDuration=""
MinimumApprovers=""
MultiGroupApproverNames=""
NextChangeDate=""
OverrideAccountability=""
Password=""
PasswordCheckProfile=""
PasswordChangeProfile=""
PasswordRule=""
ReleaseNotifyEmail=""
ReleaseChangeFlag=""
ReleaseDuration=""
RequireMultiGroupApprovalFlag=""
RequireTicketForAPI=""
RequireTicketForCLI=""
RequireTicketForISA=""
RequireTicketForPSM=""
RequireTicketForRequest=""
ResetFlag=""
RestartServiceFlag=""
ReviewCount=""              // 0-n
ReviewerName=""
ReviewerType=""             // Valid values are: Any (default), Auditor, User, Group
SimulPrivAccReleases=""      // 0-99
TicketSystemName=""          // "!Any" to allow tickets from any valid ticket system
TicketEmailNotify=""
UseSelfFlag=""

disableChangeScheduleFlag=""


println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"          
for (String s: args) {
	
	String[] parameter = s.split("=", 2)
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
	System.out.println(paramName + "=" + paramValue)
	
	if (paramName.equals("authFile")){
		authFile = keydir + "\\" + paramValue
		
	} else if (paramName.equals("apiUser")) {
		apiUser = paramValue
		
	} else if (paramName.equals("TPAM")) {
		TPAM = paramValue
		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue
		
	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue		
		
	} else if (paramName.equals("AutoFlag")) {
		AutoFlag = paramValue	
		
	} else if (paramName.equals("AccountDn")) {
		AccountDn = paramValue	
		
	} else if (paramName.equals("BlockAutoChangeFlag")) {
		BlockAutoChangeFlag = paramValue	
	
	} else if (paramName.equals("ChangeFrequency")) {
		ChangeFrequency = paramValue	

	} else if (paramName.equals("ChangeTime")) {
		ChangeTime = paramValue
	
	} else if (paramName.equals("CheckFlag")) {
		CheckFlag = paramValue
		
	} else if (paramName.equals("ChangeServiceFlag")) {
		ChangeServiceFlag = paramValue
		
	} else if (paramName.equals("ChangeTaskFlag")) {
		ChangeTaskFlag = paramValue
		
	} else if (paramName.equals("ChgSvcFl")) {
		ChgSvcFl = paramValue
	
	} else if (paramName.equals("Custom1")) {
		Custom1 = paramValue
	
	} else if (paramName.equals("Custom2")) {
		Custom2 = paramValue
	
	} else if (paramName.equals("Custom3")) {
		Custom3 = paramValue
	
	} else if (paramName.equals("Custom4")) {
		Custom4 = paramValue
	
	} else if (paramName.equals("Custom5")) {
		Custom5 = paramValue
	
	} else if (paramName.equals("Custom6")) {
		Custom6 = paramValue		

	} else if (paramName.equals("Description")) {
		Description = paramValue
		
	} else if (paramName.equals("EnableBeforeReleaseFlag")) {
		EnableBeforeReleaseFlag = paramValue
		
	} else if (paramName.equals("EscalationEmail")) {
		EscalationEmail = paramValue
		
	} else if (paramName.equals("EscalationTime")) {
		EscalationTime = paramValue
		
	} else if (paramName.equals("IgnoreSystemPolicies")) {
		IgnoreSystemPolicies = paramValue		
		
	} else if (paramName.equals("LockFlag")) {
		LockFlag = paramValue
		
	} else if (paramName.equals("MaxReleaseDuration")) {
		MaxReleaseDuration = paramValue	
		
	} else if (paramName.equals("MinimumApprovers")) {
		MinimumApprovers = paramValue	
		
	} else if (paramName.equals("MultiGroupApproverNames")) {
		MultiGroupApproverNames = paramValue
		
	} else if (paramName.equals("NextChangeDate")) {
		NextChangeDate = paramValue

	} else if (paramName.equals("OverrideAccountability")) {
		OverrideAccountability = paramValue

	} else if (paramName.equals("Password")) {
		Password = paramValue
	
	} else if (paramName.equals("PasswordCheckProfile")) {
		PasswordCheckProfile = paramValue

	} else if (paramName.equals("PasswordChangeProfile")) {
		PasswordChangeProfile = paramValue

	} else if (paramName.equals("PasswordRule")) {
		PasswordRule = paramValue
		
	} else if (paramName.equals("ReleaseNotifyEmail")) {
		ReleaseNotifyEmail = paramValue
		
	} else if (paramName.equals("ReleaseChangeFlag")) {
		ReleaseChangeFlag = paramValue
		
	} else if (paramName.equals("ReleaseDuration")) {
		ReleaseDuration = paramValue
		
	} else if (paramName.equals("RequireMultiGroupApprovalFlag")) {
		RequireMultiGroupApprovalFlag = paramValue
		
	} else if (paramName.equals("RequireTicketForAPI")) {
		RequireTicketForAPI = paramValue
		
	} else if (paramName.equals("RequireTicketForCLI")) {
		RequireTicketForCLI = paramValue
		
	} else if (paramName.equals("RequireTicketForISA")) {
		RequireTicketForISA = paramValue
		
	} else if (paramName.equals("RequireTicketForPSM")) {
		RequireTicketForPSM = paramValue
		
	} else if (paramName.equals("RequireTicketForRequest")) {
		RequireTicketForRequest = paramValue
		
	} else if (paramName.equals("ResetFlag")) {
		ResetFlag = paramValue
		
	} else if (paramName.equals("RestartServiceFlag")) {
		RestartServiceFlag = paramValue
		
	} else if (paramName.equals("ReviewCount")) {
		ReviewCount = paramValue
		
	} else if (paramName.equals("ReviewerName")) {
		ReviewerName = paramValue
		
	} else if (paramName.equals("ReviewerType")) {
		ReviewerType = paramValue
		
	} else if (paramName.equals("SimulPrivAccReleases")) {
		SimulPrivAccReleases = paramValue
		
	} else if (paramName.equals("TicketSystemName")) {
		TicketSystemName = paramValue
		
	} else if (paramName.equals("TicketEmailNotify")) {
		TicketEmailNotify = paramValue		
	
	} else if (paramName.equals("UseSelfFlag")) {
		UseSelfFlag = paramValue	
		
	} else if (paramName.equals("disableChangeScheduleFlag")) {
		disableChangeScheduleFlag = paramValue
	
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
        
	Account acct = new Account()

	if (!SystemName.equals("")) {
		acct.setSystem(SystemName)
	}
	
	if (!AccountName.equals("")) {
		acct.setAccount(AccountName)
	}
	
	if (!AutoFlag.equals("")) {
		acct.setAccountAutoFl(AutoFlag)
	}
	
	if (!AccountDn.equals("")) {
		acct.setAccountDn(AccountDn)
	}
	
	if (!BlockAutoChangeFlag.equals("")) {
		acct.setBlockAutoChangeFlag(BlockAutoChangeFlag)
	}
	
	if (!ChangeFrequency.equals("")) {
		acct.setChangeFreq(new Integer(ChangeFrequency))
	}
	
	if (!ChangeTime.equals("")) {
	
		DateFormat df = new SimpleDateFormat("HH:mm")
        Calendar scal  = Calendar.getInstance()
        scal.setTime(df.parse(ChangeTime))
		acct.setChangeTime(scal)
	}
	
	if (!CheckFlag.equals("")) {
		acct.setCheckFl(CheckFlag)
	}
	
	if (!ChangeServiceFlag.equals("")) {
		acct.setChangeServiceFlag(ChangeServiceFlag)
	}
	
	if (!ChangeTaskFlag.equals("")) {
		acct.setChangeTaskFlag(ChangeTaskFlag)
	}
	
	if (!ChangeServiceFlag.equals("")) {
		acct.setChgSvcFl(ChangeServiceFlag)
	}

	if (!Custom1.equals("")) {
		acct.setCustom1(Custom1)
	}
	
	if (!Custom2.equals("")) {
		acct.setCustom2(Custom2)
	}
	
	if (!Custom3.equals("")) {
		acct.setCustom3(Custom3)
	}
	
	if (!Custom4.equals("")) {
		acct.setCustom4(Custom4)
	}
	
	if (!Custom5.equals("")) {
		acct.setCustom5(Custom5)
	}
	
	if (!Custom6.equals("")) {
		acct.setCustom6(Custom6)
	}
	
	if (!Description.equals("")) {
		acct.setDescription(Description)
	}
	
	if (!EnableBeforeReleaseFlag.equals("")) {
		acct.setEnableBeforeReleaseFlag(EnableBeforeReleaseFlag)
	}
	
	if (!EscalationEmail.equals("")) {
		acct.setEscalationEmail(EscalationEmail)
	}
	
	if (!EscalationTime.equals("")) {
		acct.setEscalationTime(new Integer(EscalationTime))
	}

	if (!IgnoreSystemPolicies.equals("")) {
		acct.setIgnoreSystemPolicies(IgnoreSystemPolicies)
	}
	
	if (!LockFlag.equals("")) {
		acct.setLockFlag(LockFlag)
	}

	if (!MaxReleaseDuration.equals("")) {
		acct.setMaxReleaseDuration(new Integer(MaxReleaseDuration))
	}
	
	if (!MinimumApprovers.equals("")) {
		acct.setMinApprovers(new Integer(MinimumApprovers))
	}
	
	if (!MultiGroupApproverNames.equals("")) {
		acct.setMultiGroupApproverNames(MultiGroupApproverNames)
	}
	
	if (!NextChangeDate.equals("")) {
	
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        Calendar scal  = Calendar.getInstance()
        scal.setTime(df.parse(NextChangeDate))
		acct.setNextChangeDt(scal)
	}

	if (!OverrideAccountability.equals("")) {
		acct.setOverrideAccountability(OverrideAccountability)
	}
	
	if (!Password.equals("")) {
		acct.setPassword(Password)
	}

	if (!PasswordCheckProfile.equals("")) {
		acct.setPasswordCheckProfile(PasswordCheckProfile)
	}

	if (!PasswordChangeProfile.equals("")) {
		acct.setPasswordChangeProfile(PasswordChangeProfile)
	}

	if (!PasswordRule.equals("")) {
		acct.setPasswordRule(PasswordRule)
	}
	
	if (!ReleaseChangeFlag.equals("")) {
		acct.setReleaseChgFl(ReleaseChangeFlag)
	}
	
	if (!ReleaseDuration.equals("")) {
		acct.setReleaseDuration( new Integer(ReleaseDuration))
	}
	
	if (!ReleaseNotifyEmail.equals("")) {
		acct.setRelNotifyEmail(ReleaseNotifyEmail)
	}

	if (!RequireMultiGroupApprovalFlag.equals("")) {
		acct.setRequireMultiGroupApprovalFlag(RequireMultiGroupApprovalFlag)
	}
	
	if (!RequireTicketForAPI.equals("")) {
		acct.setRequireTicketForAPI(RequireTicketForAPI)
	}
	
	if (!RequireTicketForCLI.equals("")) {
		acct.setRequireTicketForCLI(RequireTicketForCLI)
	}
	
	if (!RequireTicketForISA.equals("")) {
		acct.setRequireTicketForISA(RequireTicketForISA)
	}
	
	if (!RequireTicketForPSM.equals("")) {
		acct.setRequireTicketForPSM(RequireTicketForPSM)
	}
	
    if (!RequireTicketForRequest.equals("")) {
		acct.setRequireTicketForRequest(RequireTicketForRequest)
	}
	
	if (!ResetFlag.equals("")) {
		acct.setResetFl(ResetFlag)
	}
	
	if (!RestartServiceFlag.equals("")) {
		acct.setRestartServiceFlag(RestartServiceFlag)
	}
	
	if (!ReviewCount.equals("")) {
		acct.setReviewCount(new Integer(ReviewCount))
	}
	
	if (!ReviewerName.equals("")) {
		acct.setReviewerName(ReviewerName)
	}	
	
	if (!ReviewerType.equals("")) {
		acct.setReviewerType(ReviewerType)
	}
	
	if (!SimulPrivAccReleases.equals("")) {
		acct.setSimulPrivAccReleases(SimulPrivAccReleases)
	}

	if (!SystemName.equals("")) {
		acct.setSystem(SystemName)
	}
	
	if (!TicketSystemName.equals("")) {
		acct.setTicketSystemName(TicketSystemName)
	}
	
	if (!TicketEmailNotify.equals("")) {
		acct.setTicketEmailNotify(TicketEmailNotify)
	}
	
	if (!UseSelfFlag.equals("")) {
		acct.setUseSelfFl(UseSelfFlag)
	}
	
//	disableChangeScheduleFlag
	if (!disableChangeScheduleFlag.equals("")) {
		acct.setDisableChangeScheduleFlag(disableChangeScheduleFlag)
	}
	
    IDResult res = new IDResult()
    acl.updateAccount(acct, res)
                
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)

} catch (IOException e) {

println(e)

} catch (InvalidStateException e) {

println(e)

} catch (InterruptedException e) {

println(e)

} finally {
	ac.disconnect()

}
  

