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
import com.edmz.api.bo.Collection
import com.edmz.api.bo.EDMZSystem
import com.edmz.api.bo.IDResult
import com.edmz.api.bo.ListResult
import com.edmz.api.bo.Permission
import com.edmz.api.bo.PwdRequest
import com.edmz.api.bo.SSHKey
import com.edmz.api.bo.User
import com.edmz.api.filters.PermissionsFilter
import com.edmz.api.bo.UserSshKey
import com.edmz.api.bo.Group
import com.edmz.api.bo.SetPermRequest


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

CollectionName = ""
GroupName = ""
SystemName = ""
AccountName = ""
UserName = ""
FileName = ""

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
		
	} else if (paramName.equals("AccessPolicyName")) {
		AccessPolicyName = paramValue
		
	} else if (paramName.equals("Action")) {
		Action = paramValue
		
	} else if (paramName.equals("UserName")) {
		UserName = paramValue
		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue

	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue
		
	} else if (paramName.equals("CollectionName")) {
		CollectionName = paramValue
		
	} else if (paramName.equals("FileName")) {
		FileName = paramValue
		
    } else if (paramName.equals("GroupName")) {
		GroupName = paramValue
		
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

	AccessPolicyRequest req = new AccessPolicyRequest()

	if (!AccessPolicyName.equals("")) {
		println("AccessPolicyName used!")
		req.setAccessPolicyName(AccessPolicyName)
	}
	
	if (!Action.equals("")) {
		println("Action used!")
		req.setAction(Action)
	}

	if (!UserName.equals("")) {
		println("UserName used!")
		req.setUserName(UserName)
	}
	
	if (!SystemName.equals("")) {
		println("SystemName used!")
		req.setSystemName(SystemName)
	}
	
	if (!AccountName.equals("")) {
		println("AccountName used!")
		req.setAccountName(AccountName)
}

	req.setCollectionName(CollectionName)
	req.setGroupName(GroupName)
	req.setFileName(FileName)
	CodeMessageResult res = new CodeMessageResult()
			
	acl.setAccessPolicy(req, res)
        
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