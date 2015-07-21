
INSERT INTO [KL_generic_AD].[dbo].[systems_new]
           ([UniqueSystemID]
           ,[SystemName]
           ,[NetworkAddress]
           ,[PrimaryEmail]
           ,[Platform Name]
           ,[Dflt Change Frequency]
           ,[Release Duration]
           ,[Auto-Password Mgmt]
           ,[Functional Account]
           ,[Account Credential]
           ,[Use Specific DSS Key]
           ,[Change Time]
           ,[Password Rule Name]
           ,[Port Number]
           ,[Enable Password]
           ,[Alternate IP Address]
           ,[Description]
           ,[Domain Account]
           ,[Server O S]
           ,[Line Definition]
           ,[Timeout]
           ,[Domain Name]
           ,[Parent System Name]
           ,[Oracle Type]
           ,[Oracle SIDSN]
           ,[Check Flag]
           ,[Reset Flag]
           ,[Release Change Flag]
           ,[NetBIOS Domain Name]
           ,[Non-Priv Functional Account Flag]
           ,[Use SSL Flag]
           ,[Allow Functional Account to be requested Flag]
           ,[Escalation Time]
           ,[Escalation Email]
           ,[Max Release Duration]
           ,[PSM Only Flag]
           ,[Platform Specific Value]
           ,[Require Ticket for Request]
           ,[Ticket System]
           ,[Require Ticket for ISA Retrieve]
           ,[Require Ticket for CLI Retrieve]
           ,[Require Ticket for API Retrieve]
           ,[Ticket Notification Email]
           ,[Location Custom 1]
           ,[Location Custom 2]
           ,[Location Custom 3]
           ,[Location Custom 4]
           ,[Location Custom 5]
           ,[Location Custom 6]
           ,[Allow ISA Duration]
           ,[Use SSH Flag]
           ,[SSH Account]
           ,[SSH Port]
           ,[SSH Key]
           ,[Template System Name]
           ,[Account Discovery Profile]
           ,[Account Discovery Exclude List]
           ,[Account Discovery Timeout]
           ,[PSM DPA Affinity]
           ,[PPM DPA Affinity]
           ,[Password Check Profile]
           ,[Password Change Profile]
			,[Profile Certificate Type]
			,[Profile Notification Thumbprint]
			,[Profile Certificate Password]
           ,[Func Acct DN]
           ,[Require Ticket for PSM Request])
     VALUES
('8056F35C-1E60-49EC-898C-7FF73A0DB9E8','klbatchsys9','192.168.99.9','klbatchsys9@tpam.spb.qsft','CyberGuard','0',180,'Y','klbatchfcctacct9','DSS','N','20:00:00','Default Password Rule',55,'','','desc1','','','',61,'','','','','Y','Y','Y','','N','N','Y',1,'klsysesc1@tpam.spb.qsft',1440,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('B509ADC4-39F5-403C-9EC1-F8158494D526','klbatchsys8','192.168.99.8','klbatchsys8@tpam.spb.qsft','Cisco Router (TEL)','0',180,'Y','klbatchfcctacct8','','N','19:00:00','Default Password Rule',55,'','','desc2','','','line vty 0 4',62,'','','','','Y','Y','Y','','N','N','Y',2,'klsysesc2@tpam.spb.qsft',2880,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('87387C8E-8694-4871-ACAC-A94DA5B70374','klbatchsys7','192.168.99.7','klbatchsys7@tpam.spb.qsft','Cisco Router (SSH)','0',180,'Y','klbatchfcctacct7','','N','18:00:00','Default Password Rule',55,'','','desc3','','','',63,'','','','','Y','Y','Y','','N','N','Y',3,'klsysesc3@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('D522D18A-A2DF-4555-BE43-3428CBE10D1C','klbatchsys62','192.168.99.62','klbatchsys62@tpam.spb.qsft','Windows Desktop','0',180,'Y','klbatchfcctacct8','','N','19:00:00','Default Password Rule',NULL,'','','desc4','','','',64,'','','','','Y','Y','Y','','N','N','Y',4,'klsysesc4@tpam.spb.qsft',5760,'N','wwwwwww','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('F2C9BFE6-3500-4B56-92EB-E644E86EEB82','klbatchsys61','192.168.99.61','klbatchsys61@tpam.spb.qsft','Windows Active Dir','0',180,'Y','klbatchfcctacct7','','N','18:00:00','Default Password Rule',NULL,'','','desc5','','','',65,'mydomain.mc.co','','','','Y','Y','Y','MYDOMAIN','Y','N','Y',5,'klsysesc5@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('3B6E6D35-D97A-4DE0-9286-0A63701DAB8A','klbatchsys60','192.168.99.60','klbatchsys60@tpam.spb.qsft','Windows','0',180,'Y','klbatchfcctacct60','','N','17:00:00','Default Password Rule',NULL,'','','desc6','','','',66,'','','','','Y','Y','Y','','N','N','Y',6,'klsysesc6@tpam.spb.qsft',7200,'N','computername','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('C86D9F29-38D2-4021-8878-3ADD9E7E35F4','klbatchsys6','192.168.99.6','klbatchsys6@tpam.spb.qsft','Cisco PIX','0',180,'Y','klbatchfcctacct6','','N','17:00:00','Default Password Rule',55,'','','desc7','','','',67,'','','','','Y','Y','Y','','N','N','Y',7,'klsysesc7@tpam.spb.qsft',8640,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('6B584233-CE64-4527-B450-0BACF8B3395E','klbatchsys58','192.168.99.58','klbatchsys58@tpam.spb.qsft','Unixware 7.X','0',180,'Y','klbatchfcctacct4','DSS','N','15:00:00','Default Password Rule',NULL,'','','desc9','','','',69,'','','','','Y','Y','Y','','N','N','Y',9,'klsysesc9@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('B9AB2C12-D06B-4CE9-A272-5A4C9A02F4A6','klbatchsys57','192.168.99.57','klbatchsys57@tpam.spb.qsft','UnixWare','0',120,'Y','klbatchfcctacct3','','N','14:00:00','Default Password Rule',NULL,'','','desc1','','','',61,'','','','','Y','Y','N','','N','N','Y',1,'klsysesc1@tpam.spb.qsft',1440,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('2F0A8E38-CF4A-4857-8E74-A15235681DDE','klbatchsys56','192.168.99.56','klbatchsys56@tpam.spb.qsft','Tru64 Untrusted','0',180,'Y','klbatchfcctacct2','DSS','N','13:00:00','Default Password Rule',NULL,'','','desc2','','','',62,'','','','','Y','Y','Y','','N','N','Y',2,'klsysesc2@tpam.spb.qsft',2880,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('6F79F0F3-2698-4872-9955-C261216E6622','klbatchsys55','192.168.99.55','klbatchsys55@tpam.spb.qsft','Tru64 Enhanced Sec.','0',180,'Y','klbatchfcctacct1','','N','12:00:00','Default Password Rule',NULL,'','','desc3','','','',63,'','','','','Y','Y','Y','','N','N','Y',3,'klsysesc3@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','N',NULL,'','','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('B1E2B70C-5AE2-4C2B-9C3A-FD0A08A9F726','klbatchsys54','192.168.99.54','klbatchsys54@tpam.spb.qsft','Teradata','0',180,'Y','klbatchfcctacct9','','N','20:00:00','Default Password Rule',66,'','','desc4','','','',64,'','','','','Y','Y','Y','','N','N','Y',4,'klsysesc4@tpam.spb.qsft',5760,'N','','N','','N','N','N','','','','','','','','Y','Y','sshuser',67,'Standard','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('88EF8758-C669-481E-BF74-A8A3DA945CA2','klbatchsys53','192.168.99.53','klbatchsys53@tpam.spb.qsft','Sybase','0',180,'Y','klbatchfcctacct8','','N','19:00:00','Default Password Rule',NULL,'','','desc5','','','',65,'','','','','Y','Y','Y','','N','Y','Y',5,'klsysesc5@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('EAC8C36C-7822-4923-A10D-40D505EB5817','klbatchsys52','192.168.99.53','klbatchsys53@tpam.spb.qsft','Stratus VOS','0',180,'Y','klbatchfcctacct7','','N','18:00:00','Default Password Rule',NULL,'','','desc6','','','',66,'','','','','Y','Y','Y','','N','N','Y',6,'klsysesc6@tpam.spb.qsft',7200,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('5CBAF34E-8BF6-4059-9E41-48062E470561','klbatchsys51','192.168.99.51','klbatchsys51@tpam.spb.qsft','Solaris','0',180,'Y','klbatchfcctacct6','DSS','N','17:00:00','Default Password Rule',NULL,'','','desc7','','','',67,'','','','','Y','Y','Y','','N','N','Y',7,'klsysesc7@tpam.spb.qsft',8640,'N','delpref','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('A8BB637F-D060-4E79-9E2C-9C993CDBE3BF','klbatchsys50','192.168.99.50','klbatchsys50@tpam.spb.qsft','SCO','0',180,'Y','klbatchfcctacct5','','N','16:00:00','Default Password Rule',NULL,'','','desc8','','','',68,'','','','','Y','Y','Y','','N','N','Y',8,'klsysesc8@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('7B5D184B-1B59-4DAC-89A4-055C8321D5E5','klbatchsys5','192.168.99.5','klbatchsys5@tpam.spb.qsft','Cisco CATOS','0',180,'Y','klbatchfcctacct5','','N','16:00:00','Default Password Rule',55,'','','desc9','','','',69,'','','','','Y','Y','Y','','N','N','Y',9,'klsysesc9@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('DB2D4592-AF4D-4B6F-8766-106E5FD8CEAE','klbatchsys49','192.168.99.49','klbatchsys49@tpam.spb.qsft','ProxySG','0',180,'Y','klbatchfcctacct49','','N','15:00:00','Default Password Rule',NULL,'','','desc1','','','',61,'','','','','Y','Y','Y','','N','N','Y',1,'klsysesc1@tpam.spb.qsft',1440,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('483683CA-7BA7-4A2F-B62B-9620E69B79F3','klbatchsys48','192.168.99.48','klbatchsys48@tpam.spb.qsft','PowerPassword','0',180,'N','klbatchfcctacct3','','N','14:00:00','Default Password Rule',NULL,'','','desc2','','','',62,'','','','','Y','Y','Y','','N','N','Y',2,'klsysesc2@tpam.spb.qsft',2880,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','','','','','','','N'),
('62EC4A4A-B048-4A7C-9A97-AB813689B986','klbatchsys47','192.168.99.47','klbatchsys47@tpam.spb.qsft','POS 4690','0',180,'Y','klbatchfcctacct2','','N','13:00:00','Default Password Rule',NULL,'','','desc3','','','',63,'','','','','Y','Y','Y','','N','N','Y',3,'klsysesc3@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('C5B23714-CF3E-4BC0-B23B-AD21B54B9A2D','klbatchsys46','192.168.99.46','klbatchsys46@tpam.spb.qsft','PAN-OS','0',180,'Y','klbatchfcctacct1','DSS','N','12:00:00','Default Password Rule',NULL,'','','desc4','','','',64,'','','','','Y','Y','Y','','N','N','Y',4,'klsysesc4@tpam.spb.qsft',5760,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('5C78FD1E-4E36-45D9-9A63-888291961456','klbatchsys45','192.168.99.45','klbatchsys45@tpam.spb.qsft','Other','0',240,'N','klbatchfcctacct9','','N','20:00:00','Default Password Rule',NULL,'','','desc5','','','',65,'','','','','Y','Y','Y','','N','N','Y',5,'klsysesc5@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','','','','','','','N'),
('F9B918A6-DB5C-4DC3-872A-E6A7885209B9','klbatchsys44','192.168.99.44','klbatchsys44@tpam.spb.qsft','Oracle','4',180,'Y','klbatchfcctacct44','','N','19:00:00','Default Password Rule',32,'','','desc6','','','',66,'','','Service','servisename','Y','Y','Y','','N','N','Y',6,'klsysesc6@tpam.spb.qsft',7200,'N','','N','','N','N','N','','','','','','','','N','Y','sshroot',33,'Standard','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('51DAC55B-DCAF-4A38-8EF0-0236536A4AC5','klbatchsys43','192.168.99.43','klbatchsys43@tpam.spb.qsft','OpenVMS','0',180,'Y','klbatchfcctacct7','','N','18:00:00','Default Password Rule',NULL,'','','desc7','','','',67,'','','','','Y','Y','Y','','N','N','Y',7,'klsysesc7@tpam.spb.qsft',8640,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('2CB8F93E-D641-44CE-A0CD-F09CB2047E09','klbatchsys42','192.168.99.42','klbatchsys42@tpam.spb.qsft','Novell NDS','0',180,'Y','klbatchfcctacct6','','N','17:00:00','Default Password Rule',44,'','','desc8','','','',68,'','','','','Y','Y','Y','','N','N','Y',8,'klsysesc8@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','"cn=Manager,dc=kllinux,dc=spb,dc=qsft"','N'),
('089EAE62-4D9A-465B-9D63-0F387787633A','klbatchsys41','192.168.99.41','klbatchsys41@tpam.spb.qsft','Nokia-IPSO','0',180,'Y','klbatchfcctacct5','','N','16:00:00','Default Password Rule',NULL,'','','desc9','','','',69,'','','','','Y','Y','Y','','N','N','Y',9,'klsysesc9@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('D5A0C82C-5F15-4693-BF69-C0EE52F0EE4E','klbatchsys40','192.168.99.40','klbatchsys40@tpam.spb.qsft','NIS Plus','0',180,'Y','klbatchfcctacct4','DSS','N','15:00:00','Default Password Rule',NULL,'','','desc1','','','',61,'mydomain.mc.com','','','','Y','Y','Y','','N','N','Y',1,'klsysesc1@tpam.spb.qsft',1440,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('89504B54-A82C-4EC6-A1D6-05575C56AE74','klbatchsys4','192.168.99.4','klbatchsys4@tpam.spb.qsft','CheckPoint SP','0',180,'Y','klbatchfcctacct4','','N','15:00:00','Default Password Rule',55,'','','desc2','','','',62,'','','','','Y','Y','Y','','N','N','Y',2,'klsysesc2@tpam.spb.qsft',2880,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('6CABFD96-01FD-4B5B-9253-043A93E31EB7','klbatchsys39','192.168.99.39','klbatchsys39@tpam.spb.qsft','Netscreen','0',180,'Y','klbatchfcctacct3','','N','14:00:00','Default Password Rule',NULL,'','','desc3','','','',63,'','','','','Y','Y','Y','','N','N','Y',3,'klsysesc3@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('7C55632F-AF13-44CC-8988-13DD7143AE97','klbatchsys38','192.168.99.38','klbatchsys38@tpam.spb.qsft','MySQL','0',180,'Y','klbatchfcctacct2','','N','13:00:00','Default Password Rule',NULL,'','','desc4','','','',64,'','','','','Y','Y','Y','','N','Y','Y',4,'klsysesc4@tpam.spb.qsft',5760,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('8D63408A-1941-457E-B868-C43DB56FEAEB','klbatchsys37','192.168.99.37','klbatchsys37@tpam.spb.qsft','MS SQL Server','0',180,'Y','klbatchfcctacct1','','N','12:00:00','Default Password Rule',NULL,'','','desc5','','','',65,'','','','','Y','Y','Y','','N','N','Y',5,'klsysesc5@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','Y','root',33,'Specific','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('C4EBE527-9FFC-435F-9F5D-312E230C1132','klbatchsys36','192.168.99.36','klbatchsys36@tpam.spb.qsft','Mainframe LDAP TS','0',180,'Y','klbatchfcctacct9','','N','20:00:00','Default Password Rule',NULL,'','','desc6','','','',66,'','','','','Y','Y','Y','','N','N','Y',6,'klsysesc6@tpam.spb.qsft',7200,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('D22C718C-1709-44EE-A730-8E120A7AE0F2','klbatchsys35','192.168.99.35','klbatchsys35@tpam.spb.qsft','Mainframe LDAP RACF','0',180,'Y','klbatchfcctacct8','','N','19:00:00','Default Password Rule',35,'','','desc7','','','',67,'','','','','Y','Y','Y','','N','Y','Y',7,'klsysesc7@tpam.spb.qsft',8640,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('21E7407E-D5DC-4E78-B3B2-DE7CC63147EF','klbatchsys34','192.168.99.34','klbatchsys34@tpam.spb.qsft','Mainframe LDAP ACF2','0',180,'Y','klbatchfcctacct7','','N','18:00:00','Default Password Rule',NULL,'','','desc8','','','',68,'','','','','Y','Y','Y','','N','N','Y',8,'klsysesc8@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('1E696C02-EFC1-4EA9-8288-DF568846CD4E','klbatchsys33','192.168.99.33','klbatchsys33@tpam.spb.qsft','Mainframe (ACF2)','0',180,'Y','klbatchfcctacct6','','N','17:00:00','Default Password Rule',33,'','','desc9','','','',69,'','','','','Y','Y','Y','','N','N','Y',9,'klsysesc9@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('7682FBB9-011B-4309-8496-D5A624FFB5A0','klbatchsys32','192.168.99.32','klbatchsys32@tpam.spb.qsft','Mainframe','0',180,'Y','klbatchfcctacct32','','N','16:00:00','Default Password Rule',32,'','','desc1','','','custom command',61,'','','','','Y','Y','Y','','N','Y','Y',1,'klsysesc1@tpam.spb.qsft',1440,'N','WorkstationID','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('5308CF6D-17FD-4C45-B1B0-E8F569BA3CDF','klbatchsys30','192.168.99.30','klbatchsys30@tpam.spb.qsft','MacOSX 10.4','0',180,'Y','klbatchfcctacct30','','N','14:00:00','Default Password Rule',NULL,'','','desc3','','','',63,'','','','','Y','Y','Y','','N','N','Y',3,'klsysesc3@tpam.spb.qsft',4320,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_1','KL_PASS_CHANGE_1','','','','','N'),
('CAF590B2-6424-4159-B6CB-70BC42D73418','klbatchsys3','192.168.99.3','klbatchsys3@tpam.spb.qsft','BoKS','0',180,'Y','klbatchfcctacct3','','N','14:00:00','Default Password Rule',55,'','1.2.3.4','desc4','','FreeBSD','',64,'mydomain','','','','Y','Y','Y','','N','N','Y',4,'klsysesc4@tpam.spb.qsft',5760,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('E6220619-D9DB-4AED-8299-9E92D74A4AEE','klbatchsys59','192.168.99.59','klbatchsys59@tpam.spb.qsft','"VMware vSphere 4,5"','-1',180,'Y','klbatchfcctacct5','','N','16:00:00','Default Password Rule',99,'','','desc8','','','',68,'','','','','Y','Y','Y','','N','N','Y',8,'klsysesc8@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('682AF361-E1D3-47A6-B013-DD594F4D924F','klbatchsys31','192.168.99.31','klbatchsys31@tpam.spb.qsft','"MacOSX 10.5,10.6"','-2',210,'Y','klbatchfcctacct4','','N','15:00:00','Default Password Rule',NULL,'','','desc2','','','',62,'','','','','N','Y','Y','','N','N','Y',2,'klsysesc2@tpam.spb.qsft',2880,'N','','N','','N','N','N','','','','','','','','Y','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','','N'),
('511E35CB-9E8B-4100-973B-6191E12FB97C','klbatchsys101','10.30.38.252','KLapache@tpam.spb.qsft','LDAP','0',180,'Y','Manager','frujiE15','N','17:00:00','Default Password Rule',44,'','','Real OpenLDAP Server','','','',68,'','','','','Y','Y','Y','','N','N','Y',8,'klsysesc8@tpam.spb.qsft',10080,'N','','N','','N','N','N','','','','','','','','N','N','',NULL,'','','','','','','','KL_PASS_CHECK_2','KL_PASS_CHANGE_2','','','','"cn=Manager,dc=kllinux,dc=spb,dc=qsft"','N'),
('3D74C7E3-8B43-4AEF-9140-EF7D5DE93F5B','klbatchsys100','192.168.99.100','','Windows','',NULL,'Y','Admin','frujiE4','','','',NULL,'','','','','','',NULL,'','','','','','','','','','','',NULL,'',NULL,'','','','','','','','','','','','','','','','','',NULL,'','','','','','','','','','','','','',''),
('0A3984CB-9ABF-4F21-8E58-F4AB3EF15856','klbatchsys102','192.168.99.102','','','',NULL,'Y','root2','12345','','','',NULL,'','','','','','',NULL,'','','','','','','','','','','',NULL,'',NULL,'','','','','','','','','','','','','','','','','',NULL,'','','','','','','','','','','','','','')

