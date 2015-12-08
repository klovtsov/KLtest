import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
//import java.util.Calendar
//import java.util.GregorianCalendar
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
import com.edmz.api.bo.Policy
import com.edmz.api.filters.PolicyFilter


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

SystemName = ""
AccountName = ""
AccessPolicyName = ""
PermissionType = ""
PermissionName = ""
AllOrEffectiveFlag = ""
UserName = ""
FileName = ""
CollectionName = ""
ExpandCollectionFlag = ""
ExpandGroupFlag = ""
ExpandPolicyFlag = ""


// println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
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
		
	} else if (paramName.equals("AccessPolicyName")) {
		AccessPolicyName = paramValue
		
	} else if (paramName.equals("PermissionType")) {
		PermissionType = paramValue
		
	} else if (paramName.equals("PermissionName")) {
		PermissionType = paramValue

	} else if (paramName.equals("AllOrEffectiveFlag")) {
		AllOrEffectiveFlag = paramValue
		
	} else if (paramName.equals("UserName")) {
		UserName = paramValue
		
	} else if (paramName.equals("FileName")) {
		FileName = paramValue
		
	} else if (paramName.equals("GroupName")) {
		GroupName = paramValue
		
	} else if (paramName.equals("CollectionName")) {
		CollectionName = paramValue
		
	} else if (paramName.equals("ExpandCollectionFlag")) {
		ExpandCollectionFlag = paramValue
		
	} else if (paramName.equals("ExpandGroupFlag")) {
		ExpandGroupFlag = paramValue
		
	} else if (paramName.equals("ExpandPolicyFlag")) {
		ExpandPolicyFlag = paramValue
		
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

    PolicyFilter filter = new PolicyFilter()

    filter.setSystemName(SystemName)
	filter.setAccountName(AccountName)
	filter.setAccessPolicyName(AccessPolicyName)
	filter.setPermissionType(PermissionType)
	filter.setPermName(PermissionName)
	filter.setAllOrEffectiveFlag(AllOrEffectiveFlag)
	filter.setUserName(UserName)
	filter.setFileName(FileName)
	filter.setCollectionName(CollectionName)
	filter.setExpandPolicyFlag(ExpandPolicyFlag)
	
	
	 ListResult res = new ListResult()
     List<Policy> policies = new ArrayList<Policy>()
     acl.listAssignedPolicies(filter, res, policies)
	
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
			
    Iterator<Policy> iter = policies.iterator()
    while (iter.hasNext()) {
    Policy element = (Policy) iter.next()
	
	if (AllOrEffectiveFlag.equals("A")) {
	System.out.println(element.toString())
	}
	
	if (AllOrEffectiveFlag.equals("")) {
	System.out.println(" UserName: " + element.getUserName() + " FullName: " + element.getFullName() + " GroupName: " + element.getGroupName() + " SystemName: " + element.getSystemName() + " AccountName " + element.getAccountName() + " FileName " + element.getFileName() + " CollectionName " + element.getCollectionName() + " AccessPolicyName " + element.getAccessPolicyName() + " PolicySource " + element.getPolicySource() + " PermissionType " + element.getPermissionType() + " PermissionName " + element.getPermissionName() + " EffectiveFlag " + element.getEffectiveFlag())
	}
	
	
//    System.out.println(" UserName: " + element.getUserName() + " FullName: " + element.getFullName() + " GroupName: " + element.getGroupName() + " SystemName: " + element.getSystemName() + " AccountName " + element.getAccountName() + " FileName " + element.getFileName() + " CollectionName " + element.getCollectionName() + " AccessPolicyName " + element.getAccessPolicyName() + " PolicySource " + element.getPolicySource() + " PermissionType " + element.getPermissionType() + " PermissionName " + element.getPermissionName() + " EffectiveFlag " + element.getEffectiveFlag())
	
	
//    System.out.println(element.toString())
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
  

