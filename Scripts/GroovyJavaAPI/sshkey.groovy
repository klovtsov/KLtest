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

import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException


println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
 keydir = new File(cwd).getParent() + "\\keys" 
apiUser = ""
TPAM = ""

keyFormat = ""
standardKey = ""
systemName = ""
accountName = ""
regenerate = ""

        
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
		
	} else if (paramName.equals("keyFormat")) {
		keyFormat = paramValue
		
	} else if (paramName.equals("standardKey")) {
		standardKey = paramValue
		
	} else if (paramName.equals("systemName")) {
		systemName = paramValue
		
	} else if (paramName.equals("accountName")) {
		accountName = paramValue
		
	} else if (paramName.equals("regenerate")) {
		regenerate = paramValue
		
	} else {
		println("Unknown option: " + paramName + "=" + paramValue)
	}
}


//authFile = "id_dsa_ykapiadmin209" 
//apiUser  = "ykapiadmin" 
//TPAM     = "10.30.44.209"

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
	
	SSHKey key = new SSHKey()            


	if (!keyFormat.equals("")) {
        println("keyFormat used!")
        key.setKeyFormat(keyFormat)
    }
		
	if (!standardKey.equals("")) {
		println("standardKey used!")
		key.setStandardKey(standardKey)
	}
	
	if (!regenerate.equals("")) {
		println("Regenerate used!")
		key.setRegenerate(regenerate)
	}
	
	if (!systemName.equals("")) {
		println("systemName used!")
		key.setSystemName(systemName)
	}
	
	if (!accountName.equals("")) {
		println("accountName used!")
		key.setAccountName(accountName)
	}
	
	CodeMessageResult res = new CodeMessageResult()
	acl.sshKey(key, res)           
	   
	GetFinalMessage = res.getMessage()
	
	println(GetFinalMessage)

	String ourkey = key.getSshKey()

	println("Below is going to be our key:\n\n")
	
	System.out.println(ourkey);
	

} catch (IOException e) {

println(e)

} catch (InvalidStateException e) {

println(e)

} catch (InterruptedException e) {

println(e)

} finally {
	ac.disconnect()

}
  

