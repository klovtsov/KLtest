using System;
using System.Text;
using System.IO;
using eDMZ.ParApi;

namespace SshKeyTester
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                //Connect to TPAM
                String pbkeyfile = args[0];
                String apiUser = args[1];
                String tpamAddress = args[2];
                ApiClientWrapper client = new ApiClientWrapper(tpamAddress, pbkeyfile, apiUser);
                //set command timeout
                int tpamCmdTimeout = int.Parse(args[3]);
                client.connect();
                client.setCommandTimeout(tpamCmdTimeout);
                
                //Set key parameters 
                SshKeyParms keyParameters = new SshKeyParms();
                if (args[4] == "Standard")
                {
                    keyParameters.standardKey = args[5]; //key name (id_dsa by default)
                }
                //Try to retrieve specific key, if args.length = 7 - system, if 8 - account
                else
                {
                    keyParameters.systemName = args[4];
                    if (args.Length == 8)
                    {
                        keyParameters.accountName = args[5];
                    }
                    //Should key be regenerated?
                    if (args[args.Length-2] == "Y")
                    {
                        keyParameters.regenerate = Flag.Y;
                    }
                }
                
                //Which key type is required: OpenSSH or SecSSH. Also it defines output file name (.pub or .export)
                string keyFormat = args[args.Length - 1];
                string resFileName = ".\\Scripts\\keys\\id_dsa.pub";
                switch (keyFormat)
                {
                    case "OpenSSH":
                        keyParameters.keyFormat = SshKeyParms.KeyFormat.OPENSSH;
                        break;
                    case "SecSSH":
                        keyParameters.keyFormat = SshKeyParms.KeyFormat.SECSSH;
                        resFileName = ".\\Scripts\\keys\\id_dsa.export";
                        break;
                    default:
                        System.Console.WriteLine("Wrong value of key format {0}, it should be OpenSSH or SecSSH", keyFormat);
                        break;
                }
                
                //Retrieve a key and store it to file.
                Result result = client.sshKey(keyParameters);
                if (result.returnCode == 0)
                {
                    // If returnCode indicates success, the message is the key. Save it to file.
                    Console.WriteLine("Key was retrieved successfully.");
                    SaveResultToFile(result.message, resFileName);
                }
                else
                {
                    // If returnCode indicates failure,
                    // the message is an actual message.
                    Console.WriteLine("Failed retrieving system specific key: {0}", result.message);
                }
                //Disconnected...
                client.disconnect();
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
        
        //Method which saves key to file.
        private static void SaveResultToFile(string p, string fileName)
        {
            //Check if file exists, if yes it will be deleted
            if (File.Exists(fileName))
            {
                File.Delete(fileName);
                Console.WriteLine("Old file {0} was deleted.", fileName);
            }
            //Save key to id_dsa.pub file in the .\keys directory
            StreamWriter writer = File.CreateText(fileName);
            writer.Write(p);
            writer.Close();
        }
    }
}
