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


GetFinalMessage = "TPAM: default value"
		
cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"

AccountLevelCheckProfile=""
ReleaseNotifyEmail=""
Description=""
DisabledFl=""
PasswordRule=""
ReleaseDuration=""
PasswordCheckProfile=""
PasswordChangeProfile=""

       
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
		
	} else if (paramName.equals("Password")) {
		Password = paramValue	
		
	} else if (paramName.equals("AccountLevelCheckProfile")) {
		AccountLevelCheckProfile = paramValue
		
	} else if (paramName.equals("ReleaseNotifyEmail")) {
		ReleaseNotifyEmail = paramValue
		
	} else if (paramName.equals("Description")) {
		Description = paramValue
		
	} else if (paramName.equals("DisabledFl")) {
		DisabledFl = paramValue
		
	} else if (paramName.equals("PasswordRule")) {
		PasswordRule = paramValue
		
	} else if (paramName.equals("ReleaseDuration")) {
		ReleaseDuration = paramValue
		
	} else if (paramName.equals("PasswordCheckProfile")) {
		PasswordCheckProfile = paramValue

	} else if (paramName.equals("PasswordChangeProfile")) {
		PasswordChangeProfile = paramValue

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
			
		if (!Password.equals("")) {
		sp.setPassword(Password)
	    }
			
            
		if (!ReleaseDuration.equals("")) {
		sp.setReleaseDuration(new Integer(ReleaseDuration))
	    }
            
		 
		if (!ReleaseNotifyEmail.equals("")) {
		sp.setReleaseNotifyEmail(ReleaseNotifyEmail);
	    }
			
		if (!AccountLevelCheckProfile.equals("")) {
		sp.setAccountLevelCheckProfile(AccountLevelCheckProfile);
	    }
			
		if (!Description.equals("")) {
		sp.setDescription(Description);
	    }
			
		if (!DisabledFl.equals("")) {
		sp.setDisabledFl(DisabledFl);
	    }
			
		if (!PasswordRule.equals("")) {
		sp.setPasswordRule(PasswordRule)
	    }
       
		if (!PasswordCheckProfile.equals("")) {
		sp.setPasswordCheckProfile(PasswordCheckProfile)
	    }

		if (!PasswordChangeProfile.equals("")) {
		sp.setPasswordChangeProfile(PasswordChangeProfile)
	    }

        CodeMessageResult res = new CodeMessageResult();
        acl.addSyncPass(sp, res)
			
		GetFinalMessage = res.getMessage()
		System.out.println(GetFinalMessage)

                      
        } catch (IOException e) {
println(e)
        } finally {
            ac.disconnect();      
        }  



