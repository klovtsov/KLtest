using System;
using System.Collections.Generic;
using System.Text;
using eDMZ.ParApi;

namespace tpamapinet
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                // Command should have at least 4 arguments: public key file, username, tpam address, command
                if (args.Length >= 5)
                {
                    String pbkeyfile = args[0];
                    String apiUser = args[1];
                    String tpamAddress = args[2];
                    int tpamCmdTimeout = int.Parse(args[3]);
                    String tpamCommand = args[4];
                    //Console.WriteLine("The {0} command will be executed with {1} number of arguments", tpamCommand, (args.Length - 4));
                    string mngdSystemName, mngdAccName, mngdAccPswd;
                    ApiClientWrapper client = new ApiClientWrapper(tpamAddress, pbkeyfile, apiUser);
                    client.connect();
                    client.setCommandTimeout(tpamCmdTimeout);
                    
                    switch (tpamCommand)
                    {
                        case "AddSystem":
                            EDMZSystem mngdsystem = new EDMZSystem();
                                mngdsystem.systemName = args[5];
                                mngdsystem.networkAddress = args[6];
                                mngdsystem.platformName = args[7];
                                mngdsystem.systemAutoFl = Flag.Y;
                                mngdsystem.description = args[8];
                                mngdsystem.timeout = int.Parse(args[9]);
                                mngdsystem.functionalAccount = args[10];
                                mngdsystem.functionalAcctCredentials = args[11];
                                
                               if (args.Length == 12)
                               {
                                   addSystem(client,mngdsystem);
                               }
                               
                               else if (args.Length == 13)
                               {
                                   String addParam = args[12];
                                   if (mngdsystem.platformName.Contains("Cisco"))    //For Cisco platform an additional parameter is a ENABLE system password
                                   {
                                       mngdsystem.enablePassword = addParam;
                                   }
                                   else if (mngdsystem.platformName.Contains("NIS Plus")) //For NIS Plus an additional parameter is DomainName
                                   {
                                       mngdsystem.domainName = addParam;
                                   }
                                   else
                                   {
                                       try
                                       {
                                           mngdsystem.portNumber = Int16.Parse(args[12]);
                                       }
                                       catch (FormatException)
                                       {
                                           Console.WriteLine("Unable to convert '{0}' to a integer.", addParam);
                                       }
                                   }
                                   addSystem(client,mngdsystem);
                               }
                               
                               else if (args.Length == 14)
                               {
                                   if (mngdsystem.platformName.Contains("Novell") || mngdsystem.platformName.Contains("LDAP")){
                                       mngdsystem.domainName = args[12];
                                       mngdsystem.funcAcctDN = args[13];
                                   }
                                   else
                                   {
                                       mngdsystem.netBIOSName = args[12];
                                       mngdsystem.domainName = args[13];
                                   }
                                   addSystem(client,mngdsystem);
                               }

                               else if (args.Length == 15)
                               {
                                   mngdsystem.portNumber = Int16.Parse(args[12]);
                                   mngdsystem.oracleType = args[13];
                                   mngdsystem.oracleSIDSN = args[14];
                                   addSystem(client, mngdsystem);
                               }
                               else
                               {
                                   Console.WriteLine("Invalid number of arguments ({0}).", args.Length);
                               }
                            break;

                        case "AddPpmAccount":
                            Account account = new Account();
                                    account.systemName = args[5];
                                    account.accountName = args[6];
                                    account.password = args[7];
                                    account.passwordCheckProfile = args[8];
                                    account.passwordChangeProfile = args[9];
                            if (args.Length == 10)
                            {
                                addPpmAccount(client, account);
                            }
                            else if (args.Length == 11) //for LDAP or Novell systems
                            {
                              account.accountDN = args[10];
                              addPpmAccount(client, account);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 9 arguments.", args.Length);
                            }
                            break;
                        
                        case "AddAccount":
                            if (args.Length == 9)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                mngdAccPswd = args[7];
                                string schedReset = args[8];   // Month/Day/Year
                                addManagedAccount(client, mngdSystemName, mngdAccName, mngdAccPswd, schedReset);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 9 arguments.", args.Length);
                            }
                            break;
                        case "SetAccountDescription":
                            if (args.Length == 8)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                string mngdAccDescription = args[7];
                                setAccountDescription(client, mngdSystemName, mngdAccName, mngdAccDescription);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 8 arguments.", args.Length);
                            }
                            break;
                        case "SetAccountDN":
                            if (args.Length == 8)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                string mngdAccDN = args[7];
                                setAccountDN(client, mngdSystemName, mngdAccName, mngdAccDN);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 8 arguments.", args.Length);
                            }
                            break;
                        case "DeleteSystem":
                            if (args.Length == 6)
                            {
                                String systemToDelName = args[5];
                                deleteSystem(client, systemToDelName);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 6 arguments.", args.Length);
                            }
                            break;
                        case "TestSystem":
                            if (args.Length == 6)
                            {
                                String systemToTestName = args[5];
                                testSystem(client, systemToTestName);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;
                        case "SetPassword":
                            if (args.Length == 8)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                string newPassword = args[7];
                                setPassword(client, mngdSystemName, mngdAccName, newPassword);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 8 arguments.", args.Length);
                            }
                            break;
                        case "CheckPassword":
                            if (args.Length == 7)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                checkPassword(client, mngdSystemName, mngdAccName);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;
                        case "ResetPassword":
                            if (args.Length == 7)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                resetPassword(client, mngdSystemName, mngdAccName);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;
                        case "RemoveAccount":
                            if (args.Length == 7)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                removeAccount(client, mngdSystemName, mngdAccName);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;
                        case "UpdateSystem":
                            if (args.Length == 7)
                            {
                                mngdSystemName = args[5];
                                string newIp = args[6];
                                updateSystem(client, mngdSystemName, newIp);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;
                        case "SetUserPermission":
                            if (args.Length == 10)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                string accessPolicyName = args[7];
                                string AddOrDrop = args[8];
                                string User = args[9];
                                setUserPermission(client, mngdSystemName, mngdAccName, accessPolicyName, AddOrDrop, User);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 10 arguments.", args.Length);
                            }
                            break;
                        case "RequestPassword":
                            if (args.Length == 9)
                            {
                                mngdSystemName = args[5];
                                mngdAccName = args[6];
                                string requestor = args[7];
                                string requestNotes = args[8];
                                requestPassword(client, mngdSystemName, mngdAccName, requestor, requestNotes);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 9 arguments.", args.Length);
                            }
                            break;
                        case "ApprovePassword":
                            if (args.Length == 6)
                            {
                                string reqID = args[5];
                                approvePassword(client, reqID);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 6 arguments.", args.Length);
                            }
                            break;
                        case "RetrievePassword":
                            if (args.Length == 6)
                            {
                                string RequestID = args[5];
                                //mngdAccName = args[6];
                                retrievePassword(client, RequestID);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;
                        default:
                            Console.WriteLine("The command {0} was not found in the list of command", tpamCommand);
                            break;
                    }
                    //Console.WriteLine("Disconnection...");
                    client.disconnect();
                    //Console.WriteLine("TPAMAPINET has finished his work.");
                }
                else
                {
                    System.Console.WriteLine("Invalid arguments. Please, specify at least 5 arguments: public key file, username, tpam address, command timeout ,command");
                }
            }

            catch (ApplicationException aex)
            {
                Console.WriteLine("Exception: {0}", aex.Message);
            }
            catch (Exception e)
            {
                Console.WriteLine("Generic Exception Handler: {0}", e.ToString());
            }
        }

        private static void setAccountDN(ApiClientWrapper client, string mngdSystemName, string mngdAccName, string mngdAccDN)
        {
            try
            {
                // Console.WriteLine("Method setAccountDescription started.");
                Account account = new Account();
                account.systemName = mngdSystemName;
                account.accountName = mngdAccName;
                account.accountDN = mngdAccDN;
                // Execute the operation on TPAM.
                IDResult idresult = client.updateAccount(account);
                // Check the outcome of the operation.
                Console.WriteLine("Set Account DN: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Set Account DN error: {0}", ex.Message);
            }
        }

        private static void addPpmAccount(ApiClientWrapper client, Account account)
        {
             try{
                IDResult idresult = client.addAccount(account);
                // Check the outcome of the operation.
                Console.WriteLine("addAccount: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Add PPM account failed with error: {0}", ex.Message);
            }
        }


        private static void addSystem(ApiClientWrapper client,EDMZSystem mngdsystem)
        {
 	        try{    
                // Execute the operation on TPAM.
                IDResult idresult = client.addSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("addSystem: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
                }
            catch (Exception ex)
            {
                Console.WriteLine("Add System failed with error: {0}", ex.Message);
            }
        }

        private static void retrievePassword(ApiClientWrapper client, string RequestID)
        {
            try
            {
                //Console.WriteLine("Method retrievePassword started.");
                Result result = client.retrieve(RequestID);
                if (result.returnCode == 0)
                {
                    // If returnCode indicates success, the message is the password.
                    Console.WriteLine(result.message);
                }
                else
                {
                    // If returnCode indicates failure,
                    // the message is an actual message.
                    Console.WriteLine("Failed retrieving password: {0}", result.message);
                }
                //Console.WriteLine("Retrieving password is finished");
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void approvePassword(ApiClientWrapper client, string requestID)
        {
            try
            {
                //Console.WriteLine("Method approvePassword started.");
                Result result = client.approve(requestID, "ok");
                // Check the outcome of the operation.
                Console.WriteLine("ApprovePassword: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (ApplicationException aex)
            {
                Console.WriteLine("Exception: {0}", aex.Message);
            }
            Console.WriteLine("Password Approving is finished");
        }

        private static void requestPassword(ApiClientWrapper client, string SystemName, string AccountName, string requestor, string requestNotes)
        {
            try
            {
                //Console.WriteLine("Method requestPassword started.");
                AddPwdRequestParms parms = new AddPwdRequestParms();
                parms.requestImmediateFlag = Flag.Y;
                IDResult idresult = client.addPwdRequest(SystemName, AccountName, requestor, requestNotes, parms);
                // Check the outcome of the operation.
                Console.WriteLine(idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void setUserPermission(ApiClientWrapper client, string SystemName, string AccountName, string accessPolicyName, string AddOrDrop, string User)
        {
            try
            {
                //Console.WriteLine("Method setUserPermission started.");
                SetAccessPolicyParms parms = new SetAccessPolicyParms();
                parms.accountName = AccountName;
                parms.systemName = SystemName;
                parms.userName = User;
                Result result = client.setAccessPolicy(accessPolicyName, AddOrDrop, parms);
                // Check the outcome of the operation.
                Console.WriteLine("Set User Permission: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void updateSystem(ApiClientWrapper client, string SystemName, string newIp)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                EDMZSystem mngdsystem = new EDMZSystem();
                mngdsystem.systemName = SystemName;
                mngdsystem.networkAddress = newIp;
                // Execute the operation on TPAM.
                IDResult idresult = client.updateSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("updateSystem: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void removeAccount(ApiClientWrapper client, string SystemName, string AccountName)
        {
            try
            {
                //Console.WriteLine("Method rmoveAccount started.");
                Result result = client.deleteAccount(SystemName, AccountName);
                // Check the outcome of the operation.
                Console.WriteLine("Remove Account: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void resetPassword(ApiClientWrapper client, string SystemName, string AccountName)
        {
            try
            {
                // Console.WriteLine("Method resetPassword started.");
                Result result = client.forceReset(SystemName, AccountName);
                // Check the outcome of the operation.
                Console.WriteLine("Reset Password: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void checkPassword(ApiClientWrapper client, string SystemName, string AccName)
        {
            try
            {
                //Console.WriteLine("Method checkPassword started.");
                Result result = client.checkPassword(SystemName, AccName);
                // Check the outcome of the operation.
                Console.WriteLine("Check Password: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void setAccountDescription(ApiClientWrapper client, string mngdSystemName, string mngdAccName, string mngdAccDescription)
        {
            try
            {
                // Console.WriteLine("Method setAccountDescription started.");
                Account account = new Account();
                account.systemName = mngdSystemName;
                account.accountName = mngdAccName;
                account.description = mngdAccDescription;
                // Execute the operation on TPAM.
                IDResult idresult = client.updateAccount(account);
                // Check the outcome of the operation.
                Console.WriteLine("Set Account Description: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void setPassword(ApiClientWrapper client, string mngdSystemName, string mngdAccName, string newPassword)
        {
            try
            {
                //Console.WriteLine("Method setPassword started.");
                Account account = new Account();
                account.systemName = mngdSystemName;
                account.accountName = mngdAccName;
                account.password = newPassword;
                // Execute the operation on TPAM.
                IDResult idresult = client.updateAccount(account);
                // Check the outcome of the operation.
                Console.WriteLine("updateAccount: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void testSystem(ApiClientWrapper client, string systemName)
        {
            try
            {
                //Console.WriteLine("Method testSystem started.");
                Result result = client.testSystem(systemName);
                // Check the outcome of the operatin.
                Console.WriteLine("TestSystem: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void deleteSystem(ApiClientWrapper client, string systemName)
        {
            try
            {
                //Console.WriteLine("Method deleteSystem started.");
                Result result = client.deleteSystem(systemName);
                // Check the outcome of the operation.
                Console.WriteLine("DeleteSystem: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addManagedAccount(ApiClientWrapper client, string SystemName, string AccountName, string AccPassword, string schedDelay)
        {
            try
            {
                //Console.WriteLine("Method addAccount started.");
                // Add a dummy account.
                Account account = new Account();
                account.systemName = SystemName;
                account.accountName = AccountName;
                account.password = AccPassword;
                string[] data = schedDelay.Split('/');
                int year = Convert.ToInt16(data[2]);
                int month = Convert.ToInt16(data[0]);
                int day = Convert.ToInt16(data[1]);
                account.nextChangeDt = new DateTimeWrapper(year, month, day);
                // Execute the operation on TPAM.
                IDResult idresult = client.addAccount(account);
                // Check the outcome of the operation.
                Console.WriteLine("addAccount: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }
    }
}
