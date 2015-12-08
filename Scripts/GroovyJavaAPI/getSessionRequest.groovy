import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.io.IOException

import junit.framework.TestCase

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.ListResult;
import com.edmz.api.bo.SessionRequest;
import com.sshtools.j2ssh.session.SessionChannelClient

import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

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
		
	} else if (paramName.equals("RequestID")) {
		RequestID = paramValue
		
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
    ListResult res = new ListResult()
    SessionRequest request = new SessionRequest()

    acl.getSessionRequest(RequestID, res, request)
        
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
	System.out.println(request.getRequestID())
	System.out.println(request.getSystemName()+"/"+request.getAccountName())
    System.out.println(request.getRequestorName())
	System.out.println(request.getStatus())
	System.out.println(request.getCreatedByUserFullName())
	System.out.println(request.getCreatedByUserName())
	
	
} catch (IOException e) {

println(e)

} catch (InvalidStateException e) {

println(e)

} catch (InterruptedException e) {

println(e)

} finally {
	ac.disconnect()
}