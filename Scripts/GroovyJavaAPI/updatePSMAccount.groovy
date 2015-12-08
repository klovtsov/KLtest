import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Iterator
import java.util.List

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.AccessPolicyRequest
import com.edmz.api.bo.Account
import com.edmz.api.bo.CodeMessageResult
import com.edmz.api.bo.EDMZSystem
import com.edmz.api.bo.IDResult
import com.edmz.api.bo.ListResult
import com.edmz.api.bo.Permission
import com.edmz.api.bo.PwdRequest
import com.edmz.api.bo.SSHKey
import com.edmz.api.bo.User
import com.edmz.api.filters.PermissionsFilter
import com.edmz.api.bo.UserSshKey
import com.edmz.api.bo.EGPAccount
import com.edmz.api.bo.PSMAccount


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

CliAccountName=""
CliDomainName=""
ClipboardFlag=""
CliSystemName=""
ColorDepth=""
ConnectionProfile=""
ConsoleFlag=""
DefaultSessionDuration=""
DomainAccountName=""
DssKey=""
DssKeyName=""
DssKeyType=""
EnableFlag=""
EscalationEmail=""
EscalationtTime=""
FileTransAuthMethod=""
FileTransDownFlag=""
FileTransPath=""
FileTransType=""
FileTransUpFlag=""
MaxSessionCount=""
MinApprovers=""
NotifyFrequency=""
NotifyThreshold=""
ParCLIUserName=""
PasswordMethod=""
PostSessionProfile=""
ProxyType=""
RecordingRequiredFlag=""
ReviewCount=""
ReviewerName=""
ReviewerType=""
SessionStartNotifyEmail=""

println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
 keydir = new File(cwd).getParent() + "\\keys"       
for (String s: args) {
	
	String[] parameter = s.split("=")
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

	} else if (paramName.equals("CliAccountName")) {
		CliAccountName = paramValue

	} else if (paramName.equals("CliDomainName")) {
		CliDomainName = paramValue

	} else if (paramName.equals("ClipboardFlag")) {
		ClipboardFlag = paramValue

	} else if (paramName.equals("CliSystemName")) {
		CliSystemName = paramValue

	} else if (paramName.equals("ColorDepth")) {
		ColorDepth = paramValue

	} else if (paramName.equals("ConnectionProfile")) {
		ConnectionProfile = paramValue

	} else if (paramName.equals("ConsoleFlag")) {
		ConsoleFlag = paramValue

	} else if (paramName.equals("DefaultSessionDuration")) {
		DefaultSessionDuration = paramValue

	} else if (paramName.equals("DomainAccountName")) {
		DomainAccountName = paramValue

	} else if (paramName.equals("DssKey")) {
		DssKey = paramValue

	} else if (paramName.equals("DssKeyName")) {
		DssKeyName = paramValue

	} else if (paramName.equals("DssKeyType")) {
		DssKeyType = paramValue

	} else if (paramName.equals("EnableFlag")) {
		EnableFlag = paramValue	

	} else if (paramName.equals("EscalationEmail")) {
		EscalationEmail = paramValue

	} else if (paramName.equals("EscalationtTime")) {
		EscalationtTime = paramValue	

	} else if (paramName.equals("FileTransAuthMethod")) {
		FileTransAuthMethod = paramValue

	} else if (paramName.equals("FileTransDownFlag")) {
		FileTransDownFlag = paramValue

	} else if (paramName.equals("FileTransPath")) {
		FileTransPath = paramValue

	} else if (paramName.equals("FileTransType")) {
		FileTransType = paramValue

	} else if (paramName.equals("FileTransUpFlag")) {
		FileTransUpFlag = paramValue	

	} else if (paramName.equals("MaxSessionCount")) {
		MaxSessionCount = paramValue

	} else if (paramName.equals("MinApprovers")) {
		MinApprovers = paramValue

	} else if (paramName.equals("NotifyFrequency")) {
		NotifyFrequency = paramValue

	} else if (paramName.equals("NotifyThreshold")) {
		NotifyThreshold = paramValue		

	} else if (paramName.equals("ParCLIUserName")) {
		ParCLIUserName = paramValue

	} else if (paramName.equals("PasswordMethod")) {
		PasswordMethod = paramValue

	} else if (paramName.equals("PostSessionProfile")) {
		PostSessionProfile = paramValue

	} else if (paramName.equals("ProxyType")) {
		ProxyType = paramValue

	} else if (paramName.equals("RecordingRequiredFlag")) {
		RecordingRequiredFlag = paramValue
		
	} else if (paramName.equals("ReviewCount")) {
		ReviewCount = paramValue
		
	} else if (paramName.equals("ReviewerName")) {
		ReviewerName = paramValue
		
	} else if (paramName.equals("ReviewerType")) {
		ReviewerType = paramValue
		
	} else if (paramName.equals("SessionStartNotifyEmail")) {
		SessionStartNotifyEmail = paramValue
		
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
        
	PSMAccount acct = new PSMAccount()

	if (!SystemName.equals("")) {
		acct.setSystemName(SystemName)
	}
	
	if (!AccountName.equals("")) {
		acct.setAccountName(AccountName)
	}
	
	if (!CliAccountName.equals("")) {
		acct.setCliAccountName(CliAccountName)
	}	

     if (!CliDomainName.equals("")) {
		acct.setCliDomainName(CliDomainName)
	}		
	
	if (!ClipboardFlag.equals("")) {
		acct.setClipboardFlag(ClipboardFlag)
	}	

	if (!CliSystemName.equals("")) {
		acct.setCliSystemName(CliSystemName)
	}	

	if (!ColorDepth.equals("")) {
		acct.setColorDepth(new Integer(ColorDepth))
	}	
	
	if (!ConnectionProfile.equals("")) {
		acct.setConnectionProfile(ConnectionProfile)
	}	

	if (!ConsoleFlag.equals("")) {
		acct.setConsoleFlag(ConsoleFlag)
	}
	
	if (!DefaultSessionDuration.equals("")) {
		acct.setDefaultSessionDuration(new Integer(DefaultSessionDuration))
	}
	
	if (!DomainAccountName.equals("")) {
		acct.setDomainAccountName(DomainAccountName)
	}
	
	if (!DssKey.equals("")) {
		acct.setDssKey(DssKey)
	}
	
	if (!DssKeyName.equals("")) {
		acct.setDssKeyName(DssKeyName)
	}
	
	if (!DssKeyType.equals("")) {
		acct.setDssKeyType(DssKeyType)
	}
	
	if (!EnableFlag.equals("")) {
		acct.setEnableFlag(EnableFlag)
	}
	
	if (!EscalationEmail.equals("")) {
		acct.setEscalationEmail(EscalationEmail)
	}
	
	if (!EscalationtTime.equals("")) {
		acct.setEscalationtTime(new Integer(EscalationtTime))
	}
	
	if (!FileTransAuthMethod.equals("")) {
		acct.setFileTransAuthMethod(FileTransAuthMethod)
	}
	
	if (!FileTransDownFlag.equals("")) {
		acct.setFileTransDownFlag(FileTransDownFlag)
	}
	
	if (!FileTransPath.equals("")) {
		acct.setFileTransPath(FileTransPath)
	}
	
	if (!FileTransType.equals("")) {
		acct.setFileTransType(FileTransType)
	}
	
	if (!FileTransUpFlag.equals("")) {
		acct.setFileTransUpFlag(FileTransUpFlag)
	}
	
	if (!MaxSessionCount.equals("")) {
		acct.setMaxSessionCount(new Integer(MaxSessionCount))
	}
	
	if (!MinApprovers.equals("")) {
		acct.setMinApprovers(new Integer(MinApprovers))
	}
	
	if (!NotifyFrequency.equals("")) {
		acct.setNotifyFrequency(new Integer(NotifyFrequency))
	}
	
	if (!NotifyThreshold.equals("")) {
		acct.setNotifyThreshold(new Integer(NotifyThreshold))
	}
	
	if (!ParCLIUserName.equals("")) {
		acct.setParCLIUserName(ParCLIUserName)
	}

	if (!PasswordMethod.equals("")) {
		acct.setPasswordMethod(PasswordMethod)
	}

	if (!PostSessionProfile.equals("")) {
		acct.setPostSessionProfile(PostSessionProfile)
	}

	if (!ProxyType.equals("")) {
		acct.setProxyType(ProxyType)
	}

	if (!RecordingRequiredFlag.equals("")) {
		acct.setRecordingRequiredFlag(RecordingRequiredFlag)
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
	
	if (!SessionStartNotifyEmail.equals("")) {
		acct.setSessionStartNotifyEmail(SessionStartNotifyEmail)
	}
	

    IDResult res = new IDResult()
    acl.updatePSMAccount(acct, res)
                
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
  

