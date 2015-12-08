import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

System.setProperty("webdriver.firefox.profile", "TPAM") 
driver = new FirefoxDriver()

def CheckNonAdminInterface (driver, TPAM) {

    TPAMaddr = "https://" + TPAM + "/tpam/main.asp" 
    driver.get(TPAMaddr)
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS)
	List<String> menuList = Arrays.asList("Add System", "Add System Template", "Manage Systems", "Deleted Systems", "List Systems", "Add Account", "Manage Accounts", "Deleted Accounts", "List Accounts", "List PSM Accounts", "Add Collection", "Manage Collections", "List Collections", "Add File", "Manage Files", "List Files", "Manage Passwords", "Add Synchronized Password", "Manage Synchronized Passwords", "Add UserID", "Add User Template", "Manage UserIDs", "List UserIDs", "Add Group", "Manage Groups", "List Groups", "Import UserIDs", "Import Systems", "Import Accounts", "Import/Update Collections", "Import/Update Groups", "Add/Drop Collection Members", "Add/Drop Group Members", "Update UserIDs", "Update Systems", "Update Accounts", "Update PSM Accounts", "Update Permissions", "Update Cache Server Permissions", "LDAP Directory", "Generic", "Access Policies", "Application Graphs", "Manage Cache Servers", "Manage Client Hosts", "Manage Trusted Roots", "Manage CS Permissions", "List CS Permissions", "Command Management", "Custom Platforms", "Jump Box", "DPAs", "Ping", "Nslookup", "TraceRoute", "TelnetTest", "Show Routes", "Profile Management", "Rebuild Assigned Policies", "Session Logs", "Manage Sessions", "Archive Settings", "Archive Servers", "Archive Log", "Add TPAM CLI ID", "Manage TPAM CLI IDs", "Retrieve Password", "Retrieve File", "Add Request", "Manage Requests", "Password Request", "File Request", "Session Request", "Password Releases", "PSM Session", "Activity Report", "ISA User Activity", "Approver User Activity", "Requestor User Activity", "PSM Accounts Inventory", "Password Aging Inventory", "File Aging Inventory", "Release-Reset Reconcile", "User Entitlement", "Failed Logins", "Password Update Activity", "Password Update Schedule", "Password Testing Activity", "Password Test Queue", "Expired Passwords", "Passwords Currently In-Use", "Password Requests", "Auto Approved Releases", "Auto Approved File Releases", "Password Release Activity", "File Release Activity", "Windows Domain Account Dependencies", "Auto Approved Sessions", "PSM Session Activity", "PSM Session Requests", "Report Subscriptions", "Browse Stored Reports", "Data Extract Schedules")

		for(i in 0 .. menuList.size()-1) {
		locator = menuList.get(i)
		if (driver.getPageSource().contains(locator)) {println(locator +" exists.")   }
		}

}
	
println("Ready.GO.")
	
try {
    TPAMaddress = args[0]
	UserName = args[1]
	UserPwd = args[2]

    libCom = new libCommon()
                
    libCom.login(driver, TPAMaddress, "/tpam/", UserName, UserPwd)
	
	CheckNonAdminInterface(driver, TPAMaddress)
	
}
catch (e) {
    println("Catched: something went wrong: " + e)
}

driver.quit()

println("Finished.")


