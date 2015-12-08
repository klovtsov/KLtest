package tpamjavaapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tpamjavaapi.tpamjavaapi;

import com.edmz.api.APIClient;
import com.edmz.api.APICommandLib;
import com.edmz.api.bo.AccessPolicyRequest;
import com.edmz.api.bo.Account;
import com.edmz.api.bo.AcctForRequest;
import com.edmz.api.bo.CodeMessageResult;
import com.edmz.api.bo.EDMZSystem;
import com.edmz.api.bo.IDResult;
import com.edmz.api.bo.ListResult;
import com.edmz.api.bo.PSMAccount;
import com.edmz.api.bo.Permission;
import com.edmz.api.bo.PwdRequest;
import com.edmz.api.bo.ReasonCode;
import com.edmz.api.bo.ReportActivity;
import com.edmz.api.bo.Request;
import com.edmz.api.bo.RequestDetail;
import com.edmz.api.bo.SessionRequest;
import com.edmz.api.bo.SynchronizedPwd;
import com.edmz.api.bo.User;
import com.edmz.api.filters.AcctsForRequestFilter;
import com.edmz.api.filters.EGPAccountFilter;
import com.edmz.api.filters.PSMAccountFilter;
import com.edmz.api.filters.PermissionsFilter;
import com.edmz.api.filters.ReportActivityFilter;
import com.edmz.api.filters.RequestDetailsFilter;
import com.edmz.api.filters.RequestFilter;
import com.edmz.api.filters.SessionRequestFilter;
import com.edmz.api.filters.UserFilter;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException;
import com.sshtools.j2ssh.util.Base64.InputStream;
import com.sshtools.j2ssh.util.InvalidStateException;


public class tpamjavaapi {
	public static void main(String[] args) throws IOException, InterruptedException {
		//String Result = RunExternalProgram(args[0]);
		//System.out.println(Result);
    	String Result = JavaAPI(args[0]);
    	System.out.println(Result);
	}
    
    private static Log log = LogFactory.getLog(tpamjavaapi.class);

    /**
     * 
     */
        /**
     * @param args
     */
    // public boolean JavaAppi(String[] Args) throws IOException, InterruptedException {
    // public static void main(String[] args) {
    
    
    
    
    public static String JavaAPI (String ArgString) throws IOException, InterruptedException {
    	
    	System.out.println("We are at the beginning of the JavaApi method!");
    	
    	String[] args = ArgString.split(";");
    	
    	String authFile = args[0];
    	
    	String cwd = new File( "." ).getCanonicalPath();
		authFile = cwd + "\\"+ authFile;
		    	
		System.out.println("authFile: " + authFile);
    	
        String user = args[1];
        System.out.println("User: " + user);
        
                        
        String ip = args[2];
        String operation = args[3];
        
        System.out.println("IP: " + ip);
        System.out.println("Operation: " + operation);
        
        String GetFinalMessage = "TPAM: default value";
       
        
        log.info("tpamtestjavaapi" + ":" + "JavaAPI"
                + " " + "ip: " + ip + " authFile: " + authFile + " user: " + user + " operation: " + operation);
        
        /*
        System.out.println(ip);
        System.out.println(authFile);
        System.out.println(user);
        System.out.println(operation);
		*/
        
        
        
        APIClient ac = new APIClient();
        
        try {
            ac.connect(ip);
            ac.authenticate(authFile, user);
        } catch (InvalidSshKeyException e) {
            System.out.println("InvalidSshKeyException: during connect to TPAM");
        } catch (IOException e) {
        	System.out.println("IOException: during connect to TPAM");
        } 
           
        
        try {
            SessionChannelClient scc = ac.createSessionChannel();
            APICommandLib acl = new APICommandLib(scc);
            
                        
                        
            if (operation.equals("addSystem")){
            
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	
                                
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                sys.setPlatformName(args[6]);
                sys.setFunctionalAccount(args[7]);
                
                sys.setFunctionalAcctCredentials(args[8]);
                
                int TimeOut = Integer.parseInt(args[9]);
                
                sys.setTimeout(TimeOut);
                
                acl.addSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);

                
                
                
            } else if (operation.equals("addSystemWinAD")){
            	
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	
            	            	
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                sys.setPlatformName(args[6]);
                sys.setFunctionalAccount(args[7]);
                sys.setFunctionalAcctCredentials(args[8]);
                
                int TimeOut = Integer.parseInt(args[9]);
                sys.setTimeout(TimeOut);
                
                sys.setDomainName(args[10]);
                sys.setNetBiosName(args[11]);
                                
                acl.addSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);
                
                
            } else if (operation.equals("addSystemOracle")){
            	
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	
            	            	
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                sys.setPlatformName(args[6]);
                sys.setFunctionalAccount(args[7]);
                sys.setFunctionalAcctCredentials(args[8]);
                
                int TimeOut = Integer.parseInt(args[9]);
                sys.setTimeout(TimeOut);
                
                sys.setPortNumber(args[10]);
                sys.setOracleType(args[11]);
                sys.setOracleSIDSN(args[12]);
                
                                
                acl.addSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);

                
            } else if (operation.equals("addSystemMySQL")){
            	
            	
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	
            	            	
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                sys.setPlatformName(args[6]);
                sys.setFunctionalAccount(args[7]);
                sys.setFunctionalAcctCredentials(args[8]);
                
                int TimeOut = Integer.parseInt(args[9]);
                sys.setTimeout(TimeOut);
                
                sys.setPortNumber(args[10]);
                                                
                acl.addSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);

            } else if (operation.equals("addSystemNISPlus")){
            	
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	
            	            	
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                sys.setPlatformName(args[6]);
                sys.setFunctionalAccount(args[7]);
                sys.setFunctionalAcctCredentials(args[8]);
                
                int TimeOut = Integer.parseInt(args[9]);
                sys.setTimeout(TimeOut);
                
                sys.setDomainName(args[10]);
                                                
                acl.addSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);                
                
                
                
            } else if (operation.equals("addSystemCisco")){
            	
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	
            	            	
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                sys.setPlatformName(args[6]);
                sys.setFunctionalAccount(args[7]);
                sys.setFunctionalAcctCredentials(args[8]);
                
                int TimeOut = Integer.parseInt(args[9]);
                sys.setTimeout(TimeOut);
                
                sys.setEnablePassword(args[10]);
                
                                
                acl.addSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);    
            
                
                
            } else if (operation.equals("deleteSystem")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	acl.deleteSystem(args[4], res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);    
                
                
                            
            } else if (operation.equals("addUser")) {
            	
            	User ouruser = new User();
            	ouruser.setUserName(args[4]);
            	ouruser.setFirstName(args[5]);
            	ouruser.setLastName(args[6]);
            	
            	IDResult res = new IDResult();
            	
            	acl.addUser(ouruser, res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);
            	
            	            
            } else if (operation.equals("deleteUser")) {
        	            	            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	acl.deleteUser(args[4], res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);

                
            } else if (operation.equals("addAccount")) {
            	
            	Account ouraccount = new Account();
            	
            	ouraccount.setSystem(args[4]);
            	ouraccount.setAccount(args[5]);
            	ouraccount.setPassword(args[6]);
            	
            	System.out.println("Account Password: " + args[6]);
            	
            	// ToDo: accept next change date, parse it and assign as an option for addAccount
            	// String[] NextChangeDateString = args[7].split("/");
            	
            	Calendar ourNextChangeDate = Calendar.getInstance();
            	ourNextChangeDate.set(Calendar.YEAR,2021);
            	ourNextChangeDate.set(Calendar.MONTH,Calendar.FEBRUARY);
            	ourNextChangeDate.set(Calendar.DAY_OF_MONTH,25);
            	
            	// Update for Vasabi: add Description
            	ouraccount.setDescription(args[8]);
            	

            	ouraccount.setNextChangeDt(ourNextChangeDate);
            	ouraccount.setResetFl("Y");
            	
            	ouraccount.setAccountAutoFl("Y");
            	
            	IDResult res = new IDResult();
            	
            	acl.addAccount(ouraccount, res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);    
            
                
            } else if (operation.equals("deleteAccount")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	acl.deleteAccount(args[4], args[5], res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);       

                
               
            } else if (operation.equals("testSystem")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	List TestSystemList = new ArrayList();
            	
            	acl.testSystem(args[4], res, TestSystemList);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println("testSystem:" + GetFinalMessage); 
                
                /*
                if (GetFinalMessage.matches("uccess")) {
                	System.out.println(GetFinalMessage + " MATCHES uccess inside JavaAppi");
                } else {
                	System.out.println(GetFinalMessage + " DOESN'T MATCH uccess inside JavaAppi");
                }
                */
                
                
            
            } else if (operation.equals("setPassword")) {
            	
            	Account ouraccount = new Account();
            	
            	ouraccount.setSystem(args[4]);
            	ouraccount.setAccount(args[5]);
            	ouraccount.setPassword(args[6]);
            	
            	IDResult res = new IDResult();
            	
            	acl.updateAccount(ouraccount, res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);      

            /*    
            } else if (operation.equals("scheduleResetPassword")) {
            	
            	Account ouraccount = new Account();
            	
            	ouraccount.setSystem(args[4]);
            	ouraccount.setAccount(args[5]);
            	
            	//ToDO; calendar
            	//ouraccount.setChangeTime(args[6]);
            	
            	IDResult res = new IDResult();
            	
            	acl.updateAccount(ouraccount, res);
            	
            	String GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);
            */    

            } else if (operation.equals("resetPassword")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	List ResetPasswordList = new ArrayList();
            	
            	acl.forceReset(args[4], args[5], res, ResetPasswordList);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);     
                
                Integer returncode = res.getReturnCode(); 
                System.out.println("Return code: " + returncode);
                
                // Hack: some systems (Linux, 4ex) doesn't return failed return message.
                
                if (returncode == 1) {
                	GetFinalMessage = "Failure";
                }

                if (acl.getErrorString().trim().length() == 0) {

                    System.out.println("ResetPassword: no errors, it worked!");  

                } else {

                    System.out.println("ResetPassword. Command failed --> " + acl.getErrorString()); 

                }
            

            } else if (operation.equals("checkPassword")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	List CheckPasswordList = new ArrayList();
            	
            	acl.checkPassword(args[4], args[5], res, CheckPasswordList);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);

                
            } else if (operation.equals("updateSystem")){
            	
            	EDMZSystem sys = new EDMZSystem();
                IDResult res = new IDResult();
            	            	            	
            	sys.setSystemName(args[4]);
                sys.setNetworkAddress(args[5]);
                                                
                acl.updateSystem(sys, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);

                
            } else if (operation.equals("setAccessPolicy")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
        	
            	AccessPolicyRequest ourpolicy = new AccessPolicyRequest();
            	
            	ourpolicy.setAccessPolicyName(args[4]);
            	ourpolicy.setAction(args[5]);
            	ourpolicy.setSystemName(args[6]);
            	ourpolicy.setAccountName(args[7]);
            	ourpolicy.setUserName(args[8]);
            	
            	acl.setAccessPolicy(ourpolicy,res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);     

                
            } else if (operation.equals("addPwdRequest")){
            	
            	IDResult res = new IDResult();
            	
            	PwdRequest ourrequest = new PwdRequest();
            	
            	ourrequest.setSystemName(args[4]);
            	ourrequest.setAccountName(args[5]);
            	ourrequest.setForUserName(args[6]);
            	ourrequest.setRequestNotes(args[7]);
            	                                
                acl.addPwdRequest(ourrequest, res);
                
                GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);    
                
            
           } else if (operation.equals("approve")) {
        	   
            	CodeMessageResult res = new CodeMessageResult();
            	String RequestID = args[4];
            	String ApproveComment = args[5];

            	acl.approveRequest(RequestID, ApproveComment, res);
           	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);     
    

//    

            } else if (operation.equals("retrieve")) {
            	
                
            	CodeMessageResult res = new CodeMessageResult();
            	String RequestID = args[4];
            	// Two additional parameters: Time Required and Comment
            	
            	//acl.retrievePassword(args[4],args[5],args[6],args[7],res);
            	acl.retrievePassword(RequestID,res);
            	
            	GetFinalMessage = res.getMessage();
            	if (GetFinalMessage == null) GetFinalMessage = "NullPasswordReceived";
//            	System.out.println("Password before cut: \"" + GetFinalMessage + "\"");
            	if (GetFinalMessage.length() > 0) GetFinalMessage = GetFinalMessage.substring(0,GetFinalMessage.length()-1);
                System.out.println(GetFinalMessage);     

               
                
                
            	} else if (operation.equals("reasonCodes")) {
            	
            		ListResult res = new ListResult();
                    List<ReasonCode> reasonCodes = new ArrayList<ReasonCode>();

                    acl.listReasonCodes(res, (List<ReasonCode>) reasonCodes);
                    
                    Iterator<ReasonCode> iter = reasonCodes.iterator();

                    while (iter.hasNext()) {
                      ReasonCode element = (ReasonCode) iter.next();
                      System.out.println("reasoncodename: " + element.getReasonCodeName() + " description: " + element.getDescription());
                      // System.out.println(element.toString());
                    }

            	} else if (operation.equals("reportActivity")) {
                	
            		ListResult res = new ListResult();
                    List reportActivities = new ArrayList();
                    ReportActivityFilter filter = new ReportActivityFilter();
                    
                    // filter.setMaxRows(15);

                    acl.reportActivity(filter, res, reportActivities);
                    
                    Iterator iter = reportActivities.iterator();

                    while (iter.hasNext()) {
                      ReportActivity element = (ReportActivity) iter.next();
                      System.out.println("User Name: " + element.getUserName() + " Operation: " + element.getOperation());
                      // System.out.println(element.toString());
                    }    
            	
            	} else if (operation.equals("updateSystemYK")){
                	
                	EDMZSystem sys = new EDMZSystem();
                    IDResult res = new IDResult();
                	            	            	
                	sys.setSystemName("ykWinTest");
                    sys.setNetworkAddress("1.2.3.5");
                    sys.setAutoDiscoveryProfile("adwin7");
                    sys.setAutoDiscoveryExcludeList("abc bcd efghss");
                    sys.setCustom1("Hmm");
                                                    
                    acl.updateSystem(sys, res);
                    
                    GetFinalMessage = res.getMessage();
                    System.out.println(GetFinalMessage);  
                    
            	} else if (operation.equals("updateUserYK")){
                	
                	User ouruser = new User();
                    IDResult res = new IDResult();
                	
                    ouruser.setUserName("yk_ui");
                    ouruser.setLastName("Lucky");
                    ouruser.setCustom1("blabla");
                    ouruser.setCustom2("hmm asfaf");
                    ouruser.setMobile("11-000-345678");
                                        
                                                    
                    acl.updateUser(ouruser, res);
                    
                    GetFinalMessage = res.getMessage();
                    System.out.println(GetFinalMessage);  
                                        
                    
            	} else if (operation.equals("listUsers")) {
                	
            		ListResult res = new ListResult();
                    List ourusers = new ArrayList();
                    UserFilter filter = new UserFilter();
                    
                    // filter.setMaxRows(15);

                    acl.listUsers (filter, res, ourusers);
                    
                    Iterator iter = ourusers.iterator();

                    while (iter.hasNext()) {
                      User element = (User) iter.next();
                      System.out.println("User Name: " + element.getUserName() + " Last Name: " + element.getLastName() + " Custom1: " + element.getCustom1() + " Custom2: " + element.getCustom2());
                      // System.out.println(element.toString());
                    }    
            	} else if (operation.equals("updateAccountYK")) {
                	
                	Account ouraccount = new Account();
                	
                	ouraccount.setSystem("ykWinTest");
                	ouraccount.setAccount("yk5");
                	ouraccount.setCustom1 ("first safasdf asdfaf");
                	ouraccount.setCustom2 ("second");
                	ouraccount.setChangeTaskFlag("N");
                	
                	IDResult res = new IDResult();
                	
                	acl.updateAccount(ouraccount, res);
                	
                	GetFinalMessage = res.getMessage();
                    System.out.println(GetFinalMessage);    
                
            	} else if (operation.equals("listPSMAccounts")) {
                	
                	ListResult res = new ListResult();
                	PSMAccountFilter filter = new PSMAccountFilter();
                	List<PSMAccount> psmAccounts = new ArrayList<PSMAccount>(); 
                	
                	//filter.setSystemName("ykspb9449");
                	filter.setMaxRows(15);
                	filter.setAccountPsmFlag("Y");
                	
                	acl.listPSMAccounts(filter, res, psmAccounts);
                	                	
                    Iterator<PSMAccount> iter = psmAccounts.iterator();
                    
                    while (iter.hasNext()) {
                        PSMAccount element = (PSMAccount) iter.next();
                        System.out.println(element.getAccountName());
                        System.out.println(element.getSystemName());
                        System.out.println("=====================");
                    }
                
            	} else if (operation.equals("listAcctsForSessionRequest")) {
                	
                	ListResult res = new ListResult();
                	AcctsForRequestFilter filter = new AcctsForRequestFilter();
                	List<AcctForRequest> psmAccounts = new ArrayList<AcctForRequest>(); 
                	
                	filter.setSystemName("ykspb9449");
                	filter.setMaxRows(15);
                	
                	
                	acl.listAcctsForSessionRequest(filter, res, psmAccounts);
                	                	
                    Iterator<AcctForRequest> iter = psmAccounts.iterator();
                    
                    while (iter.hasNext()) {
                    	AcctForRequest element = (AcctForRequest) iter.next();
                        System.out.println(element.getSystem());
                        System.out.println(element.getAccount());
                        System.out.println("=====================");
                    }
                   
            	} else if (operation.equals("listAcctsForPwdRequest")) {
                	
                	ListResult res = new ListResult();
                	AcctsForRequestFilter filter = new AcctsForRequestFilter();
                	List<AcctForRequest> ppmAccounts = new ArrayList<AcctForRequest>(); 
                	
                	filter.setSystemName("ykspb9449");
                	filter.setMaxRows(15);
                	
                	
                	acl.listAcctsForPwdRequest(filter, res, ppmAccounts);
                	                	
                    Iterator<AcctForRequest> iter = ppmAccounts.iterator();
                    
                    while (iter.hasNext()) {
                    	AcctForRequest element = (AcctForRequest) iter.next();
                        System.out.println(element.getSystem());
                        System.out.println(element.getAccount());
                        System.out.println("=====================");
                    }
                 
           	} else if (operation.equals("getPwdRequest")){
                	
            		ListResult res = new ListResult();
                	
                	PwdRequest myrequest = new PwdRequest();
               
                	// myrequest.setRequestID(args[4]);
                	
                	String RequestID = args[4];
                	                	               
                	acl.getPwdRequest(RequestID, res, myrequest);
                	
                	String RequestStatus = myrequest.getRequestStatus();
                	int MinApprovers = myrequest.getMinApprovers();
                	
                	System.out.println("RequestStatus:" + RequestStatus);
                	System.out.println("MinApprovers:" + MinApprovers);                                	
                    
                    GetFinalMessage = res.getMessage();
                    System.out.println(GetFinalMessage);  
              
                    
            	} else if (operation.equals("getSessionRequest")){
                	
            		ListResult res = new ListResult();
                	
                	SessionRequest myrequest = new SessionRequest();
               
                	// myrequest.setRequestID(args[4]);
                	                	               
                	String RequestID = args[4];
                	acl.getSessionRequest(RequestID, res, myrequest);
                	
                	String RequestStatus = myrequest.getStatus();
                	String MinPasswordApprovers = myrequest.getMinPasswordApprovers();
                	String MinSessionApprovers = myrequest.getMinSessionApprovers();
                	
                	System.out.println("RequestStatus:" + RequestStatus);
                	System.out.println("MinPasswordApprovers:" + MinPasswordApprovers);
                	System.out.println("MinSessionApprovers:" + MinSessionApprovers); 
                    
                    GetFinalMessage = res.getMessage();
                    System.out.println(GetFinalMessage); 
                   
            	} else if (operation.equals("listRequestDetails")){
                	
            		ListResult res = new ListResult();
                    List requestDetails = new ArrayList();
                    RequestDetailsFilter filter = new RequestDetailsFilter();
                    
                    filter.setStatus("All");
                    filter.setRequestorName("User=Myself");

                    acl.listRequestDetails(filter, res, requestDetails);
                    
                    Iterator iter = requestDetails.iterator();

                    while (iter.hasNext()) {
                      RequestDetail element = (RequestDetail) iter.next();
                      System.out.println("RequestID: " + element.getRequestID() + " MinApprovers: " + element.getMinApprovers());
                      // System.out.println(element.toString());
                    }    
            	
            	} else if (operation.equals("listSessionRequestDetails")){
                	
            		ListResult res = new ListResult();
                    List requestDetails = new ArrayList();
                    SessionRequestFilter filter = new SessionRequestFilter();
                    
                    filter.setStatus("All");
                    filter.setRequestorName("User=Myself");

                    acl.listSessionRequestDetails(filter, res, requestDetails);
                    
                    Iterator iter = requestDetails.iterator();

                    while (iter.hasNext()) {
                      SessionRequest element = (SessionRequest) iter.next();
                      System.out.println("RequestID: " + element.getRequestID() + " MinPasswordApprovers: " + element.getMinPasswordApprovers()+ " MinSessionApprovers: " + element.getMinSessionApprovers()+ " SystemName: " + element.getSystemName());
                      // System.out.println(element.toString());
                    }  
                    
            	} else if (operation.equals("listRequest")){
                	
            		ListResult res = new ListResult();
                    List requestDetails = new ArrayList();
                    RequestFilter filter = new RequestFilter();
                    
                    filter.setStatus("All");
                    filter.setRequestorName("User=Myself");

                    acl.listRequest(filter, res, requestDetails);
                    
                    Iterator iter = requestDetails.iterator();

                    while (iter.hasNext()) {
                      Request element = (Request) iter.next();
                      System.out.println("RequestID: " + element.getRequestID() + " Status: " + element.getStatus());
                      // System.out.println(element.toString());
                    }   
                    
            	} else if (operation.equals("listSessionRequest")){
                	
            		ListResult res = new ListResult();
                    List requestDetails = new ArrayList();
                    SessionRequestFilter filter = new SessionRequestFilter();
                    
                    filter.setStatus("All");
                    filter.setRequestorName("User=Myself");

                    acl.listSessionRequest(filter, res, requestDetails);
                    
                    Iterator iter = requestDetails.iterator();

                    while (iter.hasNext()) {
                      SessionRequest element = (SessionRequest) iter.next();
                      System.out.println("RequestID: " + element.getRequestID() + " Status: " + element.getStatus());
                      // System.out.println(element.toString());
                    }
                    
            	} else if (operation.equals("addSyncPass")){
                    
            		SynchronizedPwd sp = new SynchronizedPwd();
                    sp.setSyncPassName("APISyncPass");
                    sp.setPassword("APIpw1W2");
                    sp.setChangeFrequency(new Integer("-1"));
                    
                    Calendar changeTimeCalendar = Calendar.getInstance();
                    changeTimeCalendar.set(Calendar.HOUR_OF_DAY, 3);
                    changeTimeCalendar.set(Calendar.MINUTE, 30);
                    sp.setChangeTime(changeTimeCalendar);
                    
                    Calendar changeDateCalendar = Calendar.getInstance();
                    changeDateCalendar.add(Calendar.DATE, +5);
                    sp.setNextChangeDate(changeDateCalendar);
                    
                    sp.setReleaseNotifyEmail("API_Test@test.com");
                    sp.setCheckFlag("N");
                    sp.setResetFlag("N");
                    sp.setReleaseChangeFlag("N");
                    sp.setDescription("API test sync password.");
                    sp.setDisabledFl("N");
                   
                    CodeMessageResult res = new CodeMessageResult();
                    acl.addSyncPass(sp, res);
                    
                    GetFinalMessage = res.getMessage();
                    System.out.println(GetFinalMessage); 
  		
                
            } else {
            	GetFinalMessage = "TPAM: Unknown operation!";
                System.out.println("TPAM: Unknown operation!");
            }
            	
            

        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.error(sw.toString());
        } catch (InvalidStateException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.error(sw.toString());
        } catch (InterruptedException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.error(sw.toString());
        } finally {
            ac.disconnect();
         	
        }
        //Comment for Vasabi: transformation is going to be made in verify_contain_or
        //GetFinalMessage = GetFinalMessage.replaceAll("[^A-Za-z0-9 ]", "");
      GetFinalMessage = GetFinalMessage.replaceAll("\n\r", "");
        return (GetFinalMessage);
    }
}


