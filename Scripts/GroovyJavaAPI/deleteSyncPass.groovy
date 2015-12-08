import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter

import junit.framework.TestCase

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.CodeMessageResult
import com.edmz.api.bo.SynchronizedPwd
import com.sshtools.j2ssh.session.SessionChannelClient
		


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
		
	} else if (paramName.equals("SyncPassName")) {
		SyncPassName = paramValue
		
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
     SynchronizedPwd sp = new SynchronizedPwd()

			
		if (!SyncPassName.equals("")) {
		sp.setSyncPassName(SyncPassName)
	    }
            
            CodeMessageResult res = new CodeMessageResult()
            
            acl.deleteSyncPass(sp, res)
            System.out.println("code: " + res.getReturnCode() + " message: " + res.getMessage())
            
             
        } catch (IOException e) {
println(e)
        } finally {
            ac.disconnect();      
        }  




