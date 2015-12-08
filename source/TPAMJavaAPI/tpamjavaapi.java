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

import com.edmz.api.APIClient;
import com.edmz.api.APICommandLib;
import com.edmz.api.bo.AccessPolicyRequest;
import com.edmz.api.bo.Account;
import com.edmz.api.bo.CodeMessageResult;
import com.edmz.api.bo.EDMZSystem;
import com.edmz.api.bo.IDResult;
import com.edmz.api.bo.ListResult;
import com.edmz.api.bo.Permission;
import com.edmz.api.bo.PwdRequest;
import com.edmz.api.bo.User;
import com.edmz.api.filters.PermissionsFilter;

import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException;
import com.sshtools.j2ssh.util.Base64.InputStream;
import com.sshtools.j2ssh.util.InvalidStateException;




public class tpamjavaapi {
    
    private static Log log = LogFactory.getLog(tpamjavaapi.class);

    /**
     * 
     */
    public tpamjavaapi() {
        // Auto-generated constructor stub
    	// System.out.println("We are at the beginning of the main!");
    	// System.out.println("Constructor tpamjavaapi!");
    }

    /**
     * @param args
     * @throws InterruptedException 
     * @throws IOException 
     */
    // public boolean JavaAppi(String[] Args) throws IOException, InterruptedException {
     public static void main(String[] args) throws IOException, InterruptedException 
     { 
    	 tpamjavaapi t = new tpamjavaapi();
    	 t.JavaAPI(args[0]);
     }
    
    public static String JavaAPI (String ArgString) throws IOException, InterruptedException {
    	
    	System.out.println("We are at the beginning of the JavaApi method!");
    	    	
    	String[] args = ArgString.split(";");
    	
    	String authFile = args[0];
    	
    	String cwd = new File( "." ).getCanonicalPath();
		authFile = cwd + "\\scripts\\keys\\" + authFile;
		    	
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
        }

        catch (InvalidSshKeyException e) {
            System.out.println("InvalidSshKeyException: during connect to TPAM");
        } 

    	catch (IOException e) {
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
            
            } else if (operation.equals("addSystemLDAP")){
            	
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
                sys.setFuncAcctDN(args[11]);
                                
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
            	
            	Calendar ourNextChangeDate = Calendar.getInstance();
            	ourNextChangeDate.set(Calendar.YEAR,2021);
            	ourNextChangeDate.set(Calendar.MONTH,Calendar.FEBRUARY);
            	ourNextChangeDate.set(Calendar.DAY_OF_MONTH,25);
            	
            	ouraccount.setDescription(args[8]);
            	
            	ouraccount.setNextChangeDt(ourNextChangeDate);
            	ouraccount.setResetFl("Y");
            	
            	ouraccount.setAccountAutoFl("Y");
            	            	
            	IDResult res = new IDResult();
            	
            	acl.addAccount(ouraccount, res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);    

                
            } else if (operation.equals("addPpmAccount")) {
            	
            	Account ouraccount = new Account();
            	
            	ouraccount.setSystem(args[4]);
            	ouraccount.setAccount(args[5]);
            	ouraccount.setPassword(args[6]);
            	ouraccount.setDescription(args[7]);
            	            	
            	ouraccount.setPasswordChangeProfile(args[8]);
            	ouraccount.setPasswordCheckProfile(args[9]);
            	            	            	
            	ouraccount.setAccountAutoFl("Y");
            	            	
            	IDResult res = new IDResult();
            	
            	acl.addAccount(ouraccount, res);
            	
            	GetFinalMessage = res.getMessage();
                System.out.println(GetFinalMessage);        
                
            } else if (operation.equals("addPpmAccountLDAP")) {
            	
            	Account ouraccount = new Account();
            	
            	ouraccount.setSystem(args[4]);
            	ouraccount.setAccount(args[5]);
            	ouraccount.setPassword(args[6]);
            	ouraccount.setDescription(args[7]);
            	ouraccount.setAccountDn(args[7]);
            	
            	ouraccount.setPasswordChangeProfile(args[8]);
            	ouraccount.setPasswordCheckProfile(args[9]);
            	
            	            	            	
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
    

            } else if (operation.equals("retrieve")) {
            	
            	CodeMessageResult res = new CodeMessageResult();
            	String RequestID = args[4];
            	// Two additional parameters: Time Required and Comment
            	
            	//acl.retrievePassword(args[4],args[5],args[6],args[7],res);
            	acl.retrievePassword(RequestID,res);
            	
            	GetFinalMessage = res.getMessage();
            	if (GetFinalMessage == null) GetFinalMessage = "NullPasswordReceived";
//            	System.out.println("Password before cut: \"" + GetFinalMessage + "\"");
//            	if (GetFinalMessage.length() > 0) GetFinalMessage = GetFinalMessage.substring(0,GetFinalMessage.length()-1);
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
        if (GetFinalMessage != null) GetFinalMessage = GetFinalMessage.replaceAll("\n\r", "");
        return (GetFinalMessage);
    }
}
