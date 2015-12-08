import org.openqa.selenium.*
import org.openqa.selenium.firefox.*
import org.openqa.selenium.support.ui.Select
import java.util.concurrent.TimeUnit

driver = new FirefoxDriver()
		
try {

	TPAMaddress = args[0]

	SystemName = args[1]
	SystemPlatform = args[2]
	NetworkAddress = args[3]
	FuncAcc	= args[4]
	FuncAccPwd = args[5]

	libCom = new libCommon()
	libP = new libPPM()

	libCom.login(driver, TPAMaddress, "/tpam/", "Paradmin", "Admin4PAR")
	libP.addSystem(driver, TPAMaddress, SystemName, SystemPlatform, NetworkAddress, FuncAcc, FuncAccPwd)
	libP.deleteSystem(driver, TPAMaddress, SystemName)

}
catch (e) {
    println("Catched: something went wrong: " + e);
}

driver.quit()

println("Finished.");


