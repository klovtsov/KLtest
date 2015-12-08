using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using eDMZ.ParApi;

namespace tpam_multibyte
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {

                if (args.Length != 4)
                {
                    String pbkeyfile = args[0];
                    String apiUser = args[1];
                    String tpamAddress = args[2];
                    String systemName = args[3];
                    String accountName = args[4];

                    String ResultPath = Directory.GetCurrentDirectory() + "\\net_output";
                    
                    String description;
                    
                    Console.WriteLine("Results will be saved in {0}.", ResultPath);

                    using (StreamWriter writer = new StreamWriter(ResultPath, true, Encoding.UTF8))
                    {
                        
                        List<byte[]> list = new List<byte[]>();
                        list.Add(new byte[] { 0xE4, 0xB6, 0xB0 });
                        list.Add(new byte[] { 0xE2, 0x82, 0xAC });
                        list.Add(new byte[] { 0xEB ,0xA4 ,0x97 });
                        list.Add(new byte[] { 0xEB ,0x82 ,0x88 });
                        list.Add(new byte[] { 0xEB ,0x82 ,0x88 ,0xEB ,0xA0 ,0xBF });
                        list.Add(new byte[] { 0xC2 ,0xA2 });
                        list.Add(new byte[] { 0xA2 ,0xC2 });
                        list.Add(new byte[] { 0xA2 ,0xA2 });
                        list.Add(new byte[] { 0xC2 ,0xC2 });
                        list.Add(new byte[] { 0xC0 });
                        list.Add(new byte[] { 0xC1 });
                        list.Add(new byte[] { 0xC2 ,0xC0 });
                        list.Add(new byte[] { 0xF0 ,0xA4 ,0xAD ,0xA2 });
                        list.Add(new byte[] { 0xAE });
                        list.Add(new byte[] { 0xAE ,0x6E ,0xB5 ,0x4B });
                        list.Add(new byte[] { 0xF0, 0xA4, 0xAD, 0xA2, 0xF0, 0xA4, 0xAD, 0xA2, 0x24, 0x3F, 0x31, 0x41, 0xEB, 0x82, 0x88, 0x3F, 0x3F, 0x31, 0xE2, 0x82, 0xAC, 0xC2, 0xA3 });


                        int tpamCmdTimeout = 120;

                        ApiClientWrapper client = new ApiClientWrapper(tpamAddress, pbkeyfile, apiUser);
                        client.connect();
                        client.setCommandTimeout(tpamCmdTimeout);

                        Account account = new Account();
                        account.systemName = systemName;
                        account.accountName = accountName;

                        AccountFilter filter = new AccountFilter();
                        filter.systemName = systemName;
                        filter.accountName = accountName;

                        Account[] accounts = null;

                        foreach (byte[] bytes in list)
                        {

                            description = Encoding.UTF8.GetString(bytes);

                            writer.WriteLine("Description: {0}", description);

                            account.description = description;

                            IDResult idresult = client.updateAccount(account);
                            
                            writer.WriteLine("UpdateAccount: rc = {0}, message = {1}",
                                                idresult.returnCode, idresult.message);


                            ListResult lr = client.listAccounts(filter, out accounts);

                            // Since we set filters for just one system/acct pair,
                            // there should be just 1 entry returned.
                            if ((lr.returnCode == 0) && (lr.rowCount == 1))
                            {
                                writer.WriteLine("listAccounts: Account: {0}, System: {1}, Description: {2}",
                                    accounts[0].accountName, accounts[0].systemName, accounts[0].description);

                                if (description == accounts[0].description)
                                {
                                    writer.WriteLine("Matched!");
                                }
                                else
                                {
                                    writer.WriteLine("Not matched!");
                                }

                            }
                            else
                            {
                                writer.WriteLine("Unexpected result for listAccounts: {0}", lr.message);
                            }

                            writer.WriteLine("==========================================");
                        }
                        
                        writer.Close();
                    }

                } else {
                    System.Console.WriteLine("Invalid arguments. Please, specify the arguments in the following order: publicKeyFile, APIUserName, TPAMAddress, systemName, accountName");
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
