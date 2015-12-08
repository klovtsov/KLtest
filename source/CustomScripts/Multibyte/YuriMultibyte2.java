
/**
 *  Copyright 2013 by Dell Software.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information
 *  of Dell Software.
 *
 * @author ykuniver (thanks to bbaske)
 */

package com.edmz.api.testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.edmz.api.APIClient;
import com.edmz.api.APICommandLib;
import com.edmz.api.bo.Account;
import com.edmz.api.bo.CodeMessageResult;
import com.edmz.api.bo.IDResult;
import com.edmz.api.bo.ListResult;
import com.edmz.api.bo.SynchronizedPwd;
import com.edmz.api.filters.AccountFilter;
import com.sshtools.j2ssh.session.SessionChannelClient;

public class YuriMultibyte2 extends TestCase {

	public YuriMultibyte2() {
		// TODO Auto-generated constructor stub
	}

	public YuriMultibyte2(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

    public void testYuriMultibyte2() throws Exception {
        
    	APIClient ac = APITestSetUp.getClient();
    	
    	String currentDir = "E:\\Work\\TPAM\\TPAMJavaMultibyte\\Result\\";
	 	String OutputFileUTF8 = currentDir + "java.output.utf8.2.906.txt";
	 	
	 	String systemName = "ykspb9449";
	 	String accountName = "yk65"; //yk125 for 10.30.44.213

	 	String[] descriptions  = {
	 			"\u0048\u0065\u006C\u006C\u006F\u0021",
	 			"\u00A9","\u00A7","\u00A9\u00A7",
	 			"\u00C5","\u00D0","\u00C5\u00D0",
	 			"\u0048\u0065\u006C\u006C\u00C5\u006F\u0021\u00C3\u0048\u0065\u006C\u00D0\u006C\u006F\u0021",
	 			"\uB08A","\uB0A9\uB0AA",
	 			"\uB0A9\uB0AA\u0048\u0065\u006C\u006C\u006F\u0021\uB08A",
	 			"\u0024","\u00A2","\u0024\u00A2","\u20AC", 
	 			"\u24B62","\u0024\u003F\u003F\u00A2\u24B62\u003F\u0047\u0079\u20AC" 
	 	};
	 	
	 	
	 	String str, GetFinalMessage, description;
    	
    	try {
   		 	    	 

			Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(OutputFileUTF8), "UTF8"));
	 		    			
	 
			for( int i = 0; i < descriptions.length; i++)
			{
				str = descriptions[i];
				// Input string >> Output File
				
				out.append("Description (input for updateAccount): ").append(str).append("\r\n");
				
				// Update account with given description
				
				SessionChannelClient scc = ac.createSessionChannel();
	            APICommandLib acl = new APICommandLib(scc);
	            Account acct = new Account();
	            IDResult res = new IDResult();
	            
	            acct.setSystem(systemName);
	            acct.setAccount(accountName);
				
				acct.setDescription(str);
				
				assertTrue(acl.updateAccount(acct, res));
	            
	            assertEquals(0, res.getReturnCode());
	            assertTrue(res.getId().trim().length() > 0);
	            assertTrue(acl.getErrorString().trim().length() == 0);
				
	            // UpdateAccount result >> Output File
	            
            	GetFinalMessage = res.getMessage();
            	out.append("UpdateAccount: ").append(GetFinalMessage).append("\r\n");
            	
            	// ListAccounts (one account is chosen with filter)
            	
            	SessionChannelClient scc2 = ac.createSessionChannel();
	            APICommandLib acl2 = new APICommandLib(scc2);
            	
                AccountFilter filter = new AccountFilter();
                filter.setSystemName(systemName);
                filter.setAccountName(accountName);
                
                ListResult reslist = new ListResult();
                List accounts = new ArrayList();
                assertTrue(acl2.listAccounts(filter, reslist, accounts));

                assertEquals(0, reslist.getReturnCode());
                assertEquals(1, reslist.getRowCount());

                Iterator iter = accounts.iterator();

                while (iter.hasNext()) {
                    Account element = (Account) iter.next();
                    description = element.getDescription();
                    out.append("Description (output for listAccounts): ").append(description).append("\r\n");
                    
                    if (str.equals(description)) {
                    	out.append("Matched").append("\r\n");
                    } else {
                    	out.append("Not matched").append("\r\n");
                    }
                }
                
                assertTrue(acl.getErrorString().trim().length() == 0);
            	
                out.append("---------------------------------------\r\n");
			}
	 
	        out.flush();
			out.close();
	        
		    } 
		    catch (UnsupportedEncodingException e) 
		    {
				System.out.println(e.getMessage());
		    } 
		    catch (IOException e) 
		    {
				System.out.println(e.getMessage());
		    }
		    catch (Exception e)
		    {
				System.out.println(e.getMessage());
		    }
		    
		    finally {
		    	ac.disconnect();      
    }  
		}
        	
}


