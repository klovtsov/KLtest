UPDATE [KL_generic_AD].[dbo].[systems_new]
   SET 
--[Func Acct DN] = 'cn=Master,dc=kllinux,dc=spb,dc=qsft'
--[Auto-Password Mgmt] = 'N'
--[Password Change Profile] = 'KL_PASS_CHANGE_2',
--[Domain Name] = 'kllinux.spb.qsft'
--[Password Check Profile] = 'KL_PASS_CHECK_2'
--[Max Release Duration] = 1
--[Oracle Type] = ''
--[Oracle SIDSN] = ''
--[Use SSH Flag] = 'N'  
    --[Port Number] = 389
--[Platform Name] = 'MacOSX 10.5,10.6'
--[Platform Specific Value] = 'DBA Privilege=SYSDBA'
      --[Require Ticket for PSM Request] = 'Y'
--[Require Ticket for Request] = 'Y'
--[Require Ticket for CLI Retrieve] = 'Y'
--[Profile Certificate Type] = 'N',
--[Profile Notification Thumbprint] = ''
--[Profile Certificate Type] = 'T'
[Profile Notification Thumbprint] = 'A29AE963A674BBB58EFE2DA1706FB75725066D01'
--[Account Credential] = 'grain41R'
--[Functional Account] = 'newfunc'
--[PSM DPA Affinity] = '',
--[PPM DPA Affinity] = ''
--[Allow Functional Account to be requested Flag] = 'N',
--[Account Discovery Profile] = ''
--[Account Discovery Exclude List] = 'Administrator;Admin'
--[Account Discovery Timeout] = 900
--[NetworkAddress] = '10.30.44.202'
WHERE SystemName like 'klbatchsys109'
--[Functional Account] = ''
--[Platform Name] = 'PowerPassword'

--SystemName like 'klbatchsys44'
 --WHERE SystemName in ('klbatchsys31', 'klbatchsys33', 'klbatchsys35', 'klbatchsys37', 'klbatchsys39')