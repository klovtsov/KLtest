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
                            mngdSystemName = args[5];
                            string networkAddress = args[6];
                            string platformName = args[7];
                            string SystemDescription = args[8];
                            string SystemTimeout = args[9];
                            string funcAccName = args[10];
                            string funcAccPwd = args[11];
                            if (args.Length == 12)
                            {
                                addSystem(client, mngdSystemName, networkAddress, platformName, SystemDescription, SystemTimeout, funcAccName, funcAccPwd);
                            }
                            else if (args.Length == 13)
                            {
                                string adPrmtr1 = args[12];
                                addSystem(client, mngdSystemName, networkAddress, platformName, SystemDescription, SystemTimeout, funcAccName, funcAccPwd, adPrmtr1);
                            }
                            else if (args.Length == 14)
                            {
                                string NetBIOSDomainName = args[12];
                                string DomainName = args[13];
                                addSystem(client, mngdSystemName, networkAddress, platformName, SystemDescription, SystemTimeout, funcAccName, funcAccPwd, NetBIOSDomainName, DomainName);
                            }
                            else if (args.Length == 15)
                            {
                                string PortNumber = args[12];
                                string oracleType = args[13];
                                string oracleSIDSN = args[14];
                                addSystem(client, mngdSystemName, networkAddress, platformName, SystemDescription, SystemTimeout, funcAccName, funcAccPwd, PortNumber, oracleType, oracleSIDSN);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}).", args.Length);
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

                        case "ListReasonCodes":
                            if (args.Length == 5)
                            {
                                listReasonCodes(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "ListPSMAccounts":
                            if (args.Length == 5)
                            {
                                listPSMAccounts(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "reportActivity":
                            if (args.Length == 5)
                            {
                                reportActivity(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "updateSystemYK":
                            if (args.Length == 5)
                            {
                                updateSystemYK(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "UpdateAccountYK":
                            if (args.Length == 5)
                            {
                                updateAccountYK(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "UpdateUserYK":
                            if (args.Length == 5)
                            {
                                updateUserYK(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "ListUsersYK":
                            if (args.Length == 5)
                            {
                                listUsersYK(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;


                        case "ListAcctsForSessionRequest":
                            if (args.Length == 5)
                            {
                                listAcctsForSessionRequest(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "ListAcctsForPwdRequest":
                            if (args.Length == 5)
                            {
                                listAcctsForPwdRequest(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "GetPwdRequest":
                            if (args.Length == 6)
                            {
                                string RequestID = args[5];
                                
                                getPwdRequest(client, RequestID);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 6 arguments.", args.Length);
                            }
                            break;

                        case "ListRequestDetails":
                            if (args.Length == 7)
                            {
                                string RequestID = args[5];
                                string RequestUser = args[6];
                                
                                listRequestDetails(client, RequestID, RequestUser);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;

                        case "GetSessionRequest":
                            if (args.Length == 6)
                            {
                                string RequestID = args[5];

                                getSessionRequest(client, RequestID);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 6 arguments.", args.Length);
                            }
                            break;

                        case "ListSessionRequestDetails":
                            if (args.Length == 7)
                            {
                                string RequestID = args[5];
                                string RequestUser = args[6];

                                listSessionRequestDetails(client, RequestID, RequestUser);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 7 arguments.", args.Length);
                            }
                            break;

                        case "ListRequest":
                            if (args.Length == 5)
                            {
                                listRequest(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "ListSessionRequest":
                            if (args.Length == 5)
                            {
                                listSessionRequest(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "AddSyncPass":
                            if (args.Length == 5)
                            {
                                addSyncPass(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "UpdateSyncPass":
                            if (args.Length == 5)
                            {
                                updateSyncPass(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "DeleteSyncPass":
                            if (args.Length == 5)
                            {
                                deleteSyncPass(client);
                            }
                            else
                            {
                                Console.WriteLine("Invalid number of arguments ({0}). Please, specify 5 arguments.", args.Length);
                            }
                            break;

                        case "AddGroup":
                            if (args.Length == 6)
                            {
                                string GroupName = args[5];
                                addGroup(client,GroupName);
                            }
                            else
                            {
                                Console.WriteLine("addGroup: Invalid number of arguments!");
                            }
                            break;

                        case "AddGroupMember1":
                            
                            if (args.Length == 7)
                            {
                                int GroupID = int.Parse(args[5]);
                                string UserName = args[6];
                                addGroupMember1(client, GroupID, UserName);
                            }
                            else
                            {
                                Console.WriteLine("addGroupMember1: Invalid number of arguments!");
                            }
                            break;

                        case "AddGroupMember2":
                            
                            if (args.Length == 7)
                            {
                                string GroupName = args[5];
                                string UserName = args[6];
                                addGroupMember2(client, GroupName, UserName);
                            }
                            else
                            {
                                Console.WriteLine("addGroupMember2: Invalid number of arguments!");
                            }
                            break;

                        case "ListGroups":
                            if (args.Length == 6)
                            {
                                int GroupID = int.Parse(args[5]);
                                listGroups(client, GroupID);
                            }
                            else
                            {
                                Console.WriteLine("listGroups: Invalid number of arguments!");
                            }
                            break;

                        case "ListGroupMembership1":
                            
                            
                            if (args.Length == 6)
                            {
                                int GroupID = int.Parse(args[5]);
                                listGroupMembership1(client, GroupID);
                            }
                            else
                            {
                                Console.WriteLine("listGroupMembership1: Invalid number of arguments!");
                            }
                            break;

                        case "ListGroupMembership2":


                            if (args.Length == 7)
                            {
                                string GroupName = args[5];
                                string UserName = args[6];
                                listGroupMembership2(client, GroupName, UserName);
                            }
                            else
                            {
                                Console.WriteLine("listGroupMembership2: Invalid number of arguments!");
                            }
                            break;


                        case "DropGroupMember1":
                            
                            if (args.Length == 7)
                            {
                                int GroupID = int.Parse(args[5]);
                                string UserName = args[6];
                                dropGroupMember1(client, GroupID, UserName);
                            }
                            else
                            {
                                Console.WriteLine("dropGroupMember1: Invalid number of arguments!");
                            }
                            break;

                        case "DropGroupMember2":

                            if (args.Length == 7)
                            {
                                string GroupName = args[5];
                                string UserName = args[6];
                                dropGroupMember2(client, GroupName, UserName);
                            }
                            else
                            {
                                Console.WriteLine("dropGroupMember2: Invalid number of arguments!");
                            }
                            break;


                        case "DropGroup1":
                            
                            if (args.Length == 6)
                            {
                                int GroupID = int.Parse(args[5]);
                                dropGroup1(client, GroupID);
                            }
                            
                            else
                            {
                                Console.WriteLine("dropGroup1: Invalid number of arguments!");
                            }
                            break;


                        case "DropGroup2":

                            if (args.Length == 6)
                            {
                                string GroupName = args[5];
                                dropGroup2(client, GroupName);
                            }

                            else
                            {
                                Console.WriteLine("dropGroup2: Invalid number of arguments!");
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

        private static void addSystem(ApiClientWrapper client, string mngdSystemName, string networkAddress, string platformName, string SystemDescription, string SystemTimeout, string funcAccName, string funcAccPwd, string PortNumber, string oracleType, string oracleSIDSN)
        {
            try
            {
                //Console.WriteLine("Method addSystem started.");
                // Add a dummy system.
                EDMZSystem mngdsystem = new EDMZSystem();
                mngdsystem.systemName = mngdSystemName;
                mngdsystem.networkAddress = networkAddress;
                mngdsystem.platformName = platformName;
                mngdsystem.systemAutoFl = Flag.Y;
                mngdsystem.description = SystemDescription;
                mngdsystem.timeout = int.Parse(SystemTimeout);
                mngdsystem.functionalAccount = funcAccName;
                mngdsystem.functionalAcctCredentials = funcAccPwd;
                // Attempt to set port number as integer, if cast is not succesfull excpt an error
                try
                {
                    mngdsystem.portNumber = Int16.Parse(PortNumber);
                }
                catch (FormatException)
                {
                    Console.WriteLine("Unable to convert '{0}' to a integer.", PortNumber);
                }
                mngdsystem.oracleType = oracleType;
                mngdsystem.oracleSIDSN = oracleSIDSN;
                // Execute the operation on TPAM.
                IDResult idresult = client.addSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("addSystem: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addSystem(ApiClientWrapper client, string SystemName, string networkAddress, string platformName, string SystemDescription, string SystemTimeout, string funcAccName, string funcAccPwd, string NetBIOSDomainName, string DomainName)
        {
            try
            {
                //Console.WriteLine("Method addSystem started.");
                // Add a dummy system
                EDMZSystem mngdsystem = new EDMZSystem();
                mngdsystem.systemName = SystemName;
                mngdsystem.networkAddress = networkAddress;
                mngdsystem.platformName = platformName;
                mngdsystem.systemAutoFl = Flag.Y;
                mngdsystem.description = SystemDescription;
                mngdsystem.timeout = int.Parse(SystemTimeout);
                mngdsystem.functionalAccount = funcAccName;
                mngdsystem.functionalAcctCredentials = funcAccPwd;
                mngdsystem.netBIOSName = NetBIOSDomainName;
                mngdsystem.domainName = DomainName;
                // Execute the operation on TPAM.
                IDResult idresult = client.addSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("addSystem: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addSystem(ApiClientWrapper client, string SystemName, string networkAddress, string platformName,
            string SystemDescription, string SystemTimeout, string rootName, string rootPwd, string additionalParameter)
        {
            try
            {
                //Console.WriteLine("Method addSystem started.");
                // Add a dummy system.
                EDMZSystem mngdsystem = new EDMZSystem();
                mngdsystem.systemName = SystemName;
                mngdsystem.networkAddress = networkAddress;
                mngdsystem.platformName = platformName;
                mngdsystem.systemAutoFl = Flag.Y;
                mngdsystem.description = SystemDescription;
                mngdsystem.timeout = int.Parse(SystemTimeout);
                mngdsystem.functionalAccount = rootName;
                mngdsystem.functionalAcctCredentials = rootPwd;
                if (platformName.Contains("Cisco"))    //For Cisco platform an additional parameter is a ENABLE system password
                {
                    mngdsystem.enablePassword = additionalParameter;
                }
                else if (platformName.Contains("NIS Plus")) //For NIS Plus an additional parameter is DomainName
                {
                    mngdsystem.domainName = additionalParameter;
                }
                else                            //Additional parameter is a port number for non-Cisco platforms
                {
                    if (platformName.Contains("Novell") || platformName.Contains("LDAP"))
                    {
                        mngdsystem.domainName = additionalParameter;
                    }
                    else
                    {
                        try
                        {
                            mngdsystem.portNumber = Int16.Parse(additionalParameter);
                        }
                        catch (FormatException)
                        {
                            Console.WriteLine("Unable to convert '{0}' to a integer.", additionalParameter);
                        }
                    }
                }
                // Execute the operation on TPAM.
                IDResult idresult = client.addSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("addSystem: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (ApplicationException exptn)
            {
                Console.WriteLine("Exception: {0}", exptn.Message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
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

        static void addSystem(ApiClientWrapper client, string systemName, string networkAddress, string platformName,
            String SystemDescription, string SystemTimeout, string rootName, string rootPwd)
        {
            try
            {
                //Console.WriteLine("Method AddSystem started.");
                // Add a dummy system.
                EDMZSystem mngdsystem = new EDMZSystem();
                mngdsystem.systemName = systemName;
                mngdsystem.networkAddress = networkAddress;
                mngdsystem.platformName = platformName;
                mngdsystem.systemAutoFl = Flag.Y;
                mngdsystem.description = SystemDescription;
                mngdsystem.timeout = int.Parse(SystemTimeout);
                mngdsystem.functionalAccount = rootName;
                mngdsystem.functionalAcctCredentials = rootPwd;
                // Execute the operation on TPAM.
                IDResult idresult = client.addSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("addSystem: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
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

        private static void listReasonCodes(ApiClientWrapper client)
        {
            try
            {
                ReasonCode[] reasonCodes = null;
                ListResult result = client.listReasonCodes(out reasonCodes);

                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listReasonCodes: Name: {0}, Description: {1}",
                            reasonCodes[i].name, reasonCodes[i].description);
                    }
                }


                Console.WriteLine("listReasonCodes: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void reportActivity(ApiClientWrapper client)
        {
            try
            {
                Activity[] reportActivity = null;
                ActivityFilter filter = new ActivityFilter();

                filter.maxRows = 500;
                filter.userName = "kladmin";
                filter.objectType = "ManagedSystem";

                int startyear = Convert.ToInt16("2012");
                int startmonth = Convert.ToInt16("09");
                int startday = Convert.ToInt16("11");
                filter.startDate = new DateTimeWrapper(startyear, startmonth, startday);

                int endyear = Convert.ToInt16("2012");
                int endmonth = Convert.ToInt16("09");
                int endday = Convert.ToInt16("12");
                filter.endDate = new DateTimeWrapper(endyear, endmonth, endday);

                ListResult result = client.reportActivity(filter, out reportActivity);

                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("ReportActivity: User: {0}, FullName: {1}, Object: {2}, Operation: {3}, LogTime: {4}",
                            reportActivity[i].userName, reportActivity[i].userFullName, reportActivity[i].objectType,
                            reportActivity[i].operation, reportActivity[i].logTime);
                    }
                }

                Console.WriteLine("ReportActivity: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void updateUserYK(ApiClientWrapper client)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                User ouruser = new User();

                ouruser.userName = "yk_ui";
                ouruser.custom1 = "Bruce";
                ouruser.custom2 = "Hulk";
                ouruser.mobile = "777-222-333-44";



                // Execute the operation on TPAM.
                IDResult idresult = client.updateUser(ouruser);
                // Check the outcome of the operation.
                Console.WriteLine("updateUserYK: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void updateSystemYK(ApiClientWrapper client)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                EDMZSystem mngdsystem = new EDMZSystem();
                mngdsystem.systemName = "ykWinTest";
                mngdsystem.autoDiscoveryProfile = "777sort";
                mngdsystem.autoDiscoveryExcludeList = "who the fuck are you";
                mngdsystem.custom1 = "Beautiful";
                mngdsystem.custom2 = "Mind";

                // Execute the operation on TPAM.
                IDResult idresult = client.updateSystem(mngdsystem);
                // Check the outcome of the operation.
                Console.WriteLine("updateSystemYK: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void updateAccountYK(ApiClientWrapper client)
        {
            try
            {
                // Console.WriteLine("Method setAccountDescription started.");
                Account account = new Account();
                account.systemName = "ykWinTest";
                account.accountName = "yk5";
                account.changeTaskFlag = Flag.Y;

                // Execute the operation on TPAM.
                IDResult idresult = client.updateAccount(account);
                // Check the outcome of the operation.
                Console.WriteLine("UpdateAccountYK: rc = {0}, message = {1}",
                idresult.returnCode, idresult.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void listUsersYK(ApiClientWrapper client)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                User[] users = null;
                UserFilter filter = new UserFilter();

                filter.maxRows = 500;

                // Execute the operation on TPAM.

                ListResult result = client.listUsers(filter, out users);


                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listUsers: User: {0}, FullName: {1}, Custom2: {2}",
                            users[i].userName, users[i].lastName, users[i].custom2);
                    }
                }


                Console.WriteLine("listUsers: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void listPSMAccounts(ApiClientWrapper client)
        {
            try
            {
                PsmAccount[] psmAccounts = null;
                PsmAccountFilter filter = new PsmAccountFilter();


                filter.systemName = "ykw7q64a5";
                filter.accountPsmFlag = PsmAccountFilter.FlagFilter.N;

                ListResult result = client.listPsmAccounts(filter, out psmAccounts);

                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listPSMAccounts: Account: {0}, System: {1}",
                            psmAccounts[i].accountName, psmAccounts[i].systemName);
                    }
                }

                Console.WriteLine("listPSMAccounts: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void listAcctsForSessionRequest(ApiClientWrapper client)
        {
            try
            {
                AcctForSessionRequest[] psmAccounts = null;
                AcctForSessionRequestFilter filter = new AcctForSessionRequestFilter();


                filter.systemName = "ykspb9449";

                ListResult result = client.listAcctsForSessionRequest(filter, out psmAccounts);

                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listAcctsForSessionRequest: Account: {0}, System: {1}",
                            psmAccounts[i].accountName, psmAccounts[i].systemName);
                    }
                }

                Console.WriteLine("listAcctsForSessionRequest: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void listAcctsForPwdRequest(ApiClientWrapper client)
        {
            try
            {
                AcctForPwdRequest[] ppmAccounts = null;
                AcctForPwdRequestFilter filter = new AcctForPwdRequestFilter();


                filter.systemName = "ykspb9449";

                ListResult result = client.listAcctsForPwdRequest(filter, out ppmAccounts);

                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listAcctsForPwdRequest: Account: {0}, System: {1}",
                            ppmAccounts[i].accountName, ppmAccounts[i].systemName);
                    }
                }

                Console.WriteLine("listAcctsForPwdRequest: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void getPwdRequest(ApiClientWrapper client, string RequestID)
        {
            try
            {
                PwdRequest pwdRequest = null;

                ListResult result = client.getPwdRequest(RequestID, out pwdRequest);
                                
                if (result.returnCode == 0)
                {
                    Console.WriteLine("getPwdRequest: rc = {0}, message = {1}, requestStatus = {2}, minApprovers = {3}",
                        result.returnCode, result.message, pwdRequest.requestStatus, pwdRequest.minApprovers);
                    
                }
                else
                {
                    // If returnCode indicates failure,
                    // the message is an actual message.
                    Console.WriteLine("Failed getPwdRequest: {0}", result.message);
                }
                
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void listRequestDetails (ApiClientWrapper client, string RequestID, string RequestUser)
        {
            try
            {
                
                RequestFilter pwdRequestFilter = new RequestFilter ();

                Request[] pwdRequestDetails = null;

                pwdRequestFilter.status = RequestFilter.StatusFilter.ALL;
                pwdRequestFilter.requestorName = RequestUser;

                ListResult result = client.listRequestDetails(pwdRequestFilter, out pwdRequestDetails);

                if (result.returnCode == 0)
                {

                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listRequestDetails: RequestID: {0}, MinApprovers: {1}",
                            pwdRequestDetails[i].requestID, pwdRequestDetails[i].minApprovers);
                    }
                    Console.WriteLine("listRequestDetails: rc = {0}, message = {1}", result.returnCode, result.message);

                }
                else
                {
        
                    Console.WriteLine("Failed listPwdRequest: {0}", result.message);
                }
                
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void getSessionRequest(ApiClientWrapper client, string RequestID)
        {
            try
            {
                SessionRequest sessionRequest = null;

                ListResult result = client.getSessionRequest(RequestID, out sessionRequest);

                if (result.returnCode == 0)
                {
                    Console.WriteLine("getSessionRequest: rc = {0}, message = {1}, requestStatus = {2}, minPasswordApprovers = {3}, minSessionApprovers = {4}",
                        result.returnCode, result.message, sessionRequest.requestStatus, sessionRequest.minPasswordApprovers, sessionRequest.minSessionApprovers);
                }
                else
                {
                    // If returnCode indicates failure,
                    // the message is an actual message.
                    Console.WriteLine("Failed getSessionRequest: {0}", result.message);
                }
                
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void listSessionRequestDetails(ApiClientWrapper client, string RequestID, string RequestUser)
        {
            try
            {
                
                SessionRequestFilter sessionRequestFilter = new SessionRequestFilter();

                SessionRequest[] sessionRequestDetails = null;

                sessionRequestFilter.status = SessionRequestFilter.StatusFilter.ALL;

                sessionRequestFilter.requestorName = RequestUser;

                ListResult result = client.listSessionRequestDetails(sessionRequestFilter, out sessionRequestDetails);

                if (result.returnCode == 0)
                {

                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listSessionRequestDetails: RequestID: {0}, MinPasswordApprovers: {1}, MinSessionApprovers: {2}",
                            sessionRequestDetails[i].requestID, sessionRequestDetails[i].minPasswordApprovers, sessionRequestDetails[i].minSessionApprovers);
                    }
                    Console.WriteLine("listSessionRequestDetails: rc = {0}, message = {1}", result.returnCode, result.message);

                }
                else
                {
                    Console.WriteLine("Failed listSessionRequest: {0}", result.message);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void listRequest(ApiClientWrapper client)
        {
            try
            {

                RequestFilter pwdRequestFilter = new RequestFilter();

                Request[] pwdRequestDetails = null;

                pwdRequestFilter.status = RequestFilter.StatusFilter.ALL;
                pwdRequestFilter.requestorName = "User=Myself";
                
                ListResult result = client.listRequest(pwdRequestFilter, out pwdRequestDetails);

                if (result.returnCode == 0)
                {

                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listRequest: RequestID: {0}, Status: {1}",
                            pwdRequestDetails[i].requestID, pwdRequestDetails[i].status);
                    }
                    Console.WriteLine("listRequest: rc = {0}, message = {1}", result.returnCode, result.message);

                }
                else
                {

                    Console.WriteLine("Failed listRequest: {0}", result.message);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void listSessionRequest(ApiClientWrapper client)
        {
            try
            {

                SessionRequestFilter sessionRequestFilter = new SessionRequestFilter();

                SessionRequest[] sessionRequestDetails = null;

                sessionRequestFilter.status = SessionRequestFilter.StatusFilter.ALL;
                sessionRequestFilter.requestorName = "User=Myself";

                ListResult result = client.listSessionRequest(sessionRequestFilter, out sessionRequestDetails);

                if (result.returnCode == 0)
                {

                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listSessionRequest: RequestID: {0}, Status: {1}",
                            sessionRequestDetails[i].requestID, sessionRequestDetails[i].status);
                    }
                    Console.WriteLine("listSessionRequest: rc = {0}, message = {1}", result.returnCode, result.message);

                }
                else
                {
                    Console.WriteLine("Failed listSession: {0}", result.message);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addSyncPass (ApiClientWrapper client)
        {
            try
            {
                
                String SyncPassName = "yknet5";
                AddSyncPassParms parms = new AddSyncPassParms();
                parms.checkFlag = Flag.Y;
                parms.description = "Yuri Net";
                parms.changeFrequency = -2;
                parms.disableFlag = Flag.Y;

                int myyear = Convert.ToInt16("2013");
                int mymonth = Convert.ToInt16("03");
                int myday = Convert.ToInt16("03");
                int myhour = Convert.ToInt16("14");
                int mymin = Convert.ToInt16("17");

                parms.nextChangeDate = new DateTimeWrapper(myyear, mymonth, myday);
                parms.changeTime = new DateTimeWrapper(myhour, mymin);
                
                parms.password = "Q1w2e3r4t5";
                parms.passwordRule = "yk_rule";
                parms.releaseChangeFlag = Flag.Y;
                parms.releaseDuration = 30;
                parms.releaseNotifyEmail = "yknet@questnet.com";
                parms.resetFlag = Flag.Y;

                Result result = client.addSyncPass(SyncPassName, parms);
                
                Console.WriteLine("addSyncPass: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void updateSyncPass(ApiClientWrapper client)
        {
            try
            {

                String SyncPassName = "yknet5";
                UpdateSyncPassParms parms = new UpdateSyncPassParms();
                parms.checkFlag = Flag.Y;
                parms.description = "Yuri Net AGain";
                parms.changeFrequency = -1;

                int myyear = Convert.ToInt16("2013");
                int mymonth = Convert.ToInt16("03");
                int myday = Convert.ToInt16("03");
                int myhour = Convert.ToInt16("14");
                int mymin = Convert.ToInt16("17");

                parms.nextChangeDate = new DateTimeWrapper(myyear, mymonth, myday);
                parms.changeTime = new DateTimeWrapper(myhour, mymin);

                parms.disableFlag = Flag.N;
                parms.password = "Q1w2e3s";
                parms.passwordRule = "yk_rule2";
                parms.releaseChangeFlag = Flag.N;
                parms.releaseDuration = 60;
                parms.releaseNotifyEmail = "yknet@questssssnet.com";
                parms.resetFlag = Flag.N;

                Result result = client.updateSyncPass(SyncPassName, parms);

                Console.WriteLine("updateSyncPass: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void deleteSyncPass(ApiClientWrapper client)
        {
            try
            {

                String SyncPassName = "yknet5";
                
                Result result = client.deleteSyncPass(SyncPassName);

                Console.WriteLine("addSyncPass: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addGroup (ApiClientWrapper client, string GroupName)
        {
            try
            {

                Result result = client.addGroup(GroupName, "Test Group");
                Console.WriteLine("addGroup: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addGroupMember1 (ApiClientWrapper client, int GroupID, string UserName)
        {
            try
            {

                Result result = client.addGroupMember(UserName, GroupID);
                Console.WriteLine("addGroupMember1: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void addGroupMember2(ApiClientWrapper client, string GroupName, string UserName)
        {
            try
            {

                Result result = client.addGroupMember(UserName, GroupName);
                Console.WriteLine("addGroupMember2: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void listGroups(ApiClientWrapper client, int GroupID)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                Group[] groups = null;
                GroupFilter filter = new GroupFilter();

                filter.groupID = GroupID;

                // Execute the operation on TPAM.

                ListResult result = client.listGroups(filter, out groups);


                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listGroups: GroupName: {0}, GroupID: {1}",
                            groups[i].groupName, groups[i].groupID);
                    }
                }


                Console.WriteLine("listGroups: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void listGroupMembership1(ApiClientWrapper client, int GroupID)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                GroupMembership[] membership = null;
                GroupMembershipFilter filter = new GroupMembershipFilter();

                filter.groupID = GroupID;

                // Execute the operation on TPAM.

                ListResult result = client.listGroupMembership(filter, out membership);


                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listGroupMembership1: GroupName: {0}, GroupID: {1}, UserName: {2}",
                            membership[i].groupName, membership[i].groupID, membership[i].userName);
                    }
                }


                Console.WriteLine("listGroupMembership1: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void listGroupMembership2(ApiClientWrapper client, string GroupName, string UserName)
        {
            try
            {
                //Console.WriteLine("Method updateSystem started.");
                GroupMembership[] membership = null;
                

                // Execute the operation on TPAM.

                ListResult result = client.listGroupMembership(GroupName, UserName, out membership);


                if (result.returnCode == 0)
                {
                    for (int i = 0; i < result.rowCount; i++)
                    {
                        Console.WriteLine("listGroupMembership2: GroupName: {0}, GroupID: {1}, UserName: {2}",
                            membership[i].groupName, membership[i].groupID, membership[i].userName);
                    }
                }


                Console.WriteLine("listGroupMembership2: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void dropGroupMember1(ApiClientWrapper client, int GroupID, string UserName)
        {
            try
            {

                Result result = client.dropGroupMember(UserName, GroupID);
                Console.WriteLine("dropGroupMember1: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void dropGroupMember2(ApiClientWrapper client, string GroupName, string UserName)
        {
            try
            {

                Result result = client.dropGroupMember(UserName, GroupName);
                Console.WriteLine("dropGroupMember2: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }


        private static void dropGroup1(ApiClientWrapper client, int GroupID)
        {
            try
            {

                Result result = client.dropGroup(GroupID);
                Console.WriteLine("dropGroup1: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }

        private static void dropGroup2(ApiClientWrapper client, string GroupName)
        {
            try
            {

                Result result = client.dropGroup(GroupName);
                Console.WriteLine("dropGroup2: rc = {0}, message = {1}",
                result.returnCode, result.message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: {0}", ex.Message);
            }
        }
        

    }
}
