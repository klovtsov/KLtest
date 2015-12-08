import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Calendar
import java.text.DateFormat
import java.text.SimpleDateFormat

import junit.framework.TestCase

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.CodeMessageResult
import com.edmz.api.bo.SynchronizedPwd
import com.sshtools.j2ssh.session.SessionChannelClient


GetFinalMessage = "TPAM: default value"
		
cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"    
ChangeFrequency=""
ReleaseNotifyEmail=""
CheckFlag=""
ResetFlag=""
ReleaseChangeFlag=""
Description=""
DisabledFl=""
PasswordRule=""
Password=""
ExpandPolicyFlag=""
ReleaseDuration=""
ChangeTime=""
NextChangeDate=""
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
		
	} else if (paramName.equals("ChangeFrequency")) {
		ChangeFrequency = paramValue
		
	} else if (paramName.equals("ChangeTime")) {
		ChangeTime = paramValue
		
	} else if (paramName.equals("NextChangeDate")) {
		NextChangeDate = paramValue

	} else if (paramName.equals("ReleaseNotifyEmail")) {
		ReleaseNotifyEmail = paramValue
		
	} else if (paramName.equals("CheckFlag")) {
		CheckFlag = paramValue
		
	} else if (paramName.equals("ResetFlag")) {
		ResetFlag = paramValue
		
	} else if (paramName.equals("ReleaseChangeFlag")) {
		ReleaseChangeFlag = paramValue
		
	} else if (paramName.equals("Description")) {
		Description = paramValue
		
	} else if (paramName.equals("DisabledFl")) {
		DisabledFl = paramValue
		
	} else if (paramName.equals("PasswordRule")) {
		PasswordRule = paramValue
		
	} else if (paramName.equals("ExpandPolicyFlag")) {
		ExpandPolicyFlag = paramValue
		
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
			
		if (!ChangeFrequency.equals("")) {
		sp.setChangeFrequency(new Integer(ChangeFrequency))
	    }
            
	    if (!ChangeTime.equals("")) {
	
		DateFormat df = new SimpleDateFormat("HH:mm")
        Calendar scal  = Calendar.getInstance()
        scal.setTime(df.parse(ChangeTime))
		sp.setChangeTime(scal)
	    }
            
	    if (!NextChangeDate.equals("")) {
	
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        Calendar scal  = Calendar.getInstance()
        scal.setTime(df.parse(NextChangeDate))
		sp.setNextChangeDate(scal)
	    }
			
		if (!ReleaseDuration.equals("")) {
		sp.setReleaseDuration(new Integer(ReleaseDuration))
	    }
            
		 
		if (!ReleaseNotifyEmail.equals("")) {
		sp.setReleaseNotifyEmail(ReleaseNotifyEmail);
	    }
			
		if (!CheckFlag.equals("")) {
		sp.setCheckFlag(CheckFlag);
	    }
			
		if (!ResetFlag.equals("")) {
		sp.setResetFlag(ResetFlag);
	    }		
			
		if (!ReleaseChangeFlag.equals("")) {
		sp.setReleaseChangeFlag(ReleaseChangeFlag);
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
		acl.updateSyncPass(sp, res)
			
		GetFinalMessage = res.getMessage()
		System.out.println(GetFinalMessage)
                      
        } catch (IOException e) {
println(e)
        } finally {
            ac.disconnect();      
        }  



