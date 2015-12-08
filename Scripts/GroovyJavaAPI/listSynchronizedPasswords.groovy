import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Calendar

import junit.framework.TestCase

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.CodeMessageResult
import com.edmz.api.bo.SynchronizedPwd
import com.sshtools.j2ssh.session.SessionChannelClient
import com.edmz.api.bo.ListResult
import java.util.ArrayList
import java.util.Iterator
import java.util.List


GetFinalMessage = "TPAM: default value"
		
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
           List<SynchronizedPwd> synchronizedPasswords = new ArrayList<SynchronizedPwd>()
           acl.listSynchronizedPasswords(res, synchronizedPasswords)
           
			System.out.println(res)
            
              for (Iterator<SynchronizedPwd> iterator = synchronizedPasswords.iterator(); iterator
					.hasNext();) {
				SynchronizedPwd synchronizedPwd = (SynchronizedPwd) iterator
						.next();
				System.out.println("SyncPassName: " + synchronizedPwd.getSyncPassName() + " "
				+ "DisabledFl: " + synchronizedPwd.getDisabledFl() + " "
				+ "Subscribers: " + synchronizedPwd.getSubscribers() + " ");
				System.out.println("Description: " + synchronizedPwd.getDescription());
				System.out.println("PasswordChangeProfile: " + synchronizedPwd.getPasswordChangeProfile());
				System.out.println("PasswordCheckProfile: " + synchronizedPwd.getPasswordCheckProfile());
				System.out.println("PostReqReset: " + synchronizedPwd.getPostReqReset());
				System.out.println("PwdRuleName: " + synchronizedPwd.getPwdRuleName());
				System.out.println("ReleaseNotifyEmail: " + synchronizedPwd.getReleaseNotifyEmail());
				System.out.println("ScheduledChanges: " + synchronizedPwd.getScheduledChanges());
				System.out.println("UseAccountLevelCheckProfileFl: " + synchronizedPwd.getUseAccountLevelCheckProfileFl());
			  }
                      
        } catch (IOException e) {
println(e)
        } finally {
            ac.disconnect()      
        }  



