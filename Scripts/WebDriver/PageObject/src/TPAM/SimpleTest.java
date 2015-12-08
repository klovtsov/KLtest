package TPAM;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.containsString;


import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;
import java.util.Properties;

public class SimpleTest {

	private FirefoxDriver driver;
		
	public String cfgTPAMHost;
	public String cfgPrefix;
	
	public String cfgSystemPlatform;
	public String cfgSystemIP;
	public String cfgSystemFuncAcct;
	public String cfgSystemFuncAcctPwd;
	public String cfgSystemName;

	@Before
	public void prepareEnvironment() {

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Properties prop = Helper.readConfiguration("testrunner.config");

		cfgTPAMHost = prop.getProperty("TPAMHost");
		cfgPrefix = prop.getProperty("Prefix");
				
		cfgSystemPlatform = prop.getProperty("SystemPlatform");
		cfgSystemIP = prop.getProperty("SystemIP");
		cfgSystemFuncAcct = prop.getProperty("SystemFuncAcct");
		cfgSystemFuncAcctPwd = prop.getProperty("SystemFuncAcctPwd");
		
		cfgSystemName = cfgPrefix + "_" + cfgSystemPlatform;

		System.out.println("prepareEnvironment: finished.");

	}

	@After
	public void clearEnvironment() {
		driver.close();
		driver.quit();
		System.out.println("clearEnvironment: finished.");
	}

	
	@Test public void
	testManagedSystems() {

		MainPage homePage = MainPage.navigateToMainPage(driver, cfgTPAMHost)
							.login("Paradmin", "Admin4PAR");

	
		assertTrue (MainPage.navigateToManagedSystems(driver, cfgTPAMHost)
					.addSystem(cfgSystemName, cfgSystemPlatform, cfgSystemIP, cfgSystemFuncAcct, cfgSystemFuncAcctPwd)
					);
		
		assertTrue (MainPage.navigateToManagedSystems(driver, cfgTPAMHost)
					.deleteSystem(cfgSystemName)
					);
		
		System.out.println("testManagedSystems: finished.");					
	}
}
