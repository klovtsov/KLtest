var strPath = WScript.Arguments(0);
var bLogGen = 0x00000001;
var bLogFile = 0x00000001;


WSHShell = new ActiveXObject("WScript.Shell");
WSHShell.RegWrite( "HKCU\\Software\\BTT\\SNMP Trap\\szLogFileName", strPath, "REG_SZ");
WSHShell.RegWrite( "HKCU\\Software\\BTT\\SNMP Trap\\bLogGeneral", bLogGen, "REG_DWORD");
WSHShell.RegWrite( "HKCU\\Software\\BTT\\SNMP Trap\\bLog", bLogFile, "REG_DWORD");