
#include <algorithm>
#include <iostream>
#include <string> // for std::string
#include "convert.h"
//Include necessary command files
#include "ApiClient.h"
#include "AddSystemCommand.h"
#include "TestSystemCommand.h"
#include "CheckPasswordCommand.h"
#include "AddAccountCommand.h"
#include "ForceResetCommand.h"
#include "DeleteAccountCommand.h"
#include "DeleteSystemCommand.h"
#include "UpdateAccountCommand.h"
#include "SetAccessPolicyCommand.h"
#include "RetrieveCommand.h"
#include "AddPwdRequestCommand.h"
#include "ApproveCommand.h"
#include "UpdateSystemCommand.h"


void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword);
void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword, string& additionalParameter);
void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword, string& dopParam1, string& dopParam2);
void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword, int PortNumber, string& OracleType, string& OracleSIDSN);
void deleteSystem(ApiClient& client, string& SysName);
void testSystem(ApiClient& client, string& SysName);
void checkPassword(ApiClient& client, string& SystemName, string& AccountName);
void addAccount(ApiClient& client, string& SystemName, string& AccntName, string& AccountPwd);
void addAccount(ApiClient& client, string& SystemName, string& AccountName, string& AccountPwd, string& pwdCheckProfile, string& pwdChangeProfile);
void resetPassword(ApiClient& client,string& SystemName,string& AccountName);
void deleteAccount(ApiClient& client,string& SystemName,string& AccountName);
void setAccountPassword(ApiClient& client,string& SystemName,string& AccountName,string& NewPwd);
void setAccountDescription(ApiClient& client,string& SystemName,string& AccountName,string& NewDescription);
void setAccountDN(ApiClient& client,string& SystemName,string& AccountName,string& NewDN);
void setUserPermission(ApiClient& client,string& SystemName,string& AccountName,string& PermType,string& AddOrDrop,string& UserName);
void requestPassword(ApiClient& client,string& SystemName,string& AccountName,string& requestor,string& requestorNotes);
void approvePassword(ApiClient& client, string& ReqID);
void retrievePassword(ApiClient& client,string& RequestID);
void updateSystem(ApiClient& client,string& SystemName,string& newIP);

unsigned int CommandTimeOut = 120;   //Change default timeout for commands from 50 to 120
                            //It is caused by BFER 3804

int main(int argc, char *argv[])
{
	using namespace std;
	//ApiClient client("10.30.44.210", "F:\\work\\tpam\\Automation\\TPAM\\Scripts\\keys\\apikey", "ab_apiadmin");
	if (argc < 5)
    {
        cout << "At least 4 arguments have to be specified (key,apiusername, tpam address,command. " << endl;
		cout << "Only " << (argc-1) << " arguments was specified" << endl;
        exit(1);
    }
	ApiClient client(argv[3],argv[1],argv[2]);
	string strCommand = argv[4];
	std::transform(strCommand.begin(), strCommand.end(), strCommand.begin(), ::tolower);
	
	string SystemName;
	try
	{
		client.connect();
		try
		{
			// cout << "Command " << strCommand << " will be executed with " << (argc-5) << " arguments" << endl;
			if (!strcmp(strCommand.c_str(),"addsystem"))
			{
				if (argc < 12)
				{
					cout << "Too few arguments. Please specify at least 10 arguments (<keyfile>, <apiusername>, <tpam address>, AddSystem <system name> <system address> <platform> <description> <timeout> <functional account name> <funcaccount password>)";
					exit(1);
				} else {
					SystemName = argv[5]; string Sysaddr = argv[6];
					string Platform = argv[7];
					string platformDescription = argv[8];
					int TimeOut = convertToInt(argv[9]);
					string FuncAccount = argv[10];
					string FuncAccPwd = argv[11];
					if (argc == 12){
						//Standart system
						addSystem(client, SystemName, Sysaddr, Platform, platformDescription, TimeOut, FuncAccount, FuncAccPwd);
					}
					else if (argc == 13){
					//Port number will be set, except Cicsco system. For Cisco passwordEnable property will be set
						string additionalParameter = argv[12];
						addSystem(client, SystemName, Sysaddr, Platform, platformDescription, TimeOut, FuncAccount,
							FuncAccPwd, additionalParameter);
					}
					else if (argc == 14){
					//It is assumed what we deals with Active Directory System or LDAP like system    
						string NetBIOSDomainName = argv[12];
						string DomainName = argv[13];
						addSystem(client, SystemName, Sysaddr, Platform, platformDescription, TimeOut, FuncAccount,
							FuncAccPwd, NetBIOSDomainName, DomainName);
					}
					else if (argc == 15){
					//It is assumed an Oracle System
						int PortNumber = convertToInt(argv[12]);
						string oracleType = argv[13];
						string oracleSIDSN = argv[14];
						addSystem(client, SystemName, Sysaddr, Platform, platformDescription, TimeOut, FuncAccount,
							FuncAccPwd, PortNumber, oracleType, oracleSIDSN);
					}
					else
					{
						cout << "Wrong number of arguments " << (argc-1) << " is specified" << endl; 
					}
				}
			}else if (!strcmp(strCommand.c_str(),"deletesystem"))
			{
				if (argc != 6)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> DeleteSystem <system name>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				deleteSystem(client, SystemName);
			}
			else if (!strcmp(strCommand.c_str(),"testsystem"))
			{
				if (argc != 6)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> TestSystem <system name>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				testSystem(client, SystemName);
			}
			else if (!strcmp(strCommand.c_str(),"checkpassword"))
			{
				if (argc == 7)
				{
					SystemName = argv[5];
					string AccountName = argv[6];
					checkPassword(client, SystemName, AccountName);
				}
				else if (argc == 8)
				{
					CommandTimeOut = convertToInt(argv[7]);
					SystemName = argv[5];
					string AccountName = argv[6];
					checkPassword(client, SystemName, AccountName);
				}
				else
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> CheckPassword <system name> <account> [<CommandTimeout>]" << endl;
					exit(1);
				}
			}
			else if (!strcmp(strCommand.c_str(),"resetpassword"))
			{
				if(argc == 7)
				{
					SystemName = argv[5];
					string AccountName = argv[6];
					resetPassword(client, SystemName, AccountName);
				}
				else if (argc ==8)
				{
					CommandTimeOut = convertToInt(argv[7]);
					SystemName = argv[5];
					string AccountName = argv[6];
					resetPassword(client, SystemName, AccountName);
				}
				else
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> ResetPassword <system name> <account>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				resetPassword(client, SystemName, AccountName);
			}
			else if (!strcmp(strCommand.c_str(),"addaccount"))
			{
				if (argc != 8)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> AddAccount <system name> <accountName>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string AccountPwd = argv[7];
				addAccount(client,SystemName,AccountName,AccountPwd);
			}
			else if (!strcmp(strCommand.c_str(),"addppmaccount"))
			{
				if (argc != 10)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> AddPpmAccount <system name> <accountName> <pwdCheckProfile> <pwdChangeProfile>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string AccountPwd = argv[7];
				string pwdCheckProfile = argv[8];
				string pwdChangeProfile = argv[9];
				addAccount(client,SystemName,AccountName,AccountPwd,pwdCheckProfile,pwdChangeProfile);
			}
			else if (!strcmp(strCommand.c_str(),"removeaccount"))
			{
				if (argc != 7)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> DeleteAccount <system name> <accountName>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				deleteAccount(client,SystemName,AccountName);
			}
			else if (!strcmp(strCommand.c_str(),"setpassword"))
			{
				if (argc != 8)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> SetPassword <system name> <accountName> <value>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string NewPwd = argv[7];
				setAccountPassword(client,SystemName,AccountName,NewPwd);
			}
			else if (!strcmp(strCommand.c_str(),"setaccountdescription"))
			{
				if (argc != 8)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> SetAccountDescription <system name> <accountName> <value>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string NewDescription = argv[7];
				setAccountDescription(client,SystemName,AccountName,NewDescription);
			}
			else if (!strcmp(strCommand.c_str(),"setaccountdn"))
			{
				if (argc != 8)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> SetAccountDN <system name> <accountName> <accountDN>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string accntDN = argv[7];
				setAccountDN(client,SystemName,AccountName,accntDN);
			}
			else if (!strcmp(strCommand.c_str(),"setuserpermission"))
			{
				if (argc != 10)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> SetUserPermission <system name> <accountName> <PermissionPolicyName> <ADD/DROP> <User>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string PermType = argv[7];
				string AddOrDrop = argv[8];
				string UserName = argv[9];
				setUserPermission(client, SystemName,AccountName,PermType,AddOrDrop,UserName);
			}
			else if (!strcmp(strCommand.c_str(),"requestpassword"))
			{
				if (argc != 9)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> RequestPassword <system name> <accountName> <requestor> <requestor notes>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string AccountName = argv[6];
				string requestor = argv[7];
				string requestorNotes = argv[8];
				requestPassword(client, SystemName,AccountName,requestor,requestorNotes);
			}
			else if (!strcmp(strCommand.c_str(),"approvepassword"))
			{
				if (argc != 6)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> ApprovePassword <Request ID>" << endl;
					exit(1);
				}
				string RequestID = argv[5];
				approvePassword(client, RequestID);
			}
			else if (!strcmp(strCommand.c_str(),"retrievepassword"))
			{
				if (argc != 6)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> RetrievePassword <RequestID>" << endl;
					exit(1);
				}
				string RequestID = argv[5];
				//string AccountName = argv[6];
				retrievePassword(client, RequestID);
			}
			else if (!strcmp(strCommand.c_str(),"updatesystem"))
			{
				if (argc != 7)
				{
					cout << "Usage: " << argv[0] << " <keyfile> <apiusername> <tpam address> RetrievePassword <system name> <newIP>" << endl;
					exit(1);
				}
				SystemName = argv[5];
				string NewIP = argv[6];
				updateSystem(client, SystemName,NewIP);
			}
			else
			{
				cout << "Command " << strCommand << " is unknown" << endl; 
			}

		}
		catch (ValidationException& vex)
		{
			cout << "ValidationException: " << vex.toString() << endl;
		}
		catch (ParseException& pex)
		{
			cout << "ParseException: " << pex.toString() << endl;
		}
		
	// Call disconnect() on the ApiClient after commands have completed.
		client.disconnect();
	}
	catch (SshException& sshex)
	{
		cout << "SshException: " << sshex.toString() << endl;
	}
	catch (std::exception &cException)
	{
		cout << "Operation " << strCommand << " failed." << endl;
		cout << "Standard exception: " << cException.what() << endl;
	}
}

void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword)
{
		// Add a dummy system.
		AddSystemCommand asc(SystemName, SystemAddress, Platform);
		// Set some attributes of the system being added.
		asc.getSystem().setSystemAutoFl(Flag::FLAG_Y);
		asc.getSystem().setDescription(Description);
		asc.getSystem().setTimeout(TimeOut);
		asc.getSystem().setFunctionalAccount(FunctionalAccount);
		asc.getSystem().setFunctionalAcctCredentials(FuncAccPassword);
		asc.setCommandTimeout(CommandTimeOut);
		// Execute the operation on TPAM.
		client.sendCommand(asc);
		// Check the outcome of the operation.
		IDResult* idresult = asc.getResult();
		cout << "addSystem: rc = " << idresult->getReturnCode()
		<< " message = " << idresult->getMessage() << endl;
}
void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword, string& additionalParameter)
{
	// Add a dummy system.
	AddSystemCommand asc(SystemName, SystemAddress, Platform);
	// Set some attributes of the system being added.
	asc.getSystem().setSystemAutoFl(Flag::FLAG_Y);
	asc.getSystem().setDescription(Description);
	asc.getSystem().setTimeout(TimeOut);
	asc.getSystem().setFunctionalAccount(FunctionalAccount);
	asc.getSystem().setFunctionalAcctCredentials(FuncAccPassword);
	std::transform(SystemName.begin(), SystemName.end(), SystemName.begin(), ::tolower);
	//Find if system name contains cisco
	string cisco ("cisco");
	string nis ("nis");
	size_t found,found2;
	//size_t found2;
	found=SystemName.find(cisco);
	if (found!=string::npos)
	{
		//Set additional parameter as PassworEnable
		asc.getSystem().setEnablePassword(additionalParameter);
	}
	else
	{
		found2=SystemName.find(nis);	
		if (found2!=string::npos)
		{
			//Set additional parameter as DomainName
			asc.getSystem().setDomainName(additionalParameter);
		}
		else
		{
			//Additional parameter is a port number for other types of system
			int pn = convertToInt(additionalParameter);
			asc.getSystem().setPortNumber(pn);
		}


	}
	asc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(asc);
	// Check the outcome of the operation.
	IDResult* idresult = asc.getResult();
	cout << "addSystem: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}
void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword, string& dopParam1, string& dopParam2)
{
	// Add a dummy system.
	AddSystemCommand asc(SystemName, SystemAddress, Platform);
	// Set some attributes of the system being added.
	asc.getSystem().setSystemAutoFl(Flag::FLAG_Y);
	asc.getSystem().setDescription(Description);
	asc.getSystem().setTimeout(TimeOut);
	asc.getSystem().setFunctionalAccount(FunctionalAccount);
	asc.getSystem().setFunctionalAcctCredentials(FuncAccPassword);
	//Check platform
	std::transform(SystemName.begin(), SystemName.end(), SystemName.begin(), ::tolower);
	string ldap ("ldap");
	string novell ("novell");
	size_t found3,found4;
    found3=SystemName.find(ldap);	
	found4=SystemName.find(novell);
	if (found3!=string::npos || found4!=string::npos){
		//for Ldap or Novell systems
		asc.getSystem().setDomainName(dopParam1);
		asc.getSystem().setFunctionalAcctDN(dopParam2);
	}else{
		//for other systems
	    asc.getSystem().setNetBIOSName(dopParam1);
	    asc.getSystem().setDomainName(dopParam2);
	}
	asc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(asc);
	// Check the outcome of the operation.
	IDResult* idresult = asc.getResult();
	cout << "addSystem: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}
void addSystem(ApiClient& client,string& SystemName, string& SystemAddress,string& Platform, string& Description,
			   int TimeOut, string& FunctionalAccount, string& FuncAccPassword, int PortNumber, string& OracleType, string& OracleSIDSN)
{
	// Add a dummy system.
	AddSystemCommand asc(SystemName, SystemAddress, Platform);
	// Set some attributes of the system being added.
	asc.getSystem().setSystemAutoFl(Flag::FLAG_Y);
	asc.getSystem().setDescription(Description);
	asc.getSystem().setTimeout(TimeOut);
	asc.getSystem().setFunctionalAccount(FunctionalAccount);
	asc.getSystem().setFunctionalAcctCredentials(FuncAccPassword);
	asc.getSystem().setPortNumber(PortNumber);
	asc.getSystem().setOracleType(OracleType);
	asc.getSystem().setOracleSIDSN(OracleSIDSN);
	asc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(asc);
	// Check the outcome of the operation.
	IDResult* idresult = asc.getResult();
	cout << "addSystem: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}
void deleteSystem(ApiClient& client, string& SysName)
{
	// Delete the system.
	DeleteSystemCommand dsc(SysName);
	dsc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(dsc);
	// Check the outcome of the operation.
	Result* result = dsc.getResult();
	cout << "deleteSystem: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}
void testSystem(ApiClient& client, string& SysName)
{
	// Test the system.
	TestSystemCommand tsc(SysName);
	tsc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(tsc);
	// Check the outcome of the operation.
	Result* result = tsc.getResult();
	cout << "testSystem: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}
void checkPassword(ApiClient& client, string& SystemName, string& AccountName)
{
	CheckPasswordCommand cpc(SystemName,AccountName);
	cpc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(cpc);
	// Check the outcome of the operation.
	Result* result = cpc.getResult();
	cout << "checkPassword: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}

void addAccount(ApiClient& client, string& SystemName, string& AccntName, string& AccountPwd)
{
	// Add a dummy account.
	AddAccountCommand aac(SystemName, AccntName);
	aac.getAccount().setAccountAutoFl("Y");
	aac.getAccount().setPassword(AccountPwd);
	aac.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(aac);
	// Check the outcome of the operation.
	IDResult* idresult = aac.getResult();
	cout << "addAccount: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}

void addAccount(ApiClient& client, string& SystemName, string& AccountName, string& AccountPwd, string& pwdCheckProfile, string& pwdChangeProfile)
{
    // Add a dummy account.
	AddAccountCommand aac(SystemName, AccountName);
	aac.getAccount().setAccountAutoFl("Y");
	aac.getAccount().setPassword(AccountPwd);
	aac.getAccount().setPasswordCheckProfile(pwdCheckProfile);
    aac.getAccount().setPasswordChangeProfile(pwdChangeProfile);
	aac.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(aac);
	// Check the outcome of the operation.
	IDResult* idresult = aac.getResult();
	cout << "addAccount: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}

void resetPassword(ApiClient& client,string& SystemName,string& AccountName)
{
	ForceResetCommand rpc(SystemName,AccountName);
	rpc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(rpc);
	// Check the outcome of the operation.
	Result* result = rpc.getResult();
	cout << "resetPassword: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}

void deleteAccount(ApiClient& client,string& SystemName,string& AccountName)
{
	// Delete the account.
	DeleteAccountCommand dac(SystemName, AccountName);
	dac.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(dac);
	// Check the outcome of the operation.
	Result* result = dac.getResult();
	cout << "deleteAccount: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}

void setAccountPassword(ApiClient& client,string& SystemName,string& AccountName,string& NewPwd)
{
	UpdateAccountCommand uac(SystemName, AccountName);
	//new password value set
	uac.getAccount().setPassword(NewPwd);
	uac.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(uac);
	// Check the outcome of the operation.
	IDResult* idresult = uac.getResult();
	cout << "setAccountPassword: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}

void setAccountDescription(ApiClient& client,string& SystemName,string& AccountName,string& NewDescription)
{
	UpdateAccountCommand uac(SystemName, AccountName);
	//new password value set
	uac.getAccount().setDescription(NewDescription);
	uac.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(uac);
	// Check the outcome of the operation.
	IDResult* idresult = uac.getResult();
	cout << "setAccountDescription: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}

void setAccountDN(ApiClient& client,string& SystemName,string& AccountName,string& NewDN)
{
	UpdateAccountCommand uac(SystemName, AccountName);
	//new password value set
	uac.getAccount().setAccountDN(NewDN);
	uac.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(uac);
	// Check the outcome of the operation.
	IDResult* idresult = uac.getResult();
	cout << "setAccountDescription: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}

void setUserPermission(ApiClient& client,string& SystemName,string& AccountName,string& PermType,string& AddOrDrop,string& UserName)
{
	SetAccessPolicyCommand sapc(PermType,AddOrDrop);
	sapc.setSystemName(SystemName);
	sapc.setAccountName(AccountName);
	sapc.setUserName(UserName);
	sapc.setCommandTimeout(CommandTimeOut);
	client.sendCommand(sapc);
	// Check the outcome of the operation.
	Result* result = sapc.getResult();
	cout << "SetUserPermission: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}

void requestPassword(ApiClient& client,string& SystemName,string& AccountName,string& requestor,string& requestorNotes)
{
	AddPwdRequestCommand apwdreq(SystemName,AccountName,requestor,requestorNotes);
	apwdreq.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(apwdreq);
	// Check the outcome of the operation.
	IDResult* idresult = apwdreq.getResult();
	cout << idresult->getMessage() << endl;
}

void approvePassword(ApiClient& client, string& ReqID)
{
	ApproveCommand ac(ReqID,"ok");
	ac.setCommandTimeout(CommandTimeOut);
	client.sendCommand(ac);
	// Check the outcome of the operation.
	Result* result = ac.getResult();
	cout << "ApprovePassword: rc = " << result->getReturnCode()
	<< " message = " << result->getMessage() << endl;
}

void retrievePassword(ApiClient& client,string& RequestID)
{
	// Get the password for testsys/testacct.
	RetrieveCommand rc(RequestID);
	rc.setCommandTimeout(CommandTimeOut);
	// Execute the operation on TPAM.
	client.sendCommand(rc);
	Result* result = rc.getResult();
	if (result->getReturnCode() == 0)
	{
		cout << rc.getPassword() << endl;
	}
	else
	{
		cout << "Failed retrieving password: " << result->getMessage() << endl;
	}
}

void updateSystem(ApiClient& client,string& SystemName,string& newIP)
{
	UpdateSystemCommand usc(SystemName);
	usc.getSystem().setNetworkAddress(newIP);
	usc.setCommandTimeout(CommandTimeOut);
	client.sendCommand(usc);
	// Check the outcome of the operation.
	IDResult* idresult = usc.getResult();
	cout << "UpdateSystem: rc = " << idresult->getReturnCode()
	<< " message = " << idresult->getMessage() << endl;
}