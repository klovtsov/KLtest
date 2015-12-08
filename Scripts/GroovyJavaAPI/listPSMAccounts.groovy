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
import com.edmz.api.filters.SystemFilter
import com.edmz.api.filters.UserFilter
import com.edmz.api.bo.IDResult
import com.edmz.api.bo.ListResult
import com.edmz.api.bo.Permission
import com.edmz.api.bo.PwdRequest
import com.edmz.api.bo.SSHKey
import com.edmz.api.bo.User
import com.edmz.api.filters.PermissionsFilter
import com.edmz.api.bo.UserSshKey
import com.edmz.api.bo.EGPAccount
import com.edmz.api.bo.Collection
import com.edmz.api.bo.CollectionMembership
import com.edmz.api.bo.Group
import com.edmz.api.bo.GroupMembership
import com.edmz.api.bo.SessionRequest
import com.edmz.api.bo.PSMAccount
import com.edmz.api.filters.PSMAccountFilter


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

SystemName = ""
AccountName = ""
AccountAutoFlag = ""
AccountEgpFlag = ""
CollectionName = ""
DualControlFlag = ""
AccountLockFlag = ""
NetworkAddress = ""
Platform = ""
SystemAutoFlag = ""
SystemEgpFlag = ""  
 

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
		
	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue

	} else if (paramName.equals("AccountAutoFlag")) {
		AccountAutoFlag = paramValue

	} else if (paramName.equals("AccountEgpFlag")) {
		AccountEgpFlag = paramValue

	} else if (paramName.equals("CollectionName")) {
		CollectionName = paramValue

	} else if (paramName.equals("DualControlFlag")) {
		DualControlFlag = paramValue

	} else if (paramName.equals("AccountLockFlag")) {
		AccountLockFlag = paramValue

	} else if (paramName.equals("NetworkAddress")) {
		NetworkAddress = paramValue

	} else if (paramName.equals("Platform")) {
		Platform = paramValue

	} else if (paramName.equals("SystemAutoFlag")) {
		SystemAutoFlag = paramValue

	} else if (paramName.equals("SystemEgpFlag")) {
		SystemEgpFlag = paramValue
		
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
	
	if (!AccountName.equals("")) {
		println("AccountName used!")
	}


    PSMAccountFilter filter = new PSMAccountFilter()
	
	
	if (!SystemName.equals("")) {
		filter.setSystemName(SystemName)
	}
	
	if (!AccountName.equals("")) {
		filter.setAccountName(AccountName)
	}
	
	if (!AccountAutoFlag.equals("")) {
		filter.setAccountAutoFlag(AccountAutoFlag)
	}
	
	if (!AccountEgpFlag.equals("")) {
		filter.setAccountEgpFlag(AccountEgpFlag)
	}
	
	if (!CollectionName.equals("")) {
		filter.setCollectionName(CollectionName)
	}
	
	if (!DualControlFlag.equals("")) {
		filter.setDualControlFlag(DualControlFlag)
	}
	
	if (!AccountLockFlag.equals("")) {
		filter.setAccountLockFlag(AccountLockFlag)
	}
	
	if (!NetworkAddress.equals("")) {
		filter.setNetworkAddress(NetworkAddress)
	}
	
	if (!Platform.equals("")) {
		filter.setPlatform(Platform)
	}
	
	if (!SystemAutoFlag.equals("")) {
		filter.setSystemAutoFlag(SystemAutoFlag)
	}
	
	if (!SystemEgpFlag.equals("")) {
		filter.setSystemEgpFlag(SystemEgpFlag)
	}
	

	
	 ListResult res = new ListResult()
     List<PSMAccount> accounts = new ArrayList<PSMAccount>()
     acl.listPSMAccounts(filter, res, accounts)
	
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
			
    Iterator<PSMAccount> iter = accounts.iterator()
    while (iter.hasNext()) {
    PSMAccount element = (PSMAccount) iter.next()
    System.out.println(" System Name: " + element.getSystemName())
    System.out.println(" Account Name: " + element.getAccountName())
	
	System.out.println(" AccountLockFlag: " + element.getAccountLockFlag())
	System.out.println(" CliAccountName: " + element.getCliAccountName())
	System.out.println(" CliDomainName: " + element.getCliDomainName())
	System.out.println(" ClipboardFlag: " + element.getClipboardFlag())
	System.out.println(" CliSystemName: " + element.getCliSystemName())
	System.out.println(" ColorDepth: " + element.getColorDepth())
	System.out.println(" ConnectionProfile: " + element.getConnectionProfile())
	System.out.println(" ConsoleFlag: " + element.getConsoleFlag())
	System.out.println(" DefaultSessionDuration: " + element.getDefaultSessionDuration())
	System.out.println(" DomainAccountName: " + element.getDomainAccountName())
	System.out.println(" DssKey: " + element.getDssKey())
	System.out.println(" DssKeyName: " + element.getDssKeyName())
	System.out.println(" DssKeyType: " + element.getDssKeyType())
	System.out.println(" EnableFlag: " + element.getEnableFlag())
	System.out.println(" EscalationEmail: " + element.getEscalationEmail())
	System.out.println(" EscalationtTime: " + element.getEscalationtTime())
	System.out.println(" FileTransAuthMethod: " + element.getFileTransAuthMethod())
	System.out.println(" FileTransDownFlag: " + element.getFileTransDownFlag())
	System.out.println(" FileTransPath: " + element.getFileTransPath())
	System.out.println(" FileTransType: " + element.getFileTransType())
	System.out.println(" FileTransUpFlag: " + element.getFileTransUpFlag())
	System.out.println(" MaxSessionCount: " + element.getMaxSessionCount())
	System.out.println(" MinApprovers: " + element.getMinApprovers())
	System.out.println(" NotifyFrequency: " + element.getNotifyFrequency())
	System.out.println(" NotifyThreshold: " + element.getNotifyThreshold())
	System.out.println(" ParCLIUserName: " + element.getParCLIUserName())
	System.out.println(" PasswordMethod: " + element.getPasswordMethod())
	System.out.println(" PostSessionProfile: " + element.getPostSessionProfile())
	System.out.println(" ProxyType: " + element.getProxyType())
	System.out.println(" RecordingRequiredFlag: " + element.getRecordingRequiredFlag())
	System.out.println(" ReviewCount: " + element.getReviewCount())
	System.out.println(" ReviewerName: " + element.getReviewerName())
	System.out.println(" ReviewerType: " + element.getReviewerType())
	System.out.println(" SessionStartNotifyEmail: " + element.getSessionStartNotifyEmail())
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
  

