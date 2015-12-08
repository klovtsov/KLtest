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
import com.edmz.api.bo.Policy;
import com.edmz.api.bo.ReasonCode;
import com.sshtools.j2ssh.session.SessionChannelClient;


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
            ListResult res = new ListResult();
            List<ReasonCode> reasonCodes = new ArrayList<ReasonCode>();
            acl.listReasonCodes(res, reasonCodes);

			System.out.println(res.getMessage())
			
            Iterator<ReasonCode> iter = reasonCodes.iterator();
            while (iter.hasNext()) {
            	ReasonCode element = (ReasonCode) iter.next();
                System.out.println(element);
            }
			
			
           
                      
        } catch (IOException e) {
println(e)
        } finally {
            ac.disconnect()      
        }  



