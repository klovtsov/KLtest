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


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

UserName = ""
EmailAddress = ""
GroupName = ""
UserInterface = ""
UserType = ""
Status = ""
SecondaryAuthType = ""

UserCustom1 = ""
UserCustom2 = ""
UserCustom3 = ""
UserCustom4 = ""
UserCustom5 = ""
UserCustom6 = ""

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
		
	} else if (paramName.equals("UserName")) {
		UserName = paramValue
		
	} else if (paramName.equals("EmailAddress")) {
		EmailAddress = paramValue	

	} else if (paramName.equals("GroupName")) {
		GroupName = paramValue

	} else if (paramName.equals("UserInterface")) {
		UserInterface = paramValue

	} else if (paramName.equals("UserType")) {
		UserType = paramValue

	} else if (paramName.equals("Status")) {
		Status = paramValue

	} else if (paramName.equals("SecondaryAuthType")) {
		SecondaryAuthType = paramValue		
		
	} else if (paramName.equals("UserCustom1")) {
		UserCustom1 = paramValue	
				
	} else if (paramName.equals("UserCustom2")) {
		UserCustom2 = paramValue	
				
	} else if (paramName.equals("UserCustom3")) {
		UserCustom3 = paramValue	
				
	} else if (paramName.equals("UserCustom4")) {
		UserCustom4 = paramValue	
				
	} else if (paramName.equals("UserCustom5")) {
		UserCustom5 = paramValue	
				
	} else if (paramName.equals("UserCustom6")) {
		UserCustom6 = paramValue	
		
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
 
	if (!UserName.equals("")) {
		println("UserName used!")
	}
	
	if (!EmailAddress.equals("")) {
		println("EmailAddress used!")
	}
	
	if (!GroupName.equals("")) {
		println("GroupName used!")
	}
	
	if (!UserInterface.equals("")) {
		println("UserInterface used!")
	}
	
	if (!Status.equals("")) {
		println("Status used!")
	}
	
	if (!UserType.equals("")) {
		println("UserType used!")
	}
	
	if (!SecondaryAuthType.equals("")) {
		println("SecondaryAuthType used!")
	}

    UserFilter filter = new UserFilter()
    filter.setUserName(UserName)
	filter.setEmailAddress(EmailAddress)
	filter.setGroupName(GroupName)
	filter.setUserInterface(UserInterface)
	filter.setStatus(Status)
	filter.setUserType(UserType)
	filter.setSecondaryAuthType(SecondaryAuthType)
	
    ListResult res = new ListResult();
    List users = new ArrayList()
    acl.listUsers(filter, res, users)
	
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
	
            Iterator iter = users.iterator()
            while (iter.hasNext()) {
            User element = (User) iter.next()
            System.out.println(" User Name: " + element.getUserName())
			System.out.println(" Description: " + element.getDescription())
			System.out.println(" DisableFl: " + element.getDisableFl())
			System.out.println(" EmailAddress: " + element.getEmailAddress())
			System.out.println(" FirstName: " + element.getFirstName())
			System.out.println(" LastAccessDateAsString: " + element.getLastAccessDateAsString())
			System.out.println(" LastName: " + element.getLastName())
			System.out.println(" LocalTimezone: " + element.getLocalTimezone())
			System.out.println(" LogonDays: " + element.getLogonDays())
			System.out.println(" LogonHours: " + element.getLogonHours())
			System.out.println(" LogonHoursFL: " + element.getLogonHoursFL())
			System.out.println(" Mobile: " + element.getMobile())
			System.out.println(" MobileAllowedFlag: " + element.getMobileAllowedFlag())
			System.out.println(" Password: " + element.getPassword())
			System.out.println(" Phone: " + element.getPhone())
			System.out.println(" PrimaryAuthExtra: " + element.getPrimaryAuthExtra())
			System.out.println(" PrimAuthSystem: " + element.getPrimAuthSystem())
			System.out.println(" PrimAuthType: " + element.getPrimAuthType())
			System.out.println(" PrimAuthUserID: " + element.getPrimAuthUserID())
			System.out.println(" SecondaryAuth: " + element.getSecondaryAuth())
			System.out.println(" SecondaryAuthSystem: " + element.getSecondaryAuthSystem())
			System.out.println(" SecondaryUserID: " + element.getSecondaryUserID())
			System.out.println(" UserInterface: " + element.getUserInterface())
			System.out.println(" UserType: " + element.getUserType())
			
			System.out.println(" UserCustom1: " + element.getCustom1())
			System.out.println(" UserCustom2: " + element.getCustom2())
			System.out.println(" UserCustom3: " + element.getCustom3())
			System.out.println(" UserCustom4: " + element.getCustom4())
			System.out.println(" UserCustom5: " + element.getCustom5())
			System.out.println(" UserCustom6: " + element.getCustom6())
			
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
  

