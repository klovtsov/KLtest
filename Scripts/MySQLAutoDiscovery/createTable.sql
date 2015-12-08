CREATE TABLE PREFIXPLACEHOLDERadtest.systems (	UniqueSystemID varchar(1000) PRIMARY KEY NOT NULL,	SystemName varchar(30) NOT NULL,	NetworkAddress varchar(50) NOT NULL,	PrimaryEmail varchar(255) NULL,	PlatformName varchar(71) NULL,	DfltChangeFrequency varchar(50) NULL,	ReleaseDuration smallint NULL, 	AutoPasswordMgmt char(1) NOT NULL,	FunctionalAccount varchar(30) NULL,	AccountCredential varchar(128) NULL,	UseSpecificDSSKey char(1) NULL,	ChangeTime varchar(50) NULL,	PasswordRuleName varchar(30) NULL,	PortNumber int NULL,	EnablePassword varchar(30) NULL,	AlternateIPAddress varchar(50) NULL,	Description varchar(255) NULL,	DomainAccount varchar(100) NULL,	ServerOS varchar(20) NULL,	LineDefinition varchar(255) NULL,	Timeout smallint NULL,	DomainName varchar(255) NULL,	ParentSystemName varchar(50) NULL,	OracleType varchar(7) NULL,	OracleSIDSN varchar(50) NULL,	CheckFlag varchar(50) NULL,	ResetFlag varchar(50) NULL,	ReleaseChangeFlag varchar(50) NULL,	NetBIOSDomainName varchar(21) NULL,	NonPrivFunctionalAccountFlag char(1) NULL,	UseSSLFlag char(1) NULL,	AllowFunctionalAccounttoberequestedFlag char(1) NULL,	EscalationTime int NULL,	EscalationEmail varchar(255) NULL,	MaxReleaseDuration smallint NULL,	PSMOnlyFlag char(1) NULL,	PlatformSpecificValue varchar(255) NULL,	RequireTicketforRequest char(1) NULL,	TicketSystem varchar(30) NULL,	RequireTicketforISARetrieve char(1) NULL,	RequireTicketforCLIRetrieve char(1) NULL,	RequireTicketforAPIRetrieve char(1) NULL,	TicketNotificationEmail varchar(255) NULL,	LocationCustom1 varchar(255) NULL,	LocationCustom2 varchar(255) NULL,	LocationCustom3 varchar(255) NULL,	LocationCustom4 varchar(255) NULL,	LocationCustom5 varchar(255) NULL,	LocationCustom6 varchar(255) NULL,	AllowISADuration char(1) NULL,	UseSSHFlag char(1) NULL,	SSHAccount varchar(30) NULL,	SSHPort int NULL,	SSHKey varchar(40) NULL,	TemplateSystemName varchar(30) NULL,	AccountDiscoveryProfile varchar(100) NULL,	AccountDiscoveryExcludeList varchar(1000) NULL,	AccountDiscoveryTimeout int NULL,	PSMDPAAffinity varchar (2000) NULL,	PPMDPAAffinity varchar (2000) NULL,	PasswordCheckProfile varchar (100) NULL,	PasswordChangeProfile varchar (100) NULL,	ProfileCertificateType char(1) NULL,	ProfileNotificationThumbprint varchar (80) NULL,	ProfileCertificatePassword varchar (128) NULL,		FuncAcctDN varchar (2000) NULL, 	RequireTicketforPSMRequest char (1) NULL	)