import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import com.edmz.api.bo.IDResult
import java.util.Calendar
import java.text.DateFormat
import java.text.SimpleDateFormat
import com.edmz.api.bo.EDMZSystem;


import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.AccessPolicyRequest
import com.edmz.api.bo.Account

import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException
import com.sshtools.j2ssh.util.Base64.InputStream
import com.sshtools.j2ssh.util.InvalidStateException

AutoDiscoveryTimeout=""
NewSystemName=""               
AllowFuncReqFlag=""     
AllowISADurationFlag=""     
AlternateIP=""                 
AutoDiscoveryExcludeList=""        
AutoDiscoveryProfile=""        
BoksServerOS=""
ChangeFrequency=""
ChangeTime=""
CheckFlag=""
Custom1=""
Custom2=""
Custom3=""
Custom4=""
Custom5=""
Custom6=""
Description=""
DomainFuncAccount=""
DomainName="" 
EGPOnlyFlag=""   // ?????
EnablePassword=""   // cisco only
EscalationEmail=""
EscalationTime=""
FuncAcctDN="" 
FuncAcctCred=""
FunctionalAccount=""
LineDef=""
MaxReleaseDuration=""
NetBiosName=""
NetworkAddress=""
NonPrivFuncFlag=""
OracleSIDSN=""
OracleType=""
PasswordCheckProfile=""
PasswordChangeProfile=""
PasswordRule=""
PlatformName=""
PlatSpecificValue=""
PortNumber=""
PrimaryEmail=""
PSMDPAAffinity=""
PPMDPAAffinity=""
ReleaseChangeFlag=""
ReleaseDuration=""
RequireMultiGroupApprovalFlag=""
RequireTicketForAPI=""
RequireTicketForCLI=""
RequireTicketForISA=""
RequireTicketForPSM=""
RequireTicketForRequest=""
ResetFlag=""
ReleaseNotifyEmail=""
SSHAccount=""
SSHKey=""             
SSHPort=""
SystemAutoFlag="" 
SimulPrivAccReleases=""  
TicketSystemName=""          // "!Any" to allow tickets from any valid ticket system
TicketEmailNotify=""
Timeout=""
UseSslFlag=""
UseSshFlag=""




println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"    
       
for (String s: args) {
	
	String[] parameter = s.split("=", 2)
	String paramName = parameter[0]
	String paramValue = parameter[1]
	
	System.out.println(paramName + "=" + paramValue)
	
	if (paramName.equals("authFile")){
		authFile = keydir + "\\" + paramValue
		
	} else if (paramName.equals("apiUser")) {
		apiUser = paramValue
		
	} else if (paramName.equals("TPAM")) {
		TPAM = paramValue
		
	} else if (paramName.equals("SystemName")) {
		SystemName = paramValue
		
	} else if (paramName.equals("NewSystemName")) {
		NewSystemName = paramValue		
		
	} else if (paramName.equals("AutoFlag")) {
		AutoFlag = paramValue		
		
	} else if (paramName.equals("AllowISADurationFlag")) {
		AllowISADurationFlag = paramValue	
		
	} else if (paramName.equals("AllowFuncReqFlag")) {
		AllowFuncReqFlag = paramValue	
		
	} else if (paramName.equals("AlternateIP")) {
		AlternateIP = paramValue	
		
	} else if (paramName.equals("AutoDiscoveryExcludeList")) {
		AutoDiscoveryExcludeList = paramValue	

	} else if (paramName.equals("AutoDiscoveryProfile")) {
		AutoDiscoveryProfile = paramValue
	
	} else if (paramName.equals("AutoDiscoveryTimeout")) {
		AutoDiscoveryTimeout = paramValue	
		
	} else if (paramName.equals("BoksServerOS")) {
		BoksServerOS = paramValue	
	
	} else if (paramName.equals("ChangeFrequency")) {
		ChangeFrequency = paramValue	

	} else if (paramName.equals("ChangeTime")) {
		ChangeTime = paramValue
	
	} else if (paramName.equals("CheckFlag")) {
		CheckFlag = paramValue
	
	} else if (paramName.equals("Custom1")) {
		Custom1 = paramValue
	
	} else if (paramName.equals("Custom2")) {
		Custom2 = paramValue
	
	} else if (paramName.equals("Custom3")) {
		Custom3 = paramValue
	
	} else if (paramName.equals("Custom4")) {
		Custom4 = paramValue
	
	} else if (paramName.equals("Custom5")) {
		Custom5 = paramValue
	
	} else if (paramName.equals("Custom6")) {
		Custom6 = paramValue		

	} else if (paramName.equals("Description")) {
		Description = paramValue
		
	} else if (paramName.equals("DomainFuncAccount")) {
		DomainFuncAccount = paramValue
		
	} else if (paramName.equals("DomainName")) {
		DomainName = paramValue
		
	} else if (paramName.equals("EGPOnlyFlag")) {
		EGPOnlyFlag = paramValue
		
	} else if (paramName.equals("EnablePassword")) {
		EnablePassword = paramValue
		
	} else if (paramName.equals("EscalationEmail")) {
		EscalationEmail = paramValue
		
	} else if (paramName.equals("EscalationTime")) {
		EscalationTime = paramValue
		
	} else if (paramName.equals("FuncAcctDN")) {
		FuncAcctDN = paramValue	
		
	} else if (paramName.equals("FuncAcctCred")) {
		FuncAcctCred = paramValue	

	} else if (paramName.equals("FunctionalAccount")) {
		FunctionalAccount = paramValue		
		
	} else if (paramName.equals("LineDef")) {
		LineDef = paramValue
		
	} else if (paramName.equals("MaxReleaseDuration")) {
		MaxReleaseDuration = paramValue	
		
	} else if (paramName.equals("NetBiosName")) {
		NetBiosName = paramValue	
		
	} else if (paramName.equals("NetworkAddress")) {
		NetworkAddress = paramValue
		
	} else if (paramName.equals("NextChangeDate")) {
		NextChangeDate = paramValue

	} else if (paramName.equals("NonPrivFuncFlag")) {
		NonPrivFuncFlag = paramValue

	} else if (paramName.equals("OracleSIDSN")) {
		OracleSIDSN = paramValue
		
	} else if (paramName.equals("OracleType")) {
		OracleType = paramValue
	
	} else if (paramName.equals("PasswordCheckProfile")) {
		PasswordCheckProfile = paramValue

	} else if (paramName.equals("PasswordChangeProfile")) {
		PasswordChangeProfile = paramValue

	} else if (paramName.equals("PasswordRule")) {
		PasswordRule = paramValue
		
	} else if (paramName.equals("PlatformName")) {
		PlatformName = paramValue
		
	} else if (paramName.equals("PlatSpecificValue")) {
		PlatSpecificValue = paramValue

	} else if (paramName.equals("PortNumber")) {
		PortNumber = paramValue

	} else if (paramName.equals("PrimaryEmail")) {
		PrimaryEmail = paramValue

	} else if (paramName.equals("PSMDPAAffinity")) {
		PSMDPAAffinity = paramValue
		
	} else if (paramName.equals("PPMDPAAffinity")) {
		PPMDPAAffinity = paramValue
		
	} else if (paramName.equals("ReleaseChangeFlag")) {
		ReleaseChangeFlag = paramValue
		
	} else if (paramName.equals("ReleaseDuration")) {
		ReleaseDuration = paramValue
		
	} else if (paramName.equals("RequireMultiGroupApprovalFlag")) {
		RequireMultiGroupApprovalFlag = paramValue
		
	} else if (paramName.equals("RequireTicketForAPI")) {
		RequireTicketForAPI = paramValue
		
	} else if (paramName.equals("RequireTicketForCLI")) {
		RequireTicketForCLI = paramValue
		
	} else if (paramName.equals("RequireTicketForISA")) {
		RequireTicketForISA = paramValue
	
	} else if (paramName.equals("RequireTicketForPSM")) {
		RequireTicketForPSM = paramValue
	
	} else if (paramName.equals("RequireTicketForRequest")) {
		RequireTicketForRequest = paramValue
		
	} else if (paramName.equals("ResetFlag")) {
		ResetFlag = paramValue
		
	} else if (paramName.equals("RestartServiceFlag")) {
		RestartServiceFlag = paramValue
		
	} else if (paramName.equals("SSHAccount")) {
		SSHAccount = paramValue
		
	} else if (paramName.equals("SSHKey")) {
		SSHKey = paramValue
		
	} else if (paramName.equals("SSHPort")) {
		SSHPort = paramValue
		
	} else if (paramName.equals("SystemAutoFlag")) {
		SystemAutoFlag = paramValue
		
	} else if (paramName.equals("TicketSystemName")) {
		TicketSystemName = paramValue
		
	} else if (paramName.equals("TicketEmailNotify")) {
		TicketEmailNotify = paramValue		
	
	} else if (paramName.equals("Timeout")) {
		Timeout = paramValue	
		
	} else if (paramName.equals("UseSslFlag")) {
		UseSslFlag = paramValue	
		
	} else if (paramName.equals("UseSshFlag")) {
		UseSshFlag = paramValue	
	
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
        
	EDMZSystem sys = new EDMZSystem()

	if (!SystemName.equals("")) {
		sys.setSystemName(SystemName)
	}
	
	if (!NewSystemName.equals("")) {
		sys.setNewSystemName(NewSystemName)
	}
	
	if (!AllowISADurationFlag.equals("")) {
		sys.setAllowISADurationFlag(AllowISADurationFlag)
	}
	
	if (!AllowFuncReqFlag.equals("")) {
		sys.setAllowFuncReqFl(AllowFuncReqFlag)
	}
	
	if (!AutoDiscoveryTimeout.equals("")) {
		sys.setAutoDiscoveryTimeout(AutoDiscoveryTimeout)
	}
	
	if (!AutoDiscoveryExcludeList.equals("")) {
		sys.setAutoDiscoveryExcludeList(AutoDiscoveryExcludeList)
	}
	
	if (!AutoDiscoveryProfile.equals("")) {
		sys.setAutoDiscoveryProfile(AutoDiscoveryProfile)
	}
	
	if (!BoksServerOS.equals("")) {
		sys.setBoksServerOS(BoksServerOS)
	}
	
	if (!ChangeFrequency.equals("")) {
		sys.setChangeFrequency(new Integer(ChangeFrequency))
	}
	
	if (!ChangeTime.equals("")) {
	
		DateFormat df = new SimpleDateFormat("HH:mm")
        Calendar scal  = Calendar.getInstance()
        scal.setTime(df.parse(ChangeTime))
		sys.setChangeTime(scal)
	}
	
	if (!CheckFlag.equals("")) {
		sys.setCheckFl(CheckFlag)
	}

	if (!Custom1.equals("")) {
		sys.setCustom1(Custom1)
	}
	
	if (!Custom2.equals("")) {
		sys.setCustom2(Custom2)
	}
	
	if (!Custom3.equals("")) {
		sys.setCustom3(Custom3)
	}
	
	if (!Custom4.equals("")) {
		sys.setCustom4(Custom4)
	}
	
	if (!Custom5.equals("")) {
		sys.setCustom5(Custom5)
	}
	
	if (!Custom6.equals("")) {
		sys.setCustom6(Custom6)
	}
	
	if (!Description.equals("")) {
		sys.setDescription(Description)
	}
	
	if (!DomainFuncAccount.equals("")) {
		sys.setDomainFunctionalAccount(DomainFuncAccount)
	}
	
	if (!DomainName.equals("")) {
		sys.setDomainName(DomainName)
	}
	
	if (!EGPOnlyFlag.equals("")) {
		sys.setEGPOnlyFl(EGPOnlyFlag)
	}
	
	if (!EnablePassword.equals("")) {
		sys.setEnablePassword(EnablePassword)
	}
	
	if (!EscalationEmail.equals("")) {
		sys.setEscalationEmail(EscalationEmail)
	}
	
	if (!EscalationTime.equals("")) {
		sys.setEscalationTime(new Integer(EscalationTime))
	}

	if (!FuncAcctCred.equals("")) {
		sys.setFunctionalAcctCredentials(FuncAcctCred)
	}
	
	if (!FuncAcctDN.equals("")) {
		sys.setFuncAcctDN(FuncAcctDN)
	}
	
	if (!FunctionalAccount.equals("")) {
		sys.setFunctionalAccount(FunctionalAccount)
	}
	
	if (!LineDef.equals("")) {
		sys.setLineDef(LineDef)
	}

	if (!MaxReleaseDuration.equals("")) {
		sys.setMaxReleaseDuration(new Integer(MaxReleaseDuration))
	}
	
	if (!NetBiosName.equals("")) {
		sys.setNetBiosName(NetBiosName)
	}
	
	if (!NetworkAddress.equals("")) {
		sys.setNetworkAddress(NetworkAddress)
	}

	if (!NonPrivFuncFlag.equals("")) {
		sys.setNonPrivFuncFl(NonPrivFuncFlag)
	}
	
	if (!OracleSIDSN.equals("")) {
		sys.setOracleSIDSN(OracleSIDSN)
	}
	
	if (!OracleType.equals("")) {
		sys.setOracleType(OracleType)
	}

	if (!PasswordCheckProfile.equals("")) {
		sys.setPasswordCheckProfile(PasswordCheckProfile)
	}

	if (!PasswordChangeProfile.equals("")) {
		sys.setPasswordChangeProfile(PasswordChangeProfile)
	}

	if (!PasswordRule.equals("")) {
		sys.setPasswordRule(PasswordRule)
	}
	
	if (!PlatformName.equals("")) {
		sys.setPlatformName(PlatformName)
	}
	
	if (!PlatSpecificValue.equals("")) {
		sys.setPlatSpecificValue(PlatSpecificValue)
	}
	
	if (!PortNumber.equals("")) {
		sys.setPortNumber(PortNumber)
	}
	
	if (!PrimaryEmail.equals("")) {
		sys.setPrimaryEmail(PrimaryEmail)
	}
	
	if (!PSMDPAAffinity.equals("")) {
		sys.setPsmDpaAffinity(PSMDPAAffinity)
	}
	
	if (!PPMDPAAffinity.equals("")) {
		sys.setPpmDpaAffinity(PPMDPAAffinity)
	}
	
	if (!ReleaseChangeFlag.equals("")) {
		sys.setReleaseChgFl(ReleaseChangeFlag)
	}
	
	if (!ReleaseDuration.equals("")) {
		sys.setReleaseDuration( new Integer(ReleaseDuration))
	}
	
	if (!RequireTicketForAPI.equals("")) {
		sys.setRequireTicketForAPI(RequireTicketForAPI)
	}
	
	if (!RequireTicketForCLI.equals("")) {
		sys.setRequireTicketForCLI(RequireTicketForCLI)
	}
	
	if (!RequireTicketForISA.equals("")) {
		sys.setRequireTicketForISA(RequireTicketForISA)
	}
	
	if (!RequireTicketForPSM.equals("")) {
		sys.setRequireTicketForPSM(RequireTicketForPSM)
	}
	
    if (!RequireTicketForRequest.equals("")) {
		sys.setRequireTicketForRequest(RequireTicketForRequest)
	}
	
	if (!ResetFlag.equals("")) {
		sys.setResetFl(ResetFlag)
	}
	
	if (!SSHAccount.equals("")) {
		sys.setSSHAccount(SSHAccount)
	}
	
	if (!SSHKey.equals("")) {
		sys.setSSHKey(SSHKey)
	}
	
	if (!SSHPort.equals("")) {
		sys.setSSHPort(SSHPort)
	}	
	
	if (!SystemAutoFlag.equals("")) {
		sys.setSystemAutoFlag(SystemAutoFlag)
	}
	
	if (!SimulPrivAccReleases.equals("")) {
		sys.setSimulPrivAccReleases(SimulPrivAccReleases)
	}

//	if (!SystemName.equals("")) {
//		sys.setSystemName(SystemName)
//	}
	
	if (!TicketSystemName.equals("")) {
		sys.setTicketSystemName(TicketSystemName)
	}
	
	if (!TicketEmailNotify.equals("")) {
		sys.setTicketEmailNotify(TicketEmailNotify)
	}
	
	if (!Timeout.equals("")) {
		sys.setTimeout(new Integer(Timeout))
	}
	
	if (!UseSslFlag.equals("")) {
		sys.setUseSslFl(UseSslFlag)
	}
	
	if (!UseSshFlag.equals("")) {
		sys.setUseSSHflag(UseSshFlag)
	}
	
    IDResult res = new IDResult()
    acl.updateSystem(sys, res)
                
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
	
//	System.out.println(sys.getDomainFunctionalAccount())


} catch (IOException e) {

println(e)

} catch (InvalidStateException e) {

println(e)

} catch (InterruptedException e) {

println(e)

} finally {
	ac.disconnect()

}
  

