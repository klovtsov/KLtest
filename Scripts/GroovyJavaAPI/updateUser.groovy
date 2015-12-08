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
import com.edmz.api.filters.UserFilter;
import com.edmz.api.bo.User


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


Custom1=""
Custom2=""
Custom3=""
Custom4=""
Custom5=""
Custom6=""
Description=""
DisableFl="" 
DstFlag="" 
EmailAddress=""  
ExtAuthSystem=""
ExtAuthType=""
ExtAuthUserID=""
FirstName=""
InitialPassword=""
LastAccessDate=""
LastName=""
LocalTimezone=""
LogonDays=""
LogonHours=""
LogonHoursFL=""
LogonHoursValues=""
Mobile=""
MobileAllowedFlag=""
Pager=""
Password=""
Phone=""
PrimaryAuthExtra=""
PrimAuthSystem=""
PrimAuthType=""
PrimAuthUserID=""
SecondaryAuth=""
SecondaryAuthSystem=""
SecondaryUserID=""
TemplateUserName=""
UserInterface=""
UserType=""
certThumbprint=""

println("Command line parameters: ")

GetFinalMessage = "TPAM: default value"
		
authFile = ""
cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"    
       
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
		
	} else if (paramName.equals("UserName")) {
		UserName = paramValue
		
	} else if (paramName.equals("LastName")) {
		LastName = paramValue		
		
	} else if (paramName.equals("FirstName")) {
		FirstName = paramValue		
	
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
		
	} else if (paramName.equals("DisableFl")) {
		DisableFl = paramValue
		
	} else if (paramName.equals("DstFlag")) {
		DstFlag = paramValue
		
	} else if (paramName.equals("EmailAddress")) {
		EmailAddress = paramValue
		
	} else if (paramName.equals("ExtAuthSystem")) {
		ExtAuthSystem = paramValue
		
	} else if (paramName.equals("InitialPassword")) {
		InitialPassword = paramValue
		
	} else if (paramName.equals("LastAccessDate")) {
		LastAccessDate = paramValue
		
	} else if (paramName.equals("LocalTimezone")) {
		LocalTimezone = paramValue	

	} else if (paramName.equals("LogonDays")) {
		LogonDays = paramValue		
		
	} else if (paramName.equals("LogonHours")) {
		LogonHours = paramValue
		
	} else if (paramName.equals("LogonHoursFL")) {
		LogonHoursFL = paramValue	
		
	} else if (paramName.equals("LogonHoursValues")) {
		LogonHoursValues = paramValue	
		
	} else if (paramName.equals("Mobile")) {
		Mobile = paramValue
		
	} else if (paramName.equals("MobileAllowedFlag")) {
		MobileAllowedFlag = paramValue

	} else if (paramName.equals("Phone")) {
		Phone = paramValue

	} else if (paramName.equals("Password")) {
		Password = paramValue
		
	} else if (paramName.equals("PrimAuthSystem")) {
		PrimAuthSystem = paramValue
		
	} else if (paramName.equals("PrimaryAuthExtra")) {
		PrimaryAuthExtra = paramValue
		
	} else if (paramName.equals("PrimAuthType")) {
		PrimAuthType = paramValue

	} else if (paramName.equals("PrimAuthUserID")) {
		PrimAuthUserID = paramValue

	} else if (paramName.equals("SecondaryAuth")) {
		SecondaryAuth = paramValue
		
	} else if (paramName.equals("SecondaryAuthSystem")) {
		SecondaryAuthSystem = paramValue
		
	} else if (paramName.equals("SecondaryUserID")) {
		SecondaryUserID = paramValue
		
	} else if (paramName.equals("UserInterface")) {
		UserInterface = paramValue
		
	} else if (paramName.equals("UserType")) {
		UserType = paramValue
	
	} else if (paramName.equals("CertThumbprint")) {
		certThumbprint = paramValue
	}
	 else {
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
        
	User user = new User()

	if (!UserName.equals("")) {
		user.setUserName(UserName)
	}
	
	if (!LastName.equals("")) {
		user.setLastName(LastName)
	}
	
	if (!FirstName.equals("")) {
		user.setFirstName(FirstName)
	}
	

	
//	if (!ChangeTime.equals("")) {
//	
//		DateFormat df = new SimpleDateFormat("HH,mm")
//      Calendar scal  = Calendar.getInstance()
//      scal.setTime(df.parse(ChangeTime))
//		sys.setChangeTime(scal)
//	}
	


	if (!Custom1.equals("")) {
		user.setCustom1(Custom1)
	}
	
	if (!Custom2.equals("")) {
		user.setCustom2(Custom2)
	}
	
	if (!Custom3.equals("")) {
		user.setCustom3(Custom3)
	}
	
	if (!Custom4.equals("")) {
		user.setCustom4(Custom4)
	}
	
	if (!Custom5.equals("")) {
		user.setCustom5(Custom5)
	}
	
	if (!Custom6.equals("")) {
		user.setCustom6(Custom6)
	}
	
	if (!Description.equals("")) {
		user.setDescription(Description)
	}
	
	if (!DisableFl.equals("")) {
		user.setDisableFl(DisableFl)
	}
	
	if (!EmailAddress.equals("")) {
		user.setEmailAddress(EmailAddress)
	}
	
	
	if (!InitialPassword.equals("")) {
		user.setInitialPassword(InitialPassword)
	}

	if (!LocalTimezone.equals("")) {
		user.setLocalTimezone(LocalTimezone)
	}
	
	if (!LogonDays.equals("")) {
		user.setLogonDays(LogonDays)
	}

	if (!LogonHours.equals("")) {
		user.setLogonHours(LogonHours)
	}
	
	if (!LogonHoursFL.equals("")) {
		user.setLogonHoursFL(LogonHoursFL)
	}

	if (!Mobile.equals("")) {
		user.setMobile(Mobile)
	}
	
	if (!MobileAllowedFlag.equals("")) {
		user.setMobileAllowedFlag(MobileAllowedFlag)
	}
	
	if (!Password.equals("")) {
		user.setPassword(Password)
	}
	
	if (!Phone.equals("")) {
		user.setPhone(Phone)
	}
	
	if (!PrimAuthSystem.equals("")) {
		user.setPrimAuthSystem(PrimAuthSystem)
	}
	
	if (!PrimaryAuthExtra.equals("")) {
		user.setPrimaryAuthExtra(PrimaryAuthExtra)
	}
	
	if (!PrimAuthType.equals("")) {
		user.setPrimAuthType(PrimAuthType)
	}
	
	if (!PrimAuthUserID.equals("")) {
		user.setPrimAuthUserID(PrimAuthUserID)
	}
	
	if (!SecondaryUserID.equals("")) {
		user.setSecondaryUserID(SecondaryUserID)
	}
	
	if (!SecondaryAuth.equals("")) {
		user.setSecondaryAuth(SecondaryAuth)
	}
	
	if (!SecondaryAuthSystem.equals("")) {
		user.setSecondaryAuthSystem(SecondaryAuthSystem)
	}
	
	if (!UserInterface.equals("")) {
		user.setUserInterface(UserInterface)
	}
	
	if (!UserType.equals("")) {
		user.setUserType(UserType)
	}
    
	if (!certThumbprint.equals("")) {
		user.setCertThumbprint(certThumbprint)
	}
	
    IDResult res = new IDResult()
    acl.updateUser(user, res)
                
    GetFinalMessage = res.getMessage()
    System.out.println(GetFinalMessage)
	
} catch (IOException e) {

println(e)

} catch (InvalidStateException e) {

println(e)

} catch (InterruptedException e) {

println(e)

} finally {
	ac.disconnect()

}
  

