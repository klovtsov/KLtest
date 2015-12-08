package tpamjavasshkey;


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
import com.edmz.api.bo.SSHKey;
import com.edmz.api.bo.User;
import com.edmz.api.filters.PermissionsFilter;
import com.edmz.api.bo.UserSshKey;

import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException;
import com.sshtools.j2ssh.util.Base64.InputStream;
import com.sshtools.j2ssh.util.InvalidStateException;




public class tpamjavasshkey {
    
    private static Log log = LogFactory.getLog(tpamjavasshkey.class);

    /**
     * 
     */
    public tpamjavasshkey() {
        // Auto-generated constructor stub
    	// System.out.println("We are at the beginning of the main!");
    	// System.out.println("Constructor tpamjavaapi!");
    }

    /**
     * @param args
     * @throws InterruptedException 
     * @throws IOException 
     */
    
     public static void main(String[] args) throws IOException, InterruptedException 
     { 
    	     	
    	System.out.println("Command line parameters: ");
    	
    	String GetFinalMessage = "TPAM: default value";
    	    	
    	String authFile = "";
    	String cwd = new File( "." ).getCanonicalPath();
    	
        String user = "";
        String ip = "";
        
        String keyFormat = "";
        String standardKey = "";
        String systemName = "";
        String accountName = "";
        String regenerate = "";

        
        for (String s: args) {
        	
            String[] parameter = s.split("=");
            String paramName = parameter[0];
            String paramValue = parameter[1];
            
            System.out.println(paramName + "=" + paramValue);
            
            if (paramName.equals("authFile")){
        		authFile = cwd + "\\" + paramValue;
        		
            } else if (paramName.equals("apiUser")) {
            	user = paramValue;
            	
            } else if (paramName.equals("TPAM")) {
            	ip = paramValue;
            	
            } else if (paramName.equals("keyFormat")) {
            	keyFormat = paramValue;
            	
            } else if (paramName.equals("standardKey")) {
            	standardKey = paramValue;
            	
            } else if (paramName.equals("systemName")) {
            	systemName = paramValue;
            	
            } else if (paramName.equals("accountName")) {
            	accountName = paramValue;
            	
            } else if (paramName.equals("regenerate")) {
            	regenerate = paramValue;
            	
            } else {
            	System.out.println("Unknown option: " + paramName + "=" + paramValue);
            }
        }
        
        
        //System.out.println("authFile: " + authFile + "; user: " + user + "; ip: " + ip);
        
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
            
            SSHKey key = new SSHKey();            
            
            if (!keyFormat.equals("")) {
               System.out.println("keyFormat used!");
               key.setKeyFormat(keyFormat);
            }
            
            if (!standardKey.equals("")) {
            	System.out.println("standardKey used!");
            	key.setStandardKey(standardKey);
            }
            
            if (!regenerate.equals("")) {
            	System.out.println("Regenerate used!");
            	key.setRegenerate(regenerate);
            }
            
            if (!systemName.equals("")) {
            	System.out.println("systemName used!");
            	key.setSystemName(systemName);
            }
            
            if (!accountName.equals("")) {
            	System.out.println("accountName used!");
            	key.setAccountName(accountName);
            }

            
            CodeMessageResult res = new CodeMessageResult();
            acl.sshKey(key, res);           
                        
               
            GetFinalMessage = res.getMessage();
            System.out.println(GetFinalMessage);
            String ourkey = key.getSshKey();

            System.out.println("Below is going to be our key:\n\n");
            
            System.out.println(ourkey);
            

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
        
       
     }
}
