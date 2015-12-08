import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.Calendar
// import java.util.GregorianCalendar
import java.util.Iterator
import java.util.List
import java.text.DateFormat
import java.text.SimpleDateFormat

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
import com.edmz.api.filters.RequestFilter
import com.edmz.api.bo.Request


import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

SystemName = ""
AccountName = ""
RequestorName = ""

println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"        
for (String s: args) {
	
	String[] parameter = s.split("=")
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
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

	} else if (paramName.equals("RequestorName")) {
		RequestorName = paramValue

	} else if (paramName.equals("StartDate")) {
		StartDate = paramValue	

	} else if (paramName.equals("EndDate")) {
		EndDate = paramValue			
		
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
	
	if (!RequestorName.equals("")) {
		println("RequestorName used!")
	}

  RequestFilter filter = new RequestFilter()


       DateFormat df = new SimpleDateFormat("yyyy,MM,dd")
        Calendar scal  = Calendar.getInstance()
		Calendar ecal  = Calendar.getInstance()
        scal.setTime(df.parse(StartDate))
		ecal.setTime(df.parse(EndDate))
		
		scal.add(Calendar.DATE, +5)
		ecal.add(Calendar.DATE, +6)
		
		filter.setStartDate(scal)
		filter.setEndDate(ecal)
 
	filter.setStatus("ALL")
    filter.setSystemName(SystemName)
	filter.setAccountName(AccountName)
	filter.setRequestorName("User=Myself")
	
	 ListResult res = new ListResult()
     List requests = new ArrayList()
     acl.listRequest(filter, res, requests)
	
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
	
	Iterator iter = requests.iterator();
            while (iter.hasNext()) {
                Request element = (Request) iter.next()
    System.out.println(element.getRequestID() + " " + element.getSystemName() + "\\" + element.getAccountName()  + " " + element.getUserName() + " " + element.getStatus() )
	System.out.println(element.getCreatedByUserFullName())
	System.out.println(element.getCreatedByUserName())
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
  

