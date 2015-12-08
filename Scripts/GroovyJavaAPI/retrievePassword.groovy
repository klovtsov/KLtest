import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.edmz.api.APIClient;
import com.edmz.api.APICommandLib;
import com.edmz.api.bo.ListResult;
import com.edmz.api.bo.CodeMessageResult;
import com.sshtools.j2ssh.session.SessionChannelClient;

SystemName = ""
AccountName = ""
ReasonCode = ""
ReasonText = ""
TicketSystemName = ""
TicketNumber = ""
TimeRequired = ""


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
		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue
		
	} else if (paramName.equals("AccountName")) {
		AccountName = paramValue
		
	} else if (paramName.equals("ReasonCode")) {
		ReasonCode = paramValue
		
	} else if (paramName.equals("ReasonText")) {
		ReasonText = paramValue
		
	} else if (paramName.equals("TicketSystemName")) {
		TicketSystemName = paramValue
		
	} else if (paramName.equals("TicketNumber")) {
		TicketNumber = paramValue
		
	} else if (paramName.equals("TimeRequired")) {
		TimeRequired = paramValue
		
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
            CodeMessageResult res = new CodeMessageResult();
            acl.retrievePassword(SystemName, AccountName, ReasonCode, ReasonText, TicketSystemName, TicketNumber, TimeRequired,  res);

            GetFinalMessage = res.getMessage()
            System.out.println(GetFinalMessage)           
                      
        } catch (IOException e) {
println(e)
        } finally {
            ac.disconnect()      
        }  



