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

SystemName = ""
CollectionName = ""
Platform = ""
NetworkAddress = ""
SystemAutoFl = ""

SystemCustom1 = ""
SystemCustom2 = ""
SystemCustom3 = ""
SystemCustom4 = ""
SystemCustom5 = ""
SystemCustom6 = ""

PasswordChangeProfile = ""
PasswordCheckProfile = ""

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
		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue
		
	} else if (paramName.equals("CollectionName")) {
		CollectionName = paramValue	

	} else if (paramName.equals("Platform")) {
		Platform = paramValue		
		
	} else if (paramName.equals("NetworkAddress")) {
		NetworkAddress = paramValue	
		
	} else if (paramName.equals("SystemAutoFl")) {
		SystemAutoFl = paramValue	
		
	} else if (paramName.equals("SystemCustom1")) {
		SystemCustom1 = paramValue	
				
	} else if (paramName.equals("SystemCustom2")) {
		SystemCustom2 = paramValue	
				
	} else if (paramName.equals("SystemCustom3")) {
		SystemCustom3 = paramValue	
				
	} else if (paramName.equals("SystemCustom4")) {
		SystemCustom4 = paramValue	
				
	} else if (paramName.equals("SystemCustom5")) {
		SystemCustom5 = paramValue	
				
	} else if (paramName.equals("SystemCustom6")) {
		SystemCustom6 = paramValue	
		
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
	
	if (!CollectionName.equals("")) {
		println("CollectionName used!")
	}
	
	if (!Platform.equals("")) {
		println("Platform used!")
	}

    SystemFilter filter = new SystemFilter()
    filter.setSystemName(SystemName)
	filter.setCollectionName(CollectionName)
	filter.setPlatform(Platform)
	filter.setNetworkAddress(NetworkAddress)
	filter.setSystemAutoFl(SystemAutoFl)
	
	filter.setSystemCustom1(SystemCustom1)
	filter.setSystemCustom2(SystemCustom2)
	filter.setSystemCustom3(SystemCustom3)
	filter.setSystemCustom4(SystemCustom4)
	filter.setSystemCustom5(SystemCustom5)
	filter.setSystemCustom6(SystemCustom6)
		
	ListResult res = new ListResult()
	List systems = new ArrayList()
	acl.listSystems(filter, res, systems)
	
	GetFinalMessage = res.getMessage()
	System.out.println(GetFinalMessage)
	
	Iterator iter = systems.iterator()
	
	while (iter.hasNext()) {
	
		EDMZSystem element = (EDMZSystem) iter.next()
		
		System.out.println(" System Name: " + element.getSystemName())
		System.out.println(" AllowFuncReqFl: " + element.getAllowFuncReqFl())
		System.out.println(" AllowISADurationFlag: " + element.getAllowISADurationFlag())
		System.out.println(" AutoDiscoveryExcludeList: " + element.getAutoDiscoveryExcludeList())
		System.out.println(" AutoDiscoveryProfile: " + element.getAutoDiscoveryProfile())
		System.out.println(" AutoDiscoveryTimeout: " + element.getAutoDiscoveryTimeout())
	
		/*
		System.out.println(" ChangeFrequency: " + element.getChangeFrequency())
		System.out.println(" ChangeTime: " + element.getChangeTimeAsString())
		System.out.println(" CheckFl: " + element.getCheckFl())
		*/

		System.out.println(" SystemCustom1: " + element.getCustom1())
		System.out.println(" SystemCustom2: " + element.getCustom2())
		System.out.println(" SystemCustom3: " + element.getCustom3())
		System.out.println(" SystemCustom4: " + element.getCustom4())
		System.out.println(" SystemCustom5: " + element.getCustom5())
		System.out.println(" SystemCustom6: " + element.getCustom6())
		System.out.println(" Description: " + element.getDescription())
		System.out.println(" DomainFunctionalAccount: " + element.getDomainFunctionalAccount())
		System.out.println(" DomainName: " + element.getDomainName())
		System.out.println(" EscalationEmail: " + element.getEscalationEmail())
		System.out.println(" EscalationTime: " + element.getEscalationTime())
		System.out.println(" FunctionalAccount: " + element.getFunctionalAccount())
		System.out.println(" FunctionalAccountDN: " + element.getFuncAcctDN())
		System.out.println(" MaxReleaseDuration: " + element.getMaxReleaseDuration())
		System.out.println(" LineDef: " + element.getLineDef())
		System.out.println(" NetBiosName: " + element.getNetBiosName())
		System.out.println(" NetworkAddress: " + element.getNetworkAddress())
		System.out.println(" OracleSIDSN: " + element.getOracleSIDSN())
		System.out.println(" OracleType: " + element.getOracleType())
		System.out.println(" PasswordCheckProfile: " + element.getPasswordCheckProfile())
		System.out.println(" PasswordChangeProfile: " + element.getPasswordChangeProfile())
		System.out.println(" PasswordRule: " + element.getPasswordRule())
		System.out.println(" PlatformName: " + element.getPlatformName())
		System.out.println(" PlatSpecificValue: " + element.getPlatSpecificValue())
		System.out.println(" PortNumber: " + element.getPortNumber())
		System.out.println(" PrimaryEmail: " + element.getPrimaryEmail())
		System.out.println(" PSMDPAAffinity: " + element.getPsmDpaAffinity())
		System.out.println(" PPMDPAAffinity: " + element.getPpmDpaAffinity())
		System.out.println(" ReleaseChgFl: " + element.getReleaseChgFl())
		System.out.println(" ReleaseDuration: " + element.getReleaseDuration())
		System.out.println(" RequireTicketForAPI: " + element.getRequireTicketForAPI())
		System.out.println(" RequireTicketForCLI: " + element.getRequireTicketForCLI())
		System.out.println(" RequireTicketForISA: " + element.getRequireTicketForISA())
		System.out.println(" RequireTicketForRequest: " + element.getRequireTicketForRequest())
		System.out.println(" RequireTicketForPSM: " + element.getRequireTicketForPSM())
		System.out.println(" ResetFl: " + element.getResetFl())
		System.out.println(" SSHAccount: " + element.getSSHAccount())
		System.out.println(" SSHKey: " + element.getSSHKey())
		System.out.println(" SSHPort: " + element.getSSHPort())
		System.out.println(" Status: " + element.getStatus())
		System.out.println(" SystemAutoFl: " + element.getSystemAutoFl())
		System.out.println(" TicketEmailNotify: " + element.getTicketEmailNotify())
		System.out.println(" TicketSystemName: " + element.getTicketSystemName())
		System.out.println(" Timeout: " + element.getTimeout())
		System.out.println(" UseSSHflag: " + element.getUseSSHflag())
		System.out.println(" UseSslFl: " + element.getUseSslFl())
	
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
  

