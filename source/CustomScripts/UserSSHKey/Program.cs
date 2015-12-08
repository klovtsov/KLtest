using System;
using System.Collections.Generic;
using System.Text;
using eDMZ.ParApi;

namespace tpamapinetusesshkey
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                String keyFile = "";
                String apiUser = ""; 
                String tpamAddress = "";
                String cmdlineparname, cmdlineparvalue;
                String[] cmdlinepar;

                UserSshKeyParms parms = new UserSshKeyParms();

                if (args.Length >= 2)
                {

                    for (int i = 0; i < args.Length; i++)
                    {
                        cmdlinepar= args[i].Split('=');
                        cmdlineparname = cmdlinepar[0];
                        cmdlineparvalue = cmdlinepar[1];

                        switch (cmdlineparname)
                        {
                            case "keyFile":
                                keyFile= cmdlineparvalue;
                                break;
                            case "apiUser":
                                apiUser = cmdlineparvalue;
                                break;
                            case "tpamAddress":
                                tpamAddress = cmdlineparvalue;
                                break;
                            case "userName":
                                parms.username = cmdlineparvalue;
                                break;
                            case "keyType":
                                parms.keyType = cmdlineparvalue;
                                break;
                            case "passphrase":
                                parms.passphrase = cmdlineparvalue;
                                break;
                            case "regenerate":
                                if (cmdlineparvalue == "Y") {
                                   parms.regenerate = Flag.Y;
                                } else {
                                    parms.regenerate = Flag.N;
                                }
                                break;
                            default:
                                Console.WriteLine("Invalid option: {0}", args[i]);
                                break;
                        }
                    }


                    int tpamCmdTimeout = 120;

                    ApiClientWrapper client = new ApiClientWrapper(tpamAddress, keyFile, apiUser);
                    client.connect();
                    client.setCommandTimeout(tpamCmdTimeout);

                    Result result = client.userSshKey(parms);

                    //Console.WriteLine("userSSHKey: rc = {0}, message = {1}", result.returnCode, result.message);

                    if (result.returnCode == 0)
                    {
                        Console.WriteLine(result.message);
                    }
                    else
                    {
                        Console.WriteLine("userSSHKey: rc = {0}, message = {1}", result.returnCode, result.message);
                    }
                    

                } else {
                    System.Console.WriteLine("Invalid arguments. Please, specify the arguments as follows: keyFile=value apiUser=value tpamAddress=value userName=value keyType=value passphrase=value regenerate=value");
                    

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
        
    }
}
